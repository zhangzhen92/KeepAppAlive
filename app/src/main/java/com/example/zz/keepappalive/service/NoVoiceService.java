package com.example.zz.keepappalive.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.zz.keepappalive.R;

/**
 * 类描述：
 * 创建人：zz
 * 创建时间： 2017/10/30 18:16
 */


public class NoVoiceService extends Service{
    private MediaPlayer mediaPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.abc);
        mediaPlayer.setLooping(true);
    }

    @Override
    public int onStartCommand(Intent intent,  int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer!= null && !mediaPlayer.isPlaying()){
                    mediaPlayer.start();
                }
            }
        }).start();
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        stopMusic();
        Intent intent = new Intent(getApplicationContext(),NoVoiceService.class);
        startActivity(intent);
    }

    private void stopMusic() {
        if(mediaPlayer != null && mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }
    }
}
