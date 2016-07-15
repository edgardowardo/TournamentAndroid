package com.edgardoagno.tournamentandroid;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.edgardoagno.tournamentandroid.Models.ScheduleType;
import com.edgardoagno.tournamentandroid.Models.Team;
import com.edgardoagno.tournamentandroid.ViewModels.GroupSettingsViewModel;
import com.wefika.horizontalpicker.HorizontalPicker;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.OnTextChanged;
import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import io.realm.Realm;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by edgardoagno on 10/07/16.
 */
public class GroupSettingsActivity extends RealmBaseActivity {

    GroupSettingsViewModel _viewModel;
    private MenuItem _menuSaveItem;
    private Subscription _groupNameSubscription;
    private RealmChangeListener teamsListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_settings_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Add Group");

        Realm realm = Realm.getInstance(getRealmConfig());
        _viewModel = new GroupSettingsViewModel(realm);
        _viewModel.saveDefaultGroup();
        RealmResults<Team> teams = _viewModel._group.teams.where().findAll();
        final TeamRealmAdapter teamsRealmAdapter = new TeamRealmAdapter(this, teams, true, true, _viewModel);
        RealmRecyclerView realmRecyclerView = (RealmRecyclerView) findViewById(R.id.realm_recycler_view);
        realmRecyclerView.setAdapter(teamsRealmAdapter);

        //  Observers
        teamsListener = new RealmChangeListener() {
            @Override
            public void onChange(Object element) {
                teamsRealmAdapter.notifyDataSetChanged();
            }
        };
        teams.addChangeListener(teamsListener);

