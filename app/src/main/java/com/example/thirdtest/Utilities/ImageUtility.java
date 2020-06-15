package com.example.thirdtest.Utilities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class ImageUtility {

    private static final int PORTRAIT_WIDTH_IMAGE = 720 ;
    private static final int PORTRAIT_HEIGHT_IMAGE = 1200;

    private static final int LANDSCAPE_WIDTH_IMAGE = 1024;
    private static final int LANDSCAPE_HEIGHT_IMAGE = 768;

    public static Bitmap convertToBitmap(String encodedImage){
        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);

        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        return  decodedByte;
    }

    public static Bitmap convertToBitmap(byte[] imageBytes){

        Bitmap decodedByte = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

        return  decodedByte;
    }

    public static String convertToBase64(ImageView view) {
        BitmapDrawable drawable = (BitmapDrawable) view.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,40,bos);
        byte[] imageBytes = bos.toByteArray();
        String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        return imageString;
    }

    public static byte[] convertToBytesArray (Bitmap bitmap) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,40,bos);
        byte[] imageBytes = bos.toByteArray();

        return  imageBytes;
    }

    public static String resizeImageAndConvertToBase64(ImageView view, boolean isPortrait){

        BitmapDrawable drawable = (BitmapDrawable) view.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        if(isPortrait)
            bitmap = Bitmap.createScaledBitmap(bitmap, PORTRAIT_WIDTH_IMAGE, PORTRAIT_HEIGHT_IMAGE, false);
        else
            bitmap = Bitmap.createScaledBitmap(bitmap, LANDSCAPE_WIDTH_IMAGE, LANDSCAPE_HEIGHT_IMAGE, false);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,40,bos);
        byte[] imageBytes = bos.toByteArray();
        String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        return imageString;

    }

    public static Bitmap cropImage(Bitmap image, int side){
        int width = image.getWidth();
        int height = image.getHeight();
        int newWidth = width / 2; // numCiente
       //int newHeight = height / 2;

        // recreate the new Bitmap
        Bitmap resizedBitmap;
        if(side == 0){
            resizedBitmap= Bitmap.createBitmap(image, 0, 0, newWidth, height);
        } else {
            if (side == 1) {
                resizedBitmap = Bitmap.createBitmap(image, newWidth, 0, newWidth, height);
            } else {
                resizedBitmap = Bitmap.createBitmap(image, newWidth * 2, 0, newWidth, height);
            }
        }
        return resizedBitmap;
    }
}