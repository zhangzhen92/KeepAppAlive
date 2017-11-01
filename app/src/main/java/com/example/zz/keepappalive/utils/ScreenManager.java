package com.example.zz.keepappalive.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.zz.keepappalive.ui.SinglePixelActivity;

import java.lang.ref.WeakReference;

/**
 * 类描述：1像素的activity管理类
 * 创建人：zz
 * 创建时间： 2017/10/30 16:53
 */


public class ScreenManager {
    private static  ScreenManager manager;
    private Context mContext;
    private WeakReference<Activity> mActivityRef;
    private ScreenManager(Context context){
        this.mContext = context;
    }

    /**
     * 双重检测的单例模式
     * @param context
     * @return
     */
    public static ScreenManager getScreenManager(Context context){
        if(manager == null){
            synchronized (ScreenManager.class){
                if(manager == null){
                    manager = new ScreenManager(context);
                }
            }
        }
        return manager;
    }


    /**
     * 获取1px activity的引用
     * @param singleActivity
     */
    public void setSingleActivity(Activity singleActivity){
        mActivityRef = new WeakReference<Activity>(singleActivity);
    }

    /**
     * 启动1px 的activity
     */
    public void startSingleActivity(){
        Intent intent = new Intent(mContext, SinglePixelActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);

    }

    /**
     * 结束1px的activity
     */
    public void finishActivity(){
        if(mActivityRef != null){
            Activity activity = mActivityRef.get();
            if(activity != null){
                activity.finish();
            }
        }
    }
}
