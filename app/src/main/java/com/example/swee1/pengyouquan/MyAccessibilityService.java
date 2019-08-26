package com.example.swee1.pengyouquan;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.alibaba.fastjson.JSON;
import com.example.swee1.pengyouquan.dao.PengYouQuanDao;
import com.example.swee1.pengyouquan.domain.Contact;
import com.example.swee1.pengyouquan.domain.Job;
import com.example.swee1.pengyouquan.domain.JobContext;
import com.example.swee1.pengyouquan.domain.JobParams;
import com.example.swee1.pengyouquan.domain.NodeDetail;
import com.example.swee1.pengyouquan.domain.enums.JobTypeEnum;
import com.example.swee1.pengyouquan.util.JobUtils;
import com.example.swee1.pengyouquan.util.Log;
import com.example.swee1.pengyouquan.util.NodeUtils;
import com.example.swee1.pengyouquan.util.SystemUtils;
import com.example.swee1.pengyouquan.util.WebChatUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by swee1 on 2017/5/11.
 */

public class MyAccessibilityService extends AccessibilityService {
    private static MyAccessibilityService instance;
    private String listViewId = "com.tencent.mm:id/nn";  // ListView
    private String ContactNickNameId = "com.tencent.mm:id/ol"; // 通讯录，朋友昵称
    private JobContext context = new JobContext();
    private static final ExecutorService executorService = Executors.newFixedThreadPool(2);

