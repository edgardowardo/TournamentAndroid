package com.edgardoagno.tournamentandroid.ViewModels.FragmentViewModels;

import com.edgardoagno.tournamentandroid.Models.Game;
import com.edgardoagno.tournamentandroid.Models.Group;
import com.edgardoagno.tournamentandroid.Models.ScheduleType;
import com.edgardoagno.tournamentandroid.ViewModels.BaseViewModel;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.realm.RealmResults;

/**
 * Created by edgardoagno on 30/07/16.
 */
public class GamesTabViewModel extends BaseViewModel {

    private Group _group;
    private boolean _isLosersBracket;
    public String[] tabNames;

    // Constructor

    public GamesTabViewModel(Long groupId, Boolean isLosersBracket) {
        _isLosersBracket = isLosersBracket;
        _group = realm.where(Group.class).equalTo("id", groupId).findFirst();

        if ( _group.getScheduleType() == ScheduleType.DoubleElimination) {
            Function<Game, Integer> groupToInt =
                    new Function<Game, Integer>() {
                        @Override
                        public Integer apply(Game input) {
                            return input.round;
                        }
                    };

            List<Integer> roundsList = Lists.transform(_group.games.where().equalTo("elimination.isLoserBracket", isLosersBracket).findAll(), groupToInt);
            Set<Integer> uniqueRounds = new HashSet<Integer>(roundsList);

            Function<Integer, String> roundToTitle =
                    new Function<Integer, String>() {
                        @Override
                        public String apply(Integer input) {
                            return String.format("ROUND %1$s", input);
                        }
                    };

            Integer[] uniqueRoundsArray = uniqueRounds.toArray(new Integer[uniqueRounds.size()]);
            ArrayList<Integer> rounds = new ArrayList<Integer>(Arrays.asList(uniqueRoundsArray));
            Collections.sort(rounds, new Comparator<Integer>() {
                @Override
                public int compare(Integer lhs, Integer rhs) {
                    return lhs.compareTo(rhs);
                }
            });
            List<String> uniqueTitles = Lists.transform(rounds, roundToTitle);
            tabNames = uniqueTitles.toArray(new String[uniqueTitles.size()]);
        } else {
            int rounds = _group.distinctRounds();
            tabNames = new String[rounds];
            for (int i = 0; i < rounds ; i++) {
                tabNames[i] = String.format("ROUND %1$s", i + 1);
            }
        }
    }

    public int getRound(int index) {
        String title = tabNames[index];
        String trimmed = title.replace("ROUND ","");
        return Integer.parseInt(trimmed);
    }

    public int getIndexOfIncompletedRound() {
        for (String roundText: tabNames) {
            String trimmed = roundText.replace("ROUND ","");
            int round = Integer.parseInt(trimmed);
            RealmResults<Game> games = null;
            if ( _group.getScheduleType() == ScheduleType.DoubleElimination) {
                games = _group.games.where().equalTo("round", round).equalTo("elimination.isLoserBracket", _isLosersBracket).findAll();
            } else {
                games = _group.games.where().equalTo("round", round).findAll();
            }
            if (games != null) {
                int completedCount = games.where().isNotNull("winner").or().equalTo("isDraw", true).findAll().size();
                if (completedCount != games.size()) {
                    int incompleteRound = Arrays.asList(tabNames).indexOf(roundText);
                    return incompleteRound;
                }
            }
        }
        return tabNames.length - 1;
    }
}
