package com.edgardoagno.tournamentandroid.Models;

import com.google.common.base.Strings;
import com.google.common.primitives.Ints;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by edgardoagno on 01/07/16.
 */
public enum ScheduleType {
    RoundRobin, American, SingleElimination, DoubleElimination;

    public CharSequence[] getAllowedTeamCounts() {
        switch (this) {
            case American: {
                ArrayList<CharSequence> teamCounts = new ArrayList<>();
                for (int i = 4; i < 33; i++) {
                    if (i % 4 != 2) {
                        teamCounts.add(Integer.toString(i));
                    }
                }
                CharSequence[] a = teamCounts.toArray(new CharSequence[teamCounts.size()]);
                return a;
            }
            case RoundRobin:
            case SingleElimination:
            case DoubleElimination: {
                ArrayList<CharSequence> teamCounts = new ArrayList<>();
                for (int i = 2; i < 33; i++) {
                    teamCounts.add(Integer.toString(i));
                }
                CharSequence[] a = teamCounts.toArray(new CharSequence[teamCounts.size()]);
                return a;
            }
        }
        CharSequence[] counts = {};
        return counts;
    }

    public String getIconNameWithText() {
        switch (this) {
            case RoundRobin: return "icon-round-robin-text";
            case American: return "icon-american-text";
            case SingleElimination: return "icon-single-e-text";
            case DoubleElimination: return "icon-double-e-text";
        }
        return "";
    }

    public String getIconName() {
        switch (this) {
            case RoundRobin: return "icon-roundrobin";
            case American: return "icon-robinpairs";
            case SingleElimination:
            case DoubleElimination: return "icon-single-e";
        }
        return "";
    }

    public String getDescription() {
        switch (this) {
            case RoundRobin: return "Round Robin";
            case American: return "American";
            case SingleElimination: return "Single Elimination";
            case DoubleElimination: return "Double Elimination";
        }
        return "";
    }

    public static ScheduleType[] getSchedules() {
        return new ScheduleType[]{
                ScheduleType.RoundRobin,
                ScheduleType.American,
                ScheduleType.SingleElimination,
                ScheduleType.DoubleElimination
        };
    }
}
