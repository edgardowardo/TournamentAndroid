package com.edgardoagno.tournamentandroid;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public abstract class RealmBaseActivity extends AppCompatActivity {

    protected static final int[] COLORS = new int[] {
            Color.argb(255, 28, 160, 170),
            Color.argb(255, 99, 161, 247),
            Color.argb(255, 13, 79, 139),
            Color.argb(255, 89, 113, 173),
            Color.argb(255, 200, 213, 219),
            Color.argb(255, 99, 214, 74),
            Color.argb(255, 205, 92, 92),
            Color.argb(255, 105, 5, 98)
    };

    private RealmConfiguration realmConfiguration;

    protected RealmConfiguration getRealmConfig() {
        if (realmConfiguration == null) {
            realmConfiguration = new RealmConfiguration
                    .Builder(this)
                    .deleteRealmIfMigrationNeeded()
                    .build();
        }
        return realmConfiguration;
    }

    protected void resetRealm() {
        Realm.deleteRealm(getRealmConfig());
    }
}

