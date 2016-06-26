package com.edgardoagno.tournamentandroid.Models;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by edgardoagno on 25/06/16.
 */
public class DoublesInfo extends RealmObject {

    @PrimaryKey
    public String id = UUID.randomUUID().toString();
    public Team leftTeam;
    public Team rightTeam;

    public DoublesInfo() {
        super();
    }

    public DoublesInfo(Team leftTeam, Team rightTeam) {
        super();
        this.leftTeam = leftTeam;
        this.rightTeam = rightTeam;
    }
}