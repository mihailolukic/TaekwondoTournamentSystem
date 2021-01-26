package app.backend.communicator.network.tcp.tcpCommand;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mihailo on 2/4/2017.
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
}
