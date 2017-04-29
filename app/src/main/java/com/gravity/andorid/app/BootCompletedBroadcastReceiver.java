package com.gravity.andorid.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.gravity.andorid.app.services.LService;


public class BootCompletedBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent serviceIntent = new Intent(context, LService.class);
        context.startService(serviceIntent);
        Log.i("BroadcastReceiver", "Started");
    }
}