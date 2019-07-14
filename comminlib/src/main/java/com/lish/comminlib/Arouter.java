package com.lish.comminlib;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.lish.annotation.Constant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dalvik.system.DexFile;

/**
 * description: 公用跳转库
 * author: lish
 * date: 2019/7/14
 * update:
 * version: 1.0.0
 */
public class Arouter {


    private Context context;
    private Map<String, Class<? extends Activity>> activityMap;

    private Arouter() {
        activityMap = new HashMap<>();
    }

    private static Arouter instance = new Arouter();

    public static Arouter getInstance() {
        return instance;
    }

    public void init(Context context) {
        this.context = context;
        List<Class<?>> className = getClassesFromPackage(context, Constant.PACKAGE_NAME);
        for (Class aClass : className) {
            try {
                //面向接口编程
                if (IArouter.class.isAssignableFrom(aClass)) {
                    IArouter arouter = (IArouter) aClass.newInstance();
                    //执行添加方法
                    arouter.putActivity();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 需要动态代码生成添加部分
     *
     * @param path
     * @param className
     */
    public void putActivity(String path, Class<? extends Activity> className) {
        if ((!TextUtils.isEmpty(path)) && className != null) {
            activityMap.put(path, className);
        }
    }

    /**
     * 实现逻辑跳转部分
     *
     * @param path
     * @param bundle
     */
    public void toJumpActivity(String path, Bundle bundle) {
        Class<? extends Activity> aClass = activityMap.get(path);
        if (aClass == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(context, aClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }


    /**
     * 根据报名 获取包下面的所有  这里只能夺取主包下的
     */
    public static final List<Class<?>> getClassesFromPackage(Context ctx, String pkgName) {
        List<Class<?>> rtnList = new ArrayList<Class<?>>();
        String[] apkPaths = ctx.getApplicationInfo().splitSourceDirs;// 获得所有的APK的路径
        DexFile dexfile = null;
        Enumeration<String> entries = null;
        String name = null;
        for (String apkPath : apkPaths) {
            try {
                dexfile = new DexFile(apkPath);// 获得编译后的dex文件
                entries = dexfile.entries();// 获得编译后的dex文件中的所有class
                while (entries.hasMoreElements()) {
                    name = (String) entries.nextElement();
                    if (name.startsWith(pkgName)) {// 判断类的包名是否符合
                        rtnList.add(Class.forName(name));
                    }
                }
            } catch (ClassNotFoundException | IOException e) {
            } finally {
                try {
                    if (dexfile != null) {
                        dexfile.close();
                    }
                } catch (IOException e) {
                }
            }
        }
        return rtnList;
    }

}
