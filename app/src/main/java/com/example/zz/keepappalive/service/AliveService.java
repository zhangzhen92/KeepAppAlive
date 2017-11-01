package com.example.zz.keepappalive.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.example.zz.keepappalive.R;
import com.example.zz.keepappalive.config.Contants;

/**
 * 类描述：长期存活service
 * 创建人：zz
 * 创建时间： 2017/10/30 14:54
 */


public class AliveService extends Service{
    public static final int NOTICE_ID = 0x000001;                         //前台通知的ID
    private static final int DELAY_TIME = 3 * 1000;                       //3s延迟
    private Handler mHandler = new Handler(){                                //测试类
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.d(Contants.TAG,"存活Service是否还存在");
            mHandler.sendEmptyMessageDelayed(0,DELAY_TIME);
        }
    };
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mHandler.sendEmptyMessageDelayed(0, DELAY_TIME);
        //如果API 大于18以上，开启前台服务需要在通知栏弹出可见通知条目
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2){
            Notification.Builder builder = new Notification.Builder(this);
            builder.setContentTitle("存活体验")
                    .setContentText("前台服务保持存活")
                    .setSmallIcon(R.mipmap.ic_launcher);
            startForeground(NOTICE_ID,builder.build());
            //常驻通知可以通过新开启一个service,让其清空通知
            startService(new Intent(this,ClearNoticeService.class));
        }else {
            startForeground(NOTICE_ID,new Notification());
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 如果Service被终止
        // 当资源允许情况下，重启service
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //Servie 被杀掉了,清空通知
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2){
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.cancel(NOTICE_ID);
        }
        //杀死后，再次重启自己
        startService(new Intent(getApplicationContext(),AliveService.class));
    }
}
