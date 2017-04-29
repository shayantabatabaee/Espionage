package com.gravity.andorid.app.services;

import android.app.Service;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.gravity.andorid.business.net.BuddyDatabaseHelper;
import com.gravity.andorid.util.ImageResizer;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;


public class CamService extends Service implements Camera.PictureCallback {


    private Camera mCamera;
    private Camera.Parameters parameters;
    private ScheduledExecutorService mScheduledExecutorService = Executors.newScheduledThreadPool(1);
    ;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mCamera = CamService.getCameraInstance(intent.getExtras().getInt("camera"));
        try {
            parameters = mCamera.getParameters();
            //parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
            //parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);


            //set camera parameters

            mCamera.setParameters(parameters);
            mCamera.startPreview();

           /* final ScheduledFuture<?> focusTimeoutFuture = mScheduledExecutorService.schedule(new Runnable() {
                @Override
                public void run() {
                    mCamera.takePicture(null, null, CamService.this);
                }
            }, 3, TimeUnit.SECONDS);

            mCamera.autoFocus(new Camera.AutoFocusCallback() {
                @Override
                public void onAutoFocus(boolean success, Camera camera) {

                    boolean canceledFuture = focusTimeoutFuture.cancel(false);
                    if (canceledFuture) {
                        mCamera.takePicture(null, null, CamService.this);
                    }
                }
            });*/

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mCamera.takePicture(null, null, CamService.this);
                }
            },1000);


        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
        super.onDestroy();
    }

    @Override
    public void onCreate() {

        super.onCreate();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static Camera getCameraInstance(int id) {
        Camera c = null;
        try {
            c = Camera.open(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }

    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        mCamera.cancelAutoFocus();
        byte[] compresedData = ImageResizer.resizeImage(data);
        BuddyDatabaseHelper.getInstance(getApplicationContext()).uploadPicture(compresedData);
        stopSelf();
    }
}
