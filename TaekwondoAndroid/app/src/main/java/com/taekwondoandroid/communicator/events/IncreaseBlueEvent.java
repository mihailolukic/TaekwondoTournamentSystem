package com.taekwondoandroid.communicator.events;

/**
 * Created by mihailol on 6.2.2017.
 */

public class IncreaseBlueEvent extends BaseEvent {

    private int value;

    public IncreaseBlueEvent(int value) {
        super(EventType.INCREASE_BLUE_EVENT);
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
