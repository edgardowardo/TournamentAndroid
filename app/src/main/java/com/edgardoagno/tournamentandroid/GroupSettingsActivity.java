package com.edgardoagno.tournamentandroid;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.edgardoagno.tournamentandroid.Models.Group;
import com.edgardoagno.tournamentandroid.Models.Team;
import com.edgardoagno.tournamentandroid.Models.Tournament;

import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import io.realm.Realm;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;
import io.realm.Sort;

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
//        Long id = getIntent().getLongExtra("TOURNAMENT_ID", 0);
        teams = realm.where(Team.class).findAll();

        final TeamRealmAdapter groupRealmAdapter = new TeamRealmAdapter(this, teams, true, true);
        RealmRecyclerView realmRecyclerView = (RealmRecyclerView) findViewById(R.id.realm_recycler_view);
        realmRecyclerView.setAdapter(groupRealmAdapter);
    }

    public class TeamRealmAdapter extends RealmBasedRecyclerViewAdapter<Team, TeamRealmAdapter.ViewHolder> {

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

        public class HeaderViewHolder extends ViewHolder {
            public EditText groupEditText;
            public HeaderViewHolder(FrameLayout container) {
                super(container);
                this.groupEditText = (EditText) container.findViewById(R.id.group_edit_text);
            }
        }

        public TeamRealmAdapter(Context context, RealmResults<Team> realmResults, boolean automaticUpdate, boolean animateResults) {
            super(context, realmResults, automaticUpdate, animateResults);
        }

        @Override
        public ViewHolder onCreateRealmViewHolder(ViewGroup viewTeam, int viewType) {
            if (viewType == TYPE_ITEM) {
                View v = inflater.inflate(R.layout.group_settings_team_item_view, viewTeam, false);
                return new ItemViewHolder((FrameLayout) v);
            } else if (viewType == TYPE_HEADER) {
                View v = inflater.inflate(R.layout.group_settings_header_view, viewTeam, false);
                return new HeaderViewHolder((FrameLayout) v);
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
