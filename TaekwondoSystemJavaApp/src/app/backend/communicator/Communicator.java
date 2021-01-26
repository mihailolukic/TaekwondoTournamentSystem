package app.backend.communicator;

import app.backend.LogicBroker;
import app.backend.communicator.network.tcp.TCPServerListener;
import app.backend.communicator.network.tcp.TCPServerThread;
import app.backend.communicator.network.tcp.tcpCommand.*;
import app.backend.communicator.network.udp.UDPListener;
import app.backend.communicator.network.udp.UDPServerThread;
import app.backend.interfaces.LogicInterface;
import app.backend.model.LogicCommand;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Communicator implements UDPListener, TCPServerListener {

    private static final int MOBILE_DEVICES_NUMBER_SIZE = 4;
    private static Communicator instance;
    private String[] mobileIpAddresses = new String[MOBILE_DEVICES_NUMBER_SIZE];
    private LogicInterface logicInterface;
    TCPServerThread tcpServerThread;


    public Communicator() {
    }

    public static Communicator getInstance() {
        if (instance == null) {
            instance = new Communicator();
        }
        return instance;
    }

    public void startServices() {
        startUDPService();
        startTCPService();
    }

    private void startTCPService() {
        tcpServerThread = new TCPServerThread(this);
        tcpServerThread.start();
    }


    private void startUDPService() {
        UDPServerThread udpServer = UDPServerThread.getInstance();
        udpServer.setUDPListener(this);
        Thread udpDiscoveryThread = new Thread(udpServer);
        udpDiscoveryThread.start();
    }


    @Override
    public synchronized int addMobileIPAddress(String ipAddress) {
        System.out.println("Communicator: Received mobile ip address by UDP: " + ipAddress);
        Integer returnIndex = null;
        for(int i =0; i< MOBILE_DEVICES_NUMBER_SIZE; i++){
            if(mobileIpAddresses[i]==null){
                mobileIpAddresses[i] = ipAddress;
                returnIndex = i;
                break;
            }
            else{
                if(ipAddress.equals(mobileIpAddresses[i])){
                    mobileIpAddresses[i] = ipAddress;
                    returnIndex = i;
                    break;
                }
            }
        }
        printIPs();
        if(returnIndex == null){
            return -1;
        }
        return returnIndex;
    }

    private void printIPs() {
        System.out.println("********* Mobile ip address ********* ");
        for (String address : mobileIpAddresses) {
            System.out.println("Device ip :  " + address);
        }
        System.out.println("********* ***************** ********* ");
    }

    @Override
    public void processTCPMessage(JSONObject message) {
        System.out.println("TCP Received message: " + message);
        LogicBroker.getInstance().processTCPMessage(new LogicCommand(message));

    }

    public void setLogicInterface(LogicInterface logicInterface) {
        this.logicInterface = logicInterface;
    }

    public void increaseRedScoreOnTablet(int i) throws JSONException {
        tcpServerThread.sendCommand(new IncreaseRedScoreCommand(i));
    }

    public void increaseBlueScoreOnTablet(int i) throws JSONException {
        tcpServerThread.sendCommand(new IncreaseBlueScoreCommand(i));
    }

    public void resetAll() throws JSONException {
        tcpServerThread.sendCommand(new ResetAllCommand());
    }


    public void pauseAllTablets(boolean timerStarted) throws JSONException {
        tcpServerThread.sendCommand(new PauseTabletsCommand(timerStarted));
    }

    public void initializeTabletScore(int deviceNumber, int mobileDeviceRedScore, int mobileDeviceBlueScore) throws JSONException {
        tcpServerThread.sendCommandToOneClient(deviceNumber,new InitializeScoreTablet(mobileDeviceRedScore,mobileDeviceBlueScore));
    }

    public boolean checkNumberOfDevices() {
        return true;//mobileIpAddresses.size() == 4;
    }

    public void removeIpAddress(String deviceAddress) {
        System.out.println("RemoveIpAddress: " + deviceAddress);
        //TODO MISA
        for (int i = 0; i < MOBILE_DEVICES_NUMBER_SIZE; i++){
            if(deviceAddress.equals(mobileIpAddresses[i])){
                mobileIpAddresses[i] = null;
            }
        }
        printIPs();
    }
}
