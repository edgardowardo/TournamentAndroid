package com.edgardoagno.tournamentandroid.ViewModels.Fragments;

import com.edgardoagno.tournamentandroid.Models.Game;
import com.edgardoagno.tournamentandroid.Models.Group;
import com.edgardoagno.tournamentandroid.Models.Team;
import com.edgardoagno.tournamentandroid.Models.TeamStats;
import com.edgardoagno.tournamentandroid.ViewModels.BaseViewModel;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.subjects.PublishSubject;

/**
 * Created by edgardoagno on 09/08/16.
 */
public class BaseStatsViewModel extends BaseViewModel {

    private Long groupId;
    private int progress = 1;
    public PublishSubject<Integer> progressEmitterSubject;
    public ArrayList<TeamStats> teamStatsList;
    public RealmResults<Game> games;
    protected Group group;

    public BaseStatsViewModel(Long _groupId) {
        super();
        progressEmitterSubject = PublishSubject.create();
        groupId = _groupId;
        group =  realm.where(Group.class).equalTo("id", groupId).findFirst();
        if (group != null) {
            games = group.games.where().findAll();
        }
    }

    public void loadStatsList() {

        Realm realm = Realm.getDefaultInstance();
        final Group group = realm.where(Group.class).equalTo("id", groupId).findFirst();
        progress = 1;

        Function<Team, TeamStats> teamToStats =
                new Function<Team, TeamStats>() {
                    @Override
                    public TeamStats apply(Team t) {
                        int p = 100 * progress / group.teams.size();
                        progressEmitterSubject.onNext(p);
                        progress++;

                        int countPlayed = group.games.where()
                                .beginGroup()
                                .equalTo("isDraw",true).or().isNotNull("winner")
                                .endGroup()
                                .beginGroup()
                                .equalTo("leftTeam.seed", t.seed)
                                .or().equalTo("rightTeam.seed", t.seed)
                                .or().equalTo("doublesInfo.leftTeam.seed", t.seed)
                                .or().equalTo("doublesInfo.rightTeam.seed", t.seed)
                                .endGroup()
                                .findAll().size();

                        int countGames = group.games.where()
                                .beginGroup()
                                .equalTo("leftTeam.seed", t.seed)
                                .or().equalTo("rightTeam.seed", t.seed)
                                .or().equalTo("doublesInfo.leftTeam.seed", t.seed)
                                .or().equalTo("doublesInfo.rightTeam.seed", t.seed)
                                .endGroup()
                                .findAll().size();

                        int pointsForLeft = 0, pointsForRight = 0, pointsAgainstLeft = 0, pointsAgainstRight = 0;
                        int countWins = 0, countDraws = 0, countLost = 0, pointsFor = 0, pointsAgainst = 0, pointsDifference = 0;

                        for (Game g : group.games) {
                            if (g.isWinner(t.seed)) {
                                countWins++;
                            }
                            if (g.isParticipant(t.seed) && g.getWinner() != null && g.isWinner(t.seed) == false ) {
                                countLost++;
                            }
                            if (g.isParticipant(t.seed) && g.getIsDraw()) {
                                countDraws++;
                            }
                            if (g.isLeftParticipant(t.seed)) {
                                pointsForLeft += g.leftScore;
                            }
                            if (g.isRightParticipant(t.seed)) {
                                pointsForRight += g.rightScore;
                            }
                            if (g.isLeftParticipant(t.seed)) {
                                pointsAgainstLeft += g.rightScore;
                            }
                            if (g.isRightParticipant(t.seed)) {
                                pointsAgainstRight += g.leftScore;
                            }
                        }
                        pointsFor = pointsForLeft + pointsForRight;
                        pointsAgainst = pointsAgainstLeft + pointsAgainstRight;
                        pointsDifference = pointsFor - pointsAgainst;
                        return new TeamStats(t.seed, 0, t.name, t.handicap, countPlayed, countGames, countWins, countDraws, countLost, pointsFor, pointsAgainst, pointsDifference);
                    }
                };

        List<Team> teams = group.teams;
        List<TeamStats> unIndexed = Lists.transform(teams, teamToStats);
        teamStatsList = new ArrayList<TeamStats>(unIndexed);

        final int winFactor = 10000;
        final int drawFactor = 5000;
        Collections.sort(teamStatsList, new Comparator<TeamStats>() {
            @Override
            public int compare(TeamStats lhs, TeamStats rhs) {
                int left = lhs.countWins * winFactor + lhs.countDraws * drawFactor + lhs.pointsDifference;
                int right = rhs.countWins * winFactor + rhs.countDraws * drawFactor + rhs.pointsDifference;
                return Integer.valueOf(right).compareTo(left);
            }
        });

        for (int i = 0; i < teamStatsList.size(); i++) {
            TeamStats ts = teamStatsList.get(i);
            ts.seed = i + 1;
        }

        realm.close();
    }

}
