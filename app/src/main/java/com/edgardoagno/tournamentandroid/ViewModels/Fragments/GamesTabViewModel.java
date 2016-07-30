package com.edgardoagno.tournamentandroid.ViewModels.Fragments;

import com.edgardoagno.tournamentandroid.Models.Group;
import com.edgardoagno.tournamentandroid.ViewModels.BaseViewModel;

/**
 * Created by edgardoagno on 30/07/16.
 */
public class GamesTabViewModel extends BaseViewModel {

    public Group _group;

    // Constructor

    public GamesTabViewModel(Long groupId) {
        this._group = realm.where(Group.class).equalTo("id", groupId).findFirst();
    }

}
