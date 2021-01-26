package app.backend.communicator.network.tcp;

import app.backend.communicator.network.tcp.tcpCommand.BaseTCPCommand;
import app.util.Constants;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;


/**
 * Created by Westi on 1/7/2017.
 */
public class TCPClientThread extends Thread{

    private static String TAG = "TCP Device ";
    private final InetAddress deviceAddress;
    private BufferedReader is = null;
    private PrintStream os = null;



    private final Socket socket;
    private final TCPClientThread[] clientThreads;
    private int maxClientsCount;
    private TCPListener listener;



    private int deviceNumber;

    public TCPClientThread(Socket socket, TCPClientThread[] clientThreads, TCPListener listener, int deviceNumber) {
        this.socket = socket;
        this.deviceAddress = socket.getInetAddress();
        this.clientThreads = clientThreads;
        maxClientsCount = clientThreads.length;
        this.listener = listener;
        this.deviceNumber = deviceNumber;
        TAG += (deviceNumber+":");
    }

    public void disconnect() {
        System.out.println(TAG + " Message from client: Client disconnected");
        JSONObject object = new JSONObject();
        try {
            object.put("device", deviceNumber);
            object.put("deviceIndex", deviceNumber);
            object.put("command", Constants.CLIENT_DISCONECTED);
            object.put("value", 0);
            String deviceAddress = this.deviceAddress.toString().replace("/","");
            object.put("address",deviceAddress);
            listener.processMessage(object);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        System.out.println(TAG + " Thread started and waiting for client ");
        int maxClientCount = this.maxClientsCount;
        TCPClientThread[] threads = this.clientThreads;
        try {
            is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            os = new PrintStream(socket.getOutputStream());
            while(true){
                String message = is.readLine();
                if(message==null){
                    disconnect();
                    break;
                }
                JSONObject object = new JSONObject(message);
                object.put("device", deviceNumber);
                //object.put("deviceIndex", deviceNumber);
                System.out.println(TAG + " Message from client: " + object.toString());
                listener.processMessage(object);
            }
            synchronized (this) {
                for (int i = 0; i < maxClientCount; i++) {
                    if (threads[i] == this) {
                        threads[i] = null;
                    }
                }
            }
            is.close();
            os.close();
            socket.close();

        } catch (IOException e) {
            disconnect();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void sendCommand(BaseTCPCommand command) throws JSONException {
        os.println(command.getParams().toString());
    }

    public int getDeviceNumber() {
        return deviceNumber;
    }

    public Socket getSocket() {
        return socket;
    }
}
