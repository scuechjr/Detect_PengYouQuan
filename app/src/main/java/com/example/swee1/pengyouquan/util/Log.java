package com.example.swee1.pengyouquan.util;

import android.view.accessibility.AccessibilityEvent;

import java.util.ArrayList;
import java.util.List;

public class Log {
    private static final int MAX_LENGTH = 3000;
    private static final String LOG_FORMAT = "%s - %s";
    private static final String SYSTEM_TAG = "wxlog";

    public static void i(String tag, String msg) {
        List<String> msgs = split(tag, msg);
        for (String m : msgs) {
            android.util.Log.i(SYSTEM_TAG, m);
        }
    }

    public static void i(String msg) {
        i(null, msg);
    }

    public static void d(String tag, String msg) {
        List<String> msgs = split(tag, msg);
        for (String m : msgs) {
            android.util.Log.d(SYSTEM_TAG, m);
        }
    }

    public static void d(String msg) {
        d(null, msg);
    }

    public static void v(String tag, String msg) {
        List<String> msgs = split(tag, msg);
        for (String m : msgs) {
            android.util.Log.v(SYSTEM_TAG, m);
        }
    }

    public static void v(String msg) {
        v(null, msg);
    }

    public static void w(String tag, String msg) {
        List<String> msgs = split(tag, msg);
        for (String m : msgs) {
            android.util.Log.w(SYSTEM_TAG, m);
        }
    }

    public static void w(String msg) {
        w(null, msg);
    }

    public static void printEvent(AccessibilityEvent event) {
        int eventType = event.getEventType();
        i("Package Name: " + event.getPackageName());
        i("Source: " + event.getSource());
        i("Source Class: " + event.getClassName());
        i("Event Type(int): " + eventType);

        switch (eventType) {
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:// 通知栏事件
                i("Event Type: TYPE_NOTIFICATION_STATE_CHANGED");
                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED://窗体状态改变
                i("Event Type: TYPE_WINDOW_STATE_CHANGED");
                break;
            case AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED://View获取到焦点
                i("Event Type: TYPE_VIEW_ACCESSIBILITY_FOCUSED");
                break;
            case AccessibilityEvent.TYPE_GESTURE_DETECTION_START:
                i("Event Type: TYPE_VIEW_ACCESSIBILITY_FOCUSED");
                break;
            case AccessibilityEvent.TYPE_GESTURE_DETECTION_END:
                i("Event Type: TYPE_GESTURE_DETECTION_END");
                break;
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                i("Event Type: TYPE_WINDOW_CONTENT_CHANGED");
                break;
            case AccessibilityEvent.TYPE_VIEW_CLICKED:
                i("Event Type: TYPE_VIEW_CLICKED");
                break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED:
                i("Event Type: TYPE_VIEW_TEXT_CHANGED");
                break;
            case AccessibilityEvent.TYPE_VIEW_SCROLLED:
                i("Event Type: TYPE_VIEW_SCROLLED");
                break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED:
                i("Event Type: TYPE_VIEW_TEXT_SELECTION_CHANGED");
                break;
        }

        for (CharSequence txt : event.getText()) {
            i("text:" + txt);
        }
    }

    private static List<String> split(String msg) {
        return split(null, msg);
    }

    private static List<String> split(String tag, String msg) {
        List<String> msgs = new ArrayList<>();
        if (null == msg) {
            return msgs;
        }
        for (int i = 0; i < msg.length(); i += MAX_LENGTH) {
            if (msg.length() <= i + MAX_LENGTH) {
                msgs.add(format(tag, msg.substring(i)));
            } else {
                msgs.add(format(tag, msg.substring(i, i + MAX_LENGTH)));
            }
        }
        return msgs;
    }

    private static String format(String tag, String msg) {
        if (null != tag && tag.length() > 0) {
            return String.format(LOG_FORMAT, tag, msg);
        }
        return msg;
    }
}
