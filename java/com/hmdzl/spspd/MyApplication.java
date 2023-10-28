package com.hmdzl.spspd;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        initCrashHandler();
    }

    protected void initCrashHandler() {
        /*
        if (BuildConfig.isDebug) {
            CustomActivityOnCrash.install(this);
        } else {
            CrashHandler handler = CrashHandler.getInstance();
            handler.init(getApplicationContext());
            Thread.setDefaultUncaughtExceptionHandler(handler);
        }
        */

        CrashHandler handler = CrashHandler.getInstance();
        handler.init(getApplicationContext());
        Thread.setDefaultUncaughtExceptionHandler(handler);
    }

    public static Context getContext(){
        return context;
    }
}