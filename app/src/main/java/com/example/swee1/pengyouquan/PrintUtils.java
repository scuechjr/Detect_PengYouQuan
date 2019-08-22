package com.example.swee1.pengyouquan;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.example.swee1.pengyouquan.util.NodeUtils;

/**
 * Created by _SOLID
 * Date:2016/7/21
 * Time:11:14
 */
public class PrintUtils {
    private static final int MAX_LENGTH = 3000;
    private static final String PREFIX = "demolog - ";

    public static void log(CharSequence log) {
        log("notice", log + "");
    }

    public static void buslog(CharSequence log) {
        Log.i("buslog", log + "");
    }

    public static void log(CharSequence tag, CharSequence log) {
        String str = log + "";
        int index = 0;
        String sub;
        while (index < log.length()) {
            if (str.length() <= index + MAX_LENGTH) {
                sub = str.substring(index);
            } else {
                sub = str.substring(index, index + MAX_LENGTH);
            }

            index += MAX_LENGTH;
            Log.i(PREFIX + tag, sub);
        }
    }

    public static void printEvent(AccessibilityEvent event) {
        log("demolog: -------------------------------------------------------------");
        int eventType = event.getEventType();
        log("demolog: packageName:" + event.getPackageName() + "");
        log("demolog: source:" + event.getSource() + "");
        log("demolog: source class:" + event.getClassName() + "");
        log("demolog: event type(int):" + eventType + "");

        switch (eventType) {
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:// 通知栏事件
                log("demolog: event type:TYPE_NOTIFICATION_STATE_CHANGED");
                break;
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED://窗体状态改变
                log("demolog: event type:TYPE_WINDOW_STATE_CHANGED");
                break;
            case AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED://View获取到焦点
                log("demolog: event type:TYPE_VIEW_ACCESSIBILITY_FOCUSED");
                break;
            case AccessibilityEvent.TYPE_GESTURE_DETECTION_START:
                log("demolog: event type:TYPE_VIEW_ACCESSIBILITY_FOCUSED");
                break;
            case AccessibilityEvent.TYPE_GESTURE_DETECTION_END:
                log("demolog: event type:TYPE_GESTURE_DETECTION_END");
                break;
            case AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED:
                log("demolog: event type:TYPE_WINDOW_CONTENT_CHANGED");
                break;
            case AccessibilityEvent.TYPE_VIEW_CLICKED:
                log("demolog: event type:TYPE_VIEW_CLICKED");
                break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED:
                log("demolog: event type:TYPE_VIEW_TEXT_CHANGED");
                break;
            case AccessibilityEvent.TYPE_VIEW_SCROLLED:
                log("demolog: event type:TYPE_VIEW_SCROLLED");
                break;
            case AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED:
                log("demolog: event type:TYPE_VIEW_TEXT_SELECTION_CHANGED");
                break;
        }

        for (CharSequence txt : event.getText()) {
            log("text:" + txt);
        }

        log("demolog: -------------------------------------------------------------");
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static void log(AccessibilityNodeInfo nodeInfo) {
        if (null != nodeInfo) {
            log("node packageName: " + nodeInfo.getPackageName() + "");
            log("node: " + nodeInfo);
            log("node class: " + nodeInfo.getClassName() + "");

//            for (int i = 0; i < nodeInfo.getChildCount(); i++) {
//                String prefix = "child " + (i + 1) + ": ";
//                AccessibilityNodeInfo child = nodeInfo.getChild(i);
//                PrintUtils.log(prefix + " id = " + child.getViewIdResourceName() + " - text: " + nodeInfo.getText() + "");
//                log(child);
//            }
        } else {
            log("node is null!");
        }
    }
}
