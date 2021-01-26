package app.backend.communicator.network.tcp;


import app.backend.communicator.network.tcp.tcpCommand.BaseTCPCommand;
import app.util.Constants;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Westi on 1/7/2017.
 */
public class TCPServerThread extends Thread implements TCPListener {

    private final static String TAG = "TCP: ";

    private ServerSocket serverSocket = null;

    private Socket socket = null;
    private final static int MAX_CLIENTS_COUNT = 4;

    private TCPClientThread[] clientThreads = new TCPClientThread[MAX_CLIENTS_COUNT];
    private TCPServerListener listener;

    public TCPServerThread(TCPServerListener listener) {
        this.listener = listener;
    }


    @Override
    public void run() {
        try {
            System.out.println(TAG + " Thread started and waiting for client ");
            serverSocket = new ServerSocket(Constants.TCP_PORT);
            while (true) {
                socket = serverSocket.accept();
                System.out.println(TAG + " Client accepted connection : " + socket.getInetAddress());
                BufferedReader is = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String message = is.readLine();
                System.out.println(TAG + " " + message);
                if (message != null) {
                    JSONObject object = new JSONObject(message);
                    if (object.has("command") && object.getString("command").equals("CREATE_CLIENT")) {
                        int deviceIndex = object.getInt("deviceIndex");
                        (clientThreads[deviceIndex] = new TCPClientThread(socket, clientThreads, this, deviceIndex)).start();
                        PrintStream os1 = new PrintStream(socket.getOutputStream());
                        os1.println("Communication accepted");
                    }
                }
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public synchronized void processMessage(JSONObject message) {
        listener.processTCPMessage(message);
    }

    @Override
    public void sendCommand(BaseTCPCommand command) throws JSONException {
        for (TCPClientThread client : clientThreads) {
            if (client != null) {
                client.sendCommand(command);
            }
        }
    }

    @Override
    public void sendCommandToOneClient(int deviceNumber, BaseTCPCommand command) throws JSONException {
        for (TCPClientThread client : clientThreads) {
            if (client != null && (client.getDeviceNumber() == deviceNumber)) {
                client.sendCommand(command);
            }
        }
    }


}
