package app.backend.communicator.network.udp;

import app.util.Constants;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Created by Westi on 12/24/2016.
 */
public class UDPServerThread implements Runnable{



    private UDPListener listener;

    public static UDPServerThread getInstance(){
        return UDPServerThreadHolder.INSTANCE;
    }

    private static class UDPServerThreadHolder{
        private static final UDPServerThread INSTANCE = new UDPServerThread();
    }

    @Override
    public void run() {
        System.out.println("Starting");
        try {
            DatagramSocket dsocket = new DatagramSocket(Constants.UDP_PORT);
            byte[] receiveData = new byte[1024];
            // Create a packet to receive data into the buffer
            DatagramPacket packet = new DatagramPacket(receiveData, receiveData.length);
            System.out.println("UDP connection started ...");
            while (true) {
                dsocket.receive(packet);
                String sentence = new String(packet.getData());
                System.out.println("UDP: Received message: " + sentence);
                if(!sentence.isEmpty()){
                    if(sentence.startsWith(Constants.UDP_REQUEST_MESSAGE)){
                        System.out.println("UDP: Mobile ip address is : " + packet.getAddress().getHostAddress());
                        int deviceIndex = listener.addMobileIPAddress(packet.getAddress().getHostAddress().trim());
                        System.out.println("UDP: Mobile device index is : " + deviceIndex);
                        String responseMessage = Constants.UDP_RESPONSE_MESSAGE + ":" + deviceIndex;
                        packet.setData(responseMessage.getBytes());
                        packet.setLength(responseMessage.getBytes().length);
                        System.out.println("UDP: Sending response message on mobile ip address: " + packet.getAddress().getHostAddress());
                        dsocket.send(packet);
                    }
                    else{
                        System.out.println("UDP: Request is empty or it's in unsupported format" + sentence);
                    }
                }
                else{
                    System.out.println("UDP: Request is empty");
                }

            }
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }


    public void setUDPListener(UDPListener listener) {
        this.listener = listener;
    }


    public static void main(String[] ars){
        Thread udpDiscoveryThread = new Thread(UDPServerThread.getInstance());
        udpDiscoveryThread.start();
    }

}
