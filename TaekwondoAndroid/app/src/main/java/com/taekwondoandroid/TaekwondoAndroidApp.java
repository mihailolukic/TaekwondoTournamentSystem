package com.taekwondoandroid;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Westi on 12/7/2016.
 */

public class TaekwondoAndroidApp extends MultiDexApplication {

    private static Context context;
    public static final String TITLE = "TaekwondoAndroid";

    @Override
    public void onCreate() {
        Log.d(TITLE, "onCreate: ");
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        context = this;
    }


    public static Context getContext() {
        return context;
    }

}
