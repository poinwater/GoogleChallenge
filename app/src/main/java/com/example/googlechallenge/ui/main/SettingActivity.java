package com.example.googlechallenge.ui.main;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.googlechallenge.R;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Spinner languageSpinner = findViewById(R.id.languageSpinner);
        EditText userNameEditor = findViewById(R.id.userNameEditText);
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.googlechallenge", MODE_PRIVATE);
        String UserName = sharedPreferences.getString("username", "");

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.language_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(adapter);
        userNameEditor.setText(UserName);

        userNameEditor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sharedPreferences.edit().putString("username", s.toString()).apply();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }
}