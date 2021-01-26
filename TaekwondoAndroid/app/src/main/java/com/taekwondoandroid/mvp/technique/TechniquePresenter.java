package com.taekwondoandroid.mvp.technique;

import android.content.Context;
import android.util.Log;

import com.taekwondoandroid.communicator.events.BaseEvent;
import com.taekwondoandroid.communicator.events.ErrorEvent;
import com.taekwondoandroid.communicator.events.IncreaseBlueEvent;
import com.taekwondoandroid.communicator.events.IncreaseRedEvent;
import com.taekwondoandroid.communicator.events.InitializeScoreEvent;
import com.taekwondoandroid.communicator.events.PauseTabletEvent;
import com.taekwondoandroid.mvp.parentClasses.BasePresenter;
import com.taekwondoandroid.mvp.scoring.ScoringContact;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Mihailo on 1/4/2018.
 */

public class TechniquePresenter extends BasePresenter implements TechniqueContract.Presenter {

    TechniqueContract.View view = null;


    public TechniquePresenter(Context context) {
        super(context);
    }

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(BaseEvent message) {
        switch (message.getType()) {
            case ERROR_EVENT:
                ErrorEvent errorEvent = (ErrorEvent) message;
                errorOccured(errorEvent.getMessage());
                break;
            case INCREASE_BLUE_EVENT:
                increaseBlueScoreCommand(((IncreaseBlueEvent) message).getValue());
                break;
            case INCREASE_RED_EVENT:
                increaseRedScoreCommand(((IncreaseRedEvent) message).getValue());
                break;
            case PAUSE_TABLET:
                boolean isTimerStarted = ((PauseTabletEvent)message).isTimerStarted();
                showPauseTabletDialog(isTimerStarted);
                break;
            case RESET_ALL_EVENT:
                resetAllCommand();
                break;
            case INITIALIZE_SCORE:
                initScores((InitializeScoreEvent)message);
                break;
        }

    }

    private void initScores(InitializeScoreEvent message) {
        view.initScores(message.getRedScore(),message.getBlueScore());
    }

    private void resetAllCommand() {
        view.resetAll();
        getManager().resetUndo();
    }

    private void showPauseTabletDialog(boolean isTimerStarted) {
        view.showPauseTabletDialog(isTimerStarted);
    }

    private void increaseRedScoreCommand(int value) {
        view.increaseRedScoreCommand(value);
    }

    private void increaseBlueScoreCommand(int value) {
        view.increaseBlueScoreCommand(value);
    }

    private void errorOccured(String message) {
        view.errorOccured(message);
    }

    public void setView(TechniqueContract.View view) {
        this.view = view;
    }

    @Override
    public void increaseBlueScore(int blueScore) {
        increaseBlueScore_(blueScore);
    }

    @Override
    public void increaseRedScore(int redScore) {
        increaseRedScore_(redScore);
    }

    @Override
    public void getScores() {
        getScores_();
    }
}
