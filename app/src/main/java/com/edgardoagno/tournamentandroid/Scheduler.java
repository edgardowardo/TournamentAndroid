package com.edgardoagno.tournamentandroid;

import android.util.Log;

import com.edgardoagno.tournamentandroid.Models.DoublesInfo;
import com.edgardoagno.tournamentandroid.Models.Elimination;
import com.edgardoagno.tournamentandroid.Models.Game;
import com.edgardoagno.tournamentandroid.Models.Team;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

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
    public static Game[] singleElimination(Team[] teams) {
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
            Elimination e = new Elimination(false, 0, 0);
            game.elimination = e;
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

    ///
    /// Double elimination wrapper
    ///
    public static Game[] doubleElimination(Team[] teams) {
        return doubleElimination(1, 1, teams);
    }

    ///
    /// Builds double elimination match schedule from a given set
    ///
    /// - Returns: a list of game matches in double elimination format.
    ///
    private static Game[] doubleElimination(int round, int startIndex, Team[] teams) {

        ArrayList<Team> elements = new ArrayList<Team>(Arrays.asList(teams));
        ArrayList<Game> schedules = new ArrayList<Game>();

        //
        // If two teams, make it 4 beacause it needs 4 to make the losers bracket
        //
        if (elements.size() == 2) {
            for (int i = 3; i < 5; i++) {
                elements.add(null);
            }
        }

        //
        // Build single elimination tree aka winners bracket
        //
        Game[] winnersBracket = singleElimination(elements.toArray(new Team[elements.size()]));
        schedules.addAll(new ArrayList<Game>(Arrays.asList(winnersBracket)));

        //
        // Remember the last loser from the last game of the winners bracket
        //
        Game lastWinnersGame = winnersBracket[winnersBracket.length - 1];

        //
        // Build losers bracket and accumulate in schedules
        //
        Game[] losersBracket = createLosersBracket(winnersBracket, false, round, round + 1);
        schedules.addAll(new ArrayList<Game>(Arrays.asList(losersBracket)));

        //
        // Find the last winner of the losers bracket and add the final game
        //
        Game lastLosersGame = losersBracket[losersBracket.length - 1];

        Game home = lastWinnersGame;
        Game away = lastLosersGame;
        if (!home.getIsLoserBracket() && away.getIsLoserBracket()) {
            int index = schedules.size() + 1;
            Game game = new Game(home.round + 1, index, null, null);
            Elimination elimination = new Elimination(false, home.index, away.index);
            elimination.prevLeftGame = home;
            elimination.prevRightGame = away;
            game.elimination = elimination;
            schedules.add(game);
        }

        return  schedules.toArray(new Game[schedules.size()]);
    }

    ///
    /// Builds the loser bracket of double elimination match schedule
    ///
    /// - Returns: a list of game matches of the losers bracket.
    ///
    private static Game[] createLosersBracket(Game[] fromBracket, boolean whereBracketIsLoser, int withWinnersRound, int orLosersRound) {

        final boolean isLoserBracket = whereBracketIsLoser;
        final int winnersRound = withWinnersRound;
        int losersRound = orLosersRound;
        ArrayList<Game> survivors = new ArrayList<Game>();
        final int round = (isLoserBracket) ? losersRound - 1 : winnersRound;

        //
        // The first loser index determines progress of a game on the losers bracket. When the index of a previous game is lower than this number,
        // it comes from the winners bracket and hence interested in the losing team. Otherwise higher or equal to this index of a previous game,
        // we are interested to the winner of this loser game.
        //
        int firstLoserIndex = Integer.MAX_VALUE;
        List<Game> bracket = new ArrayList<Game>(Arrays.asList(fromBracket));
        Predicate<Game> gameWinnersPredicate = new Predicate<Game>() {
            @Override
            public boolean apply(Game game) {
                return  !game.getIsLoserBracket();
            }
        };
        Collection<Game> winnerGames = Collections2.filter(bracket, gameWinnersPredicate);
        Game lastWinnerGame = Iterables.getLast(winnerGames, null);
        if (lastWinnerGame != null) {
            firstLoserIndex = lastWinnerGame.index + 1;
        }

        //
        // Look for games on the previous round
        //
        Predicate<Game> previousRoundPredicate = new Predicate<Game>() {
            @Override
            public boolean apply(Game game) {
                return (game.round == round && game.getIsLoserBracket() == isLoserBracket);
            }
        };
        Collection<Game> games = Collections2.filter(bracket, previousRoundPredicate);
        if (games.size() <= 1) {
            return survivors.toArray(new Game[survivors.size()]);
        }
        class OrderingByGameIndex extends Ordering<Game> {
            @Override
            public int compare(Game g, Game h) {
                return Ints.compare(g.index, h.index);
            }
        }
        Game[] gameArray = games.toArray(new Game[games.size()]);
        ArrayList<Game> sortedGames = new ArrayList<Game>(Arrays.asList(gameArray));
        Collections.sort(sortedGames, new OrderingByGameIndex());
        int index = bracket.size();


        //
        // Look for losers for previous round and create games sequentially (no rainbows)
        //
        int i = 0;
        while (i < sortedGames.size() - 1) {
            index++;
            Game prevHome = sortedGames.get(i);
            Game prevAway = sortedGames.get(i+1);
            Game game = new Game(losersRound, index, null, null);
            Elimination elimination = new Elimination(true, prevHome.index, prevAway.index);
            elimination.firstLoserIndex = firstLoserIndex;
            elimination.prevLeftGame = prevHome;
            elimination.prevRightGame = prevAway;
            game.elimination = elimination;
            survivors.add(game);
            i+=2;
        }

        //
        // Look for losers for the next round of winners bracket and match them with winners in this current round of games
        //
        final int nextWinnersRound = withWinnersRound + 1;
        Predicate<Game> newLosersPredicate = new Predicate<Game>() {
            @Override
            public boolean apply(Game game) {
                return (game.round == nextWinnersRound && !game.getIsLoserBracket());
            }
        };
        Collection<Game> newLosers = Collections2.filter(bracket, newLosersPredicate);
        if (newLosers.size() == 0 || newLosers.size() != survivors.size()) {
            return new Game[0];
        }

        Game[] newLosersArray = newLosers.toArray(new Game[newLosers.size()]);
        ArrayList<Game> sortedNewLosers = new ArrayList<Game>(Arrays.asList(newLosersArray));
        Collections.sort(sortedNewLosers, new OrderingByGameIndex());

        //
        // Create padded rounds as result of matching the new losers of winners round and survivors
        //
        losersRound++;
        int survivorsSize = survivors.size();
        for (int j = 0; j <= survivorsSize - 1; j++) {
            index++;
            Game newLoser = sortedNewLosers.get(j);
            Game survivor = survivors.get(j);
            Game game = new Game(losersRound, index, null, null);
            Elimination elimination = new Elimination(true, newLoser.index, survivor.index);
            elimination.firstLoserIndex = firstLoserIndex;
            elimination.prevLeftGame = newLoser;
            elimination.prevRightGame = survivor;
            game.elimination = elimination;
            survivors.add(game);
        }

        //
        // Increment losers round again to create the next branch of the brackets
        //
        losersRound++;
        ArrayList<Game> allGames = new ArrayList<Game>();
        allGames.addAll(survivors);
        allGames.addAll(bracket);
        Game[] losersBracket = createLosersBracket(allGames.toArray(new Game[allGames.size()]), true, nextWinnersRound,  losersRound);
        ArrayList<Game> returnBrackets = new ArrayList<Game>();
        returnBrackets.addAll(survivors);
        returnBrackets.addAll(new ArrayList<Game>(Arrays.asList(losersBracket)));

        return returnBrackets.toArray(new Game[returnBrackets.size()]);
    }
}