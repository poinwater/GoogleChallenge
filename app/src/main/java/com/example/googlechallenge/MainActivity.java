package com.example.googlechallenge;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayout;

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

        Button wakeup = findViewById(R.id.btn_wakeup);
        wakeup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(hours[0]!=-1 && minutes[0]!=-1){
                    Intent intent = new Intent(getApplicationContext(), com.example.googlechallenge.LockScreen.class);
                    intent.putExtra("userPickTime", hours[0] + ":" + minutes[0]);

                    wakeupAfterOneMinute(v);
                    startActivity(intent);
                }else{

                }
                Toast.makeText(v.getContext(), "Please set your wake up time first!", Toast.LENGTH_SHORT).show();

            }
        });


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

    private void wakeupAfterOneMinute(View v){
        int i = 3;
        Intent intent = new Intent(v.getContext(), BoardcastReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this.getApplicationContext(), 234324243, intent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                + (i * 1000), pendingIntent);
        Toast.makeText(this, "Alarm set in " + i + " seconds",Toast.LENGTH_LONG).show();
    }

    public void lockScreen(View view) {
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

