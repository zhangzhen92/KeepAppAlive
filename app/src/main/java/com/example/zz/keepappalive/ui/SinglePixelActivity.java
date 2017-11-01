package com.example.zz.keepappalive.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.example.zz.keepappalive.R;
import com.example.zz.keepappalive.config.Contants;
import com.example.zz.keepappalive.utils.AppSystemUtils;
import com.example.zz.keepappalive.utils.ScreenManager;

/**
 * 类描述：1像素的Activity
 * 创建人：zz
 * 创建时间：2017/10/30 17:01
 */
public class SinglePixelActivity extends Activity {
    private static final String packageName = "com.example.zz.keepappalive";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_pixel);
        Log.d(Contants.TAG, "onCreate: 启动1px");
        Window window = getWindow();
        window.setGravity(Gravity.TOP | Gravity.LEFT);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.x = 0;
        attributes.y = 0;
        attributes.width = 1;
        attributes.height = 1;
        window.setAttributes(attributes);
        ScreenManager.getScreenManager(this).setSingleActivity(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(Contants.TAG,"1像素activity销毁");
        if(!AppSystemUtils.isRunning(getApplicationContext(),packageName)){
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Log.d(Contants.TAG, "onDestroy: 应用被杀死了重启他");
            startActivity(intent);
        }
    }
}
