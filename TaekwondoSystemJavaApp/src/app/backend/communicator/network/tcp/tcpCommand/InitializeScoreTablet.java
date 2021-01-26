package app.backend.communicator.network.tcp.tcpCommand;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mihailo on 2/22/2017.
 */
public class InitializeScoreTablet extends BaseTCPCommand {
    public static final String NAME = "INITIALIZE_SCORE";
    private final int mobileDeviceRedScore;
    private final int mobileDeviceBlueScore;

    public InitializeScoreTablet(int mobileDeviceRedScore, int mobileDeviceBlueScore) {
        super(NAME);
        this.mobileDeviceRedScore = mobileDeviceRedScore;
        this.mobileDeviceBlueScore = mobileDeviceBlueScore;
    }

    @Override
    public JSONObject getParams() throws JSONException {
        JSONObject object = new JSONObject();
        object.put("command", NAME);
        object.put("redScore", mobileDeviceRedScore);
        object.put("blueScore", mobileDeviceBlueScore);
        return object;
    }
}
