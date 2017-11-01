package com.example.zz.keepappalive.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;

/**
 * 类描述：移除前台服务的Service，如果感觉你有必要存留，没必要开启该服务
 * 创建人：zz
 * 创建时间： 2017/10/30 15:01
 */


public class ClearNoticeService extends Service{
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //只有API 18以上才在通知栏开启了一个通知条目
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2){
            Notification.Builder builder = new Notification.Builder(this);
            builder.setContentText("取消通知栏");
            startForeground(AliveService.NOTICE_ID,builder.build());
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SystemClock.sleep(500);                 //延迟500ms
                    stopForeground(true);
                    NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    manager.cancel(AliveService.NOTICE_ID);
                    stopSelf();   //关闭服务
                }
            }).start();


        }
        return super.onStartCommand(intent, flags, startId);
    }
}
