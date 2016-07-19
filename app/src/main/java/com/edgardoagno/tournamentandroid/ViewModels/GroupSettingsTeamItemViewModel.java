package com.edgardoagno.tournamentandroid.ViewModels;

import com.edgardoagno.tournamentandroid.Models.Team;

/**
 * Created by edgardoagno on 18/07/16.
 */
public class GroupSettingsTeamItemViewModel {

    private Team team;

    public GroupSettingsTeamItemViewModel() {
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void setName(String name) {
        team.name = name;
    }

    public void setHandicap(String handicap) {
        try {
            team.handicap = Integer.parseInt(handicap);
        } catch (NumberFormatException e) {
            team.handicap = 0;
        }
    }

    public String getHandicap() {
        return Integer.toString(team.handicap);
    }
}
