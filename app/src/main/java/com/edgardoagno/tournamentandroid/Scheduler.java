package com.edgardoagno.tournamentandroid;

import android.util.Log;

import com.edgardoagno.tournamentandroid.Models.Game;
import com.edgardoagno.tournamentandroid.Models.Team;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by edgardoagno on 25/06/16.
 */
public class Scheduler {

    ///
    /// Round robin wrapper
    ///
    public static Game[] roundRobin(Team[] teams) {
        return Scheduler.roundRobin(1, 1, teams);
    }

    ////
    /// Builds a classic round robin schedule from a given set of elements.
    ///
    /// - Returns: a list of home versus away pairs in that order. [(round, index, home, away)]
    ///
    public static Game[] roundRobin(Integer round, Integer startIndex, Team[] teams) {

        Integer index = startIndex;
        ArrayList<Game> schedules = new ArrayList<Game>();
        ArrayList<Team> elements = new ArrayList<Team>(Arrays.asList(teams));

        //
        // if odd then add a bye
        //
        if (elements.size() % 2 != 0) {
            elements.add(null);
        }

        //
        // base case
        //
        if (round >= elements.size()) {
            return schedules.toArray(new Game[schedules.size()]);
        }

        //
        // process half the elements to create the pairs
        //
        Integer endIndex = elements.size() - 1;


        for (int i = elements.size() / 2 - 1; i >= 0; i--) {
        //for (int i = 0; i < teams.size() / 2; i++) {
            Team home = elements.get(i);
            Team away = elements.get(endIndex - i);
            Game pair = new Game(round, index++, home, away);
            schedules.add(pair);
        }

        //
        // shift the elements to process as the next row. the first element is fixed hence insert to position one.
        //
        Team displaced = elements.remove(elements.size() - 1);
        elements.add(1, displaced);
        Team[] allChange = elements.toArray(new Team[elements.size()]);

        //
        // Accumulate the games
        //
        Game[] newgames = roundRobin(round + 1, index, allChange);
        schedules.addAll(new ArrayList<Game>(Arrays.asList(newgames)));
        return  schedules.toArray(new Game[schedules.size()]);
    }
}