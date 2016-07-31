package com.edgardoagno.tournamentandroid.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edgardoagno.tournamentandroid.Models.Game;
import com.edgardoagno.tournamentandroid.R;
import com.edgardoagno.tournamentandroid.ViewModels.Fragments.GameViewModel;
import com.edgardoagno.tournamentandroid.ViewModels.Fragments.GamesViewModel;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;
import io.realm.RealmBasedRecyclerViewAdapter;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.RealmViewHolder;


/**
 * Created by edgardoagno on 21/07/16.
 */

public class GamesFragment extends Fragment {

    private GamesViewModel viewModel;
    private RealmChangeListener gamesListener;

    public GamesFragment() {
    }

    @Override
    public void onStop() {
        super.onStop();
        viewModel.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.group_details_games_list_view, container, false);
        Long id = getArguments().getLong("GROUP_ID");
        int round = getArguments().getInt("ROUND_NO");
        Boolean isLosersRound = getArguments().getBoolean("IS_LOSERS_ROUND", false);

        viewModel = new GamesViewModel(id, round, isLosersRound);
        final GamesRealmAdapter adapter = new GamesRealmAdapter(getActivity(), viewModel.games, true, true);
        final RealmRecyclerView recyclerView = (RealmRecyclerView) view.findViewById(R.id.realm_games_recycler_view);
        recyclerView.setAdapter(adapter);

        gamesListener = (RealmChangeListener) new RealmChangeListener() {
            @Override
            public void onChange(Object element) {
                adapter.notifyDataSetChanged();
            }
        };
        viewModel.games.addChangeListener(gamesListener);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public class GamesRealmAdapter extends RealmBasedRecyclerViewAdapter<Game, GamesRealmAdapter.ViewHolder> {

        public class ViewHolder extends RealmViewHolder {

            public GameViewModel gameViewModel;
            @Bind(R.id.left_team) Button leftButton;
            @Bind(R.id.right_team) Button rightButton;
            @Bind(R.id.index_text_view) TextView indexTextView;
            public ViewHolder(LinearLayout container, GameViewModel gameViewModelIn) {
                super(container);
                gameViewModel = gameViewModelIn;
                ButterKnife.bind(this, container);
            }

            @OnClick(R.id.left_team)
            public void onClickLeftTeam() {
                gameViewModel.setLeftWinner();
            }

            @OnClick(R.id.right_team)
            public void onClickRightTeam() {
                gameViewModel.setRightWinner();
            }
        }

        public GamesRealmAdapter(Context context, RealmResults<Game> realmResults, boolean automaticUpdate, boolean animateResults) {
            super(context, realmResults, automaticUpdate, animateResults);
        }

        @Override
        public ViewHolder onCreateRealmViewHolder(ViewGroup viewGroup, int viewType) {
            View v = inflater.inflate(R.layout.group_details_game_item_view, viewGroup, false);
            GameViewModel gameViewModel = new GameViewModel(viewModel.group);
            return new ViewHolder((LinearLayout) v, gameViewModel);
        }

        @Override
        public void onBindRealmViewHolder(final ViewHolder viewHolder, int position) {
            final Game item = realmResults.get(position);
            GameViewModel gameViewModel = viewHolder.gameViewModel;
            gameViewModel.setGame(item);

            // Text values
            viewHolder.leftButton.setText(gameViewModel.getLeftButtonText());
            viewHolder.indexTextView.setText(gameViewModel.getIndex());
            viewHolder.rightButton.setText(gameViewModel.getRightButtonText());

            // Set colors
            viewHolder.leftButton.getBackground().setColorFilter(Color.parseColor(gameViewModel.getLeftButtonColor()), PorterDuff.Mode.MULTIPLY);
            viewHolder.leftButton.setTextColor(Color.parseColor(gameViewModel.getLeftButtonTextColor()));
            viewHolder.rightButton.getBackground().setColorFilter(Color.parseColor(gameViewModel.getRightButtonColor()), PorterDuff.Mode.MULTIPLY);
            viewHolder.rightButton.setTextColor(Color.parseColor(gameViewModel.getRightButtonTextColor()));
        }
    }
}

