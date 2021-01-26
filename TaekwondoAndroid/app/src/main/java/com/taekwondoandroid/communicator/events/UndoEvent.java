package com.taekwondoandroid.communicator.events;

/**
 * Created by mihailol on 6.2.2017.
 */

public class UndoEvent extends BaseEvent {

    private Object value;
    private String commandName;

    public UndoEvent(Object value, String commandName) {
        super(EventType.UNDO_EVENT);
        this.value = value;
        this.commandName = commandName;
    }

    public Object getValue() {
        return value;
    }

    public String getCommandName() {
        return commandName;
    }
}
