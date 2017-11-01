package com.example.zz.keepappalive.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

/**
 * 类描述：屏幕状态检测工具类
 * 创建人：zz
 * 创建时间： 2017/10/30 16:26
 */


public class ScreenReceiverUtil {
 private Context mContext;
    private ScreenListener mListener;
    private ScreenBroadCastReceiver mReceiver;

  public  ScreenReceiverUtil(Context context){
      this.mContext = context;
  }
  public void setScreenListener(ScreenListener listener){
      this.mListener = listener;
      mReceiver = new ScreenBroadCastReceiver();
      IntentFilter filter = new IntentFilter();
      filter.addAction(Intent.ACTION_SCREEN_ON);
      filter.addAction(Intent.ACTION_SCREEN_OFF);
      filter.addAction(Intent.ACTION_USER_PRESENT);
      mContext.registerReceiver(mReceiver,filter);

  }


  class ScreenBroadCastReceiver extends BroadcastReceiver{

      @Override
      public void onReceive(Context context, Intent intent) {
          if(mListener == null) return;
          if(Intent.ACTION_SCREEN_ON.equals(intent.getAction())){
              mListener.screenOn();                                       //开屏
          }else if(Intent.ACTION_SCREEN_OFF.equals(intent.getAction())){
              mListener.screenOff();                                     //锁屏
          }else if(Intent.ACTION_USER_PRESENT.equals(intent.getAction())){}
             mListener.onUserPresent();
      }
  }


  public void unRegisterScreenListenr(){
     mContext.unregisterReceiver(mReceiver);
    }



  public interface ScreenListener{
      public void screenOn();
      public void screenOff();
      public void onUserPresent();
  }
}
