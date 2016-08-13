package com.edgardoagno.tournamentandroid.ViewModels.FragmentViewModels;

import com.edgardoagno.tournamentandroid.Models.Game;
import com.edgardoagno.tournamentandroid.Models.Group;
import com.edgardoagno.tournamentandroid.Models.ScheduleType;
import com.edgardoagno.tournamentandroid.ViewModels.BaseViewModel;

import io.realm.RealmResults;

/**
 * Created by edgardoagno on 30/07/16.
 */
public class GamesViewModel extends BaseViewModel {

    public RealmResults<Game> games;
    public Group group;

    public GamesViewModel(Long groupId, int round, Boolean isLosersRound) {
        group = realm.where(Group.class).equalTo("id", groupId).findFirst();
        if (group.getScheduleType() == ScheduleType.DoubleElimination) {
             games = group.games.where().equalTo("round", round).equalTo("elimination.isLoserBracket", isLosersRound).findAll();
        } else {
            games = group.games.where().equalTo("round", round).findAll();
        }
    }
}
