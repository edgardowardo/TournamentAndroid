package com.edgardoagno.tournamentandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;

import com.edgardoagno.tournamentandroid.Fragments.ChartsTabFragment;
import com.edgardoagno.tournamentandroid.Fragments.GamesTabFragment;
import com.edgardoagno.tournamentandroid.Fragments.TeamStatsFragment;
import com.edgardoagno.tournamentandroid.Models.ScheduleType;
import com.edgardoagno.tournamentandroid.ViewModels.GroupDetailsViewModel;
import com.kaopiz.kprogresshud.KProgressHUD;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GroupDetailsActivity extends AppCompatActivity {

    private GroupDetailsViewModel viewModel;
    private Long _id;
    private KProgressHUD hud;
    private TeamStatsFragment teamStatsFragment;
    private ChartsTabFragment chartTabStatsFragment;

    @Bind(R.id.radio_round_robin) RadioButton roundRobinRadioButton;
    @Bind(R.id.radio_american) RadioButton americanRadioButton;
    @Bind(R.id.radio_single) RadioButton singleRadioButton;
    @Bind(R.id.radio_winners) RadioButton winnersRadioButton;
    @Bind(R.id.radio_losers) RadioButton losersRadioButton;
    @Bind(R.id.radio_table) RadioButton tableRadioButton;
    @Bind(R.id.radio_chart) RadioButton chartRadioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_details_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

        _id = getIntent().getLongExtra("GROUP_ID", 0);
        viewModel = new GroupDetailsViewModel(_id);

        refresh();

        if (savedInstanceState == null) {
            GamesTabFragment fragment = new GamesTabFragment();
            fragment.setArguments(createArguments(false));
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content, fragment)
                    .commit();
        }
    }

    private void refresh() {
        setTitle(viewModel.getTitle());
        configureBottomRadioGroup(viewModel._group.getScheduleType());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_group_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_edit) {
            Intent intent = new Intent(GroupDetailsActivity.this, GroupSettingsActivity.class);
            intent.putExtra("GROUP_ID", _id);
            startActivityForResult(intent, 0xe110);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0xe110) {
            refresh();
            onClickGames();
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        configureBottomRadioGroup(viewModel._group.getScheduleType());

        GamesTabFragment fragment = new GamesTabFragment();
        fragment.setArguments(createArguments(false));
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragment)
                .commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel.onDestroy();
    }

    private Bundle createArguments(Boolean isLosersBracket) {
        Bundle args = new Bundle();
        args.putLong("GROUP_ID", _id);
        args.putBoolean("IS_LOSERS_ROUND", isLosersBracket);
        return args;
    }

    private void configureBottomRadioGroup(ScheduleType scheduleType) {
        roundRobinRadioButton.setVisibility(View.GONE);
        americanRadioButton.setVisibility(View.GONE);
        singleRadioButton.setVisibility(View.GONE);
        winnersRadioButton.setVisibility(View.GONE);
        losersRadioButton.setVisibility(View.GONE);
        switch (scheduleType) {
            case RoundRobin:
                roundRobinRadioButton.setVisibility(View.VISIBLE);
                roundRobinRadioButton.setChecked(true);
                break;
            case American:
                americanRadioButton.setVisibility(View.VISIBLE);
                americanRadioButton.setChecked(true);
                break;
            case SingleElimination:
                singleRadioButton.setVisibility(View.VISIBLE);
                singleRadioButton.setChecked(true);
                break;
            case DoubleElimination:
                winnersRadioButton.setVisibility(View.VISIBLE);
                winnersRadioButton.setChecked(true);
                losersRadioButton.setVisibility(View.VISIBLE);
                break;
        }
    }

    @OnClick( R.id.radio_losers )
    public void onClickLoserGames() {
        GamesTabFragment fragment = new GamesTabFragment();
        fragment.setArguments(createArguments(true));
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragment)
                .commit();
    }

    @OnClick({ R.id.radio_round_robin, R.id.radio_american, R.id.radio_single, R.id.radio_winners })
    public void onClickGames() {
        GamesTabFragment fragment = new GamesTabFragment();
        fragment.setArguments(createArguments(false));
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragment)
                .commitAllowingStateLoss();
    }

    @OnClick(R.id.radio_table)
    public void onClickTable() {
        if (hud == null) {
            hud = KProgressHUD.create(GroupDetailsActivity.this)
                    .setStyle(KProgressHUD.Style.ANNULAR_DETERMINATE)
                    .setLabel("Calculating...")
                    .setDimAmount(0.2f)
                    .setMaxProgress(100);
        }
        if (teamStatsFragment == null) {
            teamStatsFragment = new TeamStatsFragment();
            teamStatsFragment.setArguments(createArguments(false));
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, teamStatsFragment)
                .commit();
    }

    @OnClick(R.id.radio_chart)
    public void onClickCharts() {
        if (hud == null) {
            hud = KProgressHUD.create(GroupDetailsActivity.this)
                    .setStyle(KProgressHUD.Style.ANNULAR_DETERMINATE)
                    .setLabel("Drawing...")
                    .setDimAmount(0.2f)
                    .setMaxProgress(100);
        }
        if (chartTabStatsFragment == null) {
            chartTabStatsFragment = new ChartsTabFragment();
            chartTabStatsFragment.setArguments(createArguments(false));
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, chartTabStatsFragment)
                .commit();
    }

    public void showHud() {
        if (hud != null) {
            hud.setProgress(0);
            hud.show();
        }
    }

    public void hideHud() {
        if (hud != null) {
            hud.dismiss();
        }
    }

    public void progressHud(int value) {
        if (hud != null) {
            hud.setProgress(value);
        }
    }
}
