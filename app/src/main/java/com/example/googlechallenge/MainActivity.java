package com.example.googlechallenge;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayout;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences mPreferences;
    private String sharedPrefFile =
            "com.example.googlechallengeprefs";
    final int[] hours = {-1};
    final int[] minutes = {-1};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navBarSwitch();

        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        TimePicker simpleTimePicker = (TimePicker)findViewById(R.id.simpleTimePicker);
        TextView text_userSetTime = findViewById(R.id.text_userSetTime);

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
        text_userSetTime.setText(hours[0] + " : "+ minutes[0]);

        Button lockScreen = findViewById(R.id.btn_lockscreen);




        Button btn_confirmTime = findViewById(R.id.btn_confirmTime);
        btn_confirmTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hours[0] = simpleTimePicker.getHour();
                minutes[0] = simpleTimePicker.getMinute();
                text_userSetTime.setText(simpleTimePicker.getHour()+" : "+simpleTimePicker.getMinute());
                Log.d("test", hours[0]+" : "+minutes[0]);

            }
        });
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d("test", hours[0]+" : "+ minutes[0]);

        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putInt("hours", hours[0]);
        preferencesEditor.putInt("minutes", minutes[0]);
        preferencesEditor.apply();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sleepNow(View v){
        if (minutes[0] == -1 || hours[0] == -1) {
            Toast.makeText(this, "Please set the wake up time first!",Toast.LENGTH_LONG).show();
            return;
        }



        Date currentTime = Calendar.getInstance().getTime();
        Date wakeUpTime = Calendar.getInstance().getTime();
        wakeUpTime.setHours(hours[0]);
        wakeUpTime.setMinutes(minutes[0]);
        wakeUpTime.setSeconds(0);

        if (wakeUpTime.before(currentTime)) {
            wakeUpTime.setDate(currentTime.getDate() + 1);
        }


        Log.i("current time", currentTime.toString());
        Log.i("wake time", wakeUpTime.toString());


        Intent intent = new Intent(v.getContext(), BoardcastReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this.getApplicationContext(), 234324243, intent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, wakeUpTime.getTime(), pendingIntent);
        Toast.makeText(this, "Alarm set at " + hours[0] + ":" + minutes[0] + " clock", Toast.LENGTH_LONG).show();
        lockScreen();

    }

    public void lockScreen() {
        Intent intent = new Intent(getApplicationContext(), LockScreen.class);
        startActivity(intent);
    }

    public void navBarSwitch(){
        TabLayout navBar = findViewById(R.id.navBar);
        navBar.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            Intent intent;
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        intent = new Intent (getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent (getApplicationContext(), ItemInventory.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent (getApplicationContext(), DataActivity.class);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent (getApplicationContext(), SettingActivity.class);
                        startActivity(intent);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}