    @Override
    protected void onServiceConnected() {
        //当启动服务的时候就会被调用
        super.onServiceConnected();
        Log.i("start", "onServiceConnected2");
        PengYouQuanDao.initDatabase(this);
        getJob();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onAccessibilityEvent(final AccessibilityEvent event) {
        String className = event.getClassName().toString();
        if (className.equals("com.tencent.mm.ui.LauncherUI") && context.isRun() && !context.isRunning()) {
            context.setRunning(true);
            if (null != context.getLastVisitMarkName() && context.getLastVisitMarkName().length() > 0) {
                context.setFirstCatLastVisitMarkName(false);
            } else {
                context.setFirstCatLastVisitMarkName(true);
            }
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    Log.i("[onAccessibilityEvent] main", "开始处理");
                    AccessibilityNodeInfo node = NodeUtils.findNodeByText("通讯录");
                    if (null != node) {
                        NodeUtils.click(node);
                        try {
                            for (; ; ) {
                                boolean next = visitTongXunLu();
                                if (!next) {
                                    break;
                                }
                            }
                        } catch (Exception e) {
                            Log.i("[onAccessibilityEvent] main", "异常退出，msg: " + SystemUtils.getStackTraceInfo(e));
                        }
                    } else {
                        Log.i("[onAccessibilityEvent] error", "未获取通讯录节点");
                    }
                    Log.i("[onAccessibilityEvent] main", "处理完成");
                }
            };
            executorService.execute(runnable);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private boolean visitTongXunLu() {
        if (!context.isRun()) {
            return false;
        }

        AccessibilityNodeInfo list = NodeUtils.blockFindNodeById(listViewId, 100000);
        int len = list.getChildCount();
        List<AccessibilityNodeInfo> ContactList = new ArrayList<>();

        for (int i = 0; i < len && context.isRun(); i++) {
            ContactList.add(list.getChild(i));
//            Toast.makeText(this, "扫描中...", Toast.LENGTH_LONG).show();
        }
        for (int i = 0; i < ContactList.size() && context.isRun(); i++) {
            AccessibilityNodeInfo clickNode = ContactList.get(i);
            if (null != clickNode) {
                AccessibilityNodeInfo markNameNode = NodeUtils.findNodeById(clickNode, ContactNickNameId);
                if (null == markNameNode) {
                    Log.i("[visitTongXunLu] pass node", NodeUtils.joinChildNode(clickNode).getText());
                    continue;
                }
                String markName = NodeUtils.joinChildNode(clickNode).getText();
                if (context.isFirstCatLastVisitMarkName() || (null != markName && markName.indexOf(context.getLastVisitMarkName()) == 0)) {
                    context.setFirstCatLastVisitMarkName(true);
                    Log.i("[visitTongXunLu] 点击账号节点: " + markName);
                    NodeUtils.click(clickNode);
                    Contact Contact = visitXiangQing();
                    Contact.setMarkName(markName);
                    JobUtils.saveContact(Contact);
                }
            }
        }

        if (!context.isRun()) {
            return false;
        }

        if (WebChatUtils.XIANG_QING.equals(WebChatUtils.getCurrentPage())) {
            WebChatUtils.back();
            WebChatUtils.waitPageInit(WebChatUtils.TONG_XUN_LU, 3000);
        }

        if (WebChatUtils.TONG_XUN_LU.equals(WebChatUtils.getCurrentPage())) {
            NodeDetail join = NodeUtils.joinChildNode(NodeUtils.getRoot());
            if (null != join && null != join.getText()) {
                Log.i(join.toJSONString());
                if (join.getText().indexOf("位联系人") != -1) {
                    Intent intent = new Intent(this, MainActivity.class); // 跳转到辅助功能设置页
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    return false;
                }
            }
            Log.i("[visitTongXunLu] 页面信息", "向下滚动通讯录");
            NodeUtils.scrollForward(list);
            NodeUtils.sleep(NodeUtils.INTERVAL);
            return true;
            //visitTongXunLu();
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private Contact visitXiangQing() {
        WebChatUtils.waitPageInit(WebChatUtils.XIANG_QING, 3000);
        Log.i("[visitXiangQing] 详情页", NodeUtils.joinChildNode(NodeUtils.getRoot()).toJSONString());

        Contact contact = WebChatUtils.parseContactFromXiangQing();
        // 访问朋友圈
        Log.i("[visitXiangQing] visitXiangQing", NodeUtils.joinChildNode(NodeUtils.getRoot()).getText());
        if (!contact.isDeleted()) {
            AccessibilityNodeInfo node = NodeUtils.findNodeByText("朋友圈");
            NodeUtils.click(node);

            if (WebChatUtils.waitPageInit(WebChatUtils.PENG_YOU_QUAN, 5000)) {
                contact = contact.extend(WebChatUtils.parseContactFromPengYouQuan());
                WebChatUtils.back();
                WebChatUtils.waitPageInit(WebChatUtils.XIANG_QING, 3000);
            } else {
                // 朋友圈内容加载超时
                if (WebChatUtils.PENG_YOU_QUAN.equals(WebChatUtils.getCurrentPage())) {
                    WebChatUtils.back();
                    WebChatUtils.waitPageInit(WebChatUtils.XIANG_QING, 3000);
                } else {
                    Log.i("[visitXiangQing] can not visit peng you quan!");
                    Log.i("[visitXiangQing] current page content", NodeUtils.joinChildNode(NodeUtils.getRoot()).toJSONString());
                }
            }
        }

        if (WebChatUtils.XIANG_QING.equals(WebChatUtils.getCurrentPage())) {
            Log.i("[visitXiangQing] Contact", contact.toJSONString());
            if (context.isDeleteForbiddenVisitPengYouQuan() && (contact.isForbiddenVisitPengYouQuan() || contact.isDeleted())) {
                deleteContact(context.isDeleteForbiddenVisitPengYouQuan());
            }
            if (WebChatUtils.XIANG_QING.equals(WebChatUtils.getCurrentPage())) {
                WebChatUtils.back();
            }
            WebChatUtils.waitPageInit(WebChatUtils.TONG_XUN_LU, 3000);
        }
        return contact;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void deleteContact(boolean confirm) {
        WebChatUtils.waitPageInit(WebChatUtils.XIANG_QING, 3000);
        if (WebChatUtils.XIANG_QING.equals(WebChatUtils.getCurrentPage())) {
            Log.i("[deleteContact] 详情页", NodeUtils.joinChildNode(NodeUtils.getRoot()).toJSONString());
            AccessibilityNodeInfo moreBtnNode = NodeUtils.findNodeByClassName("android.widget.ImageButton");
            if (null != moreBtnNode) {
                Log.i("[deleteContact] 更多操作按钮", NodeUtils.toNodeDetail(moreBtnNode).toJSONString());

                NodeUtils.click(moreBtnNode);
                AccessibilityNodeInfo menuItemNode = NodeUtils.blockFindNodeByText("设为星标朋友", 3000);
                Log.i("[deleteContact] 设置为星标朋友", NodeUtils.joinChildNode(NodeUtils.getRoot()).toJSONString());

                NodeUtils.scrollForward(menuItemNode);
                AccessibilityNodeInfo deleteMenuItemNode = NodeUtils.blockFindNodeByText("删除", 3000);
                Log.i("[deleteContact] 点击删除菜单项", NodeUtils.joinChildNode(NodeUtils.getRoot()).toJSONString());
                NodeUtils.click(deleteMenuItemNode);

                NodeUtils.blockFindNodeByText("删除联系人", 3000);
                if (confirm) {
                    AccessibilityNodeInfo confirmNode = NodeUtils.blockFindClickNodeByText("删除", 3000);
                    Log.i("[deleteContact] 点击确认删除", NodeUtils.joinChildNode(NodeUtils.getRoot()).toJSONString());
                    NodeUtils.click(confirmNode);
                } else {
                    AccessibilityNodeInfo confirmNode = NodeUtils.blockFindClickNodeByText("取消", 3000);
                    Log.i("[deleteContact] 点击确认取消", NodeUtils.joinChildNode(NodeUtils.getRoot()).toJSONString());
                    NodeUtils.click(confirmNode);
                }
            } else {
                Log.i("[deleteContact] 更多操作按钮", "can not fine more menu button! ");
            }
        }
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

    public void getJob() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (;;) {
                    try {
                        updateJob();
                    } catch (Exception e) {
                        Log.i("error", "更新任务异常，msg: " + SystemUtils.getStackTraceInfo(e));
                    }
                    NodeUtils.sleep(context.getScheduleInterval());
                }
            }

            public void updateJob() {
                Job job = JobUtils.getJob();
                if (null != job) {
                    context.setLastVisitMarkName(null);
                    if (JobTypeEnum.START.getValue() == job.getType()) {
                        context.setScheduleInterval(1000);
                        if (null != job.getParams()) {
                            JobParams params = job.getParams();
                            if (null != params.getLastVisitMarkName()) {
                                context.setLastVisitMarkName(params.getLastVisitMarkName());
                                Log.i("new job", "start with " + params.getLastVisitMarkName());
                            }
                            context.setDeleteForbiddenVisitPengYouQuan(params.isDeleteForbiddenVisitPengYouQuan());
                        } else {
                            Log.i("new job", "start");
                        }
                        context.setRun(true);
                    } else if (JobTypeEnum.STOP.getValue() == job.getType()) {
                        context.setScheduleInterval(5000);
                        context.setRun(false);
                        context.setRunning(false);
                        Log.i("new job", "stop");
                    }
                }
                Log.i("context", JSON.toJSONString(context));
            }
        };
        executorService.execute(runnable);
    }
}
