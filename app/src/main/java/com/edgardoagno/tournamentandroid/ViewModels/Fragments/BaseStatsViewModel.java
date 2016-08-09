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

/**
 * Created by edgardoagno on 09/08/16.
 */
public class BaseStatsViewModel extends BaseViewModel {

    public ArrayList<TeamStats> teamStatsList;
    protected Group _group;

    public BaseStatsViewModel() {
        super();
    }

    public BaseStatsViewModel(Long groupId) {
        super();
        _group = realm.where(Group.class).equalTo("id", groupId).findFirst();
    }

    public void loadStatsList() {
        Function<Team, TeamStats> teamToStats =
                new Function<Team, TeamStats>() {
                    @Override
                    public TeamStats apply(Team t) {
                        int countPlayed = _group.games.where()
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

                        int countGames = _group.games.where()
                                .beginGroup()
                                .equalTo("leftTeam.seed", t.seed)
                                .or().equalTo("rightTeam.seed", t.seed)
                                .or().equalTo("doublesInfo.leftTeam.seed", t.seed)
                                .or().equalTo("doublesInfo.rightTeam.seed", t.seed)
                                .endGroup()
                                .findAll().size();

                        int pointsForLeft = 0, pointsForRight = 0, pointsAgainstLeft = 0, pointsAgainstRight = 0;
                        int countWins = 0, countDraws = 0, countLost = 0, pointsFor = 0, pointsAgainst = 0, pointsDifference = 0;

                        for (Game g : _group.games) {
                            if (g.isWinner(t.seed)) {
                                countWins++;
                            }
                            if (g.isParticipant(t.seed) && g.getIsDraw()) {
                                countDraws++;
                            }
                            if (g.isParticipant(t.seed) && g.isWinner(t.seed) == false) {
                                countLost++;
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

        List<Team> teams =_group.teams;
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
    }

}
