package com.example.zz.keepappalive.service;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.zz.keepappalive.config.Contants;
import com.example.zz.keepappalive.utils.AppSystemUtils;

/**
 * 类描述：
 * 创建人：zz
 * 创建时间： 2017/10/31 13:51
 */

@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class AliveJobService extends JobService{
    private static final int MESSAGE_ID = 0x000001;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(AppSystemUtils.isRunning(getApplicationContext(), Contants.PACKAGE_NAME)){
                Log.d(Contants.TAG,"存活：AliveeJobService");
            }else {
                Log.d(Contants.TAG,"死亡：AliveeJobService");
            }
        }
    };
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
