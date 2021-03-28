package com.example.myapplication.ui.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.content.SharedPreferences;


import com.example.myapplication.R;
import com.example.myapplication.database.Sleep;
import com.example.myapplication.database.Word;
import com.example.myapplication.database.WordViewModel;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private SharedPreferences mPreferences;
    private String sharedPrefFile =
            "com.example.myapplicationprefs";
    final int[] hours = {-1};
    final int[] minutes = {-1};
    final long[] duration = {0};

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    final String[] startTime = new String[1];
    final String[] endTime = new String[1];

    public WordViewModel mWordViewModel;


    public BlankFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragment newInstance(String param1, String param2) {
        BlankFragment fragment = new BlankFragment();
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
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_blank, container, false);
        TextView textView = root.findViewById(R.id.text_home_fragment);
        textView.setText(mParam1 + mParam2);

        mPreferences = getContext().getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        TimePicker simpleTimePicker = (TimePicker)root.findViewById(R.id.simpleTimePicker);
        TextView text_userSetTime = root.findViewById(R.id.text_userSetTime);

        Button btn_duration = root.findViewById(R.id.btn_duration);
        btn_duration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date currentTime = Calendar.getInstance().getTime();
                String current = DateFormat.getInstance().format(currentTime);
                mWordViewModel.insertDuration(new Sleep(current,duration[0]));
                duration[0]=0;
            }
        });

        Date date = new Date();
        simpleTimePicker.setCurrentHour(date.getHours());
        simpleTimePicker.setCurrentHour(date.getMinutes());

        if(savedInstanceState != null){
            hours[0] = savedInstanceState.getInt("hours", hours[0]);
            minutes[0] = savedInstanceState.getInt("minutes", minutes[0]);

        }else{
            hours[0] = mPreferences.getInt("hours", hours[0]);
            minutes[0] = mPreferences.getInt("minutes", minutes[0]);
        }
        simpleTimePicker.setCurrentHour(hours[0]);
        simpleTimePicker.setCurrentMinute(minutes[0]);
        text_userSetTime.setText(hours[0]+" : "+minutes[0]);

        Button wakeup = root.findViewById(R.id.btn_wakeup);
        connectDatabase(root.getRootView());
        wakeup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(hours[0]!=-1 && minutes[0]!=-1){
                    Intent intent = new Intent(getContext(), LockScreen.class);
                    intent.putExtra("userPickTime", hours[0] + ":" + minutes[0]);


                    Date currentTime = Calendar.getInstance().getTime();

                    long time =0;
                    try{
                        Date lastTime = DateFormat.getInstance().parse(endTime[0]);
                        long diff = currentTime.getTime() - lastTime.getTime();
                        time = TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS);
                        Log.d("test", "onClick: "+startTime[0]);
                    }catch (Exception e){

                    }

                    //if the interval time lower than 30 mins, it will not count as a break
                    //https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/TimeUnit.html#convert-long-java.util.concurrent.TimeUnit-

                    if(time > 30 ){
                        startTime[0] = DateFormat.getInstance().format(currentTime);
                    }else{
                        startTime[0] = startTime[0];
                    }
                    Log.d("test", "onClick: "+startTime[0]);
                    //wakeupAfterOneMinute(v);
                    startActivityForResult(intent, 1);
                }else{
                    Toast.makeText(v.getContext(), "Please set your wake up time first!", Toast.LENGTH_SHORT).show();
                }

            }
        });





        Button btn_confirmTime = root.findViewById(R.id.btn_confirmTime);
        btn_confirmTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hours[0] = simpleTimePicker.getHour();
                minutes[0] = simpleTimePicker.getMinute();
                text_userSetTime.setText(simpleTimePicker.getHour()+" : "+simpleTimePicker.getMinute());
                Log.d("test", hours[0]+" : "+minutes[0]);

            }
        });

        return root;
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Date currentTime = Calendar.getInstance().getTime();
        endTime[0] = DateFormat.getInstance().format(currentTime);
        Log.d("test", "onActivityResult: user choose time: "+"start Time: "+startTime[0]+" end Time: "+endTime[0]);

        long time =0;
        try{
            Date lastTime = DateFormat.getInstance().parse(startTime[0]);
            long diff = currentTime.getTime() - lastTime.getTime();
            time = TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS);
            duration[0] = duration[0] + time;
            Word word = new Word(startTime[0], endTime[0]);
            mWordViewModel.insert(word);
            Log.d("test", "how many time user sleep: "+time);
        }catch (Exception e){

        }

        //when the time is down, ask user to click the end sleep button to store the sleeping data for today.

    }

    public void connectDatabase(View v){
        mWordViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication())).get(WordViewModel.class);

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        Log.d("test", "connectDatabase: ");
        mWordViewModel.getAllWords().observe(getViewLifecycleOwner(), words -> {
            // Update the cached copy of the words in the adapter.
            for(int i = 0 ; i < words.size() ; i++){
                Log.d("test", "connectDatabase: "+words.get(i).getStartTime());
            }
        });
    }

}