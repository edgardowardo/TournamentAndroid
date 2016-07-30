package com.edgardoagno.tournamentandroid.ViewModels;

import com.edgardoagno.tournamentandroid.Models.DoublesInfo;
import com.edgardoagno.tournamentandroid.Models.Elimination;
import com.edgardoagno.tournamentandroid.Models.Game;
import com.edgardoagno.tournamentandroid.Models.Group;
import com.edgardoagno.tournamentandroid.Models.ScheduleType;
import com.edgardoagno.tournamentandroid.Models.Team;
import com.edgardoagno.tournamentandroid.Models.Tournament;
import com.edgardoagno.tournamentandroid.Scheduler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import rx.subjects.PublishSubject;

/**
 * Created by edgardoagno on 12/07/16.
 */
public class GroupSettingsViewModel extends BaseViewModel {

    public PublishSubject<String> _groupNameEmitterSubject;
    public PublishSubject<ArrayList<Team>> _teamsEmitterSubject;
    public PublishSubject<Boolean> _isManualSortingEmitterSubject;
    public PublishSubject<Boolean> _isEditingHandicapEmitterSubject;
    private boolean isManualSorting = false;
    private boolean isEditingHandicap = false;
    public Group _group;
    public ArrayList<Team> _teams;

    // Constructor

    public GroupSettingsViewModel() {
        _groupNameEmitterSubject = PublishSubject.create();
        _teamsEmitterSubject = PublishSubject.create();
        _isManualSortingEmitterSubject = PublishSubject.create();
        _isEditingHandicapEmitterSubject = PublishSubject.create();
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
                String name = String.format("Team %1$s", seed);
                Team team = new Team(name);
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
        Game[] games = {};
        Team[] teams = _teams.toArray(new Team[_teams.size()]);
        switch (_group.getScheduleType()) {
            case RoundRobin:
                games = Scheduler.roundRobin(teams);
                break;
            case American:
                games = Scheduler.americanTournament(teams);
                break;
            case SingleElimination:
                games = Scheduler.singleElimination(teams);
                break;
            case DoubleElimination:
                games = Scheduler.doubleElimination(teams);
                break;
        }

        // Realm transaction

        realm.beginTransaction();
        realm.copyToRealmOrUpdate(_group);
        for (Team t: _teams) {
            realm.copyToRealmOrUpdate(t);
            _group.teams.add(t);
        }

        for (Game g: games) {
            Game game = new Game(g.round, g.index, g.leftTeam, g.rightTeam);
            realm.copyToRealmOrUpdate(game);

            if (g.doublesInfo != null) {
                DoublesInfo doublesInfo = new DoublesInfo(g.doublesInfo.leftTeam, g.doublesInfo.rightTeam);
                realm.copyToRealmOrUpdate(doublesInfo);
                game.doublesInfo = doublesInfo;
            }

            if (g.elimination != null) {
                Elimination elimination = new Elimination(g.elimination.isLoserBracket, g.elimination.leftGameIndex, g.elimination.rightGameIndex);
                elimination.firstLoserIndex = g.elimination.firstLoserIndex;
                elimination.prevLeftGame = g.elimination.prevLeftGame;
                elimination.prevRightGame = g.elimination.prevLeftGame;
                realm.copyToRealmOrUpdate(elimination);
                game.elimination = elimination;
            }

            _group.games.add(game);
        }

        Tournament tournament = realm.where(Tournament.class).equalTo("id", tournamentId).findFirst();
        tournament.groups.add(_group);
        realm.commitTransaction();
    }

    public void createDefaultGroup() {
        _group = new Group();
        _group.setDefaultProperties();
        _teams = new ArrayList<Team>();
        for (int i = 0; i < _group.teamCount; i++ ) {
            int seed = i + 1;
            String name = String.format("Team %1$s", seed);
            Team team = new Team(name);
            team.seed = seed;
            _teams.add(team);
        }
    }

    public void swapTeams(int fromPosition, int toPosition) {
        Team removed = _teams.remove(fromPosition);
        _teams.add(toPosition, removed);
        int seed = 1;
        for (Team team: _teams) {
            team.seed = seed++;
        }
        _teamsEmitterSubject.onNext(_teams);
    }

    public void shuffleTeams() {
        long randomSeed = System.nanoTime();
        Collections.shuffle(_teams, new Random(randomSeed));
        int seed = 1;
        for (Team team: _teams) {
            team.seed = seed;
            seed++;
        }
        _teamsEmitterSubject.onNext(_teams);
    }

    public void resetTeams() {
        for (int i = 0; i < _group.teamCount; i++ ) {
            int seed = i + 1;
            String name = String.format("Team %1$s", seed);
            Team team = _teams.get(i);
            team.name = name;
            team.handicap = 0;
        }
        _teamsEmitterSubject.onNext(_teams);
    }

    public boolean getIsManualSorting() {
        return this.isManualSorting;
    }

    public void setIsManualSorting(Boolean isManualSorting) {
        this.isManualSorting = isManualSorting;
        _isManualSortingEmitterSubject.onNext(isManualSorting);
    }

    public boolean getIsEditingHandicap() {
        return this.isEditingHandicap;
    }

    public void setIsEditingHandicap(Boolean isEditingHandicap) {
        this.isEditingHandicap = isEditingHandicap;
        this._group.isHandicap = isEditingHandicap;
        _isEditingHandicapEmitterSubject.onNext(isEditingHandicap);
    }
}
