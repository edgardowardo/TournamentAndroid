package com.edgardoagno.tournamentandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;

import com.edgardoagno.tournamentandroid.Models.ScheduleType;
import com.edgardoagno.tournamentandroid.ViewModels.GroupDetailsViewModel;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GroupDetailsActivity extends AppCompatActivity {

    private GroupDetailsViewModel viewModel;
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

        Long id = getIntent().getLongExtra("GROUP_ID", 0);
        viewModel = new GroupDetailsViewModel(id);
        setTitle(viewModel.getTitle());

        ButterKnife.bind(this);
        configureBottomRadioGroup(viewModel._group.getScheduleType());

        if (savedInstanceState == null) {
            GamesTabFragment fragment = new GamesTabFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content, fragment)
                    .commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel.onDestroy();
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

    @OnClick({ R.id.radio_round_robin, R.id.radio_american, R.id.radio_single, R.id.radio_winners, R.id.radio_losers})
    public void onClickGames() {
        GamesTabFragment fragment = new GamesTabFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragment)
                .commit();
    }

    @OnClick(R.id.radio_table)
    public void onClickTable() {
        TeamStatsFragment fragment = new TeamStatsFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragment)
                .commit();
    }

    @OnClick(R.id.radio_chart)
    public void onClickCharts() {
        ChartsFragment fragment = new ChartsFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content, fragment)
                .commit();
    }
}
