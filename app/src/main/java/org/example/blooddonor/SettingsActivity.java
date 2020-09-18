package org.example.blooddonor;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.preference.PreferenceManager;

import java.lang.ref.WeakReference;

public class SettingsActivity extends AppCompatActivity {
    SwitchCompat switchCompat;
    private WeakReference<Activity> mActivity;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        boolean check = sharedPreferences.getBoolean("switchkey", false);
        final ThemeHelper themeHelper = new ThemeHelper(this);

        if (themeHelper.isNightModeEnabled()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        switchCompat = findViewById(R.id.nightModeSwitch);

        if (check) {
            switchCompat.setText("Dark Mode On");
        } else {
            switchCompat.setText("Dark Mode Off");
        }

        switchCompat.setChecked(check);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor.putBoolean("shouldRecreate", true);
                editor.apply();
                mActivity = new WeakReference<Activity>(SettingsActivity.this);
                themeHelper.setNightMode(isChecked);

                if (isChecked) {
                    switchCompat.setText("Dark Mode On");
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    switchCompat.setText("Dark Mode Off");
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }

                editor.putBoolean("switchkey", isChecked);
                editor.apply();
                mActivity.get().recreate();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onBackPressed() {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this);
        Boolean val = sharedPreferences.getBoolean("shouldRecreate", true);
        if (val)
            startActivity(new Intent(this, MainActivity.class));

        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();

        return true;
    }
}