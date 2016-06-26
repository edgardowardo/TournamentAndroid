package com.edgardoagno.tournamentandroid;

import com.edgardoagno.tournamentandroid.Models.Game;
import com.edgardoagno.tournamentandroid.Models.Team;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class UnitTest_Scheduler {

    @Test
    public void americanTournament_isCorrect_4() throws Exception {
        Team[] teams = {new Team("1"), new Team("2"), new Team("3"), new Team("4")};
        Game[] m = Scheduler.americanTournament(teams);
        assertEquals(m.length, 3);

        assertEquals(m[0].round, 1);
        assertEquals(m[1].round, 2);
        assertEquals(m[2].round, 3);

        assertEquals(m[0].index, 1);
        assertEquals(m[1].index, 2);
        assertEquals(m[2].index, 3);

        assertEquals(m[0].leftTeam.name, "2");
        assertEquals(m[0].doublesInfo.leftTeam.name, "3");
        assertEquals(m[0].rightTeam.name, "1");
        assertEquals(m[0].doublesInfo.rightTeam.name, "4");

        assertEquals(m[1].leftTeam.name, "1");
        assertEquals(m[1].doublesInfo.leftTeam.name, "2");
        assertEquals(m[1].rightTeam.name, "3");
        assertEquals(m[1].doublesInfo.rightTeam.name, "4");

        assertEquals(m[2].leftTeam.name, "3");
        assertEquals(m[2].doublesInfo.leftTeam.name, "1");
        assertEquals(m[2].rightTeam.name, "2");
        assertEquals(m[2].doublesInfo.rightTeam.name, "4");
    }

    @Test
    public void americanTournament_isCorrect_5() throws Exception {
        Team[] teams = {new Team("1"), new Team("2"), new Team("3"), new Team("4"), new Team("5")};
        Game[] m = Scheduler.americanTournament(teams);
        assertEquals(m.length, 5);

        assertEquals(m[0].round, 1);
        assertEquals(m[1].round, 2);
        assertEquals(m[2].round, 3);
        assertEquals(m[3].round, 4);
        assertEquals(m[4].round, 5);

        assertEquals(m[0].index, 1);
        assertEquals(m[1].index, 2);
        assertEquals(m[2].index, 3);
        assertEquals(m[3].index, 4);
        assertEquals(m[4].index, 5);

        assertEquals(m[0].leftTeam.name, "3");
        assertEquals(m[0].doublesInfo.leftTeam.name, "4");
        assertEquals(m[0].rightTeam.name, "2");
        assertEquals(m[0].doublesInfo.rightTeam.name, "5");

        assertEquals(m[1].leftTeam.name, "2");
        assertEquals(m[1].doublesInfo.leftTeam.name, "3");
        assertEquals(m[1].rightTeam.name, "1");
        assertEquals(m[1].doublesInfo.rightTeam.name, "4");

        assertEquals(m[2].leftTeam.name, "1");
        assertEquals(m[2].doublesInfo.leftTeam.name, "2");
        assertEquals(m[2].rightTeam.name, "5");
        assertEquals(m[2].doublesInfo.rightTeam.name, "3");

        assertEquals(m[3].leftTeam.name, "5");
        assertEquals(m[3].doublesInfo.leftTeam.name, "1");
        assertEquals(m[3].rightTeam.name, "4");
        assertEquals(m[3].doublesInfo.rightTeam.name, "2");

        assertEquals(m[4].leftTeam.name, "4");
        assertEquals(m[4].doublesInfo.leftTeam.name, "5");
        assertEquals(m[4].rightTeam.name, "3");
        assertEquals(m[4].doublesInfo.rightTeam.name, "1");
    }

    @Test
    public void americanTournament_isCorrect_6() throws Exception {
        Team[] teams = {new Team("1"), new Team("2"), new Team("3"), new Team("4"), new Team("5"), new Team("6")};
        Game[] m = Scheduler.americanTournament(teams);
        assertEquals(m.length, 5);

        assertEquals(m[0].round, 1);
        assertEquals(m[1].round, 2);
        assertEquals(m[2].round, 3);
        assertEquals(m[3].round, 4);
        assertEquals(m[4].round, 5);

        assertEquals(m[0].index, 1);
        assertEquals(m[1].index, 2);
        assertEquals(m[2].index, 3);
        assertEquals(m[3].index, 4);
        assertEquals(m[4].index, 5);

        assertEquals(m[0].leftTeam.name, "3");
        assertEquals(m[0].doublesInfo.leftTeam.name, "4");
        assertEquals(m[0].rightTeam.name, "2");
        assertEquals(m[0].doublesInfo.rightTeam.name, "5");

        assertEquals(m[1].leftTeam.name, "2");
        assertEquals(m[1].doublesInfo.leftTeam.name, "3");
        assertEquals(m[1].rightTeam.name, "1");
        assertEquals(m[1].doublesInfo.rightTeam.name, "4");

        assertEquals(m[2].leftTeam.name, "1");
        assertEquals(m[2].doublesInfo.leftTeam.name, "2");
        assertEquals(m[2].rightTeam.name, "5");
        assertEquals(m[2].doublesInfo.rightTeam.name, "3");

        assertEquals(m[3].leftTeam.name, "5");
        assertEquals(m[3].doublesInfo.leftTeam.name, "1");
        assertEquals(m[3].rightTeam.name, "4");
        assertEquals(m[3].doublesInfo.rightTeam.name, "2");

        assertEquals(m[4].leftTeam.name, "4");
        assertEquals(m[4].doublesInfo.leftTeam.name, "5");
        assertEquals(m[4].rightTeam.name, "3");
        assertEquals(m[4].doublesInfo.rightTeam.name, "1");
    }

    @Test
    public void americanTournament_isCorrect_7() throws Exception {
        Team[] teams = {new Team("1"), new Team("2"), new Team("3"), new Team("4"), new Team("5"), new Team("6"), new Team("7")};
        Game[] m = Scheduler.americanTournament(teams);
        assertEquals(m.length, 7);

        assertEquals(m[0].round, 1);
        assertEquals(m[1].round, 2);
        assertEquals(m[2].round, 3);
        assertEquals(m[3].round, 4);
        assertEquals(m[4].round, 5);
        assertEquals(m[5].round, 6);
        assertEquals(m[6].round, 7);

        assertEquals(m[0].index, 1);
        assertEquals(m[1].index, 2);
        assertEquals(m[2].index, 3);
        assertEquals(m[3].index, 4);
        assertEquals(m[4].index, 5);
        assertEquals(m[5].index, 6);
        assertEquals(m[6].index, 7);

        assertEquals(m[0].leftTeam.name, "4");
        assertEquals(m[0].doublesInfo.leftTeam.name, "5");
        assertEquals(m[0].rightTeam.name, "3");
        assertEquals(m[0].doublesInfo.rightTeam.name, "6");

        assertEquals(m[1].leftTeam.name, "3");
        assertEquals(m[1].doublesInfo.leftTeam.name, "4");
        assertEquals(m[1].rightTeam.name, "2");
        assertEquals(m[1].doublesInfo.rightTeam.name, "5");

        assertEquals(m[2].leftTeam.name, "2");
        assertEquals(m[2].doublesInfo.leftTeam.name, "3");
        assertEquals(m[2].rightTeam.name, "1");
        assertEquals(m[2].doublesInfo.rightTeam.name, "4");

        assertEquals(m[3].leftTeam.name, "1");
        assertEquals(m[3].doublesInfo.leftTeam.name, "2");
        assertEquals(m[3].rightTeam.name, "7");
        assertEquals(m[3].doublesInfo.rightTeam.name, "3");

        assertEquals(m[4].leftTeam.name, "7");
        assertEquals(m[4].doublesInfo.leftTeam.name, "1");
        assertEquals(m[4].rightTeam.name, "6");
        assertEquals(m[4].doublesInfo.rightTeam.name, "2");

        assertEquals(m[5].leftTeam.name, "6");
        assertEquals(m[5].doublesInfo.leftTeam.name, "7");
        assertEquals(m[5].rightTeam.name, "5");
        assertEquals(m[5].doublesInfo.rightTeam.name, "1");

        assertEquals(m[6].leftTeam.name, "5");
        assertEquals(m[6].doublesInfo.leftTeam.name, "6");
        assertEquals(m[6].rightTeam.name, "4");
        assertEquals(m[6].doublesInfo.rightTeam.name, "7");
    }

    @Test
    public void americanTournament_isCorrect_8() throws Exception {
        Team[] teams = {new Team("1"), new Team("2"), new Team("3"), new Team("4"), new Team("5"), new Team("6"), new Team("7"), new Team("8")};
        Game[] m = Scheduler.americanTournament(teams);
        assertEquals(m.length, 14);

        assertEquals(m[0].round, 1);
        assertEquals(m[1].round, 1);
        assertEquals(m[2].round, 2);
        assertEquals(m[3].round, 2);
        assertEquals(m[4].round, 3);
        assertEquals(m[5].round, 3);
        assertEquals(m[6].round, 4);
        assertEquals(m[7].round, 4);
        assertEquals(m[8].round, 5);
        assertEquals(m[9].round, 5);
        assertEquals(m[10].round, 6);
        assertEquals(m[11].round, 6);
        assertEquals(m[12].round, 7);
        assertEquals(m[13].round, 7);

        assertEquals(m[0].index, 1);
        assertEquals(m[1].index, 2);
        assertEquals(m[2].index, 3);
        assertEquals(m[3].index, 4);
        assertEquals(m[4].index, 5);
        assertEquals(m[5].index, 6);
        assertEquals(m[6].index, 7);
        assertEquals(m[7].index, 8);
        assertEquals(m[8].index, 9);
        assertEquals(m[9].index, 10);
        assertEquals(m[10].index, 11);
        assertEquals(m[11].index, 12);
        assertEquals(m[12].index, 13);
        assertEquals(m[13].index, 14);

        assertEquals(m[0].leftTeam.name, "4");
        assertEquals(m[0].doublesInfo.leftTeam.name, "5");
        assertEquals(m[0].rightTeam.name, "3");
        assertEquals(m[0].doublesInfo.rightTeam.name, "6");

        assertEquals(m[1].leftTeam.name, "2");
        assertEquals(m[1].doublesInfo.leftTeam.name, "7");
        assertEquals(m[1].rightTeam.name, "1");
        assertEquals(m[1].doublesInfo.rightTeam.name, "8");

        assertEquals(m[2].leftTeam.name, "3");
        assertEquals(m[2].doublesInfo.leftTeam.name, "4");
        assertEquals(m[2].rightTeam.name, "2");
        assertEquals(m[2].doublesInfo.rightTeam.name, "5");

        assertEquals(m[3].leftTeam.name, "1");
        assertEquals(m[3].doublesInfo.leftTeam.name, "6");
        assertEquals(m[3].rightTeam.name, "7");
        assertEquals(m[3].doublesInfo.rightTeam.name, "8");

        assertEquals(m[4].leftTeam.name, "2");
        assertEquals(m[4].doublesInfo.leftTeam.name, "3");
        assertEquals(m[4].rightTeam.name, "1");
        assertEquals(m[4].doublesInfo.rightTeam.name, "4");

        assertEquals(m[5].leftTeam.name, "7");
        assertEquals(m[5].doublesInfo.leftTeam.name, "5");
        assertEquals(m[5].rightTeam.name, "6");
        assertEquals(m[5].doublesInfo.rightTeam.name, "8");

        assertEquals(m[6].leftTeam.name, "1");
        assertEquals(m[6].doublesInfo.leftTeam.name, "2");
        assertEquals(m[6].rightTeam.name, "7");
        assertEquals(m[6].doublesInfo.rightTeam.name, "3");

        assertEquals(m[7].leftTeam.name, "6");
        assertEquals(m[7].doublesInfo.leftTeam.name, "4");
        assertEquals(m[7].rightTeam.name, "5");
        assertEquals(m[7].doublesInfo.rightTeam.name, "8");

        assertEquals(m[8].leftTeam.name, "7");
        assertEquals(m[8].doublesInfo.leftTeam.name, "1");
        assertEquals(m[8].rightTeam.name, "6");
        assertEquals(m[8].doublesInfo.rightTeam.name, "2");

        assertEquals(m[9].leftTeam.name, "5");
        assertEquals(m[9].doublesInfo.leftTeam.name, "3");
        assertEquals(m[9].rightTeam.name, "4");
        assertEquals(m[9].doublesInfo.rightTeam.name, "8");

        assertEquals(m[10].leftTeam.name, "6");
        assertEquals(m[10].doublesInfo.leftTeam.name, "7");
        assertEquals(m[10].rightTeam.name, "5");
        assertEquals(m[10].doublesInfo.rightTeam.name, "1");

        assertEquals(m[11].leftTeam.name, "4");
        assertEquals(m[11].doublesInfo.leftTeam.name, "2");
        assertEquals(m[11].rightTeam.name, "3");
        assertEquals(m[11].doublesInfo.rightTeam.name, "8");

        assertEquals(m[12].leftTeam.name, "5");
        assertEquals(m[12].doublesInfo.leftTeam.name, "6");
        assertEquals(m[12].rightTeam.name, "4");
        assertEquals(m[12].doublesInfo.rightTeam.name, "7");

        assertEquals(m[13].leftTeam.name, "3");
        assertEquals(m[13].doublesInfo.leftTeam.name, "1");
        assertEquals(m[13].rightTeam.name, "2");
        assertEquals(m[13].doublesInfo.rightTeam.name, "8");
    }

    @Test
    public void roundRobin_isCorrect_3() throws Exception {
        Team[] teams = {new Team("1"), new Team("2"), new Team("3")};
        Game[] m = Scheduler.roundRobin(teams);
        assertEquals(m.length, 6);

        assertEquals(m[0].round, 1);
        assertEquals(m[1].round, 1);
        assertEquals(m[2].round, 2);
        assertEquals(m[3].round, 2);
        assertEquals(m[4].round, 3);
        assertEquals(m[5].round, 3);

        assertEquals(m[0].index, 1);
        assertEquals(m[1].index, 2);
        assertEquals(m[2].index, 3);
        assertEquals(m[3].index, 4);
        assertEquals(m[4].index, 5);
        assertEquals(m[5].index, 6);

        assertEquals(m[0].leftTeam.name, "2");
        assertEquals(m[0].rightTeam.name, "3");
        assertEquals(m[1].leftTeam.name, "1");
        assertNull(m[1].rightTeam);
        assertNull(m[2].leftTeam);
        assertEquals(m[2].rightTeam.name, "2");
        assertEquals(m[3].leftTeam.name, "1");
        assertEquals(m[3].rightTeam.name, "3");
        assertEquals(m[4].leftTeam.name, "3");
        assertNull(m[4].rightTeam);
        assertEquals(m[5].leftTeam.name, "1");
        assertEquals(m[5].rightTeam.name, "2");
    }
}