package com.taekwondoandroid.communicator.tcp.tcpCommand;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Misa on 2/16/2018.
 */

public class CreateTCPClient extends BaseTCPCommand {

    public static final String NAME = "CREATE_CLIENT";
    private int deviceIndex;

    public CreateTCPClient(int deviceIndex) {
        super(NAME);
        this.deviceIndex = deviceIndex;
    }

    @Override
    public JSONObject getParams() throws JSONException {
        JSONObject object = new JSONObject();
        object.put("command",NAME);
        object.put("value",0);
        object.put("deviceIndex", deviceIndex);
        return object;
    }

    @Override
    public BaseTCPCommand undo() {
        return null;
    }

    @Override
    public Object getValue() {
        return null;
    }
}
