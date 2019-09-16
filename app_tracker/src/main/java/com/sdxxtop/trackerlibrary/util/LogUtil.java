package com.sdxxtop.trackerlibrary.util;

import android.util.Log;

import com.sdxxtop.trackerlibrary.Tracker;

public class LogUtil {

    private final static String tag = "Tracker: ";
    private static boolean isOpened = Tracker.getInstance().isDebug();

    public static void openLog(boolean open) {
        isOpened = open;
    }

    public static void i(String info) {
        if (isOpened) {
            Log.i(tag, info);
        }
    }

    public static void e(String info) {
        if (isOpened) {
            Log.e(tag, info);
        }
    }

    public static void d(String info) {
        if (isOpened) {
            Log.d(tag, info);
        }
    }

    public static void v(String info) {
        if (isOpened) {
            Log.v(tag, info);
        }
    }

    public static void w(String info) {
        if (isOpened) {
            Log.w(tag, info);
        }
    }


    public static void i(String tag, String info) {
        if (isOpened) {
            Log.i(tag, info);
        }
    }

    public static void e(String tag, String info) {
        if (isOpened) {
            Log.e(tag, info);
        }
    }

    public static void d(String tag, String info) {
        if (isOpened) {
            Log.d(tag, info);
        }
    }

    public static void v(String tag, String info) {
        if (isOpened) {
            Log.v(tag, info);
        }
    }

    public static void w(String tag, String info) {
        if (isOpened) {
            Log.w(tag, info);
        }
    }
}
