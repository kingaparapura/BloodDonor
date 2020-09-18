package org.example.blooddonor;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import androidx.preference.PreferenceManager;

public class ThemeHelper {
    private SharedPreferences mPrefs;
    private Context context;
    private String preferenceDarkmode;

    public ThemeHelper(Context context) {
        this.context = context;
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        preferenceDarkmode = context.getString(R.string.preference_darkmode);
    }

    public boolean isNightModeEnabled() {
        return mPrefs.getBoolean(preferenceDarkmode, false);
    }

    public void setNightMode(boolean value) {
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putBoolean(preferenceDarkmode, value);
        editor.apply();
    }

    public boolean isDarkMode(Activity activity) {
        return (activity.getResources().getConfiguration()
                .uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;
    }
}