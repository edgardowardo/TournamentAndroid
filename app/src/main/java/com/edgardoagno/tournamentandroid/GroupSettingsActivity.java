package com.edgardoagno.tournamentandroid;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

import io.realm.Sort;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by edgardoagno on 10/07/16.
 */
public class GroupSettingsActivity extends RealmBaseActivity implements OnStartDragListener {

    GroupSettingsViewModel _viewModel;
    private MenuItem _menuSaveItem;
    private Subscription _groupNameSubscription;
    private RealmChangeListener _teamsListener;
    private ItemTouchHelper _itemTouchHelper;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    GroupSettingsTeamRecyclerAdapter adapter;

    RealmResults<Team> teams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_settings_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Add Group");

        Realm realm = Realm.getInstance(getRealmConfig());
        _viewModel = new GroupSettingsViewModel(realm);
        _viewModel.saveDefaultGroup();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new GroupSettingsTeamRecyclerAdapter(this, _viewModel, this);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        _itemTouchHelper = new ItemTouchHelper(callback);
        _itemTouchHelper.attachToRecyclerView(recyclerView);

        //  Observers
        Handler handler = new Handler(Looper.getMainLooper());
        final Runnable r = new Runnable() {
            public void run() {
                //do your stuff here after DELAY sec
                _teamsListener = new RealmChangeListener() {
                    @Override
                    public void onChange(Object element) {
                        adapter.notifyDataSetChanged();
                    }
                };
                teams = _viewModel._group.teams.where().findAllSorted("seed", Sort.ASCENDING);
                teams.addChangeListener(_teamsListener);
            }
        };
        handler.postDelayed(r, 500);

        // Rx Observers
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
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        _itemTouchHelper.startDrag(viewHolder);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        _groupNameSubscription.unsubscribe();
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
