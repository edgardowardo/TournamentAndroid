package com.edgardoagno.tournamentandroid;


import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.edgardoagno.tournamentandroid.Models.Group;
import com.edgardoagno.tournamentandroid.Models.Tournament;

import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import io.realm.Realm;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;
import io.realm.Sort;

/**
 * A TO-DO app example showcasing the {@link RealmRecyclerView} with swipe to delete.
 */
public class GroupsActivity extends RealmBaseActivity {

    private static final int[] COLORS = new int[] {
            Color.argb(255, 28, 160, 170),
            Color.argb(255, 99, 161, 247),
            Color.argb(255, 13, 79, 139),
            Color.argb(255, 89, 113, 173),
            Color.argb(255, 200, 213, 219),
            Color.argb(255, 99, 214, 74),
            Color.argb(255, 205, 92, 92),
            Color.argb(255, 105, 5, 98)
    };

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

        realm = Realm.getInstance(getRealmConfig());
        Long id = getIntent().getLongExtra("TOURNAMENT_ID", 0);
        tournament = realm.where(Tournament.class).equalTo("id", id).findFirst();
        setTitle(tournament.name);

        groups = tournament.groups.where().findAllSorted("id", Sort.ASCENDING);
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

            public TextView groupTextView;
            public ViewHolder(FrameLayout container) {
                super(container);
                this.groupTextView = (TextView) container.findViewById(R.id.group_text_view);
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
            viewHolder.itemView.setBackgroundColor(
                    COLORS[(int) (item.id % COLORS.length)]
            );

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Context context = v.getContext();
//                    Intent intent = new Intent(context, GroupDetailActivity.class);
//                    intent.putExtra(GroupDetailFragment.ARG_ITEM_ID, holder.mItem.id);
//
//                    context.startActivity(intent);
                }
            });
        }
    }
}

