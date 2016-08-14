package com.edgardoagno.tournamentandroid.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edgardoagno.tournamentandroid.R;
import com.edgardoagno.tournamentandroid.ViewModels.FragmentViewModels.ChartPieViewModel;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


/**
 * Created by edgardoagno on 13/08/16.
 */
public class ChartPieFragment extends Fragment {

    public ChartPieFragment() {
        super();
    }

    private PieChart chart;
    private ChartPieViewModel viewModel;
    private Long groupId;
    private Integer chartPosition;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.group_details_chart_pie_view, container, false);
        groupId = getArguments().getLong("GROUP_ID", 0);
        chartPosition = getArguments().getInt("CHART_POSITION", 0);
        viewModel = new ChartPieViewModel(groupId, chartPosition);

        chart = (PieChart) view.findViewById(R.id.pieChart1);
        chart.setUsePercentValues(false);

        chart.setDrawHoleEnabled(viewModel.isHoleEnabled());
        chart.setHoleRadius(58f);
        chart.setTransparentCircleRadius(61f);
        chart.setDescription("");
        chart.setExtraOffsets(5f, 10f, 5f, 5f);
//        chart.getDefaultValueFormatter()
        if (viewModel.isHoleEnabled()) {
            chart.setCenterText(String.format("%1$s games", viewModel.getCountGames()));
        } else {
            chart.setCenterText("");
        }
        chart.setRotationAngle(0f);
        chart.setRotationEnabled(false);
        chart.setHighlightPerTapEnabled(true);

        Legend l = chart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        chart.setData(generatePieData());
        chart.animateX(500);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    protected PieData generatePieData() {
        ArrayList<PieEntry> entries1 = new ArrayList<PieEntry>();
        ArrayList<String> titles = viewModel.getTitles();
        ArrayList<Integer> values = viewModel.getValues();
        for (int i = 0; i < titles.size(); i++) {
            entries1.add(new PieEntry((float) values.get(i), titles.get(i)));
        }

        PieDataSet ds1 = new PieDataSet(entries1, "");
        ds1.setColors(ColorTemplate.MATERIAL_COLORS); // viewModel!
        ds1.setSliceSpace(2f);
        ds1.setValueTextColor(Color.WHITE);
        ds1.setValueTextSize(12f);

        PieData d = new PieData(ds1);

        return d;
    }
}

