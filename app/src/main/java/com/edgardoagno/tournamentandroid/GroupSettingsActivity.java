package com.edgardoagno.tournamentandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;

import com.edgardoagno.tournamentandroid.ItemTouchHelperAdapter.OnStartDragListener;
import com.edgardoagno.tournamentandroid.ItemTouchHelperAdapter.SimpleItemTouchHelperCallback;
import com.edgardoagno.tournamentandroid.Models.Team;
import com.edgardoagno.tournamentandroid.ViewModels.GroupSettingsViewModel;

import java.util.ArrayList;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by edgardoagno on 10/07/16.
 */
public class GroupSettingsActivity extends AppCompatActivity implements OnStartDragListener {

    GroupSettingsViewModel _viewModel;
    private MenuItem _menuSaveItem;
    private Subscription _groupNameSubscription;
    private Subscription _teamsSubscription;
    private ItemTouchHelper _itemTouchHelper;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    GroupSettingsTeamRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_settings_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Add Group");

        _viewModel = new GroupSettingsViewModel();
        _viewModel.createDefaultGroup();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new GroupSettingsTeamRecyclerAdapter(this, _viewModel, this);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        _itemTouchHelper = new ItemTouchHelper(callback);
        _itemTouchHelper.attachToRecyclerView(recyclerView);

        //  Observers

        _groupNameSubscription = _viewModel
                ._groupNameEmitterSubject
                .asObservable()
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String value) {
                        boolean isEnabled = value.length() > 0;
                        _menuSaveItem.setEnabled(isEnabled);
                        _menuSaveItem.getIcon().setAlpha((isEnabled) ? 255 : 130);
                    }
                });

        _teamsSubscription = _viewModel
                ._teamsEmitterSubject
                .asObservable()
                .subscribe(new Action1<ArrayList<Team>>() {
                    @Override
                    public void call(ArrayList<Team> value) {
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        _itemTouchHelper.startDrag(viewHolder);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        _groupNameSubscription.unsubscribe();
        _viewModel.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_group_settings, menu);
        _menuSaveItem = menu.getItem(0);
        _menuSaveItem.setEnabled(false);
        _menuSaveItem.getIcon().setAlpha(130);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_attach_default_group) {
            Long tournament_id = getIntent().getLongExtra("TOURNAMENT_ID", 0);
            _viewModel.attachDefaultGroup(tournament_id);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
