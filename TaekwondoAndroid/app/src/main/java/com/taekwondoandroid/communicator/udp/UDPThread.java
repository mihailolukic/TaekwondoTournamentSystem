package com.taekwondoandroid.communicator.udp;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;
import android.util.Log;

import com.taekwondoandroid.R;
import com.taekwondoandroid.TaekwondoAndroidApp;
import com.taekwondoandroid.communicator.Constants;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import static com.taekwondoandroid.TaekwondoAndroidApp.getContext;

/**
 * Created by Westi on 12/20/2016.
 */

public class UDPThread implements Runnable {

    private static final String TAG = UDPThread.class.getSimpleName();
    private UDPListenerInterface listener;
    private WifiManager wifiManager;
    private Context context;
    private InetAddress broadcastAddress;

    public UDPThread(UDPListenerInterface listener, WifiManager wifiManager, Context context){
        this.listener = listener;
        this.wifiManager = wifiManager;
        this.context = context;
        try {
            broadcastAddress = getBroadcastAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private InetAddress getBroadcastAddress() throws UnknownHostException {
        DhcpInfo dhcp = wifiManager.getDhcpInfo();
        if (dhcp == null) {
            Log.e(TAG, "Could not get dhcp info");
            return null;
        }

        int broadcast = (dhcp.ipAddress & dhcp.netmask) | ~dhcp.netmask;
        byte[] quads = new byte[4];
        for (int k = 0; k < 4; k++)
            quads[k] = (byte) ((broadcast >> k * 8) & 0xFF);
        return InetAddress.getByAddress(quads);
    }

    @Override
    public void run() {
        try {
            sendUDPRequestNew();
        } catch (SocketException e) {
            Log.e(TAG, "SocketException : " + e.getMessage());
            listener.errorOccured(context.getString(R.string.laptop_ip_unreachable));

        } catch (IOException e) {
            Log.e(TAG, "IOException : " + e.getMessage());
            listener.errorOccured(context.getString(R.string.laptop_ip_unreachable));
        }
    }

    private void sendUDPRequestNew() throws IOException {
        DatagramSocket socket = new DatagramSocket(Constants.UDP_DISCOVERY_PORT);
        socket.setBroadcast(true);
        socket.setSoTimeout(Constants.UDP_TIMEOUT_MS);
        socket.setReuseAddress(false);
        sendDiscoveryRequest(socket);
        listenForResponses(socket);
    }

    private void listenForResponses(DatagramSocket socket) {
        byte[] buf = new byte[1024];
        try {
            while (true) {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                Log.d(TAG, packet.getAddress().getHostAddress());
                String receivedString = new String(packet.getData(), 0, packet.getLength());
                String ipAddress = new String(packet.getAddress().getHostName());
                if(!ipAddress.equals(Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress())) || receivedString.trim().toLowerCase().startsWith(Constants.UDP_RESPONSE_MESSAGE.toLowerCase())){
                    String[] receivedStringArray = receivedString.split(":");
                    Log.d("MISA", "Receeived string array " + receivedStringArray[1]);
                    listener.foundServerData(ipAddress,Constants.UDP_DISCOVERY_PORT, receivedStringArray[1]);
                    if(socket!=null){
                        socket.close();
                    }
                    break;
                }
            }
        } catch (IOException e) {
            Log.d(TAG, "listenForResponses: error: " + e.getMessage());
            listener.errorOccured(context.getString(R.string.laptop_ip_unreachable));
            if(socket!=null){
                socket.close();
            }
        }
    }

    private void sendDiscoveryRequest(DatagramSocket socket) throws IOException {
        DatagramPacket packet = new DatagramPacket(Constants.UDP_REQUEST_MESSAGE.getBytes(), Constants.UDP_REQUEST_MESSAGE.length(), broadcastAddress, Constants.UDP_DISCOVERY_PORT);
        socket.send(packet);
    }


}
