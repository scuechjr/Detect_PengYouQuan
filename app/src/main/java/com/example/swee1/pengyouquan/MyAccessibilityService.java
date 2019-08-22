package com.example.swee1.pengyouquan;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.example.swee1.pengyouquan.MainActivity.saveExistingData;

/**
 * Created by swee1 on 2017/5/11.
 */

public class MyAccessibilityService extends AccessibilityService {
    private static MyAccessibilityService instance;
    private boolean mutex = false;
    private myDB helper;
    private int jumpTime = 700;
    private String tongXunLuId = "com.tencent.mm:id/bwj"; // clickable child
    private String listViewId = "com.tencent.mm:id/nn";  // ListView
    private String scanOverId = "com.tencent.mm:id/agp";  // xxx位联系人，over scan
    private String backButtonId = "com.tencent.mm:id/lb"; // clickable
    private String friendNicknameId = "android:id/text1"; // in detail page nickname
    private String dayLimitId = "com.tencent.mm:id/cuw";  // TextView
    private String shieldId = "com.tencent.mm:id/ak6";  // LinearLayout
    private String pyqContentListId = "com.tencent.mm:id/epj";  // LinearLayout
//    private String penYouQuanPageFlagId = "com.tencent.mm:id/eqc";  // LinearLayout
    private String friendNickNameId = "com.tencent.mm:id/ol"; // 通讯录，朋友昵称
    private String grxqPageId = "android:id/list"; // 个人详情页ID
    private String grxqMorePageId = "com.tencent.mm:id/l0"; // 个人详情页ID
    private String grxqTouXiangId = "com.tencent.mm:id/b7q"; // 个人详情头像ID
    private String grxqXiangCeLieBiaoId = "com.tencent.mm:id/ded"; // 个人详情相册列表ID
    private String pyqTouXiangId = "com.tencent.mm:id/rb"; // 朋友圈头像ID

