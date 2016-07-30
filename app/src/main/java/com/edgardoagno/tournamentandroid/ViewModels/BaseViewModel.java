package com.edgardoagno.tournamentandroid.ViewModels;

import io.realm.Realm;

/**
 * Created by edgardoagno on 30/07/16.
 */
public class BaseViewModel {

    protected Realm realm;

    public BaseViewModel() {
        realm = Realm.getDefaultInstance();
    }

    public void onDestroy() {
        if (realm != null) {
            realm.close();
            realm = null;
        }
    }
}
