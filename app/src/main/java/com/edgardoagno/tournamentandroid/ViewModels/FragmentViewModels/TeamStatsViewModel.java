package com.edgardoagno.tournamentandroid.ViewModels.FragmentViewModels;

import com.edgardoagno.tournamentandroid.Models.ScheduleType;

/**
 * Created by edgardoagno on 07/08/16.
 */
public class TeamStatsViewModel extends BaseStatsViewModel {

    public TeamStatsViewModel(Long _groupId) {
        super(_groupId);
    }

    public String getFooterText(int countGames) {
        String sentence1 = "Rotate landscape to show points (F)or, (A)gainst and (D)ifference.";
        if (group.getScheduleType() == ScheduleType.RoundRobin || group.getScheduleType() == ScheduleType.American) {
            return String.format("%1$s %2$s %3$s %4$s", sentence1, "Round Robin or American schedule shows a maximum of", countGames, "games to play per team in the P column.");
        }
        return sentence1;
    }
}