    @Override
    protected void onServiceConnected() {
        //当启动服务的时候就会被调用
        super.onServiceConnected();
        PrintUtils.log("start", "onServiceConnected");
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        String className = event.getClassName().toString();
        PrintUtils.log("==========>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        PrintUtils.log("className", className);
        PrintUtils.printEvent(event);
        if (className.equals("com.tencent.mm.ui.LauncherUI") && !mutex) {
            mutex = true;
            PrintUtils.log("yeah", "进入wx");
            AccessibilityNodeInfo nodeInfo = AccessibilityHelper.findNodeByText("通讯录", event.getSource());
            if (null != nodeInfo) {
                PrintUtils.log("yeah", "点击通讯录");
                nodeInfo.getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
                AccessibilityNodeInfo rootNodeInfo = getRootInActiveWindow();
                if (rootNodeInfo != null) {
                    try {
                        if (saveExistingData == false) {
                            helper = new myDB(this);
                            helper.delete();
                        }
                        Thread.sleep(jumpTime);
                        PrintUtils.log("开始测试...");
                        test(rootNodeInfo);
                        PrintUtils.log("测试完成");
                    } catch (Exception e) {
                        PrintUtils.log("error");
                        e.printStackTrace();
                    }
                } else {
                    PrintUtils.log("root node is null!");
                }
            }
        }
        mutex = false;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void pressBack() throws InterruptedException {
        Thread.sleep(jumpTime);
        AccessibilityNodeInfo rootNodeInfo = getRootInActiveWindow();
        PrintUtils.log("点击返回");
        clickNodeByID(rootNodeInfo, backButtonId);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void clickNodeByID(AccessibilityNodeInfo rootNodeInfo, String NodeId) {
        if (rootNodeInfo != null && rootNodeInfo.findAccessibilityNodeInfosByViewId(NodeId).size() > 0) {
            AccessibilityNodeInfo clickNode = rootNodeInfo.findAccessibilityNodeInfosByViewId(NodeId).get(0);
            clickNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private boolean checkPictures() throws InterruptedException {
        AccessibilityNodeInfo rootNodeInfo = getRootInActiveWindow();
        PrintUtils.log("验证朋友圈是否屏蔽...");
        AccessibilityNodeInfo conentListNode = AccessibilityHelper.findNodeById(pyqContentListId,  rootNodeInfo);
//        if (rootNodeInfo.findAccessibilityNodeInfosByViewId(shieldId).size() > 0) {
        if (null != conentListNode && conentListNode.getChildCount() < 3) {
            String friendID = rootNodeInfo.findAccessibilityNodeInfosByViewId(friendNicknameId).get(0).getText().toString();
            String description = "疑似屏蔽了你";
            helper = new myDB(this);
            if (!helper.ifHasData(friendID)) {
                helper.insert2DB(helper.numOfData() + 1, friendID, description);
            }
            return true;
        } else if (rootNodeInfo.findAccessibilityNodeInfosByViewId(dayLimitId).size() > 0) {
            String friendID = rootNodeInfo.findAccessibilityNodeInfosByViewId(friendNicknameId).get(0).getText().toString();
            String description = rootNodeInfo.findAccessibilityNodeInfosByViewId(dayLimitId).get(0).getText().toString();
            helper = new myDB(this);
            if (!helper.ifHasData(friendID)) {
                helper.insert2DB(helper.numOfData() + 1, friendID, description);
            }
        } else {
            PrintUtils.log("未被好友屏蔽");
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void jumpToDetail() throws InterruptedException {
        boolean isNotFriend = check();
        if (null != AccessibilityHelper.findNodeById(pyqTouXiangId)) {
            PrintUtils.log("退出朋友圈");
            pressBack();
        } else {
            PrintUtils.log("未进入朋友圈页面");
        }
        if (isNotFriend) {
            Thread.sleep(jumpTime * 7);
            PrintUtils.log("执行删除账号操作");
            deleteFriend();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private boolean check() throws InterruptedException {
        PrintUtils.log("获取个人相册信息");
        // 获取个人详情页
        AccessibilityNodeInfo grxqPageNodeInfo = AccessibilityHelper.findNodeById(grxqPageId);
        AccessibilityNodeInfo grxqPictureListNodeInfo = AccessibilityHelper.findNodeByText("个人相册");
        AccessibilityNodeInfo pictureNode = AccessibilityHelper.findNodeByParentClick("朋友圈", grxqPictureListNodeInfo, true);
        if (null != pictureNode) {
            pictureNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            PrintUtils.log("点击相册进入朋友圈");
            Thread.sleep(jumpTime * 2);
            return checkPictures();
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void deleteFriend() throws InterruptedException {
        PrintUtils.log("打开更多操作项");
//        AccessibilityNodeInfo moreNode = AccessibilityHelper.findNodeByIdParentClick(grxqMorePageId);
        AccessibilityNodeInfo moreNode = NodeUtils.findNodeInfosByClassName("android.widget.ImageButton");
        PrintUtils.log(String.valueOf(moreNode));
        if (null != moreNode) {
            moreNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            PrintUtils.log("进入更多操作项");
//            Thread.sleep(jumpTime);
            AccessibilityNodeInfo moreBtListNode = AccessibilityHelper.blockFindNodeById("com.tencent.mm:id/ddf");
            if (null != moreBtListNode) {
                // 向下滚动
                PrintUtils.log("向下滚动查找删除按钮");
                moreBtListNode.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
//                Thread.sleep(jumpTime * 10);
                AccessibilityNodeInfo btnListNode = AccessibilityHelper.blockFindNodeById("com.tencent.mm:id/ddi");
                AccessibilityNodeInfo delBtnNode = AccessibilityHelper.blockFindNodeByParentClick("删除", btnListNode, true);
                if (null != delBtnNode) {
                    PrintUtils.log("点击删除按钮");
                    delBtnNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
//                    Thread.sleep(jumpTime * 10);
                    AccessibilityNodeInfo confirmBtnListNode = AccessibilityHelper.blockFindNodeById("com.tencent.mm:id/ddb");
                    AccessibilityNodeInfo delConfirmBtnNode = AccessibilityHelper.blockFindNodeByParentClick("删除", confirmBtnListNode, true);
                    if (null != delConfirmBtnNode) {
                        PrintUtils.log("点击确认删除按钮");
                        delConfirmBtnNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);

                        for (int i = 0; i < 100 && null != delConfirmBtnNode; i++) {
                            AccessibilityHelper.sleep(100);
                            delConfirmBtnNode = AccessibilityHelper.findNodeByParentClick("删除", confirmBtnListNode, true);
                        }
                    }
                } else {
                    PrintUtils.log(AccessibilityHelper.getRoot());
                }
            }
        } else {
            PrintUtils.log("=================<<<<<<<<<<<<<<<<<<");
            PrintUtils.log(AccessibilityHelper.getRoot());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void test(AccessibilityNodeInfo rootNodeInfo) throws InterruptedException {
        AccessibilityNodeInfo listView = AccessibilityHelper.findNodeById(listViewId, rootNodeInfo);
        int len = listView.getChildCount();
        List<AccessibilityNodeInfo> friendList = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            friendList.add(listView.getChild(i));
        }
        for (int i = 0; i < friendList.size(); i++) {
            AccessibilityNodeInfo clickNode = friendList.get(i);
            if (null != clickNode) {
                AccessibilityNodeInfo nickNameNode = AccessibilityHelper.findNodeById(friendNickNameId, clickNode);
                if (null == nickNameNode) {
                    continue;
                }
                PrintUtils.log("点击进入账号详情: " + nickNameNode.getText());
                clickNode.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                Thread.sleep(jumpTime);
                jumpToDetail();
                Thread.sleep(jumpTime);
            }

            if (null != AccessibilityHelper.findNodeByText("发消息")) {
                PrintUtils.log("退出账号详情");
                pressBack();
                Thread.sleep(jumpTime*3);
            } else {
                PrintUtils.log("未进入账号详情");
            }
        }

        listView.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
        PrintUtils.log("滚动通讯录");
        if (rootNodeInfo.findAccessibilityNodeInfosByViewId(scanOverId).size() > 0) {
            Toast.makeText(this, "扫描完毕，请关闭服务后到主界面查看结果", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);//跳转到辅助功能设置页
            startActivity(intent);
            return;
        }
        test(rootNodeInfo);
    }

    public static MyAccessibilityService getInstance() {
        return MyAccessibilityService.instance;
    }

    private static void setInstance(MyAccessibilityService instance) {
        MyAccessibilityService.instance = instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setInstance(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        setInstance(null);
    }

    @Override
    public void onInterrupt() {

    }
}