        // Rx Observers
        _groupNameSubscription = _viewModel
                ._groupNameEmitterSubject
                .asObservable()
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String value) {
                        boolean isEnabled = value.length() > 0;
                        _menuSaveItem.setEnabled(isEnabled);
                        _menuSaveItem.getIcon().setAlpha((isEnabled) ? 255 : 130);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        _groupNameSubscription.unsubscribe();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_group_settings, menu);
        _menuSaveItem = menu.getItem(0);
        _menuSaveItem.setEnabled(false);
        _menuSaveItem.getIcon().setAlpha(130);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_attach_default_group) {
            Long tournament_id = getIntent().getLongExtra("TOURNAMENT_ID", 0);
            _viewModel.attachDefaultGroup(tournament_id);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        _viewModel.deleteDefaultGroup();
    }

    public class TeamRealmAdapter extends RealmBasedRecyclerViewAdapter<Team, TeamRealmAdapter.ViewHolder> {

        private GroupSettingsViewModel viewModel;
        private static final int TYPE_HEADER = 0;
        private static final int TYPE_ITEM = 1;

        public class ViewHolder extends RealmViewHolder {
            public ViewHolder(FrameLayout container) {
                super(container);
            }
        }

        public class ItemViewHolder extends ViewHolder {
            public TextView groupTextView;
            public ItemViewHolder(FrameLayout container) {
                super(container);
                this.groupTextView = (TextView) container.findViewById(R.id.team_text_view);
            }
        }

        public class HeaderViewHolder extends ViewHolder implements  HorizontalPicker.OnItemSelected {

            @Bind(R.id.group_edit_text) EditText _groupNameEditText;
            @Bind(R.id.radio_round_robin) RadioButton _radioRoundRobin;
            @Bind(R.id.radio_american) RadioButton _radioAmerican;
            @Bind(R.id.radio_single) RadioButton _radioSingle;
            @Bind(R.id.radio_double) RadioButton _radioDouble;
            @Bind(R.id.picker_team_count) HorizontalPicker _pickerTeamCount;
            private GroupSettingsViewModel __viewModel;

            // Constructor

            public HeaderViewHolder(FrameLayout container, GroupSettingsViewModel viewModel) {
                super(container);
                this.__viewModel = viewModel;
                ButterKnife.bind(this, container);
                this._pickerTeamCount.setOnItemSelectedListener(this);
                CharSequence[] s = this.__viewModel.getAllowedTeamCounts();
                this._pickerTeamCount.setValues(s);

                CharSequence[] allowedTeamCounts = this.__viewModel._group.getScheduleType().getAllowedTeamCounts();
                String teamCount = Integer.toString(__viewModel._group.teamCount);
                int index = Arrays.asList(allowedTeamCounts).indexOf(teamCount);
                this._pickerTeamCount.setSelectedItem(index);
            }

            @OnTextChanged(R.id.group_edit_text)
            public void onGroupNameChanged() {
                String name = _groupNameEditText.getText().toString();
                this.__viewModel.setGroupName(name);
            }

            @OnClick({R.id.radio_round_robin, R.id.radio_american, R.id.radio_single, R.id.radio_double })
            public void onClickScheduleTypeGroup() {
                if (_radioRoundRobin.isChecked()) {
                    setScheduleType(ScheduleType.RoundRobin);
                } else if (_radioAmerican.isChecked()) {
                    setScheduleType(ScheduleType.American);
                } else if (_radioSingle.isChecked()) {
                    setScheduleType(ScheduleType.SingleElimination);
                } else if (_radioDouble.isChecked()) {
                    setScheduleType(ScheduleType.DoubleElimination);
                }
            }

            private void setScheduleType(ScheduleType scheduleType) {
                CharSequence oldTeamCountValueCharSequence = this.__viewModel.getTeamCountValue();
                int oldTeamCountValue = Integer.parseInt(oldTeamCountValueCharSequence.toString());
                CharSequence[] newAllowedTeamCounts = scheduleType.getAllowedTeamCounts();
                int index = Arrays.asList(newAllowedTeamCounts).indexOf(oldTeamCountValueCharSequence);
                if (index < 0) {
                    oldTeamCountValue--;
                    String newTeamCountValue = Integer.toString(oldTeamCountValue);
                    index = Arrays.asList(newAllowedTeamCounts).indexOf(newTeamCountValue);
                    if (index < 0) {
                        index = 0;
                    }
                }
                this._pickerTeamCount.setValues(scheduleType.getAllowedTeamCounts());
                this._pickerTeamCount.setSelectedItem(index);
                this.__viewModel.setScheduleType(scheduleType);
                this.__viewModel.setTeamCountIndex(index);
            }

            @Override
            public void onItemSelected(int index)    {
                this.__viewModel.setTeamCountIndex(index);
            }
        }

        // Constructor

        public TeamRealmAdapter(Context context, RealmResults<Team> realmResults, boolean automaticUpdate, boolean animateResults, GroupSettingsViewModel viewModel) {
            super(context, realmResults, automaticUpdate, animateResults);
            this.viewModel = viewModel;
        }

        @Override
        public ViewHolder onCreateRealmViewHolder(ViewGroup viewTeam, int viewType) {
            if (viewType == TYPE_ITEM) {
                View v = inflater.inflate(R.layout.group_settings_team_item_view, viewTeam, false);
                return new ItemViewHolder((FrameLayout) v);
            } else if (viewType == TYPE_HEADER) {
                View v = inflater.inflate(R.layout.group_settings_header_view, viewTeam, false);
                HeaderViewHolder headerViewHolder = new HeaderViewHolder((FrameLayout) v, this.viewModel);
                return headerViewHolder;
            }
            throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
        }

        @Override
        public void onBindRealmViewHolder(final ViewHolder viewHolder, int position) {
            if (viewHolder instanceof ItemViewHolder) {
                ItemViewHolder itemViewHolder = (ItemViewHolder)viewHolder;
                final Team item = getItem(position);
                itemViewHolder.groupTextView.setText(item.name);
                itemViewHolder.itemView.setBackgroundColor(
                        COLORS[(int) (item.createdOnMillis % COLORS.length)]
                );
                itemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });

            } else if (viewHolder instanceof HeaderViewHolder) {
                //cast holder to HeaderViewHolder and set data for header.
                HeaderViewHolder headerViewHolder = (HeaderViewHolder)viewHolder;
                headerViewHolder._groupNameEditText.requestFocus();
            }
        }

        @Override
        public int getItemCount() {
            return realmResults.size() + 1;
        }

        @Override
        public int getItemViewType(int position) {
            if (isPositionHeader(position)) {
                return TYPE_HEADER;
            }
            return TYPE_ITEM;
        }

        private boolean isPositionHeader(int position) {
            return position == 0;
        }

        private Team getItem(int position) {
            return realmResults.get(position - 1);
        }
    }
}
