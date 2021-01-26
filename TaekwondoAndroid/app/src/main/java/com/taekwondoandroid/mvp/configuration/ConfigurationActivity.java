package com.taekwondoandroid.mvp.configuration;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.taekwondoandroid.R;
import com.taekwondoandroid.mvp.choise.ChoiceActivity_;
import com.taekwondoandroid.mvp.parentClasses.BaseActivity;
import com.taekwondoandroid.mvp.scoring.ScoringActivity;
import com.taekwondoandroid.mvp.scoring.ScoringActivity_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.w3c.dom.Text;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

/**
 * Created by Westi on 12/14/2016.
 */
@EActivity(R.layout.configuration_activity_layout)
public class ConfigurationActivity extends BaseActivity implements ConfigurationActivityContact.View {

    private static final String TAG = ConfigurationActivity.class.getSimpleName();

    ConfigurationActivityPresenter presenter;

    @ViewById
    Button btnProceed;
    @ViewById
    Button btnChangeWifi;
    @ViewById
    TextView txtNetworkName;
    @ViewById
    TextView txtPhoneIpAddress;
    @ViewById
    ImageView imgIPAddressChecked;
    @ViewById
    TextView txtLaptopAddress;
    @ViewById
    ImageView imgLaptopIPAddressChecked;
    @ViewById
    TextView txtMessage;
    @ViewById
    Button btnTryAgain;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    @AfterViews
    protected void initView() {
        presenter = new ConfigurationActivityPresenter(getApplicationContext());
        initPresenter_(presenter);
        presenter.setView(this);
    }

    @Click
    public void btnProceed() {
        ChoiceActivity_.intent(this).start();
        this.finish();
    }

    @Click
    public void btnTryAgain() {
        presenter.checkWifiNetwork();
    }

    @Click
    public void btnChangeWifi() {
        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
        startActivity(intent);
    }

    @UiThread
    @Override
    public void showMessage(String message) {
        Log.d(TAG, "showMessage: " + message);
        if (!"".equals(message)) {
            txtMessage.setVisibility(View.VISIBLE);
            txtMessage.setText(message);
        } else {
            txtMessage.setVisibility(View.INVISIBLE);
        }
    }

    @UiThread
    @Override
    public void setWifiNetworkName(String name) {
        txtNetworkName.setText(name);
        if (!"".equals(name)) {
            btnChangeWifi.setText(getApplicationContext().getString(R.string.change_wifi_network));
            txtNetworkName.setText(name);
        } else {
            btnChangeWifi.setText(getApplicationContext().getString(R.string.select_wifi));
            txtNetworkName.setText(getApplicationContext().getString(R.string.no_wifi_selected));
        }
    }

    @UiThread
    @Override
    public void setPhoneIpAddress(String ipAddress) {
        if (!"".equals(ipAddress)) {
            txtPhoneIpAddress.setText(ipAddress);
            imgIPAddressChecked.setImageResource(R.drawable.checked_green);
        } else {
            txtPhoneIpAddress.setText(getApplicationContext().getString(R.string.none));
            imgIPAddressChecked.setImageResource(R.drawable.unchecked_red);

        }
    }

    @UiThread
    @Override
    public void setLaptopIPAddress(String ipAdress) {
        Log.d(TAG, "setLaptopIPAddress: " + ipAdress);
        if (!"".equals(ipAdress)) {
            txtLaptopAddress.setText(ipAdress);
            imgLaptopIPAddressChecked.setImageResource(R.drawable.checked_green);
            btnTryAgain.setVisibility(View.INVISIBLE);
        } else {
            txtLaptopAddress.setText(getApplicationContext().getString(R.string.none));
            imgLaptopIPAddressChecked.setImageResource(R.drawable.unchecked_red);
            btnTryAgain.setVisibility(View.VISIBLE);
        }
    }

    @UiThread
    @Override
    public void showProgressDialog(String message) {
        progressDialog = ProgressDialog.show(this, null, message, true);
    }

    @UiThread
    @Override
    public void hideProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }

    }
    @UiThread
    @Override
    public void hideProceedButton() {
        btnProceed.setVisibility(View.INVISIBLE);
    }

    @UiThread
    @Override
    public void showProceedButton() {
        btnProceed.setVisibility(View.VISIBLE);

    }
}
