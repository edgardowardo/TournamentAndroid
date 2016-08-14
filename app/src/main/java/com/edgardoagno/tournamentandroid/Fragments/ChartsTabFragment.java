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

import io.realm.RealmResults;
import rx.functions.Action1;


/**
 * Created by edgardoagno on 21/07/16.
 */

public class ChartsTabFragment extends Fragment {

    private GroupDetailsActivity activity;
    private ChartsTabViewModel viewModel;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private PagerAdapter adapter;
    private Long groupId;

    public ChartsTabFragment() {
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

        final View view = inflater.inflate(R.layout.group_details_tab_container_view, container, false);
        groupId = getArguments().getLong("GROUP_ID");

        if (viewModel == null) {
            viewModel = new ChartsTabViewModel(groupId);
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

        if (!viewModel.haveStats()) {
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
        }

        if (viewModel.teamStatsList != null) {
            refresh(view);
        }

        return view;
    }

    private void refresh(View view) {
        tabLayout = (TabLayout) view.findViewById(R.id.pages_tabs);
        for (String s : viewModel.tabNames) {
            tabLayout.addTab(tabLayout.newTab().setText(s));
        }
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        adapter = new PagerAdapter(getFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
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

        TabLayout.Tab tab = tabLayout.getTabAt(0);
        tab.select();
    }

    public class PagerAdapter extends FragmentStatePagerAdapter {
        int numOfTabs;

        public PagerAdapter(FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.numOfTabs = NumOfTabs;
        }
        @Override
        public Fragment getItem(int position) {
            Long id = getArguments().getLong("GROUP_ID");
            Bundle args = new Bundle();
            switch (position) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4: {
                    ChartPieFragment tab = new ChartPieFragment();
                    args.putLong("GROUP_ID", groupId);
                    args.putInt("CHART_POSITION", position);
                    tab.setArguments(args);
                    return tab;
                }
//                case 1: {
//                    // TODO: Horizontal Bar Chart
//                    return tab;
//                }
            }
            return null;
        }

        @Override
        public int getCount() {
            return numOfTabs;
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}

