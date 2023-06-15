package com.example.whatsappdesign;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;

public class SettingsActivity extends AppCompatActivity {

    private SwitchCompat darkModeSwitch;
    private ImageView backButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        darkModeSwitch = findViewById(R.id.darkModeSwitch);
        backButton = findViewById(R.id.backButton2);
        // Load the current state of dark mode from shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("settings", MODE_PRIVATE);
        boolean isDarkModeEnabled = sharedPreferences.getBoolean("dark_mode_enabled", false);
        darkModeSwitch.setChecked(isDarkModeEnabled);

        darkModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Save the state of dark mode to shared preferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("dark_mode_enabled", isChecked);
                editor.apply();

                // Apply the selected mode immediately
                applyDarkMode(isChecked);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public static void applyDarkMode(boolean isEnabled) {
        if (isEnabled) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}