package com.edgardoagno.tournamentandroid.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.edgardoagno.tournamentandroid.R;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.group_details_chart_view, container, false);

        View view = inflater.inflate(R.layout.group_details_chart_pie_view, container, false);
        int countGames = getArguments().getInt("COUNT_GAMES");

        chart = (PieChart) view.findViewById(R.id.pieChart1);
        chart.setUsePercentValues(false);

        chart.setDrawHoleEnabled(true); // viewModel?
        chart.setHoleRadius(58f);
        chart.setTransparentCircleRadius(61f);
        chart.setDescription("");
        chart.setExtraOffsets(5f, 10f, 5f, 5f);
        chart.setCenterText(String.format("%1$s games", countGames));
        chart.setRotationAngle(0f);
        chart.setRotationEnabled(false);
        chart.setHighlightPerTapEnabled(true);

        Legend l = chart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        chart.setData(generatePieData());

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    protected PieData generatePieData() {

        ArrayList<PieEntry> entries1 = new ArrayList<PieEntry>();
        int countPlayed = getArguments().getInt("COUNT_PLAYED");
        int countNotPlayed = getArguments().getInt("COUNT_NOT_PLAYED");
        entries1.add(new PieEntry((float) countPlayed, "Played"));
        entries1.add(new PieEntry((float) countNotPlayed, "Not played"));

//        for(int i = 0; i < count; i++) {
//            entries1.add(new PieEntry((float) ((Math.random() * 60) + 40), "Quarter " + (i+1)));
//        }

        PieDataSet ds1 = new PieDataSet(entries1, "");
        ds1.setColors(ColorTemplate.VORDIPLOM_COLORS); // viewModel!
        ds1.setSliceSpace(2f);
        ds1.setValueTextColor(Color.WHITE);
        ds1.setValueTextSize(12f);

        PieData d = new PieData(ds1);

        return d;
    }

}

