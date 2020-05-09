package com.example.thirdtest.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.mg.connectionlibraryandroid.Implementations.ConnectMethods;
import com.example.thirdtest.Interfaces.WebSocketReceiver;
import com.example.thirdtest.R;
import com.example.thirdtest.Utilities.ImageUtility;
import com.example.thirdtest.WebSockets.WebSocketClientImp; //Libreria WebSocket
import com.github.nisrulz.sensey.Sensey;
import com.github.nisrulz.sensey.TouchTypeDetector;

import java.util.Date;
import static com.example.thirdtest.Utilities.Constants.*;
public class ClientActivity extends AppCompatActivity implements WebSocketReceiver {

    private String serverIpAddress = "";
    private String myIpAddress = "";
    private String orientation = "";
    private final String  SERVER_PORT = "8080";

    WebSocketClientImp wsClient;
    private Bitmap bitmap;
    private ImageView imageView;
    private ConnectMethods connectMethods = new ConnectMethods();
    private Handler handler;
    private int downX, downY;
    private float upX, upY;
    private float deltaX, deltaY;
    private float actualX, pastX;
    private int sendScrollCounter = 0;
    private DisplayMetrics displayMetrics;
    private int  screenWidth;
    private float scrolledPercentage = 0;
    private static Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        Sensey.getInstance().init(context);
        serverIpAddress = getIntent().getStringExtra("ipServer");
      //  orientation = getIntent().getStringExtra("orientation");
        myIpAddress = connectMethods.FindMyIpAddress(this);
        imageView =findViewById(R.id.imageViewClient);;

        handler = new Handler();
        connectWebSocket();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        Sensey.getInstance().startTouchTypeDetection(context, touchTypListener);
    }

    @Override public boolean dispatchTouchEvent(MotionEvent event) {
        // Setup onTouchEvent for detecting type of touch gesture
        Sensey.getInstance().setupDispatchTouchEvent(event);
        return super.dispatchTouchEvent(event);
    }
    private void connectWebSocket() {
        wsClient = new WebSocketClientImp(connectMethods.GetUriServer(serverIpAddress,SERVER_PORT), this);
        wsClient.connect();
        Toast.makeText(getApplicationContext(),"Cliente Conectado",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (wsClient != null){
            wsClient.close();
        }
    }

    @Override
    public void onWebSocketMessage(String message) {

        bitmap = ImageUtility.convertToBitmap(message);
/*
        handler.post((Runnable) () -> {

            Toast.makeText(context, "Llegó la imagen", Toast.LENGTH_LONG).show();
        });*/
    }

    @Override
    public void onWebSocketClose(int code, String reason, boolean remote) {
        if(code == 1001 && remote){
            handler.post((Runnable) () -> Toast.makeText(this, "SERVER WAS CLOSE", Toast.LENGTH_LONG).show());
            finish();
        }
    }
    TouchTypeDetector.TouchTypListener touchTypListener=new TouchTypeDetector.TouchTypListener() {
        @Override public void onTwoFingerSingleTap() {
        }
        @Override public void onThreeFingerSingleTap() {
        }
        @Override public void onDoubleTap() {

        }
        @Override public void onScroll(int scrollDirection) {
            Bitmap newBitmap;
                switch (scrollDirection) {
                    case TouchTypeDetector.SCROLL_DIR_UP:


                        break;
                    case TouchTypeDetector.SCROLL_DIR_DOWN:


                        break;
                    case TouchTypeDetector.SCROLL_DIR_LEFT:
                        if(bitmap != null) {
                            newBitmap = ImageUtility.cropImage(bitmap, 2);
                            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                            imageView.setImageBitmap(newBitmap);
                        }else {
                            Toast.makeText(context, "La imagen todavía no llega", Toast.LENGTH_LONG).show();
                        }
                        break;
                    case TouchTypeDetector.SCROLL_DIR_RIGHT:
                        if(bitmap != null) {
                         newBitmap = ImageUtility.cropImage(bitmap, 0);
                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        imageView.setImageBitmap(newBitmap);
                        }else {
                            Toast.makeText(context, "La imagen todavía no llega", Toast.LENGTH_LONG).show();
                        }
                        break;
                    default:
                        // Do nothing
                        break;
                }}

        @Override public void onSingleTap() {
        }
        @Override public void onSwipe(int swipeDirection) {

                switch (swipeDirection) {
                    case TouchTypeDetector.SWIPE_DIR_UP:

                        break;
                    case TouchTypeDetector.SWIPE_DIR_DOWN:

                        break;
                    case TouchTypeDetector.SWIPE_DIR_LEFT:

                        break;
                    case TouchTypeDetector.SWIPE_DIR_RIGHT:

                        break;
                    default:
                        break;
                }
        }
        @Override public void onLongPress() {
        }
    };

}
