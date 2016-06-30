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

        assertEquals(m[0].isBye, false);
        assertEquals(m[1].isBye, false);
        assertEquals(m[2].isBye, false);

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

        assertEquals(m[0].isBye, false);
        assertEquals(m[1].isBye, false);
        assertEquals(m[2].isBye, false);
        assertEquals(m[3].isBye, false);
        assertEquals(m[4].isBye, false);

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

        assertEquals(m[0].isBye, false);
        assertEquals(m[1].isBye, false);
        assertEquals(m[2].isBye, false);
        assertEquals(m[3].isBye, false);
        assertEquals(m[4].isBye, false);

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

        assertEquals(m[0].isBye, false);
        assertEquals(m[1].isBye, false);
        assertEquals(m[2].isBye, false);
        assertEquals(m[3].isBye, false);
        assertEquals(m[4].isBye, false);
        assertEquals(m[5].isBye, false);
        assertEquals(m[6].isBye, false);

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

        assertEquals(m[0].isBye, false);
        assertEquals(m[1].isBye, false);
        assertEquals(m[2].isBye, false);
        assertEquals(m[3].isBye, false);
        assertEquals(m[4].isBye, false);
        assertEquals(m[5].isBye, false);
        assertEquals(m[6].isBye, false);
        assertEquals(m[7].isBye, false);
        assertEquals(m[8].isBye, false);
        assertEquals(m[9].isBye, false);
        assertEquals(m[10].isBye, false);
        assertEquals(m[11].isBye, false);
        assertEquals(m[12].isBye, false);
        assertEquals(m[13].isBye, false);

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
    public void roundRobin_isCorrect_2() throws Exception {
        Team[] teams = {new Team("1"), new Team("2")};
        Game[] m = Scheduler.roundRobin(teams);
        assertEquals(m.length, 1);

        assertEquals(m[0].round, 1);
        assertEquals(m[0].index, 1);
        assertEquals(m[0].leftTeam.name, "1");
        assertEquals(m[0].rightTeam.name, "2");
        assertEquals(m[0].isBye, false);
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

        assertEquals(m[0].isBye, false);
        assertEquals(m[0].leftTeam.name, "2");
        assertEquals(m[0].rightTeam.name, "3");

        assertEquals(m[1].isBye, true);
        assertEquals(m[1].leftTeam.name, "1");
        assertNull(m[1].rightTeam);

        assertEquals(m[2].isBye, true);
        assertNull(m[2].leftTeam);
        assertEquals(m[2].rightTeam.name, "2");

        assertEquals(m[3].isBye, false);
        assertEquals(m[3].leftTeam.name, "1");
        assertEquals(m[3].rightTeam.name, "3");

        assertEquals(m[4].isBye, true);
        assertEquals(m[4].leftTeam.name, "3");
        assertNull(m[4].rightTeam);

        assertEquals(m[5].isBye, false);
        assertEquals(m[5].leftTeam.name, "1");
        assertEquals(m[5].rightTeam.name, "2");
    }

    @Test
    public void roundRobin_isCorrect_4() throws Exception {
        Team[] teams = {new Team("1"), new Team("2"), new Team("3"), new Team("4")};
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

        assertEquals(m[0].isBye, false);
        assertEquals(m[1].isBye, false);
        assertEquals(m[2].isBye, false);
        assertEquals(m[3].isBye, false);
        assertEquals(m[4].isBye, false);
        assertEquals(m[5].isBye, false);

        assertEquals(m[0].leftTeam.name, "2");
        assertEquals(m[0].rightTeam.name, "3");
        assertEquals(m[1].leftTeam.name, "1");
        assertEquals(m[1].rightTeam.name, "4");
        assertEquals(m[2].leftTeam.name, "4");
        assertEquals(m[2].rightTeam.name, "2");
        assertEquals(m[3].leftTeam.name, "1");
        assertEquals(m[3].rightTeam.name, "3");
        assertEquals(m[4].leftTeam.name, "3");
        assertEquals(m[4].rightTeam.name, "4");
        assertEquals(m[5].leftTeam.name, "1");
        assertEquals(m[5].rightTeam.name, "2");
    }

    @Test
    public void roundRobin_isCorrect_5() throws Exception {
        Team[] teams = {new Team("1"), new Team("2"), new Team("3"), new Team("4"), new Team("5")};
        Game[] m = Scheduler.roundRobin(teams);
        assertEquals(m.length, 15);

        assertEquals(m[0].round, 1);
        assertEquals(m[1].round, 1);
        assertEquals(m[2].round, 1);
        assertEquals(m[3].round, 2);
        assertEquals(m[4].round, 2);
        assertEquals(m[5].round, 2);
        assertEquals(m[6].round, 3);
        assertEquals(m[7].round, 3);
        assertEquals(m[8].round, 3);
        assertEquals(m[9].round, 4);
        assertEquals(m[10].round, 4);
        assertEquals(m[11].round, 4);
        assertEquals(m[12].round, 5);
        assertEquals(m[13].round, 5);
        assertEquals(m[14].round, 5);

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
        assertEquals(m[14].index, 15);

        assertEquals(m[0].isBye, false);
        assertEquals(m[0].leftTeam.name, "3");
        assertEquals(m[0].rightTeam.name, "4");

        assertEquals(m[1].isBye, false);
        assertEquals(m[1].leftTeam.name, "2");
        assertEquals(m[1].rightTeam.name, "5");

        assertEquals(m[2].isBye, true);
        assertEquals(m[2].leftTeam.name, "1");
        assertNull(m[2].rightTeam);

        assertEquals(m[3].isBye, false);
        assertEquals(m[3].leftTeam.name, "2");
        assertEquals(m[3].rightTeam.name, "3");

        assertEquals(m[4].isBye, true);
        assertNull(m[4].leftTeam);
        assertEquals(m[4].rightTeam.name, "4");

        assertEquals(m[5].isBye, false);
        assertEquals(m[5].leftTeam.name, "1");
        assertEquals(m[5].rightTeam.name, "5");

        assertEquals(m[6].isBye, true);
        assertNull(m[6].leftTeam);
        assertEquals(m[6].rightTeam.name, "2");

        assertEquals(m[7].isBye, false);
        assertEquals(m[7].leftTeam.name, "5");
        assertEquals(m[7].rightTeam.name, "3");

        assertEquals(m[8].isBye, false);
        assertEquals(m[8].leftTeam.name, "1");
        assertEquals(m[8].rightTeam.name, "4");

        assertEquals(m[9].isBye, true);
        assertEquals(m[9].leftTeam.name, "5");
        assertNull(m[9].rightTeam);

        assertEquals(m[10].isBye, false);
        assertEquals(m[10].leftTeam.name, "4");
        assertEquals(m[10].rightTeam.name, "2");

        assertEquals(m[11].isBye, false);
        assertEquals(m[11].leftTeam.name, "1");
        assertEquals(m[11].rightTeam.name, "3");

        assertEquals(m[12].isBye, false);
        assertEquals(m[12].leftTeam.name, "4");
        assertEquals(m[12].rightTeam.name, "5");

        assertEquals(m[13].isBye, true);
        assertEquals(m[13].leftTeam.name, "3");
        assertNull(m[13].rightTeam);

        assertEquals(m[14].isBye, false);
        assertEquals(m[14].leftTeam.name, "1");
        assertEquals(m[14].rightTeam.name, "2");
    }

    @Test
    public void singleElimination_isCorrect_0() throws Exception {
        Team[] teams = {};
        Game[] m = Scheduler.singleElimination(teams);
        assertEquals(m.length, 0);
    }

    @Test
    public void singleElimination_isCorrect_1() throws Exception {
        Team[] teams = {new Team("1")};
        Game[] m = Scheduler.singleElimination(teams);
        assertEquals(m.length, 0);
    }

    @Test
    public void singleElimination_isCorrect_2() throws Exception {
        Team[] teams = {new Team("1"), new Team("2")};
        Game[] m = Scheduler.singleElimination(teams);
        assertEquals(m.length, 1);
        assertEquals(m[0].round, 1);
        assertEquals(m[0].index, 1);
        assertEquals(m[0].leftTeam.name, "1");
        assertEquals(m[0].rightTeam.name, "2");
        assertEquals(m[0].isBye, false);
        assertEquals(m[0].getIsLoserBracket(), false);
    }

    @Test
    public void singleElimination_isCorrect_3() throws Exception {
        Team[] teams = {new Team("1"), new Team("2"), new Team("3")};
        Game[] m = Scheduler.singleElimination(teams);
        assertEquals(m.length, 3);

        assertEquals(m[0].getIsLoserBracket(), false);
        assertEquals(m[1].getIsLoserBracket(), false);
        assertEquals(m[2].getIsLoserBracket(), false);

        assertEquals(m[0].round, 1);
        assertEquals(m[1].round, 1);
        assertEquals(m[2].round, 2);

        assertEquals(m[0].index, 1);
        assertEquals(m[1].index, 2);
        assertEquals(m[2].index, 3);

        assertEquals(m[0].isBye, false);
        assertEquals(m[1].isBye, true);
        assertEquals(m[2].isBye, false);

        assertEquals(m[0].leftTeam.name, "2");
        assertEquals(m[0].rightTeam.name, "3");
        assertEquals(m[1].leftTeam.name, "1");
        assertNull(m[1].rightTeam);

        assertNull(m[2].leftTeam);
        assertNull(m[2].rightTeam);
        assertEquals(m[2].elimination.prevLeftGame.index, 1);
        assertEquals(m[2].elimination.prevRightGame.index, 2);
    }

    @Test
    public void singleElimination_isCorrect_4() throws Exception {
        Team[] teams = {new Team("1"), new Team("2"), new Team("3"), new Team("4")};
        Game[] m = Scheduler.singleElimination(teams);
        assertEquals(m.length, 3);

        assertEquals(m[0].getIsLoserBracket(), false);
        assertEquals(m[1].getIsLoserBracket(), false);
        assertEquals(m[2].getIsLoserBracket(), false);

        assertEquals(m[0].round, 1);
        assertEquals(m[1].round, 1);
        assertEquals(m[2].round, 2);

        assertEquals(m[0].index, 1);
        assertEquals(m[1].index, 2);
        assertEquals(m[2].index, 3);

        assertEquals(m[0].isBye, false);
        assertEquals(m[1].isBye, false);
        assertEquals(m[2].isBye, false);

        assertEquals(m[0].leftTeam.name, "2");
        assertEquals(m[0].rightTeam.name, "3");
        assertEquals(m[1].leftTeam.name, "1");
        assertEquals(m[1].rightTeam.name, "4");
        assertNull(m[2].leftTeam);
        assertNull(m[2].rightTeam);
        assertEquals(m[2].elimination.prevLeftGame.index, 1);
        assertEquals(m[2].elimination.prevRightGame.index, 2);
    }

    @Test
    public void singleElimination_isCorrect_5() throws Exception {
        Team[] teams = {new Team("1"), new Team("2"), new Team("3"), new Team("4"), new Team("5")};
        Game[] m = Scheduler.singleElimination(teams);
        assertEquals(m.length, 7);

        assertEquals(m[0].getIsLoserBracket(), false);
        assertEquals(m[1].getIsLoserBracket(), false);
        assertEquals(m[2].getIsLoserBracket(), false);
        assertEquals(m[3].getIsLoserBracket(), false);
        assertEquals(m[4].getIsLoserBracket(), false);
        assertEquals(m[5].getIsLoserBracket(), false);
        assertEquals(m[6].getIsLoserBracket(), false);

        assertEquals(m[0].round, 1);
        assertEquals(m[1].round, 1);
        assertEquals(m[2].round, 1);
        assertEquals(m[3].round, 1);
        assertEquals(m[4].round, 2);
        assertEquals(m[5].round, 2);
        assertEquals(m[6].round, 3);

        assertEquals(m[0].index, 1);
        assertEquals(m[1].index, 2);
        assertEquals(m[2].index, 3);
        assertEquals(m[3].index, 4);
        assertEquals(m[4].index, 5);
        assertEquals(m[5].index, 6);
        assertEquals(m[6].index, 7);

        assertEquals(m[0].isBye, false);
        assertEquals(m[1].isBye, true);
        assertEquals(m[2].isBye, true);
        assertEquals(m[3].isBye, true);
        assertEquals(m[4].isBye, false);
        assertEquals(m[5].isBye, false);
        assertEquals(m[6].isBye, false);

        assertEquals(m[0].leftTeam.name, "4");
        assertEquals(m[0].rightTeam.name, "5");

        assertEquals(m[1].leftTeam.name, "3");
        assertNull(m[1].rightTeam);

        assertEquals(m[2].leftTeam.name, "2");
        assertNull(m[2].rightTeam);

        assertEquals(m[3].leftTeam.name, "1");
        assertNull(m[3].rightTeam);

        assertNull(m[4].leftTeam);
        assertNull(m[4].rightTeam);
        assertEquals(m[4].elimination.prevLeftGame.index, 2);
        assertEquals(m[4].elimination.prevRightGame.index, 3);

        assertNull(m[5].leftTeam);
        assertNull(m[5].rightTeam);
        assertEquals(m[5].elimination.prevLeftGame.index, 1);
        assertEquals(m[5].elimination.prevRightGame.index, 4);

        assertNull(m[6].leftTeam);
        assertNull(m[6].rightTeam);
        assertEquals(m[6].elimination.prevLeftGame.index, 5);
        assertEquals(m[6].elimination.prevRightGame.index, 6);
    }

    @Test
    public void singleElimination_isCorrect_6() throws Exception {
        Team[] teams = {new Team("1"), new Team("2"), new Team("3"), new Team("4"), new Team("5"), new Team("6")};
        Game[] m = Scheduler.singleElimination(teams);
        assertEquals(m.length, 7);

        assertEquals(m[0].getIsLoserBracket(), false);
        assertEquals(m[1].getIsLoserBracket(), false);
        assertEquals(m[2].getIsLoserBracket(), false);
        assertEquals(m[3].getIsLoserBracket(), false);
        assertEquals(m[4].getIsLoserBracket(), false);
        assertEquals(m[5].getIsLoserBracket(), false);
        assertEquals(m[6].getIsLoserBracket(), false);


        assertEquals(m[0].round, 1);
        assertEquals(m[1].round, 1);
        assertEquals(m[2].round, 1);
        assertEquals(m[3].round, 1);
        assertEquals(m[4].round, 2);
        assertEquals(m[5].round, 2);
        assertEquals(m[6].round, 3);

        assertEquals(m[0].index, 1);
        assertEquals(m[1].index, 2);
        assertEquals(m[2].index, 3);
        assertEquals(m[3].index, 4);
        assertEquals(m[4].index, 5);
        assertEquals(m[5].index, 6);
        assertEquals(m[6].index, 7);

        assertEquals(m[0].isBye, false);
        assertEquals(m[1].isBye, false);
        assertEquals(m[2].isBye, true);
        assertEquals(m[3].isBye, true);
        assertEquals(m[4].isBye, false);
        assertEquals(m[5].isBye, false);
        assertEquals(m[6].isBye, false);

        assertEquals(m[0].leftTeam.name, "4");
        assertEquals(m[0].rightTeam.name, "5");

        assertEquals(m[1].leftTeam.name, "3");
        assertEquals(m[1].rightTeam.name, "6");

        assertEquals(m[2].leftTeam.name, "2");
        assertNull(m[2].rightTeam);

        assertEquals(m[3].leftTeam.name, "1");
        assertNull(m[3].rightTeam);

        assertNull(m[4].leftTeam);
        assertNull(m[4].rightTeam);
        assertEquals(m[4].elimination.prevLeftGame.index, 2);
        assertEquals(m[4].elimination.prevRightGame.index, 3);

        assertNull(m[5].leftTeam);
        assertNull(m[5].rightTeam);
        assertEquals(m[5].elimination.prevLeftGame.index, 1);
        assertEquals(m[5].elimination.prevRightGame.index, 4);

        assertNull(m[6].leftTeam);
        assertNull(m[6].rightTeam);
        assertEquals(m[6].elimination.prevLeftGame.index, 5);
        assertEquals(m[6].elimination.prevRightGame.index, 6);
    }

    @Test
    public void singleElimination_isCorrect_7() throws Exception {
        Team[] teams = {new Team("1"), new Team("2"), new Team("3"), new Team("4"), new Team("5"), new Team("6"), new Team("7")};
        Game[] m = Scheduler.singleElimination(teams);
        assertEquals(m.length, 7);

        assertEquals(m[0].getIsLoserBracket(), false);
        assertEquals(m[1].getIsLoserBracket(), false);
        assertEquals(m[2].getIsLoserBracket(), false);
        assertEquals(m[3].getIsLoserBracket(), false);
        assertEquals(m[4].getIsLoserBracket(), false);
        assertEquals(m[5].getIsLoserBracket(), false);
        assertEquals(m[6].getIsLoserBracket(), false);

        assertEquals(m[0].round, 1);
        assertEquals(m[1].round, 1);
        assertEquals(m[2].round, 1);
        assertEquals(m[3].round, 1);
        assertEquals(m[4].round, 2);
        assertEquals(m[5].round, 2);
        assertEquals(m[6].round, 3);

        assertEquals(m[0].index, 1);
        assertEquals(m[1].index, 2);
        assertEquals(m[2].index, 3);
        assertEquals(m[3].index, 4);
        assertEquals(m[4].index, 5);
        assertEquals(m[5].index, 6);
        assertEquals(m[6].index, 7);

        assertEquals(m[0].isBye, false);
        assertEquals(m[1].isBye, false);
        assertEquals(m[2].isBye, false);
        assertEquals(m[3].isBye, true);
        assertEquals(m[4].isBye, false);
        assertEquals(m[5].isBye, false);
        assertEquals(m[6].isBye, false);

        assertEquals(m[0].leftTeam.name, "4");
        assertEquals(m[0].rightTeam.name, "5");

        assertEquals(m[1].leftTeam.name, "3");
        assertEquals(m[1].rightTeam.name, "6");

        assertEquals(m[2].leftTeam.name, "2");
        assertEquals(m[2].rightTeam.name, "7");

        assertEquals(m[3].leftTeam.name, "1");
        assertNull(m[3].rightTeam);

        assertNull(m[4].leftTeam);
        assertNull(m[4].rightTeam);
        assertEquals(m[4].elimination.prevLeftGame.index, 2);
        assertEquals(m[4].elimination.prevRightGame.index, 3);

        assertNull(m[5].leftTeam);
        assertNull(m[5].rightTeam);
        assertEquals(m[5].elimination.prevLeftGame.index, 1);
        assertEquals(m[5].elimination.prevRightGame.index, 4);

        assertNull(m[6].leftTeam);
        assertNull(m[6].rightTeam);
        assertEquals(m[6].elimination.prevLeftGame.index, 5);
        assertEquals(m[6].elimination.prevRightGame.index, 6);
    }

    @Test
    public void singleElimination_isCorrect_8() throws Exception {
        Team[] teams = {new Team("1"), new Team("2"), new Team("3"), new Team("4"), new Team("5"), new Team("6"), new Team("7"), new Team("8")};
        Game[] m = Scheduler.singleElimination(teams);
        assertEquals(m.length, 7);

        assertEquals(m[0].getIsLoserBracket(), false);
        assertEquals(m[1].getIsLoserBracket(), false);
        assertEquals(m[2].getIsLoserBracket(), false);
        assertEquals(m[3].getIsLoserBracket(), false);
        assertEquals(m[4].getIsLoserBracket(), false);
        assertEquals(m[5].getIsLoserBracket(), false);
        assertEquals(m[6].getIsLoserBracket(), false);

        assertEquals(m[0].round, 1);
        assertEquals(m[1].round, 1);
        assertEquals(m[2].round, 1);
        assertEquals(m[3].round, 1);
        assertEquals(m[4].round, 2);
        assertEquals(m[5].round, 2);
        assertEquals(m[6].round, 3);

        assertEquals(m[0].index, 1);
        assertEquals(m[1].index, 2);
        assertEquals(m[2].index, 3);
        assertEquals(m[3].index, 4);
        assertEquals(m[4].index, 5);
        assertEquals(m[5].index, 6);
        assertEquals(m[6].index, 7);

        assertEquals(m[0].isBye, false);
        assertEquals(m[1].isBye, false);
        assertEquals(m[2].isBye, false);
        assertEquals(m[3].isBye, false);
        assertEquals(m[4].isBye, false);
        assertEquals(m[5].isBye, false);
        assertEquals(m[6].isBye, false);

        assertEquals(m[0].leftTeam.name, "4");
        assertEquals(m[0].rightTeam.name, "5");

        assertEquals(m[1].leftTeam.name, "3");
        assertEquals(m[1].rightTeam.name, "6");

        assertEquals(m[2].leftTeam.name, "2");
        assertEquals(m[2].rightTeam.name, "7");

        assertEquals(m[3].leftTeam.name, "1");
        assertEquals(m[3].rightTeam.name, "8");

        assertNull(m[4].leftTeam);
        assertNull(m[4].rightTeam);
        assertEquals(m[4].elimination.prevLeftGame.index, 2);
        assertEquals(m[4].elimination.prevRightGame.index, 3);

        assertNull(m[5].leftTeam);
        assertNull(m[5].rightTeam);
        assertEquals(m[5].elimination.prevLeftGame.index, 1);
        assertEquals(m[5].elimination.prevRightGame.index, 4);

        assertNull(m[6].leftTeam);
        assertNull(m[6].rightTeam);
        assertEquals(m[6].elimination.prevLeftGame.index, 5);
        assertEquals(m[6].elimination.prevRightGame.index, 6);
    }

    @Test
    public void doubleElimination_isCorrect_2() throws Exception {
        Team[] teams = {new Team("1"), new Team("2")};
        Game[] m = Scheduler.doubleElimination(teams);
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

        assertEquals(m[0].isBye, true);
        assertEquals(m[1].isBye, true);
        assertEquals(m[2].isBye, false);
        assertEquals(m[3].isBye, false);
        assertEquals(m[4].isBye, false);
        assertEquals(m[5].isBye, false);

        assertEquals(m[0].leftTeam.name, "2");
        assertNull(m[0].rightTeam);
        assertEquals(m[0].getIsLoserBracket(), false);

        assertEquals(m[1].leftTeam.name, "1");
        assertNull(m[1].rightTeam);
        assertEquals(m[1].getIsLoserBracket(), false);

        assertNull(m[2].leftTeam);
        assertNull(m[2].rightTeam);
        assertEquals(m[2].elimination.prevLeftGame.index, 1);
        assertEquals(m[2].elimination.prevRightGame.index, 2);
        assertEquals(m[2].getIsLoserBracket(), false);

        assertNull(m[3].leftTeam);
        assertNull(m[3].rightTeam);
        assertEquals(m[3].elimination.prevLeftGame.index, 1);
        assertEquals(m[3].elimination.prevRightGame.index, 2);
        assertEquals(m[3].getIsLoserBracket(), true);

        assertNull(m[4].leftTeam);
        assertNull(m[4].rightTeam);
        assertEquals(m[4].elimination.prevLeftGame.index, 3);
        assertEquals(m[4].elimination.prevRightGame.index, 4);
        assertEquals(m[4].getIsLoserBracket(), true);

        assertNull(m[5].leftTeam);
        assertNull(m[5].rightTeam);
        assertEquals(m[5].elimination.prevLeftGame.index, 3);
        assertEquals(m[5].elimination.prevRightGame.index, 5);
        assertEquals(m[5].getIsLoserBracket(), false);
    }

    @Test
    public void doubleElimination_isCorrect_3() throws Exception {
        Team[] teams = {new Team("1"), new Team("2"), new Team("3")};
        Game[] m = Scheduler.doubleElimination(teams);
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

        assertEquals(m[0].isBye, false);
        assertEquals(m[1].isBye, true);
        assertEquals(m[2].isBye, false);
        assertEquals(m[3].isBye, false);
        assertEquals(m[4].isBye, false);
        assertEquals(m[5].isBye, false);

        assertEquals(m[0].leftTeam.name, "2");
        assertEquals(m[0].rightTeam.name, "3");
        assertEquals(m[0].getIsLoserBracket(), false);

        assertEquals(m[1].leftTeam.name, "1");
        assertNull(m[1].rightTeam);
        assertEquals(m[1].getIsLoserBracket(), false);

        assertNull(m[2].leftTeam);
        assertNull(m[2].rightTeam);
        assertEquals(m[2].elimination.prevLeftGame.index, 1);
        assertEquals(m[2].elimination.prevRightGame.index, 2);
        assertEquals(m[2].getIsLoserBracket(), false);

        assertNull(m[3].leftTeam);
        assertNull(m[3].rightTeam);
        assertEquals(m[3].elimination.prevLeftGame.index, 1);
        assertEquals(m[3].elimination.prevRightGame.index, 2);
        assertEquals(m[3].getIsLoserBracket(), true);

        assertNull(m[4].leftTeam);
        assertNull(m[4].rightTeam);
        assertEquals(m[4].elimination.prevLeftGame.index, 3);
        assertEquals(m[4].elimination.prevRightGame.index, 4);
        assertEquals(m[4].getIsLoserBracket(), true);

        assertNull(m[5].leftTeam);
        assertNull(m[5].rightTeam);
        assertEquals(m[5].elimination.prevLeftGame.index, 3);
        assertEquals(m[5].elimination.prevRightGame.index, 5);
        assertEquals(m[5].getIsLoserBracket(), false);
    }

    @Test
    public void doubleElimination_isCorrect_4() throws Exception {
        Team[] teams = {new Team("1"), new Team("2"), new Team("3"), new Team("4")};
        Game[] m = Scheduler.doubleElimination(teams);
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

        assertEquals(m[0].isBye, false);
        assertEquals(m[1].isBye, false);
        assertEquals(m[2].isBye, false);
        assertEquals(m[3].isBye, false);
        assertEquals(m[4].isBye, false);
        assertEquals(m[5].isBye, false);

        assertEquals(m[0].leftTeam.name, "2");
        assertEquals(m[0].rightTeam.name, "3");
        assertEquals(m[0].getIsLoserBracket(), false);

        assertEquals(m[1].leftTeam.name, "1");
        assertEquals(m[1].rightTeam.name, "4");
        assertEquals(m[1].getIsLoserBracket(), false);

        assertNull(m[2].leftTeam);
        assertNull(m[2].rightTeam);
        assertEquals(m[2].elimination.prevLeftGame.index, 1);
        assertEquals(m[2].elimination.prevRightGame.index, 2);
        assertEquals(m[2].getIsLoserBracket(), false);

        assertNull(m[3].leftTeam);
        assertNull(m[3].rightTeam);
        assertEquals(m[3].elimination.prevLeftGame.index, 1);
        assertEquals(m[3].elimination.prevRightGame.index, 2);
        assertEquals(m[3].getIsLoserBracket(), true);

        assertNull(m[4].leftTeam);
        assertNull(m[4].rightTeam);
        assertEquals(m[4].elimination.prevLeftGame.index, 3);
        assertEquals(m[4].elimination.prevRightGame.index, 4);
        assertEquals(m[4].getIsLoserBracket(), true);

        assertNull(m[5].leftTeam);
        assertNull(m[5].rightTeam);
        assertEquals(m[5].elimination.prevLeftGame.index, 3);
        assertEquals(m[5].elimination.prevRightGame.index, 5);
        assertEquals(m[5].getIsLoserBracket(), false);
    }

    @Test
    public void doubleElimination_isCorrect_5() throws Exception {
        Team[] teams = {new Team("1"), new Team("2"), new Team("3"), new Team("4"), new Team("5")};
        Game[] m = Scheduler.doubleElimination(teams);
        assertEquals(m.length, 14);

        assertEquals(m[0].round, 1);
        assertEquals(m[1].round, 1);
        assertEquals(m[2].round, 1);
        assertEquals(m[3].round, 1);
        assertEquals(m[4].round, 2);
        assertEquals(m[5].round, 2);
        assertEquals(m[6].round, 3);
        assertEquals(m[7].round, 2);
        assertEquals(m[8].round, 2);
        assertEquals(m[9].round, 3);
        assertEquals(m[10].round, 3);
        assertEquals(m[11].round, 4);
        assertEquals(m[12].round, 5);
        assertEquals(m[13].round, 4);

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

        assertEquals(m[0].isBye, false);
        assertEquals(m[1].isBye, true);
        assertEquals(m[2].isBye, true);
        assertEquals(m[3].isBye, true);
        assertEquals(m[4].isBye, false);
        assertEquals(m[5].isBye, false);
        assertEquals(m[6].isBye, false);
        assertEquals(m[7].isBye, false);
        assertEquals(m[8].isBye, false);
        assertEquals(m[9].isBye, false);
        assertEquals(m[10].isBye, false);
        assertEquals(m[11].isBye, false);
        assertEquals(m[12].isBye, false);
        assertEquals(m[13].isBye, false);

        assertEquals(m[0].leftTeam.name, "4");
        assertEquals(m[0].rightTeam.name, "5");
        assertEquals(m[0].getIsLoserBracket(), false);

        assertEquals(m[1].leftTeam.name, "3");
        assertNull(m[1].rightTeam);
        assertEquals(m[1].getIsLoserBracket(), false);

        assertEquals(m[2].leftTeam.name, "2");
        assertNull(m[2].rightTeam);
        assertEquals(m[2].getIsLoserBracket(), false);

        assertEquals(m[3].leftTeam.name, "1");
        assertNull(m[3].rightTeam);
        assertEquals(m[3].getIsLoserBracket(), false);

        assertNull(m[4].leftTeam);
        assertNull(m[4].rightTeam);
        assertEquals(m[4].elimination.prevLeftGame.index, 2);
        assertEquals(m[4].elimination.prevRightGame.index, 3);
        assertEquals(m[4].getIsLoserBracket(), false);

        assertNull(m[5].leftTeam);
        assertNull(m[5].rightTeam);
        assertEquals(m[5].elimination.prevLeftGame.index, 1);
        assertEquals(m[5].elimination.prevRightGame.index, 4);
        assertEquals(m[5].getIsLoserBracket(), false);

        assertNull(m[6].leftTeam);
        assertNull(m[6].rightTeam);
        assertEquals(m[6].elimination.prevLeftGame.index, 5);
        assertEquals(m[6].elimination.prevRightGame.index, 6);
        assertEquals(m[6].getIsLoserBracket(), false);

        assertNull(m[7].leftTeam);
        assertNull(m[7].rightTeam);
        assertEquals(m[7].elimination.prevLeftGame.index, 1);
        assertEquals(m[7].elimination.prevRightGame.index, 2);
        assertEquals(m[7].getIsLoserBracket(), true);

        assertNull(m[8].leftTeam);
        assertNull(m[8].rightTeam);
        assertEquals(m[8].elimination.prevLeftGame.index, 3);
        assertEquals(m[8].elimination.prevRightGame.index, 4);
        assertEquals(m[8].getIsLoserBracket(), true);

        assertNull(m[9].leftTeam);
        assertNull(m[9].rightTeam);
        assertEquals(m[9].elimination.prevLeftGame.index, 5);
        assertEquals(m[9].elimination.prevRightGame.index, 8);
        assertEquals(m[9].getIsLoserBracket(), true);

        assertNull(m[10].leftTeam);
        assertNull(m[10].rightTeam);
        assertEquals(m[10].elimination.prevLeftGame.index, 6);
        assertEquals(m[10].elimination.prevRightGame.index, 9);
        assertEquals(m[10].getIsLoserBracket(), true);

        assertNull(m[11].leftTeam);
        assertNull(m[11].rightTeam);
        assertEquals(m[11].elimination.prevLeftGame.index, 10);
        assertEquals(m[11].elimination.prevRightGame.index, 11);
        assertEquals(m[11].getIsLoserBracket(), true);

        assertNull(m[12].leftTeam);
        assertNull(m[12].rightTeam);
        assertEquals(m[12].elimination.prevLeftGame.index, 7);
        assertEquals(m[12].elimination.prevRightGame.index, 12);
        assertEquals(m[12].getIsLoserBracket(), true);

        assertNull(m[13].leftTeam);
        assertNull(m[13].rightTeam);
        assertEquals(m[13].elimination.prevLeftGame.index, 7);
        assertEquals(m[13].elimination.prevRightGame.index, 13);
        assertEquals(m[13].getIsLoserBracket(), false);
    }

    @Test
    public void doubleElimination_isCorrect_6() throws Exception {
        Team[] teams = {new Team("1"), new Team("2"), new Team("3"), new Team("4"), new Team("5"), new Team("6")};
        Game[] m = Scheduler.doubleElimination(teams);
        assertEquals(m.length, 14);

        assertEquals(m[0].round, 1);
        assertEquals(m[1].round, 1);
        assertEquals(m[2].round, 1);
        assertEquals(m[3].round, 1);
        assertEquals(m[4].round, 2);
        assertEquals(m[5].round, 2);
        assertEquals(m[6].round, 3);
        assertEquals(m[7].round, 2);
        assertEquals(m[8].round, 2);
        assertEquals(m[9].round, 3);
        assertEquals(m[10].round, 3);
        assertEquals(m[11].round, 4);
        assertEquals(m[12].round, 5);
        assertEquals(m[13].round, 4);

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

        assertEquals(m[0].isBye, false);
        assertEquals(m[1].isBye, false);
        assertEquals(m[2].isBye, true);
        assertEquals(m[3].isBye, true);
        assertEquals(m[4].isBye, false);
        assertEquals(m[5].isBye, false);
        assertEquals(m[6].isBye, false);
        assertEquals(m[7].isBye, false);
        assertEquals(m[8].isBye, false);
        assertEquals(m[9].isBye, false);
        assertEquals(m[10].isBye, false);
        assertEquals(m[11].isBye, false);
        assertEquals(m[12].isBye, false);
        assertEquals(m[13].isBye, false);

        assertEquals(m[0].leftTeam.name, "4");
        assertEquals(m[0].rightTeam.name, "5");
        assertEquals(m[0].getIsLoserBracket(), false);

        assertEquals(m[1].leftTeam.name, "3");
        assertEquals(m[1].rightTeam.name, "6");
        assertEquals(m[1].getIsLoserBracket(), false);

        assertEquals(m[2].leftTeam.name, "2");
        assertNull(m[2].rightTeam);
        assertEquals(m[2].getIsLoserBracket(), false);

        assertEquals(m[3].leftTeam.name, "1");
        assertNull(m[3].rightTeam);
        assertEquals(m[3].getIsLoserBracket(), false);

        assertNull(m[4].leftTeam);
        assertNull(m[4].rightTeam);
        assertEquals(m[4].elimination.prevLeftGame.index, 2);
        assertEquals(m[4].elimination.prevRightGame.index, 3);
        assertEquals(m[4].getIsLoserBracket(), false);

        assertNull(m[5].leftTeam);
        assertNull(m[5].rightTeam);
        assertEquals(m[5].elimination.prevLeftGame.index, 1);
        assertEquals(m[5].elimination.prevRightGame.index, 4);
        assertEquals(m[5].getIsLoserBracket(), false);

        assertNull(m[6].leftTeam);
        assertNull(m[6].rightTeam);
        assertEquals(m[6].elimination.prevLeftGame.index, 5);
        assertEquals(m[6].elimination.prevRightGame.index, 6);
        assertEquals(m[6].getIsLoserBracket(), false);

        assertNull(m[7].leftTeam);
        assertNull(m[7].rightTeam);
        assertEquals(m[7].elimination.prevLeftGame.index, 1);
        assertEquals(m[7].elimination.prevRightGame.index, 2);
        assertEquals(m[7].getIsLoserBracket(), true);

        assertNull(m[8].leftTeam);
        assertNull(m[8].rightTeam);
        assertEquals(m[8].elimination.prevLeftGame.index, 3);
        assertEquals(m[8].elimination.prevRightGame.index, 4);
        assertEquals(m[8].getIsLoserBracket(), true);

        assertNull(m[9].leftTeam);
        assertNull(m[9].rightTeam);
        assertEquals(m[9].elimination.prevLeftGame.index, 5);
        assertEquals(m[9].elimination.prevRightGame.index, 8);
        assertEquals(m[9].getIsLoserBracket(), true);

        assertNull(m[10].leftTeam);
        assertNull(m[10].rightTeam);
        assertEquals(m[10].elimination.prevLeftGame.index, 6);
        assertEquals(m[10].elimination.prevRightGame.index, 9);
        assertEquals(m[10].getIsLoserBracket(), true);

        assertNull(m[11].leftTeam);
        assertNull(m[11].rightTeam);
        assertEquals(m[11].elimination.prevLeftGame.index, 10);
        assertEquals(m[11].elimination.prevRightGame.index, 11);
        assertEquals(m[11].getIsLoserBracket(), true);

        assertNull(m[12].leftTeam);
        assertNull(m[12].rightTeam);
        assertEquals(m[12].elimination.prevLeftGame.index, 7);
        assertEquals(m[12].elimination.prevRightGame.index, 12);
        assertEquals(m[12].getIsLoserBracket(), true);

        assertNull(m[13].leftTeam);
        assertNull(m[13].rightTeam);
        assertEquals(m[13].elimination.prevLeftGame.index, 7);
        assertEquals(m[13].elimination.prevRightGame.index, 13);
        assertEquals(m[13].getIsLoserBracket(), false);
    }

    @Test
    public void doubleElimination_isCorrect_7() throws Exception {
        Team[] teams = {new Team("1"), new Team("2"), new Team("3"), new Team("4"), new Team("5"), new Team("6"), new Team("7")};
        Game[] m = Scheduler.doubleElimination(teams);
        assertEquals(m.length, 14);

        assertEquals(m[0].round, 1);
        assertEquals(m[1].round, 1);
        assertEquals(m[2].round, 1);
        assertEquals(m[3].round, 1);
        assertEquals(m[4].round, 2);
        assertEquals(m[5].round, 2);
        assertEquals(m[6].round, 3);
        assertEquals(m[7].round, 2);
        assertEquals(m[8].round, 2);
        assertEquals(m[9].round, 3);
        assertEquals(m[10].round, 3);
        assertEquals(m[11].round, 4);
        assertEquals(m[12].round, 5);
        assertEquals(m[13].round, 4);

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

        assertEquals(m[0].isBye, false);
        assertEquals(m[1].isBye, false);
        assertEquals(m[2].isBye, false);
        assertEquals(m[3].isBye, true);
        assertEquals(m[4].isBye, false);
        assertEquals(m[5].isBye, false);
        assertEquals(m[6].isBye, false);
        assertEquals(m[7].isBye, false);
        assertEquals(m[8].isBye, false);
        assertEquals(m[9].isBye, false);
        assertEquals(m[10].isBye, false);
        assertEquals(m[11].isBye, false);
        assertEquals(m[12].isBye, false);
        assertEquals(m[13].isBye, false);

        assertEquals(m[0].leftTeam.name, "4");
        assertEquals(m[0].rightTeam.name, "5");
        assertEquals(m[0].getIsLoserBracket(), false);

        assertEquals(m[1].leftTeam.name, "3");
        assertEquals(m[1].rightTeam.name, "6");
        assertEquals(m[1].getIsLoserBracket(), false);

        assertEquals(m[2].leftTeam.name, "2");
        assertEquals(m[2].rightTeam.name, "7");
        assertEquals(m[2].getIsLoserBracket(), false);

        assertEquals(m[3].leftTeam.name, "1");
        assertNull(m[3].rightTeam);
        assertEquals(m[3].getIsLoserBracket(), false);

        assertNull(m[4].leftTeam);
        assertNull(m[4].rightTeam);
        assertEquals(m[4].elimination.prevLeftGame.index, 2);
        assertEquals(m[4].elimination.prevRightGame.index, 3);
        assertEquals(m[4].getIsLoserBracket(), false);

        assertNull(m[5].leftTeam);
        assertNull(m[5].rightTeam);
        assertEquals(m[5].elimination.prevLeftGame.index, 1);
        assertEquals(m[5].elimination.prevRightGame.index, 4);
        assertEquals(m[5].getIsLoserBracket(), false);

        assertNull(m[6].leftTeam);
        assertNull(m[6].rightTeam);
        assertEquals(m[6].elimination.prevLeftGame.index, 5);
        assertEquals(m[6].elimination.prevRightGame.index, 6);
        assertEquals(m[6].getIsLoserBracket(), false);

        assertNull(m[7].leftTeam);
        assertNull(m[7].rightTeam);
        assertEquals(m[7].elimination.prevLeftGame.index, 1);
        assertEquals(m[7].elimination.prevRightGame.index, 2);
        assertEquals(m[7].getIsLoserBracket(), true);

        assertNull(m[8].leftTeam);
        assertNull(m[8].rightTeam);
        assertEquals(m[8].elimination.prevLeftGame.index, 3);
        assertEquals(m[8].elimination.prevRightGame.index, 4);
        assertEquals(m[8].getIsLoserBracket(), true);

        assertNull(m[9].leftTeam);
        assertNull(m[9].rightTeam);
        assertEquals(m[9].elimination.prevLeftGame.index, 5);
        assertEquals(m[9].elimination.prevRightGame.index, 8);
        assertEquals(m[9].getIsLoserBracket(), true);

        assertNull(m[10].leftTeam);
        assertNull(m[10].rightTeam);
        assertEquals(m[10].elimination.prevLeftGame.index, 6);
        assertEquals(m[10].elimination.prevRightGame.index, 9);
        assertEquals(m[10].getIsLoserBracket(), true);

        assertNull(m[11].leftTeam);
        assertNull(m[11].rightTeam);
        assertEquals(m[11].elimination.prevLeftGame.index, 10);
        assertEquals(m[11].elimination.prevRightGame.index, 11);
        assertEquals(m[11].getIsLoserBracket(), true);

        assertNull(m[12].leftTeam);
        assertNull(m[12].rightTeam);
        assertEquals(m[12].elimination.prevLeftGame.index, 7);
        assertEquals(m[12].elimination.prevRightGame.index, 12);
        assertEquals(m[12].getIsLoserBracket(), true);

        assertNull(m[13].leftTeam);
        assertNull(m[13].rightTeam);
        assertEquals(m[13].elimination.prevLeftGame.index, 7);
        assertEquals(m[13].elimination.prevRightGame.index, 13);
        assertEquals(m[13].getIsLoserBracket(), false);
    }

    @Test
    public void doubleElimination_isCorrect_8() throws Exception {
        Team[] teams = {new Team("1"), new Team("2"), new Team("3"), new Team("4"), new Team("5"), new Team("6"), new Team("7"), new Team("8")};
        Game[] m = Scheduler.doubleElimination(teams);
        assertEquals(m.length, 14);

        assertEquals(m[0].round, 1);
        assertEquals(m[1].round, 1);
        assertEquals(m[2].round, 1);
        assertEquals(m[3].round, 1);
        assertEquals(m[4].round, 2);
        assertEquals(m[5].round, 2);
        assertEquals(m[6].round, 3);
        assertEquals(m[7].round, 2);
        assertEquals(m[8].round, 2);
        assertEquals(m[9].round, 3);
        assertEquals(m[10].round, 3);
        assertEquals(m[11].round, 4);
        assertEquals(m[12].round, 5);
        assertEquals(m[13].round, 4);

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

        assertEquals(m[0].isBye, false);
        assertEquals(m[1].isBye, false);
        assertEquals(m[2].isBye, false);
        assertEquals(m[3].isBye, false);
        assertEquals(m[4].isBye, false);
        assertEquals(m[5].isBye, false);
        assertEquals(m[6].isBye, false);
        assertEquals(m[7].isBye, false);
        assertEquals(m[8].isBye, false);
        assertEquals(m[9].isBye, false);
        assertEquals(m[10].isBye, false);
        assertEquals(m[11].isBye, false);
        assertEquals(m[12].isBye, false);
        assertEquals(m[13].isBye, false);

        assertEquals(m[0].leftTeam.name, "4");
        assertEquals(m[0].rightTeam.name, "5");
        assertEquals(m[0].getIsLoserBracket(), false);

        assertEquals(m[1].leftTeam.name, "3");
        assertEquals(m[1].rightTeam.name, "6");
        assertEquals(m[1].getIsLoserBracket(), false);

        assertEquals(m[2].leftTeam.name, "2");
        assertEquals(m[2].rightTeam.name, "7");
        assertEquals(m[2].getIsLoserBracket(), false);

        assertEquals(m[3].leftTeam.name, "1");
        assertEquals(m[3].rightTeam.name, "8");
        assertEquals(m[3].getIsLoserBracket(), false);

        assertNull(m[4].leftTeam);
        assertNull(m[4].rightTeam);
        assertEquals(m[4].elimination.prevLeftGame.index, 2);
        assertEquals(m[4].elimination.prevRightGame.index, 3);
        assertEquals(m[4].getIsLoserBracket(), false);

        assertNull(m[5].leftTeam);
        assertNull(m[5].rightTeam);
        assertEquals(m[5].elimination.prevLeftGame.index, 1);
        assertEquals(m[5].elimination.prevRightGame.index, 4);
        assertEquals(m[5].getIsLoserBracket(), false);

        assertNull(m[6].leftTeam);
        assertNull(m[6].rightTeam);
        assertEquals(m[6].elimination.prevLeftGame.index, 5);
        assertEquals(m[6].elimination.prevRightGame.index, 6);
        assertEquals(m[6].getIsLoserBracket(), false);

        assertNull(m[7].leftTeam);
        assertNull(m[7].rightTeam);
        assertEquals(m[7].elimination.prevLeftGame.index, 1);
        assertEquals(m[7].elimination.prevRightGame.index, 2);
        assertEquals(m[7].getIsLoserBracket(), true);

        assertNull(m[8].leftTeam);
        assertNull(m[8].rightTeam);
        assertEquals(m[8].elimination.prevLeftGame.index, 3);
        assertEquals(m[8].elimination.prevRightGame.index, 4);
        assertEquals(m[8].getIsLoserBracket(), true);

        assertNull(m[9].leftTeam);
        assertNull(m[9].rightTeam);
        assertEquals(m[9].elimination.prevLeftGame.index, 5);
        assertEquals(m[9].elimination.prevRightGame.index, 8);
        assertEquals(m[9].getIsLoserBracket(), true);

        assertNull(m[10].leftTeam);
        assertNull(m[10].rightTeam);
        assertEquals(m[10].elimination.prevLeftGame.index, 6);
        assertEquals(m[10].elimination.prevRightGame.index, 9);
        assertEquals(m[10].getIsLoserBracket(), true);

        assertNull(m[11].leftTeam);
        assertNull(m[11].rightTeam);
        assertEquals(m[11].elimination.prevLeftGame.index, 10);
        assertEquals(m[11].elimination.prevRightGame.index, 11);
        assertEquals(m[11].getIsLoserBracket(), true);

        assertNull(m[12].leftTeam);
        assertNull(m[12].rightTeam);
        assertEquals(m[12].elimination.prevLeftGame.index, 7);
        assertEquals(m[12].elimination.prevRightGame.index, 12);
        assertEquals(m[12].getIsLoserBracket(), true);

        assertNull(m[13].leftTeam);
        assertNull(m[13].rightTeam);
        assertEquals(m[13].elimination.prevLeftGame.index, 7);
        assertEquals(m[13].elimination.prevRightGame.index, 13);
        assertEquals(m[13].getIsLoserBracket(), false);
    }

    @Test
    public void doubleElimination_isCorrect_9() throws Exception {
        Team[] teams = {new Team("1"), new Team("2"), new Team("3"), new Team("4"), new Team("5"), new Team("6"), new Team("7"), new Team("8"), new Team("9")};
        Game[] m = Scheduler.doubleElimination(teams);
        assertEquals(m.length, 30);

        assertEquals(m[0].round, 1);
        assertEquals(m[1].round, 1);
        assertEquals(m[2].round, 1);
        assertEquals(m[3].round, 1);
        assertEquals(m[4].round, 1);
        assertEquals(m[5].round, 1);
        assertEquals(m[6].round, 1);
        assertEquals(m[7].round, 1);
        assertEquals(m[8].round, 2);
        assertEquals(m[9].round, 2);
        assertEquals(m[10].round, 2);
        assertEquals(m[11].round, 2);
        assertEquals(m[12].round, 3);
        assertEquals(m[13].round, 3);
        assertEquals(m[14].round, 4);
        assertEquals(m[15].round, 2);
        assertEquals(m[16].round, 2);
        assertEquals(m[17].round, 2);
        assertEquals(m[18].round, 2);
        assertEquals(m[19].round, 3);
        assertEquals(m[20].round, 3);
        assertEquals(m[21].round, 3);
        assertEquals(m[22].round, 3);
        assertEquals(m[23].round, 4);
        assertEquals(m[24].round, 4);
        assertEquals(m[25].round, 5);
        assertEquals(m[26].round, 5);
        assertEquals(m[27].round, 6);
        assertEquals(m[28].round, 7);
        assertEquals(m[29].round, 5);

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
        assertEquals(m[14].index, 15);
        assertEquals(m[15].index, 16);
        assertEquals(m[16].index, 17);
        assertEquals(m[17].index, 18);
        assertEquals(m[18].index, 19);
        assertEquals(m[19].index, 20);
        assertEquals(m[20].index, 21);
        assertEquals(m[21].index, 22);
        assertEquals(m[22].index, 23);
        assertEquals(m[23].index, 24);
        assertEquals(m[24].index, 25);
        assertEquals(m[25].index, 26);
        assertEquals(m[26].index, 27);
        assertEquals(m[27].index, 28);
        assertEquals(m[28].index, 29);
        assertEquals(m[29].index, 30);

        assertEquals(m[0].isBye, false);
        assertEquals(m[1].isBye, true);
        assertEquals(m[2].isBye, true);
        assertEquals(m[3].isBye, true);
        assertEquals(m[4].isBye, true);
        assertEquals(m[5].isBye, true);
        assertEquals(m[6].isBye, true);
        assertEquals(m[7].isBye, true);
        assertEquals(m[8].isBye, false);
        assertEquals(m[9].isBye, false);
        assertEquals(m[10].isBye, false);
        assertEquals(m[11].isBye, false);
        assertEquals(m[12].isBye, false);
        assertEquals(m[13].isBye, false);
        assertEquals(m[14].isBye, false);
        assertEquals(m[15].isBye, false);
        assertEquals(m[16].isBye, false);
        assertEquals(m[17].isBye, false);
        assertEquals(m[18].isBye, false);
        assertEquals(m[19].isBye, false);
        assertEquals(m[20].isBye, false);
        assertEquals(m[21].isBye, false);
        assertEquals(m[22].isBye, false);
        assertEquals(m[23].isBye, false);
        assertEquals(m[24].isBye, false);
        assertEquals(m[25].isBye, false);
        assertEquals(m[26].isBye, false);
        assertEquals(m[27].isBye, false);
        assertEquals(m[28].isBye, false);
        assertEquals(m[29].isBye, false);

        assertEquals(m[0].leftTeam.name, "8");
        assertEquals(m[0].rightTeam.name, "9");
        assertEquals(m[0].getIsLoserBracket(), false);

        assertEquals(m[1].leftTeam.name, "7");
        assertNull(m[1].rightTeam);
        assertEquals(m[1].getIsLoserBracket(), false);

        assertEquals(m[2].leftTeam.name, "6");
        assertNull(m[2].rightTeam);
        assertEquals(m[2].getIsLoserBracket(), false);

        assertEquals(m[3].leftTeam.name, "5");
        assertNull(m[3].rightTeam);
        assertEquals(m[3].getIsLoserBracket(), false);

        assertEquals(m[4].leftTeam.name, "4");
        assertNull(m[4].rightTeam);
        assertEquals(m[4].getIsLoserBracket(), false);

        assertEquals(m[5].leftTeam.name, "3");
        assertNull(m[5].rightTeam);
        assertEquals(m[5].getIsLoserBracket(), false);

        assertEquals(m[6].leftTeam.name, "2");
        assertNull(m[6].rightTeam);
        assertEquals(m[6].getIsLoserBracket(), false);

        assertEquals(m[7].leftTeam.name, "1");
        assertNull(m[7].rightTeam);
        assertEquals(m[7].getIsLoserBracket(), false);

        assertNull(m[8].leftTeam);
        assertNull(m[8].rightTeam);
        assertEquals(m[8].elimination.prevLeftGame.index, 4);
        assertEquals(m[8].elimination.prevRightGame.index, 5);
        assertEquals(m[8].getIsLoserBracket(), false);

        assertNull(m[8].leftTeam);
        assertNull(m[8].rightTeam);
        assertEquals(m[8].elimination.prevLeftGame.index, 4);
        assertEquals(m[8].elimination.prevRightGame.index, 5);
        assertEquals(m[8].getIsLoserBracket(), false);

        assertNull(m[9].leftTeam);
        assertNull(m[9].rightTeam);
        assertEquals(m[9].elimination.prevLeftGame.index, 3);
        assertEquals(m[9].elimination.prevRightGame.index, 6);
        assertEquals(m[9].getIsLoserBracket(), false);

        assertNull(m[10].leftTeam);
        assertNull(m[10].rightTeam);
        assertEquals(m[10].elimination.prevLeftGame.index, 2);
        assertEquals(m[10].elimination.prevRightGame.index, 7);
        assertEquals(m[10].getIsLoserBracket(), false);

        assertNull(m[11].leftTeam);
        assertNull(m[11].rightTeam);
        assertEquals(m[11].elimination.prevLeftGame.index, 1);
        assertEquals(m[11].elimination.prevRightGame.index, 8);
        assertEquals(m[11].getIsLoserBracket(), false);

        assertNull(m[12].leftTeam);
        assertNull(m[12].rightTeam);
        assertEquals(m[12].elimination.prevLeftGame.index, 10);
        assertEquals(m[12].elimination.prevRightGame.index, 11);
        assertEquals(m[12].getIsLoserBracket(), false);

        assertNull(m[13].leftTeam);
        assertNull(m[13].rightTeam);
        assertEquals(m[13].elimination.prevLeftGame.index, 9);
        assertEquals(m[13].elimination.prevRightGame.index, 12);
        assertEquals(m[13].getIsLoserBracket(), false);

        assertNull(m[14].leftTeam);
        assertNull(m[14].rightTeam);
        assertEquals(m[14].elimination.prevLeftGame.index, 13);
        assertEquals(m[14].elimination.prevRightGame.index, 14);
        assertEquals(m[14].getIsLoserBracket(), false);

        assertNull(m[15].leftTeam);
        assertNull(m[15].rightTeam);
        assertEquals(m[15].elimination.prevLeftGame.index, 1);
        assertEquals(m[15].elimination.prevRightGame.index, 2);
        assertEquals(m[15].getIsLoserBracket(), true);

        assertNull(m[16].leftTeam);
        assertNull(m[16].rightTeam);
        assertEquals(m[16].elimination.prevLeftGame.index, 3);
        assertEquals(m[16].elimination.prevRightGame.index, 4);
        assertEquals(m[16].getIsLoserBracket(), true);

        assertNull(m[17].leftTeam);
        assertNull(m[17].rightTeam);
        assertEquals(m[17].elimination.prevLeftGame.index, 5);
        assertEquals(m[17].elimination.prevRightGame.index, 6);
        assertEquals(m[17].getIsLoserBracket(), true);

        assertNull(m[18].leftTeam);
        assertNull(m[18].rightTeam);
        assertEquals(m[18].elimination.prevLeftGame.index, 7);
        assertEquals(m[18].elimination.prevRightGame.index, 8);
        assertEquals(m[18].getIsLoserBracket(), true);

        assertNull(m[19].leftTeam);
        assertNull(m[19].rightTeam);
        assertEquals(m[19].elimination.prevLeftGame.index, 9);
        assertEquals(m[19].elimination.prevRightGame.index, 16);
        assertEquals(m[19].getIsLoserBracket(), true);

        assertNull(m[20].leftTeam);
        assertNull(m[20].rightTeam);
        assertEquals(m[20].elimination.prevLeftGame.index, 10);
        assertEquals(m[20].elimination.prevRightGame.index, 17);
        assertEquals(m[20].getIsLoserBracket(), true);

        assertNull(m[21].leftTeam);
        assertNull(m[21].rightTeam);
        assertEquals(m[21].elimination.prevLeftGame.index, 11);
        assertEquals(m[21].elimination.prevRightGame.index, 18);
        assertEquals(m[21].getIsLoserBracket(), true);

        assertNull(m[22].leftTeam);
        assertNull(m[22].rightTeam);
        assertEquals(m[22].elimination.prevLeftGame.index, 12);
        assertEquals(m[22].elimination.prevRightGame.index, 19);
        assertEquals(m[22].getIsLoserBracket(), true);

        assertNull(m[23].leftTeam);
        assertNull(m[23].rightTeam);
        assertEquals(m[23].elimination.prevLeftGame.index, 20);
        assertEquals(m[23].elimination.prevRightGame.index, 21);
        assertEquals(m[23].getIsLoserBracket(), true);

        assertNull(m[24].leftTeam);
        assertNull(m[24].rightTeam);
        assertEquals(m[24].elimination.prevLeftGame.index, 22);
        assertEquals(m[24].elimination.prevRightGame.index, 23);
        assertEquals(m[24].getIsLoserBracket(), true);

        assertNull(m[25].leftTeam);
        assertNull(m[25].rightTeam);
        assertEquals(m[25].elimination.prevLeftGame.index, 13);
        assertEquals(m[25].elimination.prevRightGame.index, 24);
        assertEquals(m[25].getIsLoserBracket(), true);

        assertNull(m[26].leftTeam);
        assertNull(m[26].rightTeam);
        assertEquals(m[26].elimination.prevLeftGame.index, 14);
        assertEquals(m[26].elimination.prevRightGame.index, 25);
        assertEquals(m[26].getIsLoserBracket(), true);

        assertNull(m[27].leftTeam);
        assertNull(m[27].rightTeam);
        assertEquals(m[27].elimination.prevLeftGame.index, 26);
        assertEquals(m[27].elimination.prevRightGame.index, 27);
        assertEquals(m[27].getIsLoserBracket(), true);

        assertNull(m[28].leftTeam);
        assertNull(m[28].rightTeam);
        assertEquals(m[28].elimination.prevLeftGame.index, 15);
        assertEquals(m[28].elimination.prevRightGame.index, 28);
        assertEquals(m[28].getIsLoserBracket(), true);

        assertNull(m[29].leftTeam);
        assertNull(m[29].rightTeam);
        assertEquals(m[29].elimination.prevLeftGame.index, 15);
        assertEquals(m[29].elimination.prevRightGame.index, 29);
        assertEquals(m[29].getIsLoserBracket(), false);
    }


}