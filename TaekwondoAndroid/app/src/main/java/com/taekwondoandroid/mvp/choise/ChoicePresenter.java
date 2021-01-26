package com.taekwondoandroid.mvp.choise;

import android.content.Context;
import android.util.Log;

import com.taekwondoandroid.communicator.events.BaseEvent;
import com.taekwondoandroid.communicator.events.ErrorEvent;
import com.taekwondoandroid.communicator.events.IncreaseBlueEvent;
import com.taekwondoandroid.communicator.events.IncreaseRedEvent;
import com.taekwondoandroid.communicator.events.InitializeScoreEvent;
import com.taekwondoandroid.communicator.events.PauseTabletEvent;
import com.taekwondoandroid.communicator.events.UndoEvent;
import com.taekwondoandroid.communicator.tcp.tcpCommand.IncreaseBlueTCPCommand;
import com.taekwondoandroid.communicator.tcp.tcpCommand.IncreaseRedTCPCommand;
import com.taekwondoandroid.mvp.parentClasses.BasePresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Mihailo on 1/10/2018.
 */

public class ChoicePresenter extends BasePresenter implements ChoiceContract.Presenter{

    private ChoiceContract.View view = null;


    public ChoicePresenter(Context context) {
        super(context);
    }

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(BaseEvent event) {
        switch (event.getType()){
            case ERROR_EVENT:
                ErrorEvent errorEvent = (ErrorEvent) event;
                errorOccured(errorEvent.getMessage());
                break;
        }
    }

    public void errorOccured(String message) {
        view.errorOccured(message);
    }
    public void setView(ChoiceContract.View view) {
        this.view = view;
    }

    public void initTCP() {
        initTCP_();
    }

    @Override
    public int getDeviceIndex() {
        return getDeviceIndex_();
    }
}
