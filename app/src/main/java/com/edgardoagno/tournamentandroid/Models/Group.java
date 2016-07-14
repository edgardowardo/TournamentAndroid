package com.edgardoagno.tournamentandroid.Models;

import java.util.Date;
import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by edgardoagno on 01/07/16.
 */
public class Group extends RealmObject {

    @PrimaryKey
    public long id = System.currentTimeMillis();
    @Required
    public String name = "";
    public int teamCount = 4;
    public boolean isHandicap = false;
    private String scheduleTypeValue = ScheduleType.RoundRobin.toString();
    public RealmList<Team> teams = new RealmList<Team>();
    public RealmList<Game> games = new RealmList<Game>();

    public void setDefaultProperties() {
        id = System.currentTimeMillis();
        name = "";
        teamCount = 4;
        isHandicap = false;
        scheduleTypeValue = ScheduleType.RoundRobin.toString();
        teams = new RealmList<Team>();
        games = new RealmList<Game>();
    }

    public void setScheduleType(ScheduleType type) { this.scheduleTypeValue = type.toString(); }
    public ScheduleType getScheduleType() { return ScheduleType.valueOf(this.scheduleTypeValue);}
}
