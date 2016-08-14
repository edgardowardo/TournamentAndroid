package com.edgardoagno.tournamentandroid.ViewModels.FragmentViewModels;

import com.edgardoagno.tournamentandroid.Models.TeamStats;

/**
 * Created by edgardoagno on 13/08/16.
 */
public class ChartsTabViewModel extends BaseStatsViewModel {

    public String[] tabNames;

    public ChartsTabViewModel(Long _groupId) {
        super(_groupId);
        tabNames = new String[] {"GAMES PLAYED", "GAMES PLAYED / TEAM", "GAMES WON", "POINTS FOR", "POINTS AGAINST"};
    }

}
