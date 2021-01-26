package app.backend.communicator.network.tcp;

import app.backend.communicator.network.tcp.tcpCommand.BaseTCPCommand;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Westi on 1/7/2017.
 */
public interface TCPListener {
    void processMessage(JSONObject message);

    void sendCommand(BaseTCPCommand increaseRedScoreCommand) throws JSONException;
    void sendCommandToOneClient(int deviceNumber,BaseTCPCommand increaseRedScoreCommand) throws JSONException;
}
