package com.edgardoagno.tournamentandroid.ViewModels;

import com.edgardoagno.tournamentandroid.Models.Group;
import com.edgardoagno.tournamentandroid.Models.ScheduleType;
import com.edgardoagno.tournamentandroid.Models.Team;
import com.edgardoagno.tournamentandroid.Models.Tournament;
import com.google.common.base.Strings;

import io.realm.Realm;
import io.realm.RealmList;
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

    public CharSequence[] getAllowedTeamCounts() {
        return this._group.getScheduleType().getAllowedTeamCounts();
    }

    public CharSequence getTeamCountValue() {
        CharSequence s = this._group.getScheduleType().getAllowedTeamCounts()[teamCountIndex];
        return s;
    }

    private int teamCountIndex;
    public int getTeamCountIndex() {
        return teamCountIndex;
    }
    public void setTeamCountIndex(int teamCountIndex) {
        this.teamCountIndex = teamCountIndex;

        int teamCount = Integer.parseInt(getTeamCountValue().toString());
        _realm.beginTransaction();
        _group.teamCount = teamCount;

        //
        // add to list
        //
        if (_group.teams.size() < teamCount) {
            for (int i = _group.teams.size(); i < teamCount; i++) {
                int seed = i + 1;
                Team team = _realm.createObject(Team.class);
                team.setDefaultProperties();
                team.name = String.format("Team %1$s", seed);
                team.seed = seed;
                _group.teams.add(team);

            }
        }

        //
        // truncate list
        //
        int teamSize = _group.teams.size();
        if (teamSize > teamCount) {
            for (int i = teamSize; i > teamCount ; i--) {
                _group.teams.deleteLastFromRealm();
            }
        }
        _realm.commitTransaction();
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
