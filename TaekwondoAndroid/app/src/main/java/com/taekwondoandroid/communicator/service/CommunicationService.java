package com.taekwondoandroid.communicator.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.taekwondoandroid.communicator.events.ErrorEvent;
import com.taekwondoandroid.communicator.events.FoundServerDataEvent;
import com.taekwondoandroid.communicator.events.IncreaseBlueEvent;
import com.taekwondoandroid.communicator.events.IncreaseRedEvent;
import com.taekwondoandroid.communicator.events.InitializeScoreEvent;
import com.taekwondoandroid.communicator.events.PauseTabletEvent;
import com.taekwondoandroid.communicator.events.ResetAllEvent;
import com.taekwondoandroid.communicator.events.UndoEvent;
import com.taekwondoandroid.communicator.tcp.TCPListenerInterface;
import com.taekwondoandroid.communicator.tcp.TCPThread;
import com.taekwondoandroid.communicator.tcp.tcpCommand.BaseTCPCommand;
import com.taekwondoandroid.communicator.tcp.tcpCommand.GetScoreTCPCommand;
import com.taekwondoandroid.communicator.tcp.tcpCommand.InitTCPCommand;
import com.taekwondoandroid.communicator.udp.UDPListenerInterface;
import com.taekwondoandroid.communicator.udp.UDPThread;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;


/**
 * Created by Westi on 12/14/2016.
 */

public class CommunicationService extends Service implements UDPListenerInterface, TCPListenerInterface {

    private static final String TAG = "CommunicationService";

    private IBinder binder;
    private String serverIpAdress;
    private int serverPort;
    private Context context;
    private Integer deviceIndex = null;

    private TCPThread tcpThread = null;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: Binding started");
        this.context = getApplicationContext();
        this.binder = new BinderClass();
        return this.binder;
    }

    public void startUDP() {
        UDPThread udp = new UDPThread(this,(WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE), context);
        Thread udpThread = new Thread(udp);
        udpThread.start();
    }

    private void startTCPThread(String ipAddress) {
        tcpThread = new TCPThread(ipAddress,this, deviceIndex);
        tcpThread.start();

    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: Unbinded service");
        deviceIndex = null;
        return super.onUnbind(intent);
    }

    @Override
    public void foundServerData(String ipAdress, int port, String deviceIndex) {
        Log.d(TAG, "foundServerData: " + ipAdress);
        if(Integer.valueOf(deviceIndex).intValue() != -1){
            this.deviceIndex = Integer.valueOf(deviceIndex);
            this.serverIpAdress = ipAdress;
            this.serverPort = port;
            startTCPThread(ipAdress);
        }
        else{
            errorOccured("Device is not connected with server application");
        }

    }



    @Override
    public void errorOccured(String message) {
        Log.e(TAG, "errorOccured: "+ message );
        EventBus.getDefault().post(new ErrorEvent(message));
    }

    @Override
    public void foundServerTCPData(InetAddress inetAddress, int port) {
        EventBus.getDefault().post(new FoundServerDataEvent(inetAddress.getHostAddress(),port));
    }

    @Override
    public void processTCPMessage(JSONObject messageFromServer) {
        Log.d(TAG, "processTCPMessage: " + messageFromServer);
        synchronized (this) {
            try {
                String commandName  = messageFromServer.getString("command");
                switch (commandName){
                    case "INCREASE_BLUE":
                        Log.d(TAG,"INCREASE_BLUE");
                        int valueBlue = messageFromServer.getInt("value");
                        EventBus.getDefault().post(new IncreaseBlueEvent(valueBlue));
                        break;
                    case "INCREASE_RED":
                        Log.d(TAG,"INCREASE_RED");
                        int valueRed = messageFromServer.getInt("value");
                        EventBus.getDefault().post(new IncreaseRedEvent(valueRed));
                        break;
                    case "RESET_ALL":
                        EventBus.getDefault().post(new ResetAllEvent());
                        break;
                    case "PAUSE_ALL":
                        boolean timerStarted = messageFromServer.getBoolean("timerStarted");
                        EventBus.getDefault().post(new PauseTabletEvent(timerStarted));
                        break;
                    case "INITIALIZE_SCORE":
                        int redScore = messageFromServer.getInt("redScore");
                        int blueScore = messageFromServer.getInt("blueScore");
                        EventBus.getDefault().post(new InitializeScoreEvent(redScore,blueScore));
                        break;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private void sendCommandOverTCP(BaseTCPCommand command){
        if(tcpThread.isAlive()){
            Log.d(TAG, "sendCommandOverTCP: " + command.getCommandName());
            try {
                tcpThread.sendCommand(command);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void initTCP() {
        sendCommandOverTCP(new InitTCPCommand());
    }

    public void increaseBlueScore(BaseTCPCommand command) {
        sendCommandOverTCP(command);
    }

    public void increaseRedScore(BaseTCPCommand command) {
        sendCommandOverTCP(command);
    }

    public void doUndo(BaseTCPCommand command) {
        sendCommandOverTCP(command);
        Log.d(TAG, "doUndo: " + command.getCommandName());
        Log.d(TAG, "doUndo: " + command.getValue());
        EventBus.getDefault().post(new UndoEvent(command.getValue(), command.getCommandName()));
    }

    public void getScores() {
        sendCommandOverTCP(new GetScoreTCPCommand());
    }

    public int getDeviceIndex() {
        return deviceIndex;
    }

    public class BinderClass extends Binder {

        public CommunicationService getService() {
            return CommunicationService.this;
        }
    }
}
