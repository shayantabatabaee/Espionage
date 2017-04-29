package com.gravity.andorid.business;

import android.content.Context;

import com.google.gson.Gson;
import com.gravity.andorid.business.model.ModifiedLocation;
import com.gravity.andorid.business.net.BuddyDatabaseHelper;

import java.util.Map;

public class LocationRepository implements BuddyDatabaseHelper.OnRemoveSharedPrefListener {

    private static LocationRepository instance;
    private ModifiedLocation modifiedLocation;
    private Context mContext;
    private SharedPrefsHelper sharedPrefsHelper;
    private Gson gson;


    private LocationRepository(Context context) {
        this.mContext = context;
        sharedPrefsHelper = new SharedPrefsHelper(context);
        gson = new Gson();
    }

    public static LocationRepository getInstance(Context context) {
        if (instance == null) {
            instance = new LocationRepository(context);
        }
        return instance;
    }


    public void addLocation(ModifiedLocation modifiedLocation) {
        String locationJson = gson.toJson(modifiedLocation);
        sharedPrefsHelper.addToSharedPrefs(modifiedLocation.getTime(), locationJson);

        Map<String, ?> allEntries = sharedPrefsHelper.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            BuddyDatabaseHelper.getInstance(mContext).addLocation(entry.getKey(), entry.getValue().toString(), this);
        }
    }

    @Override
    public void remove(String key) {
        sharedPrefsHelper.remove(key);
    }

}
