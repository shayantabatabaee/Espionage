package com.gravity.andorid.business.model;

import android.location.Location;

public class ModifiedLocation {

    private String time;
    private String provider;
    private String latitude;
    private String longitude;
    private String accuracy;


    public ModifiedLocation(String time, Location location) {
        if (location != null) {
            this.latitude = String.valueOf(location.getLatitude());
            this.longitude = String.valueOf(location.getLongitude());
            this.accuracy = String.valueOf(location.getAccuracy());
            this.provider = String.valueOf(location.getProvider());
        }
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public String getProvider() {
        return provider;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getAccuracy() {
        return accuracy;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }
}
