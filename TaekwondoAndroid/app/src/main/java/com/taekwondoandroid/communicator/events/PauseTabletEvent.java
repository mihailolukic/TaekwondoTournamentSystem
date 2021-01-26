package com.taekwondoandroid.communicator.events;

/**
 * Created by mihailol on 6.2.2017.
 */

public class PauseTabletEvent extends BaseEvent {

    private boolean timerStarted;

    public PauseTabletEvent(boolean timerStarted) {
        super(EventType.PAUSE_TABLET);
        this.timerStarted = timerStarted;
    }

    public boolean isTimerStarted() {
        return timerStarted;
    }
}
