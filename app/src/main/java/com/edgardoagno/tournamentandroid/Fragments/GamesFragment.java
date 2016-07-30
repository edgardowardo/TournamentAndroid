package com.edgardoagno.tournamentandroid.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edgardoagno.tournamentandroid.R;
import com.edgardoagno.tournamentandroid.ViewModels.Fragments.GamesViewModel;

import co.moonmonkeylabs.realmrecyclerview.RealmRecyclerView;


/**
 * Created by edgardoagno on 21/07/16.
 */

public class GamesFragment extends Fragment {

    private GamesViewModel viewModel;

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

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}

