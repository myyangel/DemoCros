package com.example.thirdtest.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thirdtest.R;
import com.example.thirdtest.Utilities.ImageUtility;
import com.example.thirdtest.Utilities.TimeClass;
import com.github.nisrulz.sensey.Sensey;
import com.github.nisrulz.sensey.TouchTypeDetector;

import java.util.Timer;
import java.util.TimerTask;

public class HalfImageActivity extends AppCompatActivity {
    private String orientation;
    private byte[] base64Image;
    private Bitmap bitmap;
    private boolean side;
    public ImageView imageView ;

    //Screen Size
    private  int screenWidth;
    private  int screenHeight;
    private final int per = 20;
    //Image
    private ImageView imgBird;
    //Position
    private float birdX;
    private float birdY;
    //Initialize class
    private Handler handler = new Handler();
    private Timer time = new Timer();

    //Animación Pajaro
    private ObjectAnimator animateX;
    private long animateDuration = 2000;

    //Timer counter
    //private TextView time_count;
    TimeClass timeC = new TimeClass();
    private Button btStart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_half_image);

        Sensey.getInstance().init(this);
        imageView = findViewById(R.id.imageViewServer);
        Intent intent = getIntent();
        Sensey.getInstance().startTouchTypeDetection(this, touchTypListener); // Dectecta el toque
        base64Image = intent.getByteArrayExtra("Base64Image");
        bitmap = ImageUtility.convertToBitmap(base64Image);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageBitmap(bitmap);

        Bitmap newsBitmap;
        newsBitmap = ImageUtility.cropImage(bitmap, 0);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageBitmap(newsBitmap);

        //Codigo Pajaro
        imgBird = (ImageView)findViewById(R.id.imageBird);  //Pajarito
        WindowManager wm = getWindowManager();
        Display disp =wm.getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        //Dibujar pajaro
        imgBird.setX(-300.0f);
        imgBird.setY(900.0f);

        //Time Down
        //time_count = findViewById(R.id.time);
        btStart = findViewById(R.id.btStart);


        btStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //timeC.starTime();

                time.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                //if (timeC.mTimeMilis == 6000)
                                if (!timeC.client) {
                                    Pajaro();
                                    //timeC.client = true;
                                }
                            }
                        });
                    }
                },0,2000);

                //time_count.setText(timeC.timeCount);
                //if(timeC.getValor() < 6000 ) {
                    //Pajaro();
                    //timeC.client = true;
                //}
            }
        });


    }


    public void Pajaro () {
        animateX = ObjectAnimator.ofFloat(imgBird, "x", 1100f);
        animateX.setDuration(animateDuration);
        AnimatorSet animateSetX = new AnimatorSet();
        animateSetX.play(animateX);
        animateSetX.start();

    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        // Setup onTouchEvent for detecting type of touch gesture
        Sensey.getInstance().setupDispatchTouchEvent(event);
        return super.dispatchTouchEvent(event);
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
                    /*newBitmap = ImageUtility.cropImage(bitmap, 0);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    imageView.setImageBitmap(newBitmap);*/


                    break;
                case TouchTypeDetector.SCROLL_DIR_RIGHT:
                    /*newBitmap = ImageUtility.cropImage(bitmap, 0);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    imageView.setImageBitmap(newBitmap);*/

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
