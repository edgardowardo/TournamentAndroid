package com.edgardoagno.tournamentandroid.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edgardoagno.tournamentandroid.GroupDetailsActivity;
import com.edgardoagno.tournamentandroid.R;
import com.edgardoagno.tournamentandroid.ViewModels.Fragments.TeamStatsViewModel;

import rx.functions.Action1;

/**
 * Created by edgardoagno on 21/07/16.
 */
public class TeamStatsFragment extends Fragment {

    private GroupDetailsActivity activity;
    private TeamStatsViewModel viewModel;

    public TeamStatsFragment() {
        super();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity){
            activity = (GroupDetailsActivity) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.group_details_team_stats_item_view, container, false);
        final Long id = getArguments().getLong("GROUP_ID", 0);
        viewModel = new TeamStatsViewModel();
        viewModel.progressEmitterSubject
                .asObservable()
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer value) {
                        final int v = value;
                        activity.runOnUiThread(new Runnable() {
                            public void run() {
                                activity.progressHud(v);
                            }
                        });
                    }
                });
        activity.showHud();
        AsyncTask<String, String, String> task = new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... params) {
                viewModel.loadStatsList(id);
                return null;
            }
            protected void onPostExecute(String file_url) {
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        activity.hideHud();
                    }
                });
            }
        };
        task.execute();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

}
