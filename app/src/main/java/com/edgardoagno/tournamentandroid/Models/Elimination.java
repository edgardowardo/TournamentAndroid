package com.edgardoagno.tournamentandroid.Models;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by edgardoagno on 25/06/16.
 */
public class Elimination extends RealmObject {

    @PrimaryKey
    public String id = UUID.randomUUID().toString();
    public boolean isLoserBracket = false;
    public int leftGameIndex = 0;
    public Game prevLeftGame;
    public int rightGameIndex = 0;
    public Game prevRightGame;
    public int firstLoserIndex = Integer.MAX_VALUE;

    public Elimination() {
        super();
    }

    public Elimination(boolean isLoserBracket, int leftGameIndex, int rightGameIndex) {
        super();
        this.isLoserBracket = isLoserBracket;
        this.leftGameIndex = leftGameIndex;
        this.rightGameIndex = rightGameIndex;
    }
}