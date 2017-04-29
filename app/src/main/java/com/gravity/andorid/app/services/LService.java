package com.gravity.andorid.app.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.gravity.andorid.business.LocationRepository;
import com.gravity.andorid.business.model.ModifiedLocation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LService extends Service implements LocationListener {


    private LocationManager locationManager;
    boolean isGPSEnabled;
    boolean isNetworkEnabled;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("LService", "onStart");

        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnabled && !isNetworkEnabled) {
            stopSelf();
            return START_STICKY;
        }

        if (isNetworkEnabled) {
            Log.d("Network", "Network Enabled");
            try {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
                stopSelf();
                return START_STICKY;
            } catch (SecurityException e) {
                e.printStackTrace();
            }


        }
        if (isGPSEnabled) {
            Log.d("GPS", "GPS Enabled");
            try {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                stopSelf();
                return START_STICKY;
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }

        stopSelf();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.d("LService", "Service Stopped");
        AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarm.set(
                alarm.RTC_WAKEUP,
                System.currentTimeMillis() + (1000 * 60 * 60),
                PendingIntent.getService(this, 0, new Intent(this, LService.class), 0)
        );

    }

    @Override
    public void onLocationChanged(Location location) {
        try {
            locationManager.removeUpdates(this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        Log.d("Location is : ", location.getLatitude() + " " + location.getLongitude());
        LocationRepository.getInstance(getApplicationContext()).addLocation(new ModifiedLocation(getTime(), location));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private static String getTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        return dateFormat.format(calendar.getTime());
    }
}
