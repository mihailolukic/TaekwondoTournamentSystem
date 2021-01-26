package app.backend.communicator.network.tcp.tcpCommand;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mihailo on 2/9/2017.
 */
public class PauseTabletsCommand extends BaseTCPCommand {

    public static final String NAME = "PAUSE_ALL";
    private boolean timerStarted;

    public PauseTabletsCommand(boolean timerStarted) {
        super(NAME);
        this.timerStarted = timerStarted;
    }

    @Override
    public JSONObject getParams() throws JSONException {
        JSONObject object = new JSONObject();
        object.put("command", NAME);
        object.put("timerStarted", timerStarted);
        return object;
    }
}
