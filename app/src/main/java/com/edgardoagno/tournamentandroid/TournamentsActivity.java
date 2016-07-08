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
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.edgardoagno.tournamentandroid.Models.Tournament;

import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import io.realm.Realm;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;
import io.realm.Sort;


/**
 * A TO-DO app example showcasing the {@link RealmRecyclerView} with swipe to delete.
 */
public class TournamentsActivity extends RealmBaseActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tournaments_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buildAndShowInputDialog();
            }
        });

        realm = Realm.getInstance(getRealmConfig());

        Log.d("", "*****path: " + realm.getPath());

        RealmResults<Tournament> toDoItems = realm
                .where(Tournament.class)
                .findAllSorted("id", Sort.ASCENDING);
        TournamentRealmAdapter toDoRealmAdapter =
                new TournamentRealmAdapter(this, toDoItems, true, true);
        RealmRecyclerView realmRecyclerView =
                (RealmRecyclerView) findViewById(R.id.realm_recycler_view);
        realmRecyclerView.setAdapter(toDoRealmAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null) {
            realm.close();
            realm = null;
        }
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

    private void addTournamentItem(String toDoItemText) {
        if (toDoItemText == null || toDoItemText.length() == 0) {
            Toast
                    .makeText(this, "Please enter a tournament name", Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        realm.beginTransaction();
        Tournament tournamentItem = realm.createObject(Tournament.class);
        tournamentItem.id = System.currentTimeMillis();
        tournamentItem.name = toDoItemText;
        realm.commitTransaction();
    }

    public class TournamentRealmAdapter
            extends RealmBasedRecyclerViewAdapter<Tournament, TournamentRealmAdapter.ViewHolder> {

        public class ViewHolder extends RealmViewHolder {

            public TextView tournamentTextView;
            public ViewHolder(FrameLayout container) {
                super(container);
                this.tournamentTextView = (TextView) container.findViewById(R.id.tournament_text_view);
            }
        }

        public TournamentRealmAdapter(
                Context context,
                RealmResults<Tournament> realmResults,
                boolean automaticUpdate,
                boolean animateResults) {
            super(context, realmResults, automaticUpdate, animateResults);
        }

        @Override
        public ViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int viewType) {
            View v = inflater.inflate(R.layout.tournaments_item_view, viewGroup, false);
            return new ViewHolder((FrameLayout) v);
        }

        @Override
        public void onBindRealmViewHolder(ViewHolder viewHolder, int position) {
            final Tournament item = realmResults.get(position);
            viewHolder.tournamentTextView.setText(item.name);
            viewHolder.itemView.setBackgroundColor(
                    COLORS[(int) (item.id % COLORS.length)]
            );
        }
    }
}

