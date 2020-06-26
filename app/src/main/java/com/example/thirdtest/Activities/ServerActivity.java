package com.example.thirdtest.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.mg.connectionlibraryandroid.Implementations.ConnectMethods;
import com.example.thirdtest.Interfaces.WebSocketReceiver;
import com.example.thirdtest.R;
import com.example.thirdtest.Utilities.ImageUtility;
import com.example.thirdtest.WebSockets.WebSocketClientImp;  //Libreria WebSocket
import com.example.thirdtest.WebSockets.WebSocketServerImp;  //Libreria WebSocket
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.util.Timer;

public class ServerActivity extends AppCompatActivity implements WebSocketReceiver {

    private  String port = "8080";
    private  String myIpAddress;

    private ConnectMethods connectMethods = new ConnectMethods();

    private WebSocketServerImp wsServer;
    private WebSocketClientImp wsClient;
    private InetSocketAddress inetSockAddress;

    private ImageView imageView;
    private Button btnSubmitImage, btnSendImage, btnCloseSession, btnShowLeft, btnShowRight;
    private TextView myIpTextView;
    private Handler handler;
    private Bitmap senderImage;

    private final int MAX_WIDTH_IMAGE = 1400;
    private final int MAX_HEIGHT_IMAGE = 1600;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);

        myIpAddress = connectMethods.FindMyIpAddress(this);
        btnSendImage = findViewById(R.id.send_data);
        btnSubmitImage = findViewById(R.id.submit_image);
        btnCloseSession = findViewById(R.id.disconnect_server);
        imageView = findViewById(R.id.imageViewServer);
        myIpTextView = findViewById(R.id.text_ip_client);
        myIpTextView.setText(myIpAddress);
        //Picasso.get (). load (R.drawable.mapa) .into (imageView);




        btnSubmitImage.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");

            //Picasso.get (). load (R.drawable.mapa) .into (imageView);
            startActivityForResult(Intent.createChooser(intent,"Pick Image"), 1);
        });

        btnSendImage.setOnClickListener(view -> sendMessageToServer());

        btnCloseSession.setOnClickListener(view -> {
            if(wsClient != null && wsClient.isOpen()){
                CloseServer();
                finish();
            }
        });

        SetWServerAndStart();

        handler = new Handler();
        handler.postDelayed(this::connectWebSocket, 2000);


    }

    private void connectWebSocket() {
        wsClient = new WebSocketClientImp(connectMethods.GetUriServer(myIpAddress, port), this);
        wsClient.connect();
        Toast.makeText(getApplicationContext(),"Cliente Conectado",Toast.LENGTH_SHORT).show();

        //contador de clientes



    }

    private void SetWServerAndStart() {
        inetSockAddress = connectMethods.GetISocketAddres(this, port);
        wsServer = new WebSocketServerImp(inetSockAddress);
        wsServer.setReuseAddr(true);
        wsServer.start();

        Toast.makeText(getApplicationContext(),"Server Abierto",Toast.LENGTH_SHORT).show();
    }

    private void sendMessageToServer() {

        String type = imageView.getDrawable().getClass().getSimpleName();
        if (imageView.getDrawable() != null && type.equals("BitmapDrawable") && wsClient.isOpen()) {
            Toast.makeText(getApplicationContext(), "Enviando....", Toast.LENGTH_SHORT).show();
            String base64Image = checkImageResolution(imageView);
            wsClient.send(base64Image);
            Intent intent = new Intent(this, HalfImageActivity.class);
            intent.putExtra("Base64Image", ImageUtility.convertToBytesArray(senderImage));
            Toast.makeText(getApplicationContext(), "Imagen Enviada", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        } else {
           Toast.makeText(getApplicationContext(),"Por favor, seleccione una imagen",Toast.LENGTH_SHORT).show();
        }


    }
    private String checkImageResolution(ImageView imageView){
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        senderImage = bitmap;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        boolean isPortrait = width < height;
        String base64Image;
        if(width <= MAX_WIDTH_IMAGE && height <= MAX_HEIGHT_IMAGE ) {
            base64Image = ImageUtility.convertToBase64(imageView);
        } else {
            base64Image = ImageUtility.resizeImageAndConvertToBase64(imageView, isPortrait);
        }
        return base64Image;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (wsClient != null){
            CloseServer();
        }
    }

    private void CloseServer(){
        try {
            wsClient.close();
            wsServer.stop();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == 1) {
            try{
                InputStream inputStream = getContentResolver().openInputStream(data.getData());

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException ex){

            }
        }
    }

    @Override
    public void onWebSocketMessage(String message) {

    }

    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {
        handler.post((Runnable) () -> Toast.makeText(this, "El servidor se apagó correctamente", Toast.LENGTH_LONG).show());
        finish();
    }
}
