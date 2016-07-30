package com.edgardoagno.tournamentandroid.ViewModels;


import com.edgardoagno.tournamentandroid.Models.Group;


/**
 * Created by edgardoagno on 21/07/16.
 */
public class GroupDetailsViewModel extends BaseViewModel {

    public Group _group;

    // Constructor

    public GroupDetailsViewModel(Long groupId) {
        this._group = realm.where(Group.class).equalTo("id", groupId).findFirst();
    }

    public String getTitle() {
        return _group.name;
    }
}
