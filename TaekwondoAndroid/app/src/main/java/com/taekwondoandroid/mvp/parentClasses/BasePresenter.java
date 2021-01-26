package com.taekwondoandroid.mvp.parentClasses;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.taekwondoandroid.TaekwondoAndroidApp;
import com.taekwondoandroid.communicator.events.BaseEvent;
import com.taekwondoandroid.communicator.service.CommunicationManager;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by Westi on 12/7/2016.
 */

public abstract class BasePresenter implements AbstractPresenter {
    private static String TAG = BasePresenter.class.getSimpleName();

    CommunicationManager manager;
    Context context;
    SharedPreferences preferences;

    public BasePresenter(Context context) {
        this.context = context;
        manager = CommunicationManager.getInstance(this.context);
        TAG = context.getClass().getSimpleName();
        preferences = context.getSharedPreferences(TaekwondoAndroidApp.TITLE, Context.MODE_PRIVATE);
    }
    public abstract void onMessage(BaseEvent message);


    @Override
    public void start() {
        Log.d(TAG, "start: ");
        if(!EventBus.getDefault().isRegistered(this)){
            Log.d(TAG, "start: register: "  + this.getClass().getSimpleName());
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void resume() {
        Log.d(TAG, "resume: ");
        if(!EventBus.getDefault().isRegistered(this)){
            Log.d(TAG, "resume: register: "  + this.getClass().getSimpleName());
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void pause() {
    }

    @Override
    public void stop() {
        Log.d(TAG, "stop: ");
    }

    @Override
    public void destroy() {
        if(EventBus.getDefault().isRegistered(this)){
            Log.d(TAG, "destroy: unregister: "  + this.getClass().getSimpleName());
            EventBus.getDefault().unregister(this);
        }
    }

    protected CommunicationManager getManager() {
        return CommunicationManager.getInstance(context);
    }

    protected Context getContext() {
        return context;
    }

    protected void insertIntoSharedPreff(String key, String data) {
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(key, data);
            editor.apply();
        } else {
            Log.e(TAG, "insertIntoSharedPreff: preferences are null");
        }
    }

    protected void initTCP_() {
        getManager().initTCP();
    }

    protected int getDeviceIndex_() {
        return getManager().getDeviceIndex();
    }

    protected void increaseBlueScore_(int newScore) {
        getManager().increaseBlueScore(newScore);
    }

    protected void increaseRedScore_(int newScore) {
        getManager().increaseRedScore(newScore);
    }

    protected void undo_() {
        getManager().undo();
    }

    protected void getScores_() {
        getManager().getScores();
    }

    public void registerPresenter() {
        if(!EventBus.getDefault().isRegistered(this)){
            Log.d(TAG, "resume: register: "  + this.getClass().getSimpleName());
            EventBus.getDefault().register(this);
        }
    }
}
