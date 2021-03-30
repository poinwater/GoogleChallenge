package com.example.myapplication.ui.main;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.database.WordViewModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StaticsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StaticsFragment extends Fragment {
    public WordViewModel mWordViewModel;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StaticsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StaticsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StaticsFragment newInstance(String param1, String param2) {
        StaticsFragment fragment = new StaticsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root =  inflater.inflate(R.layout.fragment_statics, container, false);
        BarChart barChart = (BarChart) root.findViewById(R.id.barchart);

        ArrayList<BarEntry> entries = new ArrayList<>();

        BarDataSet bardataset = new BarDataSet(entries, "Cells");

        ArrayList<String> labels = new ArrayList<String>();

        ArrayList<Integer> color = new ArrayList<>();


        //access DB interface
        mWordViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())).get(WordViewModel.class);

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        mWordViewModel.getSleepDuration().observe(getViewLifecycleOwner(), sleep -> {
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
        return root;
    }
}