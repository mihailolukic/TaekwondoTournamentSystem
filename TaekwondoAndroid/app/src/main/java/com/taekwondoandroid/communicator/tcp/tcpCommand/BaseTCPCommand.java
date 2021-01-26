package com.taekwondoandroid.communicator.tcp.tcpCommand;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Westi on 1/7/2017.
 */

public abstract class BaseTCPCommand {

    private String commandName;

    public BaseTCPCommand(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName(){
        return commandName;
    }

    public abstract JSONObject getParams() throws JSONException;

    public abstract BaseTCPCommand undo();

    public abstract Object getValue();
}
