package com.edgardoagno.tournamentandroid.Fragments;

import android.graphics.Color;
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

import com.edgardoagno.tournamentandroid.R;
import com.edgardoagno.tournamentandroid.ViewModels.Fragments.GamesTabViewModel;

/**
 * Created by edgardoagno on 21/07/16.
 */

public class GamesTabFragment extends Fragment {

    private GamesTabViewModel viewModel;

    public GamesTabFragment() {
    }

    @Override
    public void onStop() {
        super.onStop();
        viewModel.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.group_details_games_tab_view, container, false);

        Long id = getArguments().getLong("GROUP_ID");
        Boolean isLosersRound = getArguments().getBoolean("IS_LOSERS_ROUND", false);
        viewModel = new GamesTabViewModel(id, isLosersRound);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.pages_tabs);
        for (String s : viewModel.tabNames) {
            tabLayout.addTab(tabLayout.newTab().setText(s));
        }

        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.view_pager);

        if (isLosersRound) {
            viewPager.setBackgroundColor(Color.DKGRAY);
            tabLayout.setBackgroundColor(Color.DKGRAY);
        }

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

        TabLayout.Tab tab = tabLayout.getTabAt(viewModel.getIndexOfIncompletedRound());
        tab.select();

        return view;
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
            int round = viewModel.getRound(position);
            Boolean isLosersRound = getArguments().getBoolean("IS_LOSERS_ROUND", false);

            Bundle args = new Bundle();
            args.putLong("GROUP_ID", id);
            args.putInt("ROUND_NO", round);
            args.putBoolean("IS_LOSERS_ROUND", isLosersRound);

            GamesFragment tab = new GamesFragment();
            tab.setArguments(args);
            return tab;
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