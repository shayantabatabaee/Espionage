package com.gravity.andorid.app.services;

import android.app.Service;
import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.gravity.andorid.business.net.BuddyDatabaseHelper;
import com.gravity.andorid.util.ShortToByteConvertor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class AudioService extends Service {

    private AudioRecord audioRecord;
    private int minBufferSize;
    private final static int RECORDER_SAMPLE_RATE = 15000;
    private final static int RECORDER_CHANNELS = AudioFormat.CHANNEL_IN_MONO;
    private final static int RECORDER_AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;
    private short[] buffer;
    private int BufferElement2Rec = 1024;
    private int BytesPerElement = 2;


    @Override
    public void onCreate() {
        super.onCreate();
        minBufferSize = AudioRecord.getMinBufferSize(RECORDER_SAMPLE_RATE, RECORDER_CHANNELS, RECORDER_AUDIO_ENCODING);
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, RECORDER_SAMPLE_RATE, RECORDER_CHANNELS,
                RECORDER_AUDIO_ENCODING, BufferElement2Rec * BytesPerElement);
        buffer = new short[BufferElement2Rec];
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        audioRecord.startRecording();

        long startTime = System.currentTimeMillis();
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();

        while (System.currentTimeMillis() - startTime < 10000) {
            audioRecord.read(buffer, 0, BufferElement2Rec);
            try {
                byteArray.write(ShortToByteConvertor.shortToByte(buffer));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        BuddyDatabaseHelper.getInstance(this).uploadAudio(byteArray.toByteArray());

        stopRecording();
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void stopRecording() {
        if (audioRecord != null) {
            audioRecord.stop();
            audioRecord.release();
            audioRecord = null;
        }
        stopSelf();
    }
}
