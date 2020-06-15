package com.example.thirdtest;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.app.mg.connectionlibraryandroid.Implementations.ConnectMethods;
import com.example.thirdtest.Activities.FindServerActivity;
import com.example.thirdtest.Activities.ServerActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.TextureView;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


//aqui
import com.example.thirdtest.Utilities.TimeClass;

public class MainActivity extends AppCompatActivity {

    ConnectMethods connectMethods = new ConnectMethods();
    TextView myIpTextView;
    String myIpAddress;
    Button btnClient, btnServer;
    String port;
    ConnectivityManager cm;
    boolean isConnected = false;
    boolean isWiFiConnected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myIpTextView = findViewById(R.id.my_ip_text);

        myIpAddress = connectMethods.FindMyIpAddress(this);
        myIpTextView.setText(myIpAddress);
        port = "8080";
        btnClient = findViewById(R.id.btn_client);
        btnServer = findViewById(R.id.btn_server);

        cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        btnServer.setOnClickListener(view -> {
            isConnectedToWiFi();
            if (isConnectedToWiFi()){
                if (port.length() == 4) {
                    Intent intent = new Intent(view.getContext(), ServerActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "No se pudo iniciar el servidor correctamente", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Por favor, conéctese a una red", Toast.LENGTH_SHORT).show();

            }
        });

        btnClient.setOnClickListener(view -> {
            if (isConnectedToWiFi() == true) {
                Intent intent = new Intent(view.getContext(), FindServerActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Por favor, conéctese a una red", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean isConnectedToWiFi() {
        for (Network network : cm.getAllNetworks()) {
            NetworkInfo networkInfo = cm.getNetworkInfo(network);
            isConnected = networkInfo.isConnected();
            if (isConnected) {
                if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    isWiFiConnected = networkInfo.isConnected();
                } else {
                    isWiFiConnected = false;
                }
            }
        }
        if (isWiFiConnected) {
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
