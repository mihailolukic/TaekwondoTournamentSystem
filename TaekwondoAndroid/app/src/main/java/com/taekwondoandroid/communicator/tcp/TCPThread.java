package com.taekwondoandroid.communicator.tcp;

import android.util.Log;

import com.taekwondoandroid.communicator.Constants;
import com.taekwondoandroid.communicator.events.FoundServerDataEvent;
import com.taekwondoandroid.communicator.tcp.tcpCommand.BaseTCPCommand;
import com.taekwondoandroid.communicator.tcp.tcpCommand.CreateTCPClient;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Created by Westi on 1/7/2017.
 */
public class TCPThread extends Thread{
    private static final String TAG = TCPThread.class.getSimpleName();
    private String serverIPAddress;
    private TCPListenerInterface listener;
    private Integer deviceIndex;
    Socket socket = null;
    BufferedReader is = null;
    PrintStream os = null;

    public TCPThread(String ipAddress, TCPListenerInterface listener, Integer deviceIndex) {
        this.serverIPAddress = ipAddress;
        this.listener = listener;
        this.deviceIndex = deviceIndex;
    }

    @Override
    public void run() {
        try {
            Log.d(TAG, "run: " + serverIPAddress);
            socket = new Socket(serverIPAddress, Constants.TCP_PORT);
            os = new PrintStream(socket.getOutputStream());
            is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            os.println(new CreateTCPClient(deviceIndex).getParams().toString());
            String messageFromServer;
            while ((messageFromServer = is.readLine())!= null){
                Log.d(TAG, "run: messageFromServer: " + messageFromServer);
                if(!messageFromServer.startsWith("Communication accepted")){
                    JSONObject messageObject = null;
                    try {
                        messageObject = new JSONObject(messageFromServer);
                        listener.processTCPMessage(messageObject);
                    } catch (JSONException e) {
                        Log.e(TAG,e.getMessage());
                    }
                }
                else{
                    listener.foundServerTCPData(socket.getInetAddress(),socket.getPort());
                }
            }
            destroyThread();
        } catch (IOException e) {
            listener.errorOccured("Device has lost connection with laptop application");
            destroyThread();
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void sendCommand(BaseTCPCommand command) throws JSONException {
        JSONObject object = command.getParams();
        object.put("deviceIndex", deviceIndex);
        os.println(object);
    }

    public void destroyThread(){
        try {
            if(os != null){
                os.close();
            }
            if(is!=null){
                is.close();
            }
            if(socket!=null){
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
