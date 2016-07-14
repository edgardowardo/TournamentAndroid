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
import android.widget.TextView;
import android.widget.Toast;

import com.edgardoagno.tournamentandroid.Models.Team;
import com.edgardoagno.tournamentandroid.ViewModels.GroupSettingsViewModel;
import com.wefika.horizontalpicker.HorizontalPicker;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import io.realm.Realm;
import io.realm.RealmBasedRecyclerViewAdapter;
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
        TeamRealmAdapter teamsRealmAdapter = new TeamRealmAdapter(this, teams, true, true, _viewModel){
            public void onItemSelectedAdapterCallBack(int index){
                onItemSelectedActivityCallBack(index);
            }
        };
        RealmRecyclerView realmRecyclerView = (RealmRecyclerView) findViewById(R.id.realm_recycler_view);
        realmRecyclerView.setAdapter(teamsRealmAdapter);

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

    public void onItemSelectedActivityCallBack(int index){
        String s = String.format("Item %1$s selected", index);
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
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
            public HorizontalPicker pickerTeamCount;
            private GroupSettingsViewModel viewModel;

            // Constructor

            public HeaderViewHolder(FrameLayout container, GroupSettingsViewModel viewModel) {
                super(container);
                this.viewModel = viewModel;
                ButterKnife.bind(this, container);
                this.pickerTeamCount = (HorizontalPicker) container.findViewById(R.id.picker_team_count);
                this.pickerTeamCount.setOnItemSelectedListener(this);
            }

            @OnTextChanged({ R.id.group_edit_text })
            public void onGroupNameChanged() {
                String name = _groupNameEditText.getText().toString();
                this.viewModel.setGroupName(name);
            }

            @Override
            public void onItemSelected(int index)    {
                onItemSelectedHolderCallBack(index);
            }
            public void onItemSelectedHolderCallBack(int index){
                // do nothing. to be overridden
            }
        }

        // Constructor

        public TeamRealmAdapter(Context context, RealmResults<Team> realmResults, boolean automaticUpdate, boolean animateResults, GroupSettingsViewModel viewModel) {
            super(context, realmResults, automaticUpdate, animateResults);
            this.viewModel = viewModel;
        }

        public void onItemSelectedAdapterCallBack(int index){
            // do nothing. to be overridden
        }

        @Override
        public ViewHolder onCreateRealmViewHolder(ViewGroup viewTeam, int viewType) {
            if (viewType == TYPE_ITEM) {
                View v = inflater.inflate(R.layout.group_settings_team_item_view, viewTeam, false);
                return new ItemViewHolder((FrameLayout) v);
            } else if (viewType == TYPE_HEADER) {
                View v = inflater.inflate(R.layout.group_settings_header_view, viewTeam, false);
                HeaderViewHolder headerViewHolder = new HeaderViewHolder((FrameLayout) v, this.viewModel){
                    public void onItemSelectedHolderCallBack(int index){
                        onItemSelectedAdapterCallBack(index);
                    }
                };
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
