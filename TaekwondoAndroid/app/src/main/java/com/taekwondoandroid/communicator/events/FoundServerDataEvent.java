package com.taekwondoandroid.communicator.events;

/**
 * Created by Westi on 2/17/2017.
 */
public class FoundServerDataEvent extends BaseEvent {
    private final String ipAdress;
    private final int port;

    public FoundServerDataEvent(String ipAdress, int port) {
        super(EventType.FOUND_SERVER_EVENT);
        this.ipAdress = ipAdress;
        this.port = port;
    }

    public String getIpAdress() {
        return ipAdress;
    }

    public int getPort() {
        return port;
    }
}
