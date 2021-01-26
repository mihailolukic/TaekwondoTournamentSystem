package com.taekwondoandroid.communicator.tcp.tcpCommand;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Westi on 1/7/2017.
 */

public class InitTCPCommand extends BaseTCPCommand{

    public static final String NAME = "INIT_TCP";

    public InitTCPCommand() {
        super(NAME);
    }

    @Override
    public JSONObject getParams() throws JSONException {
        JSONObject object = new JSONObject();
        object.put("command",NAME);
        object.put("value",0);
        return object;
    }

    @Override
    public BaseTCPCommand undo() {
        return null;
    }

    @Override
    public Object getValue() {
        return 0;
    }
}
