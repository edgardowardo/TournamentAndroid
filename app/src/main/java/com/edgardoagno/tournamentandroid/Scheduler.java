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
            pair.isBye = (home == null || away == null);
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


    ///
    /// Single elimination wrapper
    ///
    static Game[] singleElimination(Team[] teams) {
        return singleElimination(1, 1, teams);
    }

    ///
    /// Builds single elimination match schedule from a given set
    ///
    /// - Returns: a list of game matches in single elimination format.
    ///
    private static Game[] singleElimination(int round, int startIndex, Team[] teams) {

        int index = startIndex;
        ArrayList<Team> elements = new ArrayList<Team>(Arrays.asList(teams));
        ArrayList<Game> schedules = new ArrayList<Game>();

        //
        // base case
        //
        if (elements.size() > 64 || round >= elements.size()) {
            return schedules.toArray(new Game[schedules.size()]);
        }

        //
        // Adjust the number of teams necessary to construct the brackets which are 2, 4, 8, 16, 32 and 64
        //
        for (int i = 1; i <= 8; i++ ) {
            int minimum = (int)Math.pow(2, i);
            if (elements.size() < minimum) {
                int diff = minimum - elements.size();
                for (int j = 0; j < diff; j++) {
                    elements.add(null);
                }
                break;
            } else if (elements.size() == minimum) {
                break;
            }
        }

        //
        // process half the elements to create the pairs
        //
        int endIndex = elements.size() - 1;
        for (int i = elements.size() / 2 - 1; i >= 0; i--) {
            Team home = elements.get(i);
            Team away = elements.get(endIndex - i);
            Game game = new Game(round, index, home, away);
            game.isBye = (home == null || away == null);
            schedules.add(game);
            index++;
        }

        //
        // apply rainbow pairing for the new game winners instead of teams
        //
        Game[] newGames = futureSingleElimination(round + 1, index, schedules.toArray(new Game[schedules.size()]));
        schedules.addAll(new ArrayList<Game>(Arrays.asList(newGames)));
        return  schedules.toArray(new Game[schedules.size()]);
    }

    ///
    /// Builds single elimination match schedule from a given set
    ///
    /// - Returns: a game tree in single elimination format.
    ///
    private static Game[] futureSingleElimination(int round, int startIndex, Game[] games) {

        int index = startIndex;
        ArrayList<Game> schedules = new ArrayList<Game>();

        if (games.length < 2) {
            return schedules.toArray(new Game[schedules.size()]);
        }

        //
        // process all the game winners to create new games for the round
        //
        int endIndex = games.length - 1;
        for (int i = games.length / 2 - 1; i >= 0; i--) {
            Game prevHome = games[i];
            Game prevAway = games[endIndex - i];
            Game game = new Game();
            game.round = round;
            game.index = index;
            Elimination e = new Elimination(false, prevHome.index, prevAway.index);
            e.prevLeftGame = prevHome;
            e.prevRightGame = prevAway;
            game.elimination = e;

            index++;
            schedules.add(game);
        }

        //
        // apply rainbow pairing for the new game winners until the base case happens
        //
        Game[] newGames = futureSingleElimination(round + 1, index, schedules.toArray(new Game[schedules.size()]));
        schedules.addAll(new ArrayList<Game>(Arrays.asList(newGames)));
        return  schedules.toArray(new Game[schedules.size()]);
    }
}