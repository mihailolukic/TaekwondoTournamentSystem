package app.backend.communicator.network.tcp.tcpCommand;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mihailo on 2/4/2017.
 */
public class ResetAllCommand extends BaseTCPCommand{
    public static final String NAME = "RESET_ALL";

    public ResetAllCommand() {
        super(NAME);
    }

    @Override
    public JSONObject getParams() throws JSONException {
        JSONObject object = new JSONObject();
        object.put("command", NAME);
        return object;
    }
}
