package com.edgardoagno.tournamentandroid.Models;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by edgardoagno on 01/07/16.
 */
public class AppRealm extends RealmObject {

    @PrimaryKey
    public String id = UUID.randomUUID().toString();
    public boolean isAdShown = false;

}
