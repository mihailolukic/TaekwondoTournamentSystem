package com.taekwondoandroid.mvp.configuration;

/**
 * Created by Westi on 12/14/2016.
 */

public class ConfigurationActivityContact {

    interface Presenter{
        void checkWifiNetwork();
        void startUDP();
    }

    interface View{

        void showMessage(String message);
        void setWifiNetworkName(String name);
        void setPhoneIpAddress(String s);
        void setLaptopIPAddress(String ipAdress);
        void showProgressDialog(String message);
        void hideProgressDialog();
        void hideProceedButton();
        void showProceedButton();

    }
}
