package com.edgardoagno.tournamentandroid.ViewModels;


import com.edgardoagno.tournamentandroid.Models.Group;

import io.realm.Realm;

/**
 * Created by edgardoagno on 21/07/16.
 */
public class GroupDetailsViewModel {

    private Realm _realm;
    public Group _group;

    // Constructor

    public GroupDetailsViewModel(Realm realm, Long groupId) {
        this._realm = realm;
        this._group = realm.where(Group.class).equalTo("id", groupId).findFirst();
    }

    public String getTitle() {
        return _group.name;
    }
}
