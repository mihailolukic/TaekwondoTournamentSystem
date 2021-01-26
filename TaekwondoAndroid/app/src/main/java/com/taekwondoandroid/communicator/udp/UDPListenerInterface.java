package com.taekwondoandroid.communicator.udp;

/**
 * Created by Westi on 12/20/2016.
 */

public interface UDPListenerInterface {

    void foundServerData(String ipAdress, int port, String deviceIndex);
    void errorOccured(String message);
}
