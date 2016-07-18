package com.edgardoagno.tournamentandroid.ViewModels;

import com.edgardoagno.tournamentandroid.Models.Group;
import com.edgardoagno.tournamentandroid.Models.ScheduleType;
import com.edgardoagno.tournamentandroid.Models.Team;
import com.edgardoagno.tournamentandroid.Models.Tournament;
import com.google.common.base.Strings;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

import io.realm.Realm;
import io.realm.RealmList;
import rx.subjects.PublishSubject;

/**
 * Created by edgardoagno on 12/07/16.
 */
public class GroupSettingsViewModel {

    private Realm _realm;
    public PublishSubject<String> _groupNameEmitterSubject;
    public PublishSubject<ArrayList<Team>> _teamsEmitterSubject;
    public Group _group;
    public ArrayList<Team> _teams;

    // Constructor

    public GroupSettingsViewModel(Realm realm) {
        this._realm = realm;
        _groupNameEmitterSubject = PublishSubject.create();
        _teamsEmitterSubject = PublishSubject.create();
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
        _group.teamCount = teamCount;

        //
        // add to list
        //
        if (_teams.size() < teamCount) {
            for (int i = _teams.size(); i < teamCount; i++) {
                int seed = i + 1;
                Team team = new Team();
                team.setDefaultProperties();
                team.name = String.format("Team %1$s", seed);
                team.seed = seed;
                _teams.add(team);
            }
        }

        //
        // truncate list
        //
        int teamSize = _teams.size();
        if (teamSize > teamCount) {
            for (int i = teamSize; i > teamCount ; i--) {
                _teams.remove(i - 1);
            }
        }

        _teamsEmitterSubject.onNext(_teams);
    }

    public  void setScheduleType(ScheduleType scheduleType) {
        this._group.setScheduleType(scheduleType);
    }

    public String seed(int seed) {
        return String.format("%1$s.", seed);
    }

    public void setGroupName(String groupName) {
        this._group.name = groupName;
        _groupNameEmitterSubject.onNext(groupName);
    }

    public void attachDefaultGroup(long tournamentId) {
        _realm.beginTransaction();
        _realm.copyToRealmOrUpdate(_group);
        for (Team t: _teams) {
            _realm.copyToRealm(t);
            _group.teams.add(t);
        }
        Tournament tournament = _realm.where(Tournament.class).equalTo("id", tournamentId).findFirst();
        tournament.groups.add(_group);
        _realm.commitTransaction();
    }

    public void createDefaultGroup() {
        _group = new Group();
        _group.setDefaultProperties();
        _teams = new ArrayList<Team>();
        for (int i = 0; i < _group.teamCount; i++ ) {
            int seed = i + 1;
            String name = String.format("Team %1$s", seed);
            Team team = new Team();
            team.setDefaultProperties();
            team.name = name;
            team.seed = seed;
            _teams.add(team);
        }
    }

    public void swapTeams(int fromPosition, int toPosition) {

        Team fromTeam = _teams.get(fromPosition);
        int fromSeed = fromTeam.seed;

        Team toTeam = _teams.get(toPosition);
        int toSeed = toTeam.seed;

        fromTeam.seed = toSeed;
        toTeam.seed = fromSeed;
        Collections.sort(_teams);
        _teamsEmitterSubject.onNext(_teams);
    }
}
