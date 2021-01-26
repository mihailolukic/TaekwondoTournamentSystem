package com.taekwondoandroid.mvp.scoring;

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
 * Created by Westi on 12/7/2016.
 */
public class ScoringPresenter extends BasePresenter implements ScoringContact.Presenter {

    ScoringContact.View view = null;

    private static final String TAG = ScoringPresenter.class.getSimpleName();
    private Context context;

    public ScoringPresenter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(BaseEvent event) {
        Log.d(TAG, "onMessage: " + event.getType());
        switch (event.getType()){
            case ERROR_EVENT:
                ErrorEvent errorEvent = (ErrorEvent) event;
                errorOccured(errorEvent.getMessage());
                break;
            case INCREASE_BLUE_EVENT:
                increaseBlueScoreCommand(((IncreaseBlueEvent)event).getValue());
                break;
            case INCREASE_RED_EVENT:
                increaseRedScoreCommand(((IncreaseRedEvent)event).getValue());
                break;
            case RESET_ALL_EVENT:
                resetAllCommand();
                break;
            case PAUSE_TABLET:
                boolean isTimerStarted = ((PauseTabletEvent)event).isTimerStarted();
                showPauseTabletDialog(isTimerStarted);
                break;
            case UNDO_EVENT:
                UndoEvent undoEvent = (UndoEvent) event;
                if(undoEvent.getCommandName().equals(IncreaseBlueTCPCommand.NAME)){
                    view.undoBlueScoreCommand((Integer) undoEvent.getValue());
                }
                else if(undoEvent.getCommandName().equals(IncreaseRedTCPCommand.NAME)){
                    view.undoRedScoreCommand((Integer) undoEvent.getValue());
                }
                break;
            case INITIALIZE_SCORE:
                Log.d(TAG, "onMessage: INITIALIZE_SCORE");
                InitializeScoreEvent initEvent = (InitializeScoreEvent) event;
                view.initScore(initEvent.getRedScore(),initEvent.getBlueScore());
                break;
        }
    }

    private void showPauseTabletDialog(boolean isTimerStarted) {
        view.showPauseTabletDialog(isTimerStarted);
    }

    private void resetAllCommand() {
        view.resetAll();
        getManager().resetUndo();
    }

    public void setView(ScoringContact.View view) {
        this.view = view;
    }

    public void errorOccured(String message) {
        Log.e(TAG, "errorOccured: " + message );
        view.errorOccured(message);
    }

    @Override
    public void increaseBlueScore(int newScore) {
        increaseBlueScore_(newScore);
    }

    @Override
    public void increaseRedScore(int newScore) {
        increaseRedScore_(newScore);
    }

    @Override
    public void undo() {
        undo_();
    }

    @Override
    public void getScores() {
        getScores_();
    }

    public void increaseBlueScoreCommand(int value) {
        Log.d(TAG, "increaseRedScoreCommand: " + value);
        view.increaseBlueScoreCommand(value);
    }

    public void increaseRedScoreCommand(int value) {
        Log.d(TAG, "increaseRedScoreCommand: " + value);
        view.increaseRedScoreCommand(value);
    }


}
