package com.example.thirdtest.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.mg.connectionlibraryandroid.Implementations.ConnectMethods;
import com.example.thirdtest.Interfaces.WebSocketReceiver;
import com.example.thirdtest.R;
import com.example.thirdtest.Utilities.ImageUtility;
import com.example.thirdtest.Utilities.TimeClass;
import com.example.thirdtest.WebSockets.WebSocketClientImp; //Libreria WebSocket
import com.github.nisrulz.sensey.Sensey;
import com.github.nisrulz.sensey.TouchTypeDetector;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

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
    private int  screenWidth, screenHeight;
    private float scrolledPercentage = 0;
    private static Context context;


    //Image
    private ImageView imgBird;
    //Position
    private float birdX;
    private float birdY;
    //Initialize class
    private Timer time = new Timer();
    //Animación Pajaro
    private ObjectAnimator animateX;
    private long animateDuration = 2000;

    //Timer counter
    TimeClass timeC = new TimeClass();

    private TextView nClient;
    //private String newClient = "";
    private int numCliente;
    AnimationDrawable astro;



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

        //Pajaro var
        imgBird = (ImageView)findViewById(R.id.imageBird);  //Pajarito
        screenHeight = displayMetrics.heightPixels;

        //Sprite
        if (imgBird==null) throw new AssertionError();
        imgBird.setBackgroundResource(R.drawable.astro_animation);
        astro = (AnimationDrawable)imgBird.getBackground();


        //Dibujar pajaro
        imgBird.setX(-300.0f);
        imgBird.setY(900.0f);
        /*
        timeC.starTime();
        if (timeC.client == true ) {
            Pajaro();
            timeC.client = false;
        }

*/
        /*time.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //if (timeC.mTimeMilis == 4000)
                            Pajaro();
                    }
                });
            }
        },5000,2000);*/

        nClient = findViewById(R.id.numClient);


    }

    public void Pajaro () {
        animateX = ObjectAnimator.ofFloat(imgBird, "x", 1100f);
        animateX.setDuration(animateDuration);
        animateX.setRepeatMode(ValueAnimator.RESTART);
        AnimatorSet animateSetX = new AnimatorSet();
        animateSetX.play(animateX);
        animateSetX.start();
    }

    @Override public boolean dispatchTouchEvent(MotionEvent event) {
        // Setup onTouchEvent for detecting type of touch gesture
        Sensey.getInstance().setupDispatchTouchEvent(event);
        return super.dispatchTouchEvent(event);
    }
    private void connectWebSocket() {
        //if (newClient != myIpAddress){
            //numCliente ++;
//        }

        wsClient = new WebSocketClientImp(connectMethods.GetUriServer(serverIpAddress,SERVER_PORT), this);
        wsClient.connect();
        Toast.makeText(getApplicationContext(),"Cliente Conectado",Toast.LENGTH_SHORT).show();
        numCliente = wsClient.numCliente;

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
        //handler.post((Runnable) () -> {
            //while (!timeC.client) {
            //if (timeC.client) {
                //Object().wait(2);
                //Pajaro();
                //timeC.client = false;
            //}
            //Toast.makeText(context, "Llegó la imagen", Toast.LENGTH_LONG).show();
        //});

        time.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        astro.start();
                        Pajaro();
                    }
                }, 2000);
                imgBird.setX(-300.0f);
                imgBird.setY(900.0f);
            }
        },3000 , 6000);
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
                            newBitmap = ImageUtility.cropImage(bitmap, numCliente); //side numClient
                            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                            imageView.setImageBitmap(newBitmap);
                            nClient.setText(String.valueOf(numCliente)); // numCliente
                        }else {
                            Toast.makeText(context, "La imagen todavía no llega", Toast.LENGTH_LONG).show();
                        }
                        break;
                    case TouchTypeDetector.SCROLL_DIR_RIGHT:
                        /*if(bitmap != null) {
                         newBitmap = ImageUtility.cropImage(bitmap, 1);
                         imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                         imageView.setImageBitmap(newBitmap);
                         nClient.setText("1");
                        }else {
                            Toast.makeText(context, "La imagen todavía no llega", Toast.LENGTH_LONG).show();
                        }*/
                        break;
                    default:
                        // Do nothing
                        break;
                }

        }

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
