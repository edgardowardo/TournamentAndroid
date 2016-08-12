package com.edgardoagno.tournamentandroid.ViewModels.Fragments;

import com.edgardoagno.tournamentandroid.Models.TeamStats;
import com.edgardoagno.tournamentandroid.ViewModels.BaseViewModel;

/**
 * Created by edgardoagno on 12/08/16.
 */
public class TeamStatsItemViewModel extends BaseViewModel {

    private TeamStats teamStats;

    public TeamStatsItemViewModel(TeamStats item) {
        teamStats = item;
    }

    public String getRank() {
        return String.format("%1$s",teamStats.seed);
    }

    public String getRankDeltaDirection() {
        if (teamStats.seed < teamStats.oldSeed) {
            return "↑";
        } else if (teamStats.seed > teamStats.oldSeed) {
            return "↓";
        } else {
            return "";
        }
    }

    public String getRankDelta() {
        int number = teamStats.seed - teamStats.oldSeed;
        if (number < 0) {
            number *= -1;
        }
        if (teamStats.seed == teamStats.oldSeed) {
            return "";
        } else {
            return String.format("%1$s", number);
        }
    }

    public String getName() {
        return String.format("%1$s",teamStats.name);
    }

    public String getPlayed() {
        return String.format("%1$s",teamStats.countPlayed);
    }

    public String getWins() {
        return String.format("%1$s",teamStats.countWins);
    }

    public String getLost() {
        return String.format("%1$s",teamStats.countLost);
    }

    public String getDraws() {
        return String.format("%1$s",teamStats.countDraws);
    }

    public String getPointsFor() {
        return String.format("%1$s",teamStats.pointsFor);
    }

    public String getPointsAgainst() {
        return String.format("%1$s",teamStats.pointsAgainst);
    }

    public String getPointsDifference() {
        return String.format("%1$s",teamStats.pointsDifference);
    }
}
