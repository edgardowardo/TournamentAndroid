package com.edgardoagno.tournamentandroid.ViewModels.Fragments;

import com.edgardoagno.tournamentandroid.Models.Group;
import com.edgardoagno.tournamentandroid.ViewModels.BaseViewModel;

/**
 * Created by edgardoagno on 30/07/16.
 */
public class GamesTabViewModel extends BaseViewModel {

    private Group _group;
    public String[] tabNames;

    // Constructor

    public GamesTabViewModel(Long groupId) {
        _group = realm.where(Group.class).equalTo("id", groupId).findFirst();

        int rounds = _group.distinctRounds();
        tabNames = new String[rounds];
        for (int i = 0; i < rounds ; i++) {
            tabNames[i] = String.format("ROUND  %1$s", i + 1);
        }
    }
}
