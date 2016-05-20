package ro.pub.cs.systems.eim.practicaltest02;

/**
 * Created by Jorj on 20-May-16.
 */
import android.util.Log;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class ServerThread extends Thread {

    private int port = 0;
    private ServerSocket serverSocket = null;
    private HashMap<String, AlarmInfo> hash = null;

    public ServerThread(int port) {
        this.port = port;
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException ioException) {
            Log.e("ServerThread", "An exception has occurred: " + ioException.getMessage());
            ioException.printStackTrace();
        }
        this.hash= new HashMap<>();
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public void setServerSocker(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public synchronized void setData(String ip, AlarmInfo alarmInfo) {
        this.hash.put(ip, alarmInfo);
    }

    public synchronized HashMap<String, AlarmInfo> getData() {
        return hash;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                Log.i("ServerThread", "[SERVER] Waiting for a connection...");
                Socket socket = serverSocket.accept();
                Log.i("ServerThread", "[SERVER] A connection request was received from " + socket.getInetAddress() + ":" + socket.getLocalPort());
//                CommunicationThread communicationThread = new CommunicationThread(this, socket);
//                communicationThread.start();
            }
        } catch (Exception clientProtocolException) {
            Log.e("ServerThread", "An exception has occurred: " + clientProtocolException.getMessage());
            clientProtocolException.printStackTrace();
        }
    }

    public void stopThread() {
        if (serverSocket != null) {
            interrupt();
            try {
                serverSocket.close();
            } catch (IOException ioException) {
                Log.e("ServerThread", "An exception has occurred: " + ioException.getMessage());
                ioException.printStackTrace();
            }
        }
    }

}