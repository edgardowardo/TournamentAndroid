package com.edgardoagno.tournamentandroid.Models;

import java.util.UUID;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by edgardoagno on 25/06/16.
 */
public class Team extends RealmObject {

    @PrimaryKey
    public long id = System.currentTimeMillis();
    @Required
    public String name = "";
    public boolean isHandicapped = false;
    public int handicap = 0;
    public int seed = 0;

    public Team() {
        super();
    }

    public Team(String name) {
        super();
        this.name = name;
    }

    public Team(String name, boolean isHandicapped, int handicap, int seed) {
        super();
        this.name = name;
        this.isHandicapped = isHandicapped;
        this.handicap = handicap;
        this.seed = seed;
    }
}