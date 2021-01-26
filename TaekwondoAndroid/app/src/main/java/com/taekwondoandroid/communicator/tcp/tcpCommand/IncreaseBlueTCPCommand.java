package com.taekwondoandroid.communicator.tcp.tcpCommand;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Westi on 1/9/2017.
 */
public class IncreaseBlueTCPCommand extends BaseTCPCommand {

    public static final String NAME = "INCREASE_BLUE";
    private int newScore;

    public IncreaseBlueTCPCommand(int newScore) {
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
        return new IncreaseBlueTCPCommand(newScore * (-1));
    }

    @Override
    public Object getValue() {
        return newScore;
    }
}
