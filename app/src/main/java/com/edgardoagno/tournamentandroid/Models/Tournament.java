package com.edgardoagno.tournamentandroid.Models;

import java.util.Date;
import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by edgardoagno on 01/07/16.
 */
public class Tournament extends RealmObject {

    @PrimaryKey
    public String id = UUID.randomUUID().toString();
    public Date createdOn = new Date();
    @Required
    public String name = "";
    public RealmList<Group> groups = new RealmList<Group>();

}
