package com.taekwondoandroid.mvp.configuration;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;
import android.util.Log;

import com.taekwondoandroid.R;
import com.taekwondoandroid.communicator.events.BaseEvent;
import com.taekwondoandroid.communicator.events.ErrorEvent;
import com.taekwondoandroid.communicator.events.FoundServerDataEvent;
import com.taekwondoandroid.mvp.parentClasses.BasePresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Westi on 12/14/2016.
 */
//@EBean
public class ConfigurationActivityPresenter extends BasePresenter implements ConfigurationActivityContact.Presenter {

    private static final String TAG = ConfigurationActivityPresenter.class.getSimpleName();
    private ConfigurationActivityContact.View view;
    private Context context;

    public ConfigurationActivityPresenter(Context context) {
        super(context);
        this.context = context;
    }



    public void setView(ConfigurationActivityContact.View view) {
        this.view = view;
    }

    public void errorOccured(String message) {
        view.showMessage(message);
        view.hideProgressDialog();
        view.setLaptopIPAddress("");
        view.hideProceedButton();
    }

    public void foundServerData(String ipAdress, int port) {
        view.setLaptopIPAddress(ipAdress);
        view.hideProgressDialog();
        view.showMessage("");
        view.showProceedButton();
        insertIntoSharedPreff("LAPTOP_ID", ipAdress);
        getManager().setLaptopIPAdress(ipAdress);

    }


    @Override
    public void checkWifiNetwork() {
        view.showProgressDialog(getContext().getString(R.string.connecting_to_network));
        WifiManager wifiMng = (WifiManager) getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if(wifiMng.isWifiEnabled()){
            WifiInfo wifiInfo = wifiMng.getConnectionInfo();
            if(wifiInfo.getNetworkId() == -1){
                view.showMessage(getContext().getString(R.string.not_connected_on_wifi));
                view.hideProgressDialog();

            }
            else{
                view.setWifiNetworkName(wifiInfo.getSSID());
                view.setPhoneIpAddress(Formatter.formatIpAddress(wifiInfo.getIpAddress()));
                startUDP();
            }
        }
        else{
            view.showMessage(getContext().getString(R.string.not_connected_on_wifi));
            view.hideProgressDialog();
        }

    }

    public void startUDP() {
        getManager().startUDP();
    }

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(BaseEvent message) {

        switch (message.getType()){
            case ERROR_EVENT:
                errorOccured(((ErrorEvent)message).getMessage());
                break;
            case ON_BIND_EVENT:
                onBind();
                break;
            case ON_UNBIND_EVENT:
                onUnbind();
                break;
            case FOUND_SERVER_EVENT:
                FoundServerDataEvent event = (FoundServerDataEvent) message;
                foundServerData(event.getIpAdress(),event.getPort());
                break;
        }

    }


    public void onBind() {
        checkWifiNetwork();
    }

    public void onUnbind() {
    }

    @Override
    public void resume() {
        super.resume();
        checkWifiNetwork();
    }

    @Override
    public void destroy() {
        super.destroy();
    }



}
