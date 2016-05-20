package ro.pub.cs.systems.eim.practicaltest02;

import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Jorj on 20-May-16.
 */
public class CommunicationThread extends Thread {

    private ServerThread serverThread;
    private Socket socket;

    public CommunicationThread(ServerThread serverThread, Socket socket) {
        this.serverThread = serverThread;
        this.socket = socket;
    }

    @Override
    public void run() {
        if (socket != null) {
            try {
                BufferedReader bufferedReader = Utilities.getReader(socket);
                final PrintWriter printWriter = Utilities.getWriter(socket);
                if (bufferedReader != null && printWriter != null) {
                    Log.i("ComThread", "[COMMUNICATION THREAD] Waiting for parameters from client (city / information type)!");
                    String command = bufferedReader.readLine();
                    HashMap<String, AlarmInfo> data = serverThread.getData();
                    if (command.startsWith("reset")) {
                        data.put(socket.getInetAddress().toString(),null);
                    }
                    if (command.startsWith("set")) {
                       String[] s = command.split(",");
                        data.put(socket.getInetAddress().toString(),new AlarmInfo(s[1], s[2],"inactive"));
                    }
                    if (command.startsWith("poll")) {
                        final AlarmInfo alarmInfo = data.get(socket.getInetAddress());
                        if (alarmInfo == null)
                            printWriter.write(alarmInfo.status);
                        else {
                            Retrofit retrofit2 = new Retrofit.Builder()
                                    .baseUrl("http://www.timeapi.org")
                                    .build();
                            ServerAPI serverAPI2 = retrofit2.create(ServerAPI.class);
                            Call<ResponseBody> call2 = serverAPI2.getTime();
                            call2.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call2, Response<ResponseBody> response) {
                                    Long l = (long) 0;
                                    try {
                                        String[] time = response.body().string().split(".");
                                        if (time[1].compareTo(alarmInfo.min) > 0) {

                                            if (time[0].compareTo(alarmInfo.hour) > 0) {
                                                printWriter.write("active");
                                            }
                                            printWriter.write("inactive");
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    Log.d("[Retrofit]", "Error long" + t.getMessage());
                                }
                            });
                        }

                    }
                }

                socket.close();
            } catch (IOException ioException) {
                Log.e("ComThread", "[COMMUNICATION THREAD] An exception has occurred: " + ioException.getMessage());
                    ioException.printStackTrace();
            }
        } else {
            Log.e("ComThread", "[COMMUNICATION THREAD] Socket is null!");
        }
    }

}
