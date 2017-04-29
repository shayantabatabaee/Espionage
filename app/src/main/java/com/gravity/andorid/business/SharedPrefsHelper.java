package com.gravity.andorid.business;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

/**
 * Created by shayantabatabee on 1/22/17.
 */

public class SharedPrefsHelper {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharedPrefsHelper(Context context) {
        sharedPreferences = context.getSharedPreferences("com.gravity.andorid", Context.MODE_PRIVATE);
    }

    public void addToSharedPrefs(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    public Map<String, ?> getAll() {
        return sharedPreferences.getAll();
    }

    public void remove(String key){
       sharedPreferences.edit().remove(key).apply();
    }
}
