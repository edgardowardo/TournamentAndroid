package com.edgardoagno.tournamentandroid;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.edgardoagno.tournamentandroid.Models.Group;
import com.edgardoagno.tournamentandroid.Models.ScheduleType;
import com.edgardoagno.tournamentandroid.Models.Tournament;

import butterknife.Bind;
import butterknife.ButterKnife;
import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import io.realm.Realm;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;
import io.realm.Sort;

public class GroupsActivity extends AppCompatActivity {

    private Realm realm;
    private Tournament tournament;
    RealmResults<Group> groups;
    private RealmChangeListener groupsListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.groups_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        realm = Realm.getDefaultInstance();
        Long id = getIntent().getLongExtra("TOURNAMENT_ID", 0);
        tournament = realm.where(Tournament.class).equalTo("id", id).findFirst();
        setTitle(tournament.name);

        groups = tournament.groups.where().findAllSorted("id", Sort.DESCENDING);
        final GroupRealmAdapter groupRealmAdapter = new GroupRealmAdapter(this, groups, true, true);
        RealmRecyclerView realmRecyclerView = (RealmRecyclerView) findViewById(R.id.realm_recycler_view);
        realmRecyclerView.setAdapter(groupRealmAdapter);

        groupsListener = new RealmChangeListener() {
            @Override
            public void onChange(Object element) {
                groupRealmAdapter.notifyDataSetChanged();
            }
        };
        groups.addChangeListener(groupsListener);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_groups, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_edit) {
            buildAndShowInputDialog();
            return true;
        } else if (id == R.id.action_add) {
            Intent intent = new Intent(GroupsActivity.this, GroupSettingsActivity.class);
            intent.putExtra("TOURNAMENT_ID", tournament.id);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void buildAndShowInputDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(GroupsActivity.this);
        builder.setTitle("Update Tournament");

        LayoutInflater li = LayoutInflater.from(this);
        View dialogView = li.inflate(R.layout.tournaments_dialog_view, null);
        final EditText input = (EditText) dialogView.findViewById(R.id.input);

        builder.setView(dialogView);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateTournamentItem(input.getText().toString());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        final AlertDialog dialog = builder.show();
        input.setText(tournament.name);
        input.setOnEditorActionListener(
                new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_DONE ||
                                (event.getAction() == KeyEvent.ACTION_DOWN &&
                                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                            dialog.dismiss();
                            updateTournamentItem(input.getText().toString());
                            return true;
                        }
                        return false;
                    }
                });
    }

    private void updateTournamentItem(String tournamentItemText) {
        if (tournamentItemText == null || tournamentItemText.length() == 0) {
            Toast
                    .makeText(this, "Please enter a tournament name", Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        realm.beginTransaction();
        tournament.name = tournamentItemText;
        realm.commitTransaction();

        setTitle(tournament.name);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null) {
            realm.close();
            realm = null;
        }
    }

    public class GroupRealmAdapter
            extends RealmBasedRecyclerViewAdapter<Group, GroupRealmAdapter.ViewHolder> {

        public class ViewHolder extends RealmViewHolder {

            @Bind(R.id.image_schedule_type) ImageView imageScheduleType;
            @Bind(R.id.group_text_view) TextView groupTextView;
            @Bind(R.id.group_detail_text_view) TextView groupDetailTextView;
            public ViewHolder(FrameLayout container) {
                super(container);
                ButterKnife.bind(this, container);
            }
        }

        public GroupRealmAdapter(
                Context context,
                RealmResults<Group> realmResults,
                boolean automaticUpdate,
                boolean animateResults) {
            super(context, realmResults, automaticUpdate, animateResults);
        }

        @Override
        public ViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int viewType) {
            View v = inflater.inflate(R.layout.groups_item_view, viewGroup, false);
            return new ViewHolder((FrameLayout) v);
        }

        @Override
        public void onBindRealmViewHolder(final ViewHolder viewHolder, int position) {
            final Group item = realmResults.get(position);
            viewHolder.groupTextView.setText(item.name);
            String handicapText =  (item.isHandicap)? ", handicapped" : "";
            String detail = String.format("%1$s teams, %2$s games, %3$s rounds%4$s", item.teams.size(), item.games.size(), item.distinctRounds(), handicapText);
            viewHolder.groupDetailTextView.setText(detail);
            ScheduleType s = item.getScheduleType();
            switch (s) {
                case RoundRobin: {
                    viewHolder.imageScheduleType.setImageResource(R.drawable.icon_round_robin_text);
                    break;
                }
                case American: {
                    viewHolder.imageScheduleType.setImageResource(R.drawable.icon_american_text);
                    break;
                }
                case SingleElimination: {
                    viewHolder.imageScheduleType.setImageResource(R.drawable.icon_single_e_text);
                    break;
                }
                case DoubleElimination: {
                    viewHolder.imageScheduleType.setImageResource(R.drawable.icon_double_e_text);
                    break;
                }
            }

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, GroupDetailsActivity.class);
                    intent.putExtra("GROUP_ID", item.id);
                    context.startActivity(intent);
                }
            });
        }
    }
}


