package com.edgardoagno.tournamentandroid.Fragments;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edgardoagno.tournamentandroid.Models.Game;
import com.edgardoagno.tournamentandroid.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;

/**
 * Created by edgardoagno on 30/07/16.
 */

public class GamesRealmAdapter extends RealmBasedRecyclerViewAdapter<Game, GamesRealmAdapter.ViewHolder> {

    public class ViewHolder extends RealmViewHolder {
        @Bind(R.id.left_team) Button leftButton;
        @Bind(R.id.right_team) Button rightButton;
        @Bind(R.id.index_text_view) TextView indexTextView;
        public ViewHolder(LinearLayout container) {
            super(container);
            ButterKnife.bind(this, container);
        }
    }

    public GamesRealmAdapter(Context context, RealmResults<Game> realmResults, boolean automaticUpdate, boolean animateResults) {
        super(context, realmResults, automaticUpdate, animateResults);
    }

    @Override
    public ViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int viewType) {
        View v = inflater.inflate(R.layout.group_details_game_item_view, viewGroup, false);
        return new ViewHolder((LinearLayout) v);
    }

    @Override
    public void onBindRealmViewHolder(final ViewHolder viewHolder, int position) {
        final Game item = realmResults.get(position);

        if (item.leftTeam == null) {
            viewHolder.leftButton.setText("");
        } else {
            viewHolder.leftButton.setText(item.leftTeam.name);
        }
        viewHolder.indexTextView.setText(String.format("%1$s", item.index));

        if (item.rightTeam == null) {
            viewHolder.rightButton.setText("");
        } else {
            viewHolder.rightButton.setText(item.rightTeam.name);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }
}