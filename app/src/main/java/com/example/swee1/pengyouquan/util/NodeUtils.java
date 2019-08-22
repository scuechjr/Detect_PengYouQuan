package com.example.swee1.pengyouquan.util;

import android.annotation.TargetApi;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.accessibility.AccessibilityNodeInfo;

import com.example.swee1.pengyouquan.MyAccessibilityService;
import com.example.swee1.pengyouquan.PrintUtils;
import com.example.swee1.pengyouquan.domain.NodeDetail;
import com.example.swee1.pengyouquan.domain.enums.DataTypeEnum;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class NodeUtils {
    public static final long INTERVAL = 400;

    public static NodeDetail joinChildNode(AccessibilityNodeInfo parentNode) {
        return joinChildNode(toNodeDetail(parentNode));
    }

    public static NodeDetail joinChildNode(NodeDetail parentNode) {
        if (null == parentNode) {
            return null;
        }
        List<String> resourceIdList = new ArrayList<>();
        List<String> textList = new ArrayList<>();
        List<String> contentDescList = new ArrayList<>();

        if (null != parentNode.getResourceId()) {
            resourceIdList.add(parentNode.getResourceId());
        }
        if (null != parentNode.getText()) {
            textList.add(parentNode.getText());
        }
        if (null != parentNode.getContentDesc()) {
            contentDescList.add(parentNode.getContentDesc());
        }

        List<NodeDetail> children = parentNode.getChildren();
        if (null != children && children.size() > 0) {
            for (NodeDetail child : children) {
                NodeDetail ret = joinChildNode(child);
                if (null != ret) {
                    if (null != ret.getResourceId()) {
                        resourceIdList.add(ret.getResourceId());
                    }
                    if (null != ret.getText()) {
                        textList.add(ret.getText());
                    }
                    if (null != ret.getContentDesc()) {
                        contentDescList.add(ret.getContentDesc());
                    }
                }
            }
        }

        NodeDetail node = new NodeDetail();
        node.setResourceId(join(resourceIdList));
        node.setText(join(textList));
        node.setContentDesc(join(contentDescList));

        return node;
    }

    private static String join(List<String> list) {
        return join(list, ",");
    }

    private static String join(List<String> list, String s) {
        StringBuilder sb = new StringBuilder();
        for (String str : list) {
            sb.append(str).append(s);
        }
        return sb.length() > 0 ? sb.substring(0, sb.length() - 1) : "";
    }

    public static NodeDetail clearNode(AccessibilityNodeInfo parentNode) {
        return clearNode(toNodeDetail(parentNode));
    }

    public static NodeDetail clearNode(NodeDetail parentNode) {
        if (null == parentNode) {
            return null;
        }
        NodeDetail node = new NodeDetail();
        node.setResourceId(parentNode.getResourceId());
        node.setText(parentNode.getText());
        node.setContentDesc(parentNode.getContentDesc());
        node.setClickable(parentNode.isClickable());

        List<NodeDetail> children = parentNode.getChildren();
        if (null != children && children.size() > 0) {
            List<NodeDetail> list = new ArrayList<>();
            for (NodeDetail child : children) {
                NodeDetail ret = clearNode(child);
                if (null != ret) {
                    list.add(ret);
                }
            }

            if (list.size() > 0) {
                node.setChildren(list);
            }
        }

        return node;
    }

    public static List<NodeDetail> getAllHasTextNodes(AccessibilityNodeInfo parentNode) {
        return getAllHasTextNodes(toNodeDetail(parentNode));
    }

    public static List<NodeDetail> getAllHasTextNodes(NodeDetail parentNode) {
        if (null == parentNode) {
            return new ArrayList<>();
        }

        List<NodeDetail> list = new ArrayList<>();
        if (null != parentNode.getText() || null != parentNode.getContentDesc()) {
            NodeDetail node = new NodeDetail();
            node.setResourceId(parentNode.getResourceId());
            node.setText(parentNode.getText());
            node.setContentDesc(parentNode.getContentDesc());
            list.add(node);
        }

        List<NodeDetail> children = parentNode.getChildren();
        if (null != children && children.size() > 0) {
            for (NodeDetail child : children) {
                List<NodeDetail> sub = getAllHasTextNodes(child);
                if (null != sub) {
                    list.addAll(sub);
                }
            }
        }

        return list;
    }



    /**
     * 向前滚动
     * @param node 待滚动节点
     */
    public static void scrollForward(AccessibilityNodeInfo node) {
        AccessibilityNodeInfo scrollNode = getClickableParentNode(node);
        if (null != scrollNode) {
            scrollNode.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
        } else {
            PrintUtils.log("error", "The node can not scroll forward!");
        }
    }

    /**
     * 获取可点击的父节点
     * @param node 待查询节点
     * @return
     */
    public static AccessibilityNodeInfo getScrollableParentNode(AccessibilityNodeInfo node) {
        if (null == node) {
            return null;
        }

        return node.isScrollable() ? node : getScrollableParentNode(node.getParent());
    }

    /**
     * 点击节点
     * @param node 待点击节点或者待点击节点子节点
     */
    public static void click(AccessibilityNodeInfo node) {
        AccessibilityNodeInfo clickNode = getClickableParentNode(node);
        if (null != clickNode) {
            clickNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        } else {
            PrintUtils.log("error", "The node can not click!");
        }
    }

    /**
     * 获取可点击的父节点
     * @param node 待查询节点
     * @return
     */
    public static AccessibilityNodeInfo getClickableParentNode(AccessibilityNodeInfo node) {
        if (null == node) {
            return null;
        }

        return node.isClickable() ? node : getClickableParentNode(node.getParent());
    }

    /**
     * 根据控件文本，阻塞查询控件
     * @param text 待查询控件文本
     * @param timeout 超时时间，毫秒(ms)
     * @return AccessibilityNodeInfo
     */
    public static AccessibilityNodeInfo blockFindClickNodeByText(String text, @NonNull Integer timeout) {
        long maxCount = timeout / INTERVAL + (timeout % INTERVAL == 0 ? 0 : 1);
        for (int i = 0; i < maxCount; i++) {
            List<AccessibilityNodeInfo> nodes = findNodesByText(text);
            if (null != nodes && nodes.size() > 0) {
                for (AccessibilityNodeInfo node : nodes) {
                    if (node.isClickable()) {
                        return node;
                    }
                }
            }
            sleep(INTERVAL);
        }
        return null;
    }

    /**
     * 根据控件文本，阻塞查询控件
     * @param text 待查询控件文本
     * @param timeout 超时时间，毫秒(ms)
     * @return AccessibilityNodeInfo
     */
    public static AccessibilityNodeInfo blockFindNodeByText(String text, @NonNull Integer timeout) {
        long maxCount = timeout / INTERVAL + (timeout % INTERVAL == 0 ? 0 : 1);
        for (int i = 0; i < maxCount; i++) {
            AccessibilityNodeInfo node = findNodeByText(text);
            if (null != node) {
                return node;
            }
            sleep(INTERVAL);
            PrintUtils.log(text + " block find time " + (i + 1));
        }
        return null;
    }

    /**
     * 根据控件文本，阻塞查询控件
     * @param text 待查询控件文本
     * @param index 命中多个指定取得下标
     * @param timeout 超时时间，毫秒(ms)
     * @return AccessibilityNodeInfo
     */
    public static AccessibilityNodeInfo blockFindNodeByText(String text, int index, @NonNull Integer timeout) {
        long maxCount = timeout / INTERVAL + (timeout % INTERVAL == 0 ? 0 : 1);
        for (int i = 0; i < maxCount; i++) {
            AccessibilityNodeInfo node = findNodeByText(text, index);
            if (null != node) {
                return node;
            }
            sleep(INTERVAL);
        }
        return null;
    }

    /**
     * 根据控件id，阻塞查询控件
     * @param id 待查询控件id
     * @param timeout 超时时间，毫秒(ms)
     * @return AccessibilityNodeInfo
     */
    public static AccessibilityNodeInfo blockClickFindNodeById(String id, @NonNull Integer timeout) {
        long maxCount = timeout / INTERVAL + (timeout % INTERVAL == 0 ? 0 : 1);
        for (int i = 0; i < maxCount; i++) {
            List<AccessibilityNodeInfo> nodes = findNodesById(id);
            if (null != nodes && nodes.size() > 0) {
                for (AccessibilityNodeInfo node : nodes) {
                    if (node.isClickable()) {
                        return node;
                    }
                }
            }
            sleep(INTERVAL);
        }
        return null;
    }

    /**
     * 根据控件id，阻塞查询控件
     * @param id 待查询控件id
     * @param timeout 超时时间，毫秒(ms)
     * @return AccessibilityNodeInfo
     */
    public static AccessibilityNodeInfo blockFindNodeById(String id, @NonNull Integer timeout) {
        long maxCount = timeout / INTERVAL + (timeout % INTERVAL == 0 ? 0 : 1);
        for (int i = 0; i < maxCount; i++) {
            AccessibilityNodeInfo node = findNodeById(id);
            if (null != node) {
                return node;
            }
            sleep(INTERVAL);
        }
        return null;
    }

    /**
     * 根据控件id，阻塞查询控件
     * @param id 待查询控件id
     * @param index 命中多个指定取得下标
     * @param timeout 超时时间，毫秒(ms)
     * @return AccessibilityNodeInfo
     */
    public static AccessibilityNodeInfo blockFindNodeById(String id, int index, @NonNull Integer timeout) {
        long maxCount = timeout / INTERVAL + (timeout % INTERVAL == 0 ? 0 : 1);
        for (int i = 0; i < maxCount; i++) {
            AccessibilityNodeInfo node = findNodeById(id, index);
            if (null != node) {
                return node;
            }
            sleep(INTERVAL);
        }
        return null;
    }

    /**
     * 根据控件文本，阻塞查询控件
     * @param className 待查询控件文本
     * @param timeout 超时时间，毫秒(ms)
     * @return AccessibilityNodeInfo
     */
    public static AccessibilityNodeInfo blockFindClickNodeByClassName(String className, @NonNull Integer timeout) {
        long maxCount = timeout / INTERVAL + (timeout % INTERVAL == 0 ? 0 : 1);
        for (int i = 0; i < maxCount; i++) {
            List<AccessibilityNodeInfo> nodes = findNodesByClassName(className);
            if (null != nodes && nodes.size() > 0) {
                for (AccessibilityNodeInfo node : nodes) {
                    if (node.isClickable()) {
                        return node;
                    }
                }
            }
            sleep(INTERVAL);
        }
        return null;
    }

    /**
     * 根据控件文本，阻塞查询控件
     * @param className 待查询控件文本
     * @param timeout 超时时间，毫秒(ms)
     * @return AccessibilityNodeInfo
     */
    public static AccessibilityNodeInfo blockFindNodeByClassName(String className, @NonNull Integer timeout) {
        long maxCount = timeout / INTERVAL + (timeout % INTERVAL == 0 ? 0 : 1);
        for (int i = 0; i < maxCount; i++) {
            AccessibilityNodeInfo node = findNodeByClassName(className);
            if (null != node) {
                return node;
            }
            sleep(INTERVAL);
            PrintUtils.log(i + "");
        }
        return null;
    }

    /**
     * 根据控件文本，阻塞查询控件
     * @param className 待查询控件文本
     * @param index 命中多个指定取得下标
     * @param timeout 超时时间，毫秒(ms)
     * @return AccessibilityNodeInfo
     */
    public static AccessibilityNodeInfo blockFindNodeByClassName(String className, int index, @NonNull Integer timeout) {
        long maxCount = timeout / INTERVAL + (timeout % INTERVAL == 0 ? 0 : 1);
        for (int i = 0; i < maxCount; i++) {
            AccessibilityNodeInfo node = findNodeByClassName(className, index);
            if (null != node) {
                return node;
            }
            sleep(INTERVAL);
        }
        return null;
    }

    /**
     * 获取当前页面根节点
     * @return 跟节点 AccessibilityNodeInfo
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static AccessibilityNodeInfo getRoot() {
        return MyAccessibilityService.getInstance().getRootInActiveWindow();
    }

    /**
     * 根据控件文本，查询控件
     * @param text 待查询控件文本
     * @return
     */
    public static AccessibilityNodeInfo findNodeByText(String text) {
        return findNodeByText(text, 0);
    }

    /**
     * 根据控件文本，查询控件
     * @param text 待查询控件文本
     * @return
     */
    public static AccessibilityNodeInfo findNodeByText(String text, int index) {
        List<AccessibilityNodeInfo> list = findNodesByText(text);
        if (null != list && list.size() > index) {
            return list.get(index);
        }
        return null;
    }

    /**
     * 根据控件文本，查询控件
     * @param text 待查询控件文本
     * @return
     */
    public static AccessibilityNodeInfo findNodeByText(AccessibilityNodeInfo parentNode, String text) {
        return findNodeByText(parentNode, text, 0);
    }

    /**
     * 根据控件文本，查询控件
     * @param text 待查询控件文本
     * @return
     */
    public static AccessibilityNodeInfo findNodeByText(AccessibilityNodeInfo parentNode, String text, int index) {
        List<AccessibilityNodeInfo> list = findNodesByText(parentNode, text);
        if (null != list && list.size() > index) {
            return list.get(index);
        }
        return null;
    }

    /**
     * 根据控件文本，查询控件
     * @param text 待查询控件文本
     * @return
     */
    public static List<AccessibilityNodeInfo> findNodesByText(String text) {
        return findNodesByText(getRoot(), text);
    }

    /**
     * 根据控件文本，查询控件
     * @param parentNode 父节点
     * @param text 控件text
     * @return
     */
    public static List<AccessibilityNodeInfo> findNodesByText(AccessibilityNodeInfo parentNode, String text) {
        return commonFindNodes(parentNode, text, DataTypeEnum.TEXT);
    }

    /**
     * 根据控件文本，查询控件
     * @param id 待查询控件id
     * @return
     */
    public static AccessibilityNodeInfo findNodeById(String id) {
        return findNodeById(id, 0);
    }

    /**
     * 根据控件文本，查询控件
     * @param id 待查询控件id
     * @return
     */
    public static AccessibilityNodeInfo findNodeById(String id, int index) {
        List<AccessibilityNodeInfo> list = findNodesById(id);
        if (null != list && list.size() > index) {
            return list.get(index);
        }
        return null;
    }

    /**
     * 根据控件文本，查询控件
     * @param id 待查询控件id
     * @return
     */
    public static AccessibilityNodeInfo findNodeById(AccessibilityNodeInfo parentNode, String id) {
        return findNodeById(parentNode, id, 0);
    }

    /**
     * 根据控件文本，查询控件
     * @param id 待查询控件id
     * @return
     */
    public static AccessibilityNodeInfo findNodeById(AccessibilityNodeInfo parentNode, String id, int index) {
        List<AccessibilityNodeInfo> list = findNodesById(parentNode, id);
        if (null != list && list.size() > index) {
            return list.get(index);
        }
        return null;
    }

    /**
     * 根据控件文本，查询控件
     * @param id 待查询控件id
     * @return
     */
    public static List<AccessibilityNodeInfo> findNodesById(String id) {
        return findNodesById(getRoot(), id);
    }

    /**
     * 根据控件文本，查询控件
     * @param parentNode 父节点
     * @param id 控件id
     * @return
     */
    public static List<AccessibilityNodeInfo> findNodesById(AccessibilityNodeInfo parentNode, String id) {
        return commonFindNodes(parentNode, id, DataTypeEnum.ID);
    }

    /**
     * 根据控件文本，查询控件
     * @param className 待查询控件className
     * @return
     */
    public static AccessibilityNodeInfo findNodeByClassName(String className) {
        return findNodeByClassName(className, 0);
    }

    /**
     * 根据控件文本，查询控件
     * @param className 待查询控件className
     * @return
     */
    public static AccessibilityNodeInfo findNodeByClassName(String className, int index) {
        List<AccessibilityNodeInfo> list = findNodesByClassName(className);
        if (null != list && list.size() > index) {
            return list.get(index);
        }
        return null;
    }

    /**
     * 根据控件文本，查询控件
     * @param className 待查询控件className
     * @return
     */
    public static AccessibilityNodeInfo findNodeByClassName(AccessibilityNodeInfo parentNode, String className) {
        return findNodeByClassName(parentNode, className, 0);
    }

    /**
     * 根据控件文本，查询控件
     * @param className 待查询控件className
     * @return
     */
    public static AccessibilityNodeInfo findNodeByClassName(AccessibilityNodeInfo parentNode, String className, int index) {
        List<AccessibilityNodeInfo> list = findNodesByClassName(parentNode, className);
        if (null != list && list.size() > index) {
            return list.get(index);
        }
        return null;
    }

    /**
     * 根据控件文本，查询控件
     * @param className 待查询控件className
     * @return
     */
    public static List<AccessibilityNodeInfo> findNodesByClassName(String className) {
        return findNodesByClassName(getRoot(), className);
    }

    /**
     * 根据控件文本，查询控件
     * @param parentNode 父节点
     * @param className 控件className
     * @return
     */
    public static List<AccessibilityNodeInfo> findNodesByClassName(AccessibilityNodeInfo parentNode, String className) {
        return commonFindNodes(parentNode, className, DataTypeEnum.CLASS);
    }

    /**
     * 根据控件id/text，查询控件
     * @param parentNode 父节点
     * @param value 控件id/text
     * @return
     */
    public static List<AccessibilityNodeInfo> commonFindNodes(AccessibilityNodeInfo parentNode, String value, DataTypeEnum type) {
        if (null == value || null == parentNode) {
            return new ArrayList<>();
        }
        NodeDetail nodeDetail = toNodeDetail(parentNode);
        return commonFindNodes(nodeDetail, value, type);
    }

    /**
     * 根据控件id/text，查询控件
     * @param parentNode 父节点
     * @param value 控件id/text...
     * @return
     */
    public static List<AccessibilityNodeInfo> commonFindNodes(NodeDetail parentNode, String value, DataTypeEnum type) {
        List<AccessibilityNodeInfo> list = new ArrayList<>();
        if (null == value || null == parentNode) {
            return list;
        }

        if (null != parentNode) {
            boolean isMatch = match(parentNode, value, type);
            if (isMatch) {
                list.add(parentNode.getOrgin());
            }
            List<NodeDetail> children = parentNode.getChildren();
            if (null != children) {
                for (NodeDetail item : children) {
                    List<AccessibilityNodeInfo> sub = commonFindNodes(item, value, type);
                    if (null != sub && sub.size() > 0) {
                        list.addAll(sub);
                    }
                }
            }
        }

        return list;
    }

    private static boolean match(NodeDetail node, String value, DataTypeEnum type) {
        if (null != value && null != node) {
            if (DataTypeEnum.ID.equals(type)) {
                return value.equals(node.getResourceId());
            } else if (DataTypeEnum.TEXT.equals(type)) {
                // TODO 临时匹配方式
                return value.equals(node.getText()) || value.equals(node.getContentDesc());
            } else if (DataTypeEnum.CONTENT.equals(type)) {
                return value.equals(node.getContentDesc());
            } else if (DataTypeEnum.CLASS.equals(type)) {
                return value.equals(node.getClassName());
            } else if (DataTypeEnum.PACKAGE.equals(type)) {
                return value.equals(node.getPackageName());
            }
        }
        return false;
    }

    public static NodeDetail toNodeDetail(AccessibilityNodeInfo nodeInfo) {
        return toNodeDetail(nodeInfo, 0);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static NodeDetail toNodeDetail(AccessibilityNodeInfo nodeInfo, int index) {
        if (null == nodeInfo) {
            return null;
        }
        int count = nodeInfo.getChildCount();
        List<NodeDetail> children = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            NodeDetail child = toNodeDetail(nodeInfo.getChild(i), i);
            if (null != child) {
                children.add(child);
            }
        }

        NodeDetail node = new NodeDetail();
        node.setIndex(index);
        if (null != nodeInfo.getText()) {
            node.setText(nodeInfo.getText() + "");

        }
        node.setResourceId(nodeInfo.getViewIdResourceName());
        if (null != nodeInfo.getClassName()) {
            node.setClassName(nodeInfo.getClassName() + "");
        }
        if (null != nodeInfo.getPackageName()) {
            node.setPackageName(nodeInfo.getPackageName() + "");
        }
        if (null != nodeInfo.getContentDescription()) {
            node.setContentDesc(nodeInfo.getContentDescription() + "");
        }
        node.setCheckable(nodeInfo.isCheckable());
        node.setChecked(nodeInfo.isChecked());
        node.setClickable(nodeInfo.isClickable());
        node.setEnabled(nodeInfo.isEnabled());
        node.setFocusable(nodeInfo.isFocusable());
        node.setScrollable(nodeInfo.isScrollable());
        node.setLongClickable(nodeInfo.isLongClickable());
        node.setPassword(nodeInfo.isPassword());
        node.setSelected(nodeInfo.isSelected());
        Rect boundsInParent = new Rect();
        nodeInfo.getBoundsInParent(boundsInParent);
        node.setBoundsInParent(boundsInParent);
        Rect boundsInScreen = new Rect();
        nodeInfo.getBoundsInScreen(boundsInScreen);
        node.setBoundsInScreen(boundsInScreen);
        node.setChildren(children);
        node.setOrgin(nodeInfo);

        return node;
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {}
    }

    public static String getStackTraceInfo(Exception e) {
        StringWriter sw = null;
        PrintWriter pw = null;
        try {
            sw = new StringWriter();
            pw = new PrintWriter(sw);
            e.printStackTrace(pw); // 将出错的栈信息输出到printWriter中
            pw.flush();
            sw.flush();
            return sw.toString();
        } catch (Exception ex) {
            return "发生错误";
        } finally {
            if (sw != null) {
                try {
                    sw.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (pw != null) {
                pw.close();
            }
        }
    }
}
