package com.edgardoagno.tournamentandroid.Models;

import java.util.UUID;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by edgardoagno on 25/06/16.
 */
public class Team extends RealmObject implements Comparable<Team> {

    @PrimaryKey
    public String id = UUID.randomUUID().toString();
    public String name = "";
    public boolean isHandicapped = false;
    public int handicap = 0;
    public int seed = 0;

    public void setDefaultProperties() {
        id = UUID.randomUUID().toString();
        name = "";
        isHandicapped = false;
        handicap = 0;
        seed = 0;
    }

    public Team() {
        super();
    }

    public Team(String name) {
        super();
        this.name = name;
    }

    public int compareTo(Team team2) {
        return this.seed - team2.seed;
    }
}