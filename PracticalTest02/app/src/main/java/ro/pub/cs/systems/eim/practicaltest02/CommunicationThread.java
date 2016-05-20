//package ro.pub.cs.systems.eim.practicaltest02;
//
//import android.util.Log;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.net.Socket;
//import java.util.ArrayList;
//import java.util.HashMap;
//
///**
// * Created by Jorj on 20-May-16.
// */
//public class CommunicationThread extends Thread {
//
//    private ServerThread serverThread;
//    private Socket socket;
//
//    public CommunicationThread(ServerThread serverThread, Socket socket) {
//        this.serverThread = serverThread;
//        this.socket = socket;
//    }
//
//    @Override
//    public void run() {
//        if (socket != null) {
//            try {
//                BufferedReader bufferedReader = Utilities.getReader(socket);
//                PrintWriter printWriter = Utilities.getWriter(socket);
//                if (bufferedReader != null && printWriter != null) {
//                    Log.i("ComThread", "[COMMUNICATION THREAD] Waiting for parameters from client (city / information type)!");
//                    String city = bufferedReader.readLine();
//                    String informationType = bufferedReader.readLine();
//                    HashMap<String, AlarmInfo> data = serverThread.getData();
//                    AlarmInfo alarmInfo = null;
//                    if (city != null && !city.isEmpty() && informationType != null && !informationType.isEmpty()) {
//                        if (data.containsKey(city)) {
//                            Log.i("ComThread", "[COMMUNICATION THREAD] Getting the information from the cache...");
//                            alarmInfo = data.get(city);
//                        } else {
//                            Log.i("ComThread", "[COMMUNICATION THREAD] Getting the information from the webservice...");
//                            HttpClient httpClient = new DefaultHttpClient();
//                            HttpPost httpPost = new HttpPost(Constants.WEB_SERVICE_ADDRESS);
//                            List<NameValuePair> params = new ArrayList<>();
//                            params.add(new BasicNameValuePair(Constants.QUERY_ATTRIBUTE, city));
//                            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
//                            httpPost.setEntity(urlEncodedFormEntity);
//                            ResponseHandler<String> responseHandler = new BasicResponseHandler();
//                            String pageSourceCode = httpClient.execute(httpPost, responseHandler);
//                            if (pageSourceCode != null) {
//
//                                alarmInfo = new AlarmInfo();
//                                serverThread.setData(city, alarmInfo);
//                            } else {
//                                Log.e("ComThread", "[COMMUNICATION THREAD] Error getting the information from the webservice!");
//                            }
//                        }
//
//                        if (alarmInfo != null) {
//                            String result = null;
//                            if () {
//                            } else {
//                                result = "Wrong information type (all / temperature / wind_speed / condition / humidity / pressure)!";
//                            }
//                            printWriter.println(result);
//                            printWriter.flush();
//                        } else {
//                            Log.e("ComThread", "[COMMUNICATION THREAD] Weather Forecast information is null!");
//                        }
//
//                    } else {
//                        Log.e("ComThread", "[COMMUNICATION THREAD] Error receiving parameters from client (city / information type)!");
//                    }
//                } else {
//                    Log.e("ComThread", "[COMMUNICATION THREAD] BufferedReader / PrintWriter are null!");
//                }
//                socket.close();
//            } catch (IOException ioException) {
//                Log.e("ComThread", "[COMMUNICATION THREAD] An exception has occurred: " + ioException.getMessage());
//                    ioException.printStackTrace();
//            } catch (JSONException jsonException) {
//                Log.e("ComThread", "[COMMUNICATION THREAD] An exception has occurred: " + jsonException.getMessage());
//                jsonException.printStackTrace();
//            }
//        } else {
//            Log.e("ComThread", "[COMMUNICATION THREAD] Socket is null!");
//        }
//    }
//
//}
