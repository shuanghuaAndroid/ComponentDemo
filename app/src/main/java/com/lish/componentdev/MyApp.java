package com.lish.componentdev;

import android.app.Application;

import com.lish.comminlib.Arouter;

/**
 * description:
 * author: lish
 * date: 2019/7/14
 * update:
 * version: 1.0.0
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //注册类
        Arouter.getInstance().init(this);
    }
}
