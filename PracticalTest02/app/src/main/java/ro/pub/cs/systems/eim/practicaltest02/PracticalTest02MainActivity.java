package ro.pub.cs.systems.eim.practicaltest02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class PracticalTest02MainActivity extends AppCompatActivity implements View.OnClickListener{
    EditText serverEdit;
    EditText clientEdit;
    EditText serverIpEdit;
    EditText serverPortEdit;
    Button serverButton;
    Button clientButton;
    ServerThread serverThread;
    TextView clientText;
    HashMap<String, String> hash;
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.server_button) {
            String serverPort = serverEdit.getText().toString();
            if (serverPort == null || serverPort.isEmpty()) {
                Toast.makeText(
                        getApplicationContext(),
                        "Server port should be filled!",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            serverThread = new ServerThread(Integer.parseInt(serverPort));
            if (serverThread.getServerSocket() != null) {
                serverThread.start();
            } else {
                Log.e("ServerThread", "[MAIN ACTIVITY] Could not creat server thread!");
            }
        }
        if(id == R.id.client_button) {

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test02_main);
        serverButton = (Button) findViewById(R.id.server_button);
        clientButton = (Button) findViewById(R.id.client_button);
        serverEdit = (EditText) findViewById(R.id.server_edit);
        clientEdit = (EditText) findViewById(R.id.client_edit);
        serverButton.setOnClickListener(this);
        clientButton.setOnClickListener(this);
        serverPortEdit = (EditText) findViewById(R.id.client_server_ip);
        serverIpEdit = (EditText) findViewById(R.id.client_server_port);
        clientText = (TextView) findViewById(R.id.client_text);
    }

    @Override
    protected void onDestroy() {
        if (serverThread != null) {
            serverThread.stopThread();
        }
        super.onDestroy();
    }
}
