package com.taekwondoandroid.communicator.tcp.tcpCommand;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Westi on 1/9/2017.
 */
public class IncreaseRedTCPCommand extends BaseTCPCommand {

    public static final String NAME = "INCREASE_RED";
    private int newScore;

    public IncreaseRedTCPCommand(int newScore) {
        super(NAME);
        this.newScore = newScore;
    }

    @Override
    public JSONObject getParams() throws JSONException {
        JSONObject object = new JSONObject();
        object.put("command", NAME);
        object.put("value", newScore);
        return object;
    }

    @Override
    public BaseTCPCommand undo() {
        return new IncreaseRedTCPCommand(newScore*(-1));
    }

    @Override
    public Object getValue() {
        return newScore;
    }
}
