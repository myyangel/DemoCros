package com.example.thirdtest.Utilities;

public class Constants {
    //Web Socket
    public static final String PORT = "8080";

    //Pinch interaction
    public static final int MIN_SWIPE_DISTANCE = 80;
    public static final int MIN_DISTANCE_OFF_BORDERS = 300;
    public static final long DELAY_DETECT_PINCH = 300000;

    //SCROLL
    public static final int SERVER_SEND_SCROLL_INTERVAL = 10;
    public static final int CLIENT_SEND_SCROLL_INTERVAL = 30;

    //Images
    public static final int MAX_WIDTH_IMAGE = 1400;
    public static final int MAX_HEIGHT_IMAGE = 1600;

    //Messages
    public static final String CONFIRM_PAIRING = ".CONFIRM_PAIRING";
    public static final String CONFIRM_UNPAIRING = ".CONFIRM_UNPAIRING";
    public static final String SWIPE_ORIENTATION = ".SWIPE_ORIENTATION";
    public static final String SCROLLED_PERCENTAGE = ".SCROLLED_PERCENTAGE";

    //Shake interaction
    public static final float SHAKE_THRESHOLD = 1.15f;
    public static final int SHAKE_WAIT_TIME_MS = 250;

    public enum ORIENTATION{
        TOP,
        RIGHT,
        BOTTOM,
        LEFT
    }
}
