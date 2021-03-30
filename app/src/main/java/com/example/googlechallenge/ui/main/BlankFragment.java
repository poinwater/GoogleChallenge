package com.example.myapplication.ui.main;

import android.app.TimePickerDialog;
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


import com.example.myapplication.R;
import com.example.myapplication.database.Word;
import com.example.myapplication.database.WordViewModel;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment extends Fragment {

    private SharedPreferences mPreferences;
    private String sharedPrefFile =
            "com.example.myapplicationprefs";
    final int[] hours = {-1};
    final int[] minutes = {-1};
    final long[] duration = {0};

    final String[] startTime = new String[1];
    final String[] endTime = new String[1];

    public WordViewModel mWordViewModel;

    private Button btn_wakeuptime;
    private TextView text_userSetTime;
    private Button btn_sleepingtime;
    private Button wakeup;


    public BlankFragment() {
    }

    public static BlankFragment newInstance() {
        BlankFragment fragment = new BlankFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_blank, container, false);

        prefenceSetting(savedInstanceState);
        findViewById(root);
        setText();
        buttonOnClick(root);
        connectDatabase(root.getRootView());
        return root;
    }

    public void findViewById(View root){
        btn_wakeuptime = root.findViewById(R.id.btn_wakeuptime);
        text_userSetTime = root.findViewById(R.id.text_userSetTime);
        btn_sleepingtime = root.findViewById(R.id.btn_sleepingtime);
        wakeup = root.findViewById(R.id.btn_wakeup);
    }

    public void setText(){
        text_userSetTime.setText(hours[0]+" : "+minutes[0]);
    }

    public void buttonOnClick(View root){
        btn_wakeuptime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);
                hours[0] = mHour;
                minutes[0] = mMinute;
                text_userSetTime.setText(hours[0]+" : "+minutes[0]);
                Log.d("test", hours[0]+" : "+minutes[0]);

            }
        });

        btn_sleepingtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Date currentTime = Calendar.getInstance().getTime();
//                String current = DateFormat.getInstance().format(currentTime);
//                mWordViewModel.insertDuration(new Sleep(current,duration[0]));
//                duration[0]=0;
                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        hours[0] = hourOfDay;
                        minutes[0] = minute;
                        text_userSetTime.setText(hours[0]+" : "+minutes[0]);
                    }
                }, mHour, mMinute, true);
                timePickerDialog.show();
            }
        });

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

                    if(time > 0){
                        startTime[0] = DateFormat.getInstance().format(currentTime);
                    }else{
                        startTime[0] = startTime[0];
                    }

                    if(startTime[0] == null){
                        startTime[0] = DateFormat.getInstance().format(currentTime);
                    }
                    Log.d("test", "onClick: "+startTime[0]);
                    //wakeupAfterOneMinute(v);
                    startActivityForResult(intent, 1);
                }else{
                    Toast.makeText(v.getContext(), "Please set your wake up time first!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void prefenceSetting(Bundle savedInstanceState){
        mPreferences = getContext().getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        if(savedInstanceState != null){
            hours[0] = savedInstanceState.getInt("hours", hours[0]);
            minutes[0] = savedInstanceState.getInt("minutes", minutes[0]);

        }else{
            hours[0] = mPreferences.getInt("hours", hours[0]);
            minutes[0] = mPreferences.getInt("minutes", minutes[0]);
        }
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