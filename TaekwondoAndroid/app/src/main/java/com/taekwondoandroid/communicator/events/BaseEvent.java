package com.taekwondoandroid.communicator.events;

/**
 * Created by mihailol on 6.2.2017.
 */

public class BaseEvent {


    protected EventType type;

    public BaseEvent(EventType type) {
        this.type = type;
    }

    public EventType getType() {
        return type;
    }


}
