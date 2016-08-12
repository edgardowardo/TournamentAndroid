package com.edgardoagno.tournamentandroid.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.edgardoagno.tournamentandroid.GroupDetailsActivity;
import com.edgardoagno.tournamentandroid.Models.TeamStats;
import com.edgardoagno.tournamentandroid.R;
import com.edgardoagno.tournamentandroid.ViewModels.Fragments.TeamStatsItemViewModel;
import com.edgardoagno.tournamentandroid.ViewModels.Fragments.TeamStatsViewModel;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.realm.RealmChangeListener;
import rx.functions.Action1;

/**
 * Created by edgardoagno on 21/07/16.
 */
public class TeamStatsFragment extends Fragment {

    private GroupDetailsActivity activity;
    private TeamStatsViewModel viewModel;
    private boolean reload;
    private RealmChangeListener gamesListener;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private TeamStatsRecyclerAdapter adapter;

    @Bind(R.id.title_points_for) TextView pointsForTextView;
    @Bind(R.id.title_points_against) TextView pointsAgainstTextView;
    @Bind(R.id.title_points_difference) TextView pointsDifferenceTextView;

    public TeamStatsFragment() {
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
        final View view = inflater.inflate(R.layout.group_details_team_stats_layout, container, false);
        final Long id = getArguments().getLong("GROUP_ID", 0);
        ButterKnife.bind(this, view);

        // Visibility
        boolean isLandscape = getContext().getResources().getBoolean(R.bool.is_landscape);
        pointsForTextView.setVisibility( (isLandscape) ? View.VISIBLE : View.GONE);
        pointsAgainstTextView.setVisibility( (isLandscape) ? View.VISIBLE : View.GONE);
        pointsDifferenceTextView.setVisibility( (isLandscape) ? View.VISIBLE : View.GONE);

        if (viewModel == null) {
            viewModel = new TeamStatsViewModel(id);

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

                            adapter = new TeamStatsRecyclerAdapter();
                            recyclerView = (RecyclerView) view.findViewById(R.id.team_stats_recycler_view);
                            layoutManager = new LinearLayoutManager(activity);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(adapter);
                        }
                    });
                }
            };
            task.execute();
            reload = false;
        }

        if (viewModel.teamStatsList != null) {
            adapter = new TeamStatsRecyclerAdapter();
            recyclerView = (RecyclerView) view.findViewById(R.id.team_stats_recycler_view);
            layoutManager = new LinearLayoutManager(activity);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public class TeamStatsRecyclerAdapter extends RecyclerView.Adapter<TeamStatsRecyclerAdapter.ViewHolder> {

        private static final int TYPE_ITEM = 0;
        private static final int TYPE_FOOTER = 1;

        public class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(LinearLayout container) {
                super(container);
            }
        }

        public class ItemViewHolder extends ViewHolder {
            @Bind(R.id.rank_text_view) TextView rankTextView;
            @Bind(R.id.rank_delta_direction_text_view) TextView deltaDirectionTextView;
            @Bind(R.id.rank_delta_value_text_view) TextView detlaValueTextView;
            @Bind(R.id.stats_name_text_view) TextView nameTextView;
            @Bind(R.id.stats_played_text_view) TextView playedTextView;
            @Bind(R.id.stats_win_text_view) TextView winTextView;
            @Bind(R.id.stats_lost_text_view) TextView lostTextView;
            @Bind(R.id.stats_drawn_text_view) TextView drawnTextView;
            @Bind(R.id.stats_points_for_text_view) TextView pointsForTextView;
            @Bind(R.id.stats_points_against_text_view) TextView pointsAgainstTextView;
            @Bind(R.id.stats_points_difference_drawn_text_view) TextView pointsDifferenceTextView;

            public ItemViewHolder(final LinearLayout container) {
                super(container);
                ButterKnife.bind(this, container);
            }
        }

        public class FooterViewHolder extends ViewHolder {
            @Bind(R.id.team_stats_footer_text_view) TextView footerTextView;
            public FooterViewHolder(LinearLayout container) {
                super(container);
                ButterKnife.bind(this, container);
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            if (viewType == TYPE_ITEM) {
                View v = inflater.inflate(R.layout.group_details_team_stats_item_view, parent, false);
                ItemViewHolder holder = new ItemViewHolder((LinearLayout) v);
                return holder;
            } else if (viewType == TYPE_FOOTER) {
                View v = inflater.inflate(R.layout.group_details_team_stats_footer_view, parent, false);
                FooterViewHolder holder = new FooterViewHolder((LinearLayout) v);
                return holder;
            }
            throw new RuntimeException("there is no matching type" + viewType + " + make sure your using types correctly");
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (holder instanceof ItemViewHolder) {
                final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                final TeamStats item = getItem(position);
                TeamStatsItemViewModel itemViewModel = new TeamStatsItemViewModel(item);
                itemViewHolder.rankTextView.setText(itemViewModel.getRank());
                itemViewHolder.deltaDirectionTextView.setText(itemViewModel.getRankDeltaDirection());
                itemViewHolder.detlaValueTextView.setText(itemViewModel.getRankDelta());
                itemViewHolder.nameTextView.setText(itemViewModel.getName());
                itemViewHolder.playedTextView.setText(itemViewModel.getPlayed());
                itemViewHolder.winTextView.setText(itemViewModel.getWins());
                itemViewHolder.lostTextView.setText(itemViewModel.getLost());
                itemViewHolder.drawnTextView.setText(itemViewModel.getDraws());
                itemViewHolder.pointsForTextView.setText(itemViewModel.getPointsFor());
                itemViewHolder.pointsAgainstTextView.setText(itemViewModel.getPointsAgainst());
                itemViewHolder.pointsDifferenceTextView.setText(itemViewModel.getPointsDifference());

                // Visibility
                boolean isLandscape = getContext().getResources().getBoolean(R.bool.is_landscape);
                itemViewHolder.pointsForTextView.setVisibility( (isLandscape) ? View.VISIBLE : View.GONE);
                itemViewHolder.pointsAgainstTextView.setVisibility( (isLandscape) ? View.VISIBLE : View.GONE);
                itemViewHolder.pointsDifferenceTextView.setVisibility( (isLandscape) ? View.VISIBLE : View.GONE);
            } else if (holder instanceof FooterViewHolder) {
                final FooterViewHolder viewHolder = (FooterViewHolder) holder;
                final TeamStats item = getItem(0);
                viewHolder.footerTextView.setText(viewModel.getFooterText(item.countGames));
            }
        }

        @Override
        public int getItemCount() {
            return viewModel.teamStatsList.size() + 1;
        }

        @Override
        public int getItemViewType(int position) {
            if (isPositionFooter(position)) {
                return TYPE_FOOTER;
            }
            return TYPE_ITEM;
        }

        private TeamStats getItem(int position) {
            return viewModel.teamStatsList.get(position);
        }

        private boolean isPositionFooter(int position) {
            return viewModel.teamStatsList.size() == position;
        }
    }
}