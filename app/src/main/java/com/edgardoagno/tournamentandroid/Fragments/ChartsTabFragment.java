package com.edgardoagno.tournamentandroid.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edgardoagno.tournamentandroid.GroupDetailsActivity;
import com.edgardoagno.tournamentandroid.R;
import com.edgardoagno.tournamentandroid.ViewModels.FragmentViewModels.ChartsTabViewModel;

import io.realm.RealmChangeListener;
import rx.functions.Action1;


/**
 * Created by edgardoagno on 21/07/16.
 */

public class ChartsTabFragment extends Fragment {

    private GroupDetailsActivity activity;
    private ChartsTabViewModel viewModel;
    private boolean reload;
    private RealmChangeListener gamesListener;

    public ChartsTabFragment() {
        super();
        reload = true;
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

        final View view = inflater.inflate(R.layout.group_details_tab_container_view, container, false);
        final Long id = getArguments().getLong("GROUP_ID");

        if (viewModel == null) {
            viewModel = new ChartsTabViewModel(id);

            if (viewModel.games != null) {
                gamesListener = new RealmChangeListener() {
                    @Override
                    public void onChange(Object element) {
                        reload = true;
                    }
                };
                viewModel.games.addChangeListener(gamesListener);
            }
        }

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

        if (reload) {
            activity.showHud();
            AsyncTask<String, String, String> task = new AsyncTask<String, String, String>() {
                @Override
                protected String doInBackground(String... params) {
                    viewModel.loadStatsList();
                    return null;
                }
                protected void onPostExecute(String file_url) {
                    activity.runOnUiThread(new Runnable() {
                        public void run() {
                            activity.hideHud();
                            refresh(view);
                        }
                    });
                }
            };
            task.execute();
            reload = false;
        }

        if (viewModel.teamStatsList != null) {
            refresh(view);
        }

        return view;
    }

    private void refresh(View view) {
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.pages_tabs);
        for (String s : viewModel.tabNames) {
            tabLayout.addTab(tabLayout.newTab().setText(s));
        }
        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        viewPager.setAdapter(new PagerAdapter(getFragmentManager(), tabLayout.getTabCount()));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    public class PagerAdapter extends FragmentStatePagerAdapter {
        int mNumOfTabs;

        public PagerAdapter(FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
        }

        @Override
        public Fragment getItem(int position) {
            Long id = getArguments().getLong("GROUP_ID");
            Bundle args = new Bundle();
            args.putLong("GROUP_ID", id);
            switch (position) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                    ChartGamesPlayedFragment tab = new ChartGamesPlayedFragment();
                    tab.setArguments(args);
                    return tab;
            }
            return null;
        }

        @Override
        public int getCount() {
            return mNumOfTabs;
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}

