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
    private Team winner;
    public Team leftTeam;
    public Team rightTeam;
    private boolean isDraw = false;
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

    public Team getWinner() {
        return winner;
    }

    public void setWinner(Team winner1) {
        winner = winner1;
        isDraw = false;
    }

    public Team getWinner2() {
        if (winner != null && doublesInfo != null) {
            if (winner.seed == leftTeam.seed) {
                return doublesInfo.leftTeam;
            } else if (winner.seed == rightTeam.seed) {
                return doublesInfo.rightTeam;
            }
        }
        return  null;
    }

    public boolean isWinner(int seed) {
        Team winner = getWinner();
        Team winner2 = getWinner2();
        return winner != null && winner.seed == seed || winner2 != null && winner2.seed == seed;
    }

    public boolean isLeftParticipant(int seed) {
        return (leftTeam != null && leftTeam.seed == seed)
                || (doublesInfo != null && doublesInfo.leftTeam != null && doublesInfo.leftTeam.seed == seed);
    }

    public boolean isRightParticipant(int seed) {
        return (rightTeam != null && rightTeam.seed == seed)
                || (doublesInfo != null && doublesInfo.rightTeam != null && doublesInfo.rightTeam.seed == seed);
    }

    public boolean isParticipant(int seed) {
        return isLeftParticipant(seed) || isRightParticipant(seed);
    }

    public boolean getIsDraw() {
        return isDraw;
    }

    public void setIsDraw(boolean isDraw1) {
        isDraw = isDraw1;
        winner = null;
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
                setWinner(rightTeam);
            } else {
                setWinner(leftTeam);
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

