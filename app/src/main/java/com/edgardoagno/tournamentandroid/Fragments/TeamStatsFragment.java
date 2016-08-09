package com.edgardoagno.tournamentandroid.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edgardoagno.tournamentandroid.R;
import com.edgardoagno.tournamentandroid.ViewModels.Fragments.TeamStatsViewModel;

/**
 * Created by edgardoagno on 21/07/16.
 */
public class TeamStatsFragment extends Fragment {

    private TeamStatsViewModel viewModel;

    public TeamStatsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.group_details_team_stats_item_view, container, false);
        Long id = getArguments().getLong("GROUP_ID", 0);
        viewModel = new TeamStatsViewModel(id);
        viewModel.loadStatsList();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
