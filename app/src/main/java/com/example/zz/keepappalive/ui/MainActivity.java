package com.example.zz.keepappalive.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.zz.keepappalive.R;
import com.example.zz.keepappalive.config.Contants;
import com.example.zz.keepappalive.service.AliveService;
import com.example.zz.keepappalive.service.NoVoiceService;
import com.example.zz.keepappalive.utils.ScreenManager;
import com.example.zz.keepappalive.utils.ScreenReceiverUtil;

/**
 * 类描述：(保持应用长期存活)测试类
 * 创建人：zz
 * 创建时间：2017/10/30 14:40
 */
public class MainActivity extends Activity implements View.OnClickListener{

    private Button buttonService;
    private Button buttonSinglePixel;
    private ScreenManager manager;
    private ScreenReceiverUtil screenReceiverUtil;


    private final int DELAY_TIME = 3000;
    private Handler mHandelr = new Handler(){                 //测试类
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mHandelr.sendEmptyMessageDelayed(0,DELAY_TIME);
            Log.d(Contants.TAG,"主界面还存在");
        }
    };
    private Button buttonPlayMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    /**
     * 初始化1px像素activity
     */
    private void initPXActivity() {
        screenReceiverUtil = new ScreenReceiverUtil(this);
        manager = ScreenManager.getScreenManager(this);
        screenReceiverUtil.setScreenListener(new ScreenReceiverUtil.ScreenListener() {
            @Override
            public void screenOn() {
             manager.finishActivity();
            }

            @Override
            public void screenOff() {
               manager.startSingleActivity();
            }

            @Override
            public void onUserPresent() {
              //锁屏按钮操作
            }
        });
    }



    /**
     * 初始化UI
     */
    private void initView() {
        buttonService = ((Button) findViewById(R.id.button_service));
        buttonService.setOnClickListener(this);
        buttonSinglePixel = ((Button) findViewById(R.id.button_single_pixel));   //1像素事件
        buttonSinglePixel.setOnClickListener(this);
        buttonPlayMusic = ((Button) findViewById(R.id.button_play_music));
        buttonPlayMusic.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_service:
                //此种方法在Android 7.0上的系统不适用，当锁屏的状态下service被系统Force stopping
                startService(new Intent(getApplicationContext(), AliveService.class));
                break;
            case R.id.button_single_pixel:
                //通过检测锁屏广播从而控制开启一个1px的activity（使其处于前台进程）
                mHandelr.sendEmptyMessageDelayed(0,DELAY_TIME);
                initPXActivity();
                break;
            case R.id.button_play_music:
                //后台播放无声音乐使其一直处于运行状态
                startService(new Intent(getApplicationContext(), NoVoiceService.class));
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        screenReceiverUtil.unRegisterScreenListenr();
    }
}
