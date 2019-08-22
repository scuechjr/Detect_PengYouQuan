package com.example.swee1.pengyouquan;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

/**
 * find node tools
 */
public class NodeUtils {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static AccessibilityNodeInfo findNodeInfosByClassName(String className) {
        return findNodeInfosByClassName(null, className);
    }

    /**
     * find node by classname
     *
     * @param nodeInfo  AccessibilityNodeInfo
     * @param className classname
     * @return node
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static AccessibilityNodeInfo findNodeInfosByClassName(AccessibilityNodeInfo nodeInfo, String className) {
        if (nodeInfo == null) {
            nodeInfo = MyAccessibilityService.getInstance().getRootInActiveWindow();
            if (nodeInfo == null) {
                return null;
            }
        }
        int size = nodeInfo.getChildCount();
        for (int i = 0; i < size; i++) {
            AccessibilityNodeInfo childNode = nodeInfo.getChild(i);
            if (childNode != null) {
                String childClassName = (childNode.getClassName() != null) ? childNode.getClassName().toString() : "";
                if (childClassName.equals(className)) {
                    return childNode;
                }
                AccessibilityNodeInfo grandNode = findNodeInfosByClassName(childNode, className);
                if (grandNode != null) {
                    return grandNode;
                }
                recycel(childNode);
            }
        }
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static AccessibilityNodeInfo findNodeInfosByText(String text) {
        return findNodeInfosByText(null, text);
    }

    /**
     * 查找到含应用字段的Node列表后，根据相应字段作排除，只适应节点字段相等的匹配情况。
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static AccessibilityNodeInfo findNodeInfosByText(AccessibilityNodeInfo root, String text) {
        if (root == null) {
            root = MyAccessibilityService.getInstance().getRootInActiveWindow();
            if (root == null) {
                return null;
            }
        }
        List<AccessibilityNodeInfo> list = root.findAccessibilityNodeInfosByText(text);
        if (list == null || list.isEmpty()) {
            return null;
        }
        AccessibilityNodeInfo resultNode = null;
        String nodeText;
        String nodeDesc;
        for (AccessibilityNodeInfo node : list) {
            nodeText = node.getText() + "";
            nodeDesc = node.getContentDescription() + "";
            if (nodeText == null && nodeDesc == null) {
                continue;
            }
            if (nodeText != null && (nodeText.equals("[" + text + "]") || nodeText.equals(text))) {
                resultNode = node;
                break;
            }
            if (nodeDesc != null && nodeDesc.equals(text)) {
                resultNode = node;
                break;
            }
        }
        for (AccessibilityNodeInfo node : list) {
            if (!node.equals(resultNode)) {
                recycel(node);
            }
        }
        return resultNode;
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static AccessibilityNodeInfo findNodeByParentClick(String text) {
        return findNodeByParentClick(null, text);
    }

    /**
     * find the parent node that can be clicked
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static AccessibilityNodeInfo findNodeByParentClick(AccessibilityNodeInfo root, String text) {
        AccessibilityNodeInfo node = findNodeInfosByText(root, text);
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

    private static void recycel(AccessibilityNodeInfo node) {
        try {
            if (node != null) {
                node.recycle();
            }
        } catch (IllegalStateException e) {
            Log.e("e", e.getMessage());
        }
    }
}
