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
    public String id = UUID.randomUUID().toString();
    public long createdOnMillis = System.currentTimeMillis();
    public String name = "";
    public boolean isHandicapped = false;
    public int handicap = 0;
    public int seed = 0;

    public void setDefaultProperties() {
        id = UUID.randomUUID().toString();
        createdOnMillis = System.currentTimeMillis();
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
}