package com.example.thirdtest.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.thirdtest.R;

public class FindServerActivity extends AppCompatActivity {

    EditText editIp;
    Button btnFindServer;
    RadioGroup radioGroup;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_server);

        editIp = findViewById(R.id.editIpServer);
        btnFindServer = findViewById(R.id.btnFindServer);
        //  radioGroup = findViewById(R.id.radioGroup);

        btnFindServer.setOnClickListener(view -> {
            String ipServer =  editIp.getText().toString();
            if(!ipServer.isEmpty() &&  Patterns.IP_ADDRESS.matcher(ipServer).matches()){
                Intent intent = new Intent(FindServerActivity.this,ClientActivity.class);
                intent.putExtra("ipServer",ipServer);
                // intent.putExtra("orientation",radioButton.getText());
                startActivity(intent);
                finish();
            } else {
                if(ipServer.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Ingrese una dirección IP a buscar",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(),"Ingrese una IP válida",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

 /*   public void  checkButton(View v){
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
    }*/
}
