package app.backend.communicator.network.tcp;

import org.json.JSONObject;

/**
 * Created by Westi on 1/7/2017.
 */
public interface TCPServerListener {
    void processTCPMessage(JSONObject message);
}
