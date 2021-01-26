package app.backend.communicator.network.tcp.tcpCommand;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mihailo on 2/4/2017.
 */
public class IncreaseRedScoreCommand extends BaseTCPCommand{
    public static final String NAME = "INCREASE_RED";
    private final int value;

    public IncreaseRedScoreCommand(int value) {
        super(NAME);
        this.value = value;
    }

    @Override
    public JSONObject getParams() throws JSONException {
        JSONObject object = new JSONObject();
        object.put("command", NAME);
        object.put("value", value);

        return object;
    }
}
