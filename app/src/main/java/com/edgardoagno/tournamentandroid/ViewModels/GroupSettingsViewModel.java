package com.edgardoagno.tournamentandroid.ViewModels;

import android.util.Log;

import com.edgardoagno.tournamentandroid.Models.Group;
import com.edgardoagno.tournamentandroid.Models.Team;
import com.edgardoagno.tournamentandroid.Models.Tournament;

import io.realm.Realm;

/**
 * Created by edgardoagno on 12/07/16.
 */
public class GroupSettingsViewModel {

    private Realm realm;
    public Group group;

    public GroupSettingsViewModel(Realm realm) {
        this.realm = realm;
    }

    public void attachDefaultGroup(long tournamentId) {
        realm.beginTransaction();
        Tournament tournament = realm.where(Tournament.class).equalTo("id", tournamentId).findFirst();
        tournament.groups.add(group);
        realm.commitTransaction();
    }

    public void saveDefaultGroup() {
        realm.beginTransaction();
        group = realm.createObject(Group.class);
        group.setDefaultProperties();
        long runningTime = System.currentTimeMillis();
        for (int i = 0; i < group.teamCount; i++ ) {
            runningTime++;
            int seed = i + 1;
            Team team = realm.createObject(Team.class);
            team.setDefaultProperties();
            team.createdOnMillis = runningTime;
            team.name = String.format("Team %1$s", seed);
            team.seed = seed;
            group.teams.add(team);
        }
        realm.commitTransaction();
    }

    public void deleteDefaultGroup() {
        realm.beginTransaction();
        group.teams.deleteAllFromRealm();
        group.deleteFromRealm();
        realm.commitTransaction();
    }
}
