package com.edgardoagno.tournamentandroid;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class GroupDetailsActivity extends RealmBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_details_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Insert Group Name");
    }
}
