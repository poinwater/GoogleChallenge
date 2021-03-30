package com.example.googlechallenge.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.googlechallenge.MainActivity;
import com.example.googlechallenge.R;
import com.example.googlechallenge.item.GiftActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LockScreen extends AppCompatActivity {
    Toast text;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lock_screen);
        String getUserPickTime = getIntent().getStringExtra("userPickTime");
        Date currentTime = Calendar.getInstance().getTime();
        TextView time = findViewById(R.id.text_currenttime);
        if(currentTime.getHours()/10 ==0){
            if(currentTime.getMinutes()/10==0){
                time.setText("Current time 0"+currentTime.getHours()+":0"+currentTime.getMinutes());
            }else {
                time.setText("Current time 0" + currentTime.getHours() + ":" + currentTime.getMinutes());
            }
        }else{
            if(currentTime.getMinutes()/10==0){
                time.setText("Current time "+currentTime.getHours()+":0"+currentTime.getMinutes());
            }else {
                time.setText("Current time " + currentTime.getHours() + ":" + currentTime.getMinutes());
            }
        }

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date wakeUpDate = new Date(MainActivity.wakeUpTime);
        TextView wakeUpTime = findViewById(R.id.text_wakeupTime);
        wakeUpTime.setText("Expected wakeup time: " + dateFormat.format(wakeUpDate));


        Button unlock = findViewById(R.id.btn_unlockscreen);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }
    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        finish();
    }

    public void unLockScreen(View v) {

        long currentTime = System.currentTimeMillis();
        if (BoardcastReceiver.mp != null && BoardcastReceiver.mp.isPlaying()) {
            BoardcastReceiver.mp.stop();
        }
        if (currentTime < MainActivity.wakeUpTime) {
            MainActivity.alarmManager.cancel(MainActivity.pendingIntent);
        }

        MainActivity.sleepingStatus = MainActivity.getSleepingStatus(MainActivity.sleepTime, currentTime);
        // TODO: Testing for gift branch, reset to == 0 after testing
        if (MainActivity.sleepingStatus != 0){
            Log.i("sleeping Status", String.valueOf(MainActivity.sleepingStatus));
            text.makeText(v.getContext(), "Your sleeping time is too short to get a gift!", Toast.LENGTH_SHORT).show();
            showSystemUI();
        } else {
            Log.i("sleeping Status", String.valueOf(MainActivity.sleepingStatus));
            text.makeText(v.getContext(), "Alice brought a gift during your sleeping time!", Toast.LENGTH_SHORT).show();
            showSystemUI();
            Intent intent = new Intent(this, GiftActivity.class);
            startActivity(intent);
        }


    }
}
