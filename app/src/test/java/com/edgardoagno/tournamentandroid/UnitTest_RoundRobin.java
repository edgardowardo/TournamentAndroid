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
public class UnitTest_RoundRobin {

    @Test
    public void roundRobin_isCorrect() throws Exception {
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