package com.taekwondoandroid.communicator.events;

/**
 * Created by mihailol on 6.2.2017.
 */

public class ErrorEvent extends BaseEvent {


    private String message;

    public ErrorEvent(String message) {
        super(EventType.ERROR_EVENT);

        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
