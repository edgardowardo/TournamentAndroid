package com.edgardoagno.tournamentandroid.Models;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public int distinctRounds() {
        Function<Game, Integer> groupToInt =
                new Function<Game, Integer>() {
                    @Override
                    public Integer apply(Game input) {
                        return input.round;
                    }
                };
        List<Integer> roundsList = Lists.transform(games, groupToInt);
        Set<Integer> uniqueRounds = new HashSet<Integer>(roundsList);
        return uniqueRounds.size();
    }
}
