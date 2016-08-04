package com.edgardoagno.tournamentandroid.Models;

import java.util.UUID;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by edgardoagno on 25/06/16.
 */
public class Game extends RealmObject {

    @PrimaryKey
    public String id = UUID.randomUUID().toString();
    public int round = 0;
    public int index = 0;
    public Team winner;
    public Team leftTeam;
    public Team rightTeam;
    public boolean isDraw = false;
    public int leftScore = 0;
    public int rightScore = 0;
    public String note = "";

    public DoublesInfo doublesInfo;
    public Elimination elimination;

    public Game() {
        super();
    }

    public Game(int round, int index, Team leftTeam, Team rightTeam) {
        super();
        this.round = round;
        this.index = index;
        this.leftTeam = leftTeam;
        this.rightTeam = rightTeam;
    }

    public Game(int round, int index, Team winner, Team leftTeam, Team rightTeam, DoublesInfo doublesInfo, Elimination elimination) {
        super();
        this.round = round;
        this.index = index;
        this.winner = winner;
        this.leftTeam = leftTeam;
        this.rightTeam = rightTeam;
        this.doublesInfo = doublesInfo;
        this.elimination = elimination;
    }

    public Team getLoser() {
        if (winner != null) {
            if (leftTeam != null && leftTeam.id.compareTo(winner.id) == 0) {
                return rightTeam;
            } else if (rightTeam != null && rightTeam.id.compareTo(winner.id) == 0) {
                return leftTeam;
            }
        }
        return null;
    }

    public boolean getIsBothBye() {
        return  (leftTeam != null && leftTeam.isBye && rightTeam != null && rightTeam.isBye);
    }

    public boolean getIsBye() {
        return  (leftTeam != null && leftTeam.isBye || rightTeam != null && rightTeam.isBye);
    }

    public void promoteTeamOnBye() {
        if (getIsBye()) {
            if (leftTeam != null && leftTeam.isBye) {
                winner = rightTeam;
            } else {
                winner = leftTeam;
            }
        }
    }

    public boolean getIsLoserBracket() {
        if (this.elimination == null) {
            return false;
        } else {
            return this.elimination.isLoserBracket;
        }
    }

}

