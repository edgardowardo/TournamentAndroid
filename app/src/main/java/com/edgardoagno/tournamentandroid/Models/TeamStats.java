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
}
