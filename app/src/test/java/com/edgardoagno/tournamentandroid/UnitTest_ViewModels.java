package com.edgardoagno.tournamentandroid;

import com.edgardoagno.tournamentandroid.Models.Game;
import com.edgardoagno.tournamentandroid.Models.Team;
import com.edgardoagno.tournamentandroid.ViewModels.GroupSettingsViewModel;

import org.junit.Test;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.internal.Context;

import static org.junit.Assert.*;

/**
 * Created by edgardoagno on 13/07/16.
 */
public class UnitTest_ViewModels {

    @Test
    public void tes_groupSettingsViewModel() throws Exception {

/*
    Looks like I need to mock realm etc.

        RealmConfiguration myConfig = new RealmConfiguration.Builder(this)
                .name("testRealm.realm")
                .inMemory()
                .build();
        Realm realm = Realm.getInstance(myConfig);
        GroupSettingsViewModel viewModel = new GroupSettingsViewModel(realm);
*/
        assertEquals(3, 3);
    }

}
