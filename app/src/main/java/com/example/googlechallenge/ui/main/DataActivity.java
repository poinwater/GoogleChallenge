package com.example.googlechallenge.ui.main;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.googlechallenge.R;
import com.example.googlechallenge.database.WordViewModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DataActivity extends AppCompatActivity {

    public WordViewModel mWordViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        BarChart barChart = (BarChart) findViewById(R.id.barchart);

        ArrayList<BarEntry> entries = new ArrayList<>();

        BarDataSet bardataset = new BarDataSet(entries, "Cells");

        ArrayList<String> labels = new ArrayList<String>();

        ArrayList<Integer> color = new ArrayList<>();


        //access DB interface
        mWordViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(WordViewModel.class);

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        mWordViewModel.getSleepDuration().observe(this, sleep -> {
//        // Update the cached copy of the words in the adapter.
//          //words is List typ
            for(int i = 0 ; i < sleep.size() ; i++){
                Log.d("test", "connectDatabase: "+sleep.get(i).getDuration());
                entries.add(new BarEntry((sleep.get(i).getDuration()/60), i));
                long hour = (sleep.get(i).getDuration()/60);
                if( hour <= 4){
                    color.add(Color.RED);
                }else if(hour > 4 && hour <= 6 ){
                    color.add(Color.YELLOW);
                }else{
                    color.add(Color.GREEN);
                }

                final Date[] time= new Date[1];
                try{
                    time[0] = DateFormat.getInstance().parse(sleep.get(i).getDate());
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(time[0]);
                    SimpleDateFormat sdf = new SimpleDateFormat("EEE");
                    String stringDate = sdf.format(time[0]);

                    labels.add((time[0].getMonth()+1)+"/"+time[0].getDate()+", "+(time[0].getYear()+1900)+" "+stringDate);

                }catch (Exception e){

                }

            }

            bardataset.setColors(color);

            BarData data = new BarData(labels, bardataset);

            barChart.setData(data); // set the data and list of labels into chart
            barChart.setDescription("Your Sleep Data Over the last 7 days");  // set the description
            barChart.animateY(3000);
    });
//
//


    }
}