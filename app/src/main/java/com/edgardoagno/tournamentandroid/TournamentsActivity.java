package com.edgardoagno.tournamentandroid;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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


public class TournamentsActivity extends RealmBaseActivity {

    private Realm realm;
    private RealmResults<Tournament> tournaments;
    private RealmChangeListener tournamentsListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tournaments_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        realm = Realm.getInstance(getRealmConfig());
        tournaments = realm.where(Tournament.class).findAllSorted("id", Sort.DESCENDING);
        final TournamentRealmAdapter tournamentRealmAdapter = new TournamentRealmAdapter(this, tournaments, true, true);
        final RealmRecyclerView realmRecyclerView = (RealmRecyclerView) findViewById(R.id.realm_recycler_view);
        realmRecyclerView.setAdapter(tournamentRealmAdapter);

        tournamentsListener = new RealmChangeListener() {
            @Override
            public void onChange(Object element) {
                tournamentRealmAdapter.notifyDataSetChanged();
            }
        };
        tournaments.addChangeListener(tournamentsListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tournaments, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            buildAndShowInputDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null) {
            realm.close();
            realm = null;
        }
        tournaments.removeChangeListener(tournamentsListener);
    }

    private void buildAndShowInputDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(TournamentsActivity.this);
        builder.setTitle("Create A Tournament");

        LayoutInflater li = LayoutInflater.from(this);
        View dialogView = li.inflate(R.layout.tournaments_dialog_view, null);
        final EditText input = (EditText) dialogView.findViewById(R.id.input);

        builder.setView(dialogView);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addTournamentItem(input.getText().toString());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        final AlertDialog dialog = builder.show();
        input.setOnEditorActionListener(
                new EditText.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_DONE ||
                                (event.getAction() == KeyEvent.ACTION_DOWN &&
                                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                            dialog.dismiss();
                            addTournamentItem(input.getText().toString());
                            return true;
                        }
                        return false;
                    }
                });
    }

    private void addTournamentItem(String tournamentItemText) {
        if (tournamentItemText == null || tournamentItemText.length() == 0) {
            Toast
                    .makeText(this, "Please enter a tournament name", Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        realm.beginTransaction();
        Tournament tournamentItem = realm.createObject(Tournament.class);
        tournamentItem.id = System.currentTimeMillis();
        tournamentItem.name = tournamentItemText;
        realm.commitTransaction();
    }

    public class TournamentRealmAdapter
            extends RealmBasedRecyclerViewAdapter<Tournament, TournamentRealmAdapter.ViewHolder> {

        public class ViewHolder extends RealmViewHolder {
            @Bind(R.id.tournament_text_view) TextView tournamentTextView;
            @Bind(R.id.tournament_detail_text_view) TextView tournamentDetailTextView;
            public ViewHolder(FrameLayout container) {
                super(container);
                ButterKnife.bind(this, container);
            }
        }

        public TournamentRealmAdapter(Context context, RealmResults<Tournament> realmResults, boolean automaticUpdate, boolean animateResults) {
            super(context, realmResults, automaticUpdate, animateResults);
        }

        @Override
        public ViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int viewType) {
            View v = inflater.inflate(R.layout.tournaments_item_view, viewGroup, false);
            return new ViewHolder((FrameLayout) v);
        }

        @Override
        public void onBindRealmViewHolder(final ViewHolder viewHolder, int position) {
            final Tournament item = realmResults.get(position);
            viewHolder.tournamentTextView.setText(item.name);
            String details = String.format("%1$s groups", item.groups.size());
            viewHolder.tournamentDetailTextView.setText(details);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, GroupsActivity.class);
                    intent.putExtra("TOURNAMENT_ID", item.id);
                    context.startActivity(intent);
                }
            });
        }
    }
}

