package com.taekwondoandroid.communicator.events;

/**
 * Created by mihailol on 6.2.2017.
 */

public class IncreaseRedEvent extends BaseEvent {

    private int value;

    public IncreaseRedEvent(int value) {
        super(EventType.INCREASE_RED_EVENT);
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
