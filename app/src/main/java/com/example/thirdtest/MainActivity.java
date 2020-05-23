package com.example.thirdtest;

import android.content.Intent;
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


//aqui
import com.example.thirdtest.Utilities.TimeClass;

public class MainActivity extends AppCompatActivity {

    ConnectMethods connectMethods = new ConnectMethods();
    TextView myIpTextView;
    String myIpAddress;

    Button btnClient, btnServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myIpTextView = findViewById(R.id.my_ip_text);

        myIpAddress = connectMethods.FindMyIpAddress(this);
        myIpTextView.setText(myIpAddress);



        btnClient = findViewById(R.id.btn_client);
        btnServer = findViewById(R.id.btn_server);

        btnServer.setOnClickListener(view -> {

            Intent intent = new Intent(view.getContext(), ServerActivity.class);
            startActivity(intent);

        });

        btnClient.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), FindServerActivity.class);
            startActivity(intent);
        });
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
