package ro.pub.cs.systems.eim.practicaltest02;

import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Jorj on 20-May-16.
 */
public class ClientThread extends Thread {

    private String address;
    private int port;
    private TextView clientText;

    private Socket socket;

    public ClientThread(
            String address,
            int port,
            TextView clientText) {
        this.address = address;
        this.port = port;
        this.clientText = clientText;
    }

    @Override
    public void run() {
        try {
            socket = new Socket(address, port);
            if (socket == null) {
                Log.e("ClientThread", "[CLIENT THREAD] Could not create socket!");
            }

            BufferedReader bufferedReader = Utilities.getReader(socket);
            PrintWriter printWriter = Utilities.getWriter(socket);
            if (bufferedReader != null && printWriter != null) {
                String weatherInformation;
                while ((weatherInformation = bufferedReader.readLine()) != null) {
                    final String finalizedWeatherInformation = weatherInformation;
                    clientText.post(new Runnable() {
                        @Override
                        public void run() {
                            clientText.append(finalizedWeatherInformation + "\n");
                        }
                    });
                }
            } else {
                Log.e("ClientThread", "[CLIENT THREAD] BufferedReader / PrintWriter are null!");
            }
            socket.close();
        } catch (IOException ioException) {
            Log.e("ClientThread", "[CLIENT THREAD] An exception has occurred: " + ioException.getMessage());
            ioException.printStackTrace();
        }
    }

}
