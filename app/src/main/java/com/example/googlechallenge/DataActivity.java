package com.example.googlechallenge;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class DataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        BarChart barChart = (BarChart) findViewById(R.id.barchart);

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(5f, 0));
        entries.add(new BarEntry(6f, 1));
        entries.add(new BarEntry(7f, 2));
        entries.add(new BarEntry(4f, 3));
        entries.add(new BarEntry(5f, 4));
        entries.add(new BarEntry(8f, 5));
        entries.add(new BarEntry(7f, 6));

        BarDataSet bardataset = new BarDataSet(entries, "Cells");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Mon");
        labels.add("Tue");
        labels.add("Wed");
        labels.add("Thur");
        labels.add("Fri");
        labels.add("Sat");
        labels.add("Sun");

        bardataset.setColors(new int[] {Color.YELLOW, Color.YELLOW, Color.GREEN, Color.RED, Color.RED, Color.GREEN, Color.GREEN});

        BarData data = new BarData(labels, bardataset);
        barChart.setData(data); // set the data and list of labels into chart
        barChart.setDescription("Set Bar Chart Description Here");  // set the description
        barChart.animateY(3000);

    }
}