package app.backend.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mihailol on 23.1.2017.
 */
public class LogicCommand {
    private int deviceNumber;
    private String commandName;
    private int value;
    private JSONObject jsonObject;
    private String deviceAddress;
    private int deviceIndex;

    public LogicCommand(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
        try {
            commandName = jsonObject.getString("command");
            value = jsonObject.getInt("value");
            deviceNumber = jsonObject.getInt("device");
            deviceIndex = jsonObject.getInt("deviceIndex");
            if(jsonObject.has("address")){
                deviceAddress = jsonObject.getString("address");
            }
        } catch (JSONException e) {
            System.err.print("Error: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public int getDeviceNumber() {
        return deviceNumber;
    }


    public String getCommandName() {
        return commandName;
    }


    public int getValue() {
        return value;
    }


    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public String getDeviceAddress() {
        return deviceAddress;
    }

    public int getDeviceIndex() {
        return deviceIndex;
    }

    public void setDeviceIndex(int deviceIndex) {
        this.deviceIndex = deviceIndex;
    }
}
