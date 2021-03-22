package com.example.myapplication;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class MyAdmin extends DeviceAdminReceiver {
    @Override
    public void onEnabled(@NonNull Context context, @NonNull Intent intent) {
//        super.onEnabled(context, intent);
        Toast.makeText(context, "Hello admin", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDisabled(@NonNull Context context, @NonNull Intent intent) {
//        super.onDisabled(context, intent);
        Toast.makeText(context, "You are not admin", Toast.LENGTH_SHORT).show();

    }
}
