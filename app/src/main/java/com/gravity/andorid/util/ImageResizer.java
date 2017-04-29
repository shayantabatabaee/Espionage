package com.gravity.andorid.util;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class ImageResizer {

    public static byte[] resizeImage(byte[] input){
        Bitmap original = BitmapFactory.decodeByteArray(input , 0, input.length);
        Bitmap resized = Bitmap.createScaledBitmap(original, 480, 300, true);

        ByteArrayOutputStream blob = new ByteArrayOutputStream();
        resized.compress(Bitmap.CompressFormat.PNG, 70, blob);

        return blob.toByteArray();
    }
}
