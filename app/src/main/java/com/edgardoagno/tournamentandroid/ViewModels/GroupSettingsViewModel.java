package com.edgardoagno.tournamentandroid.ViewModels;

import com.edgardoagno.tournamentandroid.Models.Group;
import com.edgardoagno.tournamentandroid.Models.ScheduleType;
import com.edgardoagno.tournamentandroid.Models.Team;
import com.edgardoagno.tournamentandroid.Models.Tournament;

import io.realm.Realm;
import rx.subjects.PublishSubject;

/**
 * Created by edgardoagno on 12/07/16.
 */
public class GroupSettingsViewModel {

    private Realm _realm;
    public Group _group;
    public PublishSubject<String> _groupNameEmitterSubject;

    // Constructor

    public GroupSettingsViewModel(Realm realm) {
        this._realm = realm;
        _groupNameEmitterSubject = PublishSubject.create();
    }

    public  void setScheduleType(ScheduleType scheduleType) {
        _realm.beginTransaction();
        this._group.setScheduleType(scheduleType);
        _realm.commitTransaction();
    }

    public void setGroupName(String groupName) {
        _realm.beginTransaction();
        this._group.name = groupName;
        _realm.commitTransaction();
        _groupNameEmitterSubject.onNext(groupName);
    }

    public void attachDefaultGroup(long tournamentId) {
        _realm.beginTransaction();
        Tournament tournament = _realm.where(Tournament.class).equalTo("id", tournamentId).findFirst();
        tournament.groups.add(_group);
        _realm.commitTransaction();
    }

    public void saveDefaultGroup() {
        _realm.beginTransaction();
        _group = _realm.createObject(Group.class);
        _group.setDefaultProperties();
        long runningTime = System.currentTimeMillis();
        for (int i = 0; i < _group.teamCount; i++ ) {
            runningTime++;
            int seed = i + 1;
            Team team = _realm.createObject(Team.class);
            team.setDefaultProperties();
            team.createdOnMillis = runningTime;
            team.name = String.format("Team %1$s", seed);
            team.seed = seed;
            _group.teams.add(team);
        }
        _realm.commitTransaction();
    }

    public void deleteDefaultGroup() {
        _realm.beginTransaction();
        _group.teams.deleteAllFromRealm();
        _group.deleteFromRealm();
        _realm.commitTransaction();
    }
}
