package com.taekwondoandroid.communicator.tcp.tcpCommand;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Misa on 2/17/2018.
 */

public class GetScoreTCPCommand extends BaseTCPCommand {

    public static final String NAME = "GET_SCORE";

    public GetScoreTCPCommand() {
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
