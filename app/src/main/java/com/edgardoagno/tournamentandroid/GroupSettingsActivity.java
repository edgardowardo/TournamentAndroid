package com.edgardoagno.tournamentandroid;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.edgardoagno.tournamentandroid.Models.Group;
import com.edgardoagno.tournamentandroid.Models.Team;
import com.edgardoagno.tournamentandroid.Models.Tournament;
import com.wefika.horizontalpicker.HorizontalPicker;

import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import io.realm.Realm;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;

/**
 * Created by edgardoagno on 10/07/16.
 */
public class GroupSettingsActivity extends RealmBaseActivity {

    private Realm realm;
    Group group;
    RealmResults<Team> teams;
    private RealmChangeListener teamsListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_settings_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Add Group");

//        group = new Group();
        realm = Realm.getInstance(getRealmConfig());
        Long id = getIntent().getLongExtra("TOURNAMENT_ID", 0);
        teams = realm.where(Team.class).findAll();

        TeamRealmAdapter teamsRealmAdapter = new TeamRealmAdapter(this, teams, true, true){
            public void onItemSelectedAdapterCallBack(int index){
                onItemSelectedActivityCallBack(index);
            }
        };
        RealmRecyclerView realmRecyclerView = (RealmRecyclerView) findViewById(R.id.realm_recycler_view);
        realmRecyclerView.setAdapter(teamsRealmAdapter);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void onItemSelectedActivityCallBack(int index){
        String s = String.format("Item %1$s selected", index);
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    public class TeamRealmAdapter extends RealmBasedRecyclerViewAdapter<Team, TeamRealmAdapter.ViewHolder> {

        public HeaderViewHolder headerViewHolder;
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
            public EditText groupEditText;
            public HorizontalPicker pickerTeamCount;

            public HeaderViewHolder(FrameLayout container) {
                super(container);
                this.groupEditText = (EditText) container.findViewById(R.id.group_edit_text);
                this.pickerTeamCount = (HorizontalPicker) container.findViewById(R.id.picker_team_count);
                this.pickerTeamCount.setOnItemSelectedListener(this);
            }

            @Override
            public void onItemSelected(int index)    {
                onItemSelectedHolderCallBack(index);
            }
            public void onItemSelectedHolderCallBack(int index){
                // do nothing. to be overridden
            }
        }

        // Initializer

        public TeamRealmAdapter(Context context, RealmResults<Team> realmResults, boolean automaticUpdate, boolean animateResults) {
            super(context, realmResults, automaticUpdate, animateResults);
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
                this.headerViewHolder = new HeaderViewHolder((FrameLayout) v){
                    public void onItemSelectedHolderCallBack(int index){
                        onItemSelectedAdapterCallBack(index);
                    }
                };
                return this.headerViewHolder;
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
                        COLORS[(int) (item.id % COLORS.length)]
                );
                itemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                });

            } else if (viewHolder instanceof HeaderViewHolder) {
                //cast holder to HeaderViewHolder and set data for header.
                HeaderViewHolder headerViewHolder = (HeaderViewHolder)viewHolder;
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
