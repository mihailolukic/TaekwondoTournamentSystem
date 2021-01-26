package com.taekwondoandroid.communicator.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.taekwondoandroid.R;
import com.taekwondoandroid.TaekwondoAndroidApp;
import com.taekwondoandroid.communicator.events.ErrorEvent;
import com.taekwondoandroid.communicator.events.OnBindEvent;
import com.taekwondoandroid.communicator.events.ResetAllEvent;
import com.taekwondoandroid.communicator.service.CommunicationService.BinderClass;
import com.taekwondoandroid.communicator.tcp.tcpCommand.BaseTCPCommand;
import com.taekwondoandroid.communicator.tcp.tcpCommand.IncreaseBlueTCPCommand;
import com.taekwondoandroid.communicator.tcp.tcpCommand.IncreaseRedTCPCommand;
import com.taekwondoandroid.communicator.udp.UDPListenerInterface;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by Westi on 12/14/2016.
 */

public class CommunicationManager{

    private static final String TAG = "CommunicationManager";

    private Context context;

    private static CommunicationManager instance;

    private CommunicationService service;


    private boolean isBound = false;
    private boolean serviceBounded = false;
    private String ipAdress;
    private String port;
    private String laptopIPAddress;

    private ArrayList<BaseTCPCommand> tcpCommands = new ArrayList<>();

    public CommunicationManager(Context context) {
        this.context = context;
        bindToService();
    }

    private void bindToService() {
        Log.d(TAG, "bindToService: context " + context.getClass().getSimpleName());
        if(!isBound){
            Log.d(TAG, "bindToService: isBound: " + isBound);
            isBound = true;
            Intent intent = new Intent(context, CommunicationService.class);
            context.bindService(intent,connection,Context.BIND_AUTO_CREATE);
        }
    }

    //this will be called only when we stop application
    public void destroyInstance(Context context){
        unbindService(context);
    }

    public void unbindService(Context context) {
        Log.d(TAG, "unbindService: ");
        if (isBound) {
            instance = null;
            context.unbindService(connection);
            Intent intent = new Intent(context, CommunicationService.class);
            context.stopService(intent);
            isBound = false;
        }
    }

    public static CommunicationManager getInstance(Context context){
        if(instance == null){
            instance = new CommunicationManager(context);
        }
        return instance;
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            BinderClass binder = (BinderClass) iBinder;
            service = binder.getService();
            serviceBounded = true;
            EventBus.getDefault().post(new OnBindEvent());

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            serviceBounded = false;
            EventBus.getDefault().post(new OnBindEvent());
            service = null;

        }
    };

    public boolean isServiceBounded() {
        return serviceBounded;
    }

    public void startUDP() {
        if(service!=null){
            service.startUDP();
        }
        else{
            EventBus.getDefault().post(new ErrorEvent(this.context.getString(R.string.laptop_ip_unreachable)));
        }
    }

    public void setLaptopIPAdress(String ipAddress) {
        laptopIPAddress = ipAddress;
    }

    public void initTCP() {
        if(service!=null){
            service.initTCP();
        }
        else{
            EventBus.getDefault().post(new ErrorEvent(this.context.getString(R.string.laptop_ip_unreachable)));
        }
    }

    public void increaseBlueScore(int newScore) {
        if(service!=null){
            IncreaseBlueTCPCommand command = new IncreaseBlueTCPCommand(newScore);
            tcpCommands.add(command);
            service.increaseBlueScore(command);
        }
        else{
            EventBus.getDefault().post(new ErrorEvent(this.context.getString(R.string.laptop_ip_unreachable)));
        }
    }

    public void increaseRedScore(int newScore) {
        if(service!=null){
            IncreaseRedTCPCommand command = new IncreaseRedTCPCommand(newScore);
            tcpCommands.add(command);
            service.increaseRedScore(command);
        }
        else{
            EventBus.getDefault().post(new ErrorEvent(this.context.getString(R.string.laptop_ip_unreachable)));
        }
    }


    public void getScores() {
        if(service!=null){
           service.getScores();
        }
        else{
            EventBus.getDefault().post(new ErrorEvent(this.context.getString(R.string.laptop_ip_unreachable)));
        }
    }

    public void undo() {
        if(tcpCommands!= null && !tcpCommands.isEmpty()){
            BaseTCPCommand command = tcpCommands.get(tcpCommands.size()-1);
            tcpCommands.remove(command);
            service.doUndo(command.undo());
        }
        else{
            //TODO MISA - ako stigne do kraja liste, izbaciti poruku da je zavrsio mukica
        }
    }

    public void resetUndo() {
        if(tcpCommands!=null && !tcpCommands.isEmpty()){
            tcpCommands.clear();
        }
    }

    public int getDeviceIndex() {
        if(service!=null){
            return service.getDeviceIndex();
        }
        return -1;
    }
}
