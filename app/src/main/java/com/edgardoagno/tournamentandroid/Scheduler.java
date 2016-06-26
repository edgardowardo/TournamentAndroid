package com.edgardoagno.tournamentandroid;

import android.util.Log;

import com.edgardoagno.tournamentandroid.Models.DoublesInfo;
import com.edgardoagno.tournamentandroid.Models.Elimination;
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
    private static Game[] roundRobin(int round, Integer startIndex, Team[] teams) {

        int index = startIndex;
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
        int endIndex = elements.size() - 1;


        for (int i = elements.size() / 2 - 1; i >= 0; i--) {
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


    ///
    /// American Tournament Round robin wrapper
    ///
    public static Game[] americanTournament(Team[] teams) {
        return Scheduler.americanTournament(1, 1, teams);
    }

    ///
    /// Builds american tournament schedule from a given set. The characteristic of this scheme is that each
    /// individual player plays with multiple doubles partners. This permutation is the round robin scheme.
    ///
    /// - Returns: a list of home pairs and away pairs in that order. [(round, index, home1, home2, away1, away2)]
    ///
    private static Game[] americanTournament(int round, int startIndex, Team[] teams) {

        int index = startIndex;
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
        int topHalf = (elements.size() / 2) - 1;
        if (round >= elements.size() || elements.size() <= 3) {
            return schedules.toArray(new Game[schedules.size()]);
        }

        //
        // process half the elements to create the pairs
        //
        int endIndex = elements.size() - 1;

        while (topHalf > 0) {
            int i = topHalf;

            //
            // home pair
            //
            Team home1 = elements.get(i);
            Team home2 = elements.get(endIndex - i);

            //
            // away pair
            //
            Team away1 = elements.get(i - 1);
            Team away2 = elements.get(endIndex - (i - 1));

            if (home1 == null || home2 == null || away1 == null || away2 == null) {
                break;
            }
            Game pair = new Game(round, index, home1, away1);
            pair.doublesInfo = new DoublesInfo(home2, away2);
            schedules.add(pair);

            index++;
            topHalf-=2;
        }

        //
        // shift the elements to process as the next row. the last element is fixed hence, displaced is minus two.
        //
        Team displaced = elements.remove(elements.size() - 2);
        elements.add(0, displaced);
        Team[] allChange = elements.toArray(new Team[elements.size()]);

        //
        // Accumulate the games
        //
        Game[] newGames = americanTournament(round + 1, index, allChange);
        schedules.addAll(new ArrayList<Game>(Arrays.asList(newGames)));
        return  schedules.toArray(new Game[schedules.size()]);
    }
}