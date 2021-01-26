package com.taekwondoandroid.mvp.parentClasses;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;

/**
 * Created by Westi on 12/7/2016.
 */

public abstract class BaseActivity extends Activity {

    private static final String TAG = "BaseActivity";

    BasePresenter presenter = null;

    protected abstract void initView();

    public BaseActivity() {
        super();
    }

    protected void initPresenter_(BasePresenter presenter) {
        this.presenter = presenter;
        presenter.registerPresenter();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.resume();
    }

    @Override
    protected void onStop() {
        presenter.stop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        presenter.destroy();
        presenter = null;
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }


}
