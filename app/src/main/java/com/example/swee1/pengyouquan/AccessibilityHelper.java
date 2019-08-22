package com.example.swee1.pengyouquan;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

public class AccessibilityHelper {
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static AccessibilityNodeInfo getRoot() {
        return MyAccessibilityServiceV2.getInstance().getRootInActiveWindow();
    }

    /**
     * 根据控件文本，查询控件
     * @param text 待查询控件文本
     * @return
     */
    public static AccessibilityNodeInfo findNodeByText(String text) {
        return findNodeByText(text, getRoot(), 0);
    }

    /**
     * 根据控件文本，查询控件
     * @param text 待查询控件文本
     * @param parentNode 父节点
     * @return
     */
    public static AccessibilityNodeInfo findNodeByText(String text, AccessibilityNodeInfo parentNode) {
        return findNodeByText(text, parentNode, 0);
    }

    /**
     * 根据控件文本，查询控件
     * @param text 待查询控件文本
     * @param parentNode 父节点
     * @param needNumber 指定提取的控件
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static AccessibilityNodeInfo findNodeByText(String text, AccessibilityNodeInfo parentNode, int needNumber) {
        if (null != parentNode) {
            List<AccessibilityNodeInfo> list = parentNode.findAccessibilityNodeInfosByText(text);
            PrintUtils.log("notice", "find " + list.size() + " nodes which text is '" + text + "'");
            if (list.size() > 0) {
                return list.get(needNumber);
            } else {
                int count = parentNode.getChildCount();
                for (int i = 0; i < count; i++) {
                    AccessibilityNodeInfo node = findNodeByText(text, parentNode.getChild(i));
                    if (null != node) {
                        return node;
                    }
                }
            }
        }
        PrintUtils.log("notice", "can not find a node which text is '" + text + "'");
        return null;
    }

    public static AccessibilityNodeInfo findNodeById(String viewId) {
        return findNodeById(viewId, getRoot(), 0);
    }

    public static AccessibilityNodeInfo findNodeById(String viewId, AccessibilityNodeInfo parentNode) {
        return findNodeById(viewId, parentNode, 0);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static AccessibilityNodeInfo findNodeById(String viewId, AccessibilityNodeInfo parentNode, int needNumber) {
        if (null != parentNode) {
            List<AccessibilityNodeInfo> list = parentNode.findAccessibilityNodeInfosByViewId(viewId);
//            PrintUtils.log("notice", "find " + list.size() + " nodes which viewId is '" + viewId + "'");
            if (list.size() > needNumber) {
                return list.get(needNumber);
            } else {
                PrintUtils.log(parentNode);
                int count = parentNode.getChildCount();
                for (int i = 0; i < count; i++) {
                    AccessibilityNodeInfo node = findNodeById(viewId, parentNode.getChild(i));
                    if (null != node) {
                        return node;
                    }
                }
            }
        }
//        PrintUtils.log("notice", "can not find a node which viewId is '" + viewId + "'");
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static AccessibilityNodeInfo findNodeByParentClick(String text) {
        return findNodeByParentClick(text, null, true);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static AccessibilityNodeInfo findNodeByIdParentClick(String id) {
        return findNodeByParentClick(id, null, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static AccessibilityNodeInfo findNodeByParentClick(String text, AccessibilityNodeInfo root, boolean isByText) {
        AccessibilityNodeInfo node = isByText ? findNodeByText(text, root) : findNodeById(text, root);
        if (node == null) {
            return null;
        }
        AccessibilityNodeInfo parent = node;
        while (parent != null && !parent.isClickable()) {
            parent = parent.getParent();
        }

        if (parent != null && parent.isClickable()) {
            return parent;
        }
        return null;
    }

    private static final long INTERVAL = 300;
    private static final long MAX_INTERVAL_COUNT = 30;
    public static final void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {
            PrintUtils.log("sleep error");
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static AccessibilityNodeInfo blockFindNodeById(String viewId) {
        for (int i = 0; i < MAX_INTERVAL_COUNT; i++) {
            AccessibilityNodeInfo node = findNodeById(viewId);
            if (null != node) {
                return node;
            }
            sleep(INTERVAL);
        }
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static AccessibilityNodeInfo blockFindNodeByParentClick(String text, AccessibilityNodeInfo root, boolean isByText) {
        for (int i = 0; i < MAX_INTERVAL_COUNT; i++) {
            AccessibilityNodeInfo node = findNodeByParentClick(text, root, isByText);
            if (null != node) {
                return node;
            }
            sleep(INTERVAL);
        }
        return null;
    }

    public static AccessibilityNodeInfo blockFindNodeById(String viewId, AccessibilityNodeInfo parentNode) {
        for (int i = 0; i < MAX_INTERVAL_COUNT; i++) {
            AccessibilityNodeInfo node = findNodeById(viewId, parentNode);
            if (null != node) {
                return node;
            }
            sleep(INTERVAL);
        }
        return null;
    }
}
