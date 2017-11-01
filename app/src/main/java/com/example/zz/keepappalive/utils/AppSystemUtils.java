package com.example.zz.keepappalive.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;

import java.util.List;

/**
 * 类描述：获取手机上APP信息的工具类
 * 创建人：zz
 * 创建时间： 2017/10/30 17:25
 */


public class AppSystemUtils {

    /**
     * 通过包名判断应用是否存活
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isRunning(Context context,String packageName){
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTasks = manager.getRunningTasks(100);
        if(runningTasks.size() <= 0){
            return false;
        }
        for(ActivityManager.RunningTaskInfo taskInfo: runningTasks){
            if(packageName.equals(taskInfo.baseActivity.getPackageName())){
                return true;
            }
        }
        return false;
    }


    /**
     * 通过包名获取UID,,未安装app会出现异常
     * @param context
     * @param packageName
     * @return
     */
    public static int getPackageUid(Context context, String packageName) {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(packageName, 0);
            if (applicationInfo != null) {
                return applicationInfo.uid;
            }
        } catch (Exception e) {
            return -1;
        }
        return -1;
    }

    /**
     * 通过UID 判断应用是否处于活动状态
     * @param context
     * @param uid
     * @return
     */
    public static boolean isProcessRunning(Context context, int uid) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServiceInfos = am.getRunningServices(200);
        if (runningServiceInfos.size() > 0) {
            for (ActivityManager.RunningServiceInfo appProcess : runningServiceInfos){
                if (uid == appProcess.uid) {
                    return true;
                }
            }
        }
        return false;
    }

}
