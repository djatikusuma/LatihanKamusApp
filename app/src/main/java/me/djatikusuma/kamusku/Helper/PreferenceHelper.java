package me.djatikusuma.kamusku.Helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import me.djatikusuma.kamusku.R;

/**
 * Created by djatikusuma on 03/01/2018.
 *
 */

public class PreferenceHelper {

    private Context context;
    private SharedPreferences prefs;

    public PreferenceHelper(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        this.context = context;
    }

    public void setFirstRun(Boolean input) {
        SharedPreferences.Editor editor = prefs.edit();
        String key = context.getResources().getString(R.string.isFirstRun);
        editor.putBoolean(key, input);
        editor.apply();
    }

    public Boolean getFirstRun() {
        String key = context.getResources().getString(R.string.isFirstRun);
        return prefs.getBoolean(key, true);
    }
}
