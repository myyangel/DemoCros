package com.example.thirdtest.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.example.thirdtest.R;
import com.example.thirdtest.Utilities.ImageUtility;
import com.github.nisrulz.sensey.Sensey;
import com.github.nisrulz.sensey.TouchTypeDetector;

public class HalfImageActivity extends AppCompatActivity {
    private String orientation;
    private byte[] base64Image;
    private Bitmap bitmap;
    private boolean side;
    public ImageView imageView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_half_image);
        Sensey.getInstance().init(this);
        imageView = findViewById(R.id.imageViewServer);
        Intent intent = getIntent();
        Sensey.getInstance().startTouchTypeDetection(this, touchTypListener);
        base64Image = intent.getByteArrayExtra("Base64Image");
        bitmap = ImageUtility.convertToBitmap(base64Image);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageBitmap(bitmap);

    }

    @Override public boolean dispatchTouchEvent(MotionEvent event) {
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
                    newBitmap = ImageUtility.cropImage(bitmap, false);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    imageView.setImageBitmap(newBitmap);

                    break;
                case TouchTypeDetector.SCROLL_DIR_RIGHT:
                    newBitmap = ImageUtility.cropImage(bitmap, true);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    imageView.setImageBitmap(newBitmap);

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
