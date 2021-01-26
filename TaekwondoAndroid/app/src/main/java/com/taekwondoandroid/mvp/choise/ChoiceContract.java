package com.taekwondoandroid.mvp.choise;

/**
 * Created by Mihailo on 1/10/2018.
 */

public class ChoiceContract {

    interface View {

        void errorOccured(String message);
    }

    interface Presenter {

        int getDeviceIndex();
    }
}
