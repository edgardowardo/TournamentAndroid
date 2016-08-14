package com.edgardoagno.tournamentandroid.ViewModels.FragmentViewModels;

import com.edgardoagno.tournamentandroid.Models.Group;
import com.edgardoagno.tournamentandroid.Models.TeamStats;
import com.edgardoagno.tournamentandroid.ViewModels.BaseViewModel;

import java.util.ArrayList;

/**
 * Created by edgardoagno on 14/08/16.
 */
public class ChartPieViewModel extends BaseViewModel {

    private Group group;
    private int chartPosition;

    public ChartPieViewModel() {
        super();
    }

    public ChartPieViewModel(Long groupId, int _chartPosition) {
        super();
        group = realm.where(Group.class).equalTo("id", groupId).findFirst();
        chartPosition = _chartPosition;
    }

    public boolean isHoleEnabled() {
        return chartPosition == 0;
    }

    public int getCountGames() {
        return group.games.size();
    }

    public ArrayList<String> getTitles() {
        ArrayList<String> titles = new ArrayList<String>();
        // COUNT PLAYED
        if (chartPosition == 0) {
            titles.add("Played");
            titles.add("Not played");
        }
        return titles;
    }

    public ArrayList<Integer> getValues() {
        ArrayList<Integer> values = new ArrayList<Integer>();
        // COUNT PLAYED
        if (chartPosition == 0) {
            values.add(this.getCountPlayed());
            values.add(this.getCountNotPlayed());
        }
        return values;
    }

    private int getCountNotPlayed() {
        return getCountGames() - getCountPlayed();
    }

    private int getCountPlayed() {
        return group.games
                .where()
                .equalTo("isDraw",true).or().isNotNull("winner")
                .findAll()
                .size();
    }
}
