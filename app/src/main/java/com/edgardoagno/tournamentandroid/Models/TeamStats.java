package com.edgardoagno.tournamentandroid.Models;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by edgardoagno on 01/07/16.
 */
public class TeamStats extends RealmObject {
    @PrimaryKey
    public String id = UUID.randomUUID().toString();
    public int oldSeed;
    public int seed;
    public String name;
    public int handicap;
    public int countPlayed;
    public int countGames;
    public int countWins;
    public int countDraws;
    public int countLost;
    public int pointsFor;
    public int pointsAgainst;
    public int pointsDifference;

    public TeamStats() {
        super();
    }

    public TeamStats(int oldSeed1, int seed1, String name1, int handicap1, int countPlayed1, int countGames1, int countWins1, int countDraws1, int countLost1, int pointsFor1, int pointsAgainst1, int pointsDifference1) {
        super();
        oldSeed = oldSeed1;
        seed = seed1;
        name = name1;
        handicap = handicap1;
        countPlayed = countPlayed1;
        countGames = countGames1;
        countWins = countWins1;
        countDraws = countDraws1;
        countLost = countLost1;
        pointsFor = pointsFor1;
        pointsAgainst = pointsAgainst1;
        pointsDifference = pointsDifference1;
    }

}
