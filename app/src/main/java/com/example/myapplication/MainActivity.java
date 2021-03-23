package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final int[] hours = {-1};
        final int[] minutes = {-1};

        Button lockScreen = findViewById(R.id.btn_lockscreen);

        lockScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LockScreen.class);
                startActivity(intent);
            }
        });

        Button wakeup = findViewById(R.id.btn_wakeup);
        wakeup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(hours[0]!=-1 && minutes[0]!=-1){
                    Intent intent = new Intent(getApplicationContext(), LockScreen.class);
                    intent.putExtra("userPickTime", hours[0] + ":" + minutes[0]);

                    wakeupAfterOneMinute(v);
                    startActivity(intent);
                }else{

                }
                Toast.makeText(v.getContext(), "Please set your wake up time first!", Toast.LENGTH_SHORT).show();

            }
        });

        TimePicker simpleTimePicker = (TimePicker)findViewById(R.id.simpleTimePicker);
        Date date = new Date();
        simpleTimePicker.setCurrentHour(date.getHours());
        simpleTimePicker.setCurrentHour(date.getMinutes());

        TextView text_userSetTime = findViewById(R.id.text_userSetTime);


        Button btn_confirmTime = findViewById(R.id.btn_confirmTime);
        btn_confirmTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hours[0] = simpleTimePicker.getHour();
                minutes[0] = simpleTimePicker.getMinute();
                text_userSetTime.setText(simpleTimePicker.getHour()+" : "+simpleTimePicker.getMinute());

            }
        });
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
}

