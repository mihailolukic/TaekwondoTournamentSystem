package com.taekwondoandroid.communicator.tcp;

import org.json.JSONObject;

import java.net.InetAddress;

/**
 * Created by Westi on 1/7/2017.
 */
public interface TCPListenerInterface {
    void processTCPMessage(JSONObject messageFromServer);
    void errorOccured(String message);
    void foundServerTCPData(InetAddress inetAddress, int port);
}
