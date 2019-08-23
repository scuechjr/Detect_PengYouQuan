package com.example.swee1.pengyouquan.util;

import android.view.accessibility.AccessibilityNodeInfo;

import com.example.swee1.pengyouquan.domain.Friend;
import com.example.swee1.pengyouquan.domain.FriendBean;
import com.example.swee1.pengyouquan.domain.NodeDetail;

public class WebChatUtils {
    public static final long INTERVAL = 200;
    public static final String UNKNOW = "unknow";
    public static final String TONG_XUN_LU = "tong_xun_lu";
    public static final String XIANG_QING = "xiang_qing";
    public static final String PENG_YOU_QUAN = "peng_you_quan";

    public static String getCurrentPage() {
        int maxCount = 50;
        for (int i = 0; i < maxCount; i++) {
            AccessibilityNodeInfo root = NodeUtils.getRoot();
            if (null != root) {
                NodeDetail joinNode = NodeUtils.joinChildNode(root);
                if (null != joinNode && null != joinNode.getText()) {
                    String text = joinNode.getText();
                    String resourceId = joinNode.getResourceId();
                    String contentDesc = joinNode.getContentDesc();
                    if (text.indexOf("微信号: ") != -1 && text.indexOf("发消息") != -1 || text.indexOf("对方已经删除帐号") != -1) {
                        return XIANG_QING;
                    } else if (resourceId.indexOf("com.tencent.mm:id/nn") != -1) {
                        return TONG_XUN_LU;
                    } else if (contentDesc.indexOf("朋友圈封面，再点一次可以改封面") != -1) {
                        return PENG_YOU_QUAN;
                    }
                }
            }
            NodeUtils.sleep(INTERVAL);
        }
        return UNKNOW;
    }

    public static boolean waitPageInit(String pageName, long timeout) {
        if (null != pageName) {
            long maxCount = timeout / INTERVAL + (timeout % INTERVAL == 0 ? 0 : 1);
            for (int i = 0; i < maxCount; i++) {
                String currentPage = getCurrentPage();
                if (pageName.equals(currentPage)) {
                    if (pageName.equals(PENG_YOU_QUAN) || pageName.equals(XIANG_QING)) {
                        NodeUtils.sleep(300); // 暂停，防止页面未完全初始化
                        String text = NodeUtils.joinChildNode(NodeUtils.getRoot()).getText();
                        if (null != text && text.indexOf("正在加载") != -1) {
                            continue;
                        }
                    }
                    return true;
                }
                NodeUtils.sleep(INTERVAL);
            }
        }
        return false;
    }

    public static void back() {
        AccessibilityNodeInfo node = NodeUtils.blockFindNodeByText("返回", 3000);
        NodeUtils.click(node);
    }

    public static FriendBean parseFriendFromXiangQing() {
        FriendBean friend = new FriendBean();
        if (WebChatUtils.waitPageInit(WebChatUtils.XIANG_QING,3000)) {
            NodeDetail join = NodeUtils.joinChildNode(NodeUtils.getRoot());
            if (null != join) {
                String xiangQingContent = join.toJSONString();
                friend.setXiangQingContent(xiangQingContent);
                if (null != xiangQingContent) {
                    String[] parts = xiangQingContent.split(",");
                    for (int i = 1; i < parts.length; i++) {
                        if (null != parts[i-1] && "电话号码".equals(parts[i-1])) {
                            friend.setPhone(parts[i]);
                            break;
                        }
                    }
                }
                if (null != join.getText()) {
                    if (join.getText().indexOf("对方已经删除帐号") != -1) {
                        friend.setDeleted(true);
                        friend.setDesc("对方已经删除帐号");
                    }
                    String[] parts = join.getText().split(",");
                    for (String part : parts) {
                        if (null != part && part.indexOf("昵称:") != -1) {
                            friend.setNickName(part.substring("昵称:".length()).trim());
                        }
                        if (null != part && part.indexOf("微信号:") != -1) {
                            friend.setUserId(part.substring("微信号:".length()).trim());
                        }
                        if (null != part && part.indexOf("地区:") != -1) {
                            friend.setAddress(part.substring("地区:".length()).trim());
                        }
                    }
                }
            }
        }
        return friend;
    }

    public static FriendBean parseFriendFromPengYouQuan() {
        FriendBean friend = new FriendBean();
        if (WebChatUtils.waitPageInit(WebChatUtils.PENG_YOU_QUAN,3000)) {
            friend.setPengYouQuanContent(NodeUtils.clearNode(NodeUtils.getRoot()).toJSONString());
            AccessibilityNodeInfo listViewNode = NodeUtils.findNodeByClassName("android.widget.ListView");
            if (null != listViewNode && listViewNode.getChildCount() < 3) {
                NodeDetail join = NodeUtils.joinChildNode(listViewNode);

                String[] contentDescParts = join.getContentDesc().split(",");
                for (int i = 0; i < contentDescParts.length; i++) {
                    if ("我的头像".equals(contentDescParts[i])) {
                        friend.setNickName(contentDescParts[i-1]);
                        break;
                    }
                }

                if (join.getText().indexOf("朋友仅展示最近") != -1) {
                    friend.setForbiddenVisitPengYouQuan(false);
                    String[] textParts = join.getText().split(",");
                    for (int i = 0; i < textParts.length; i++) {
                        if (textParts[i].indexOf("朋友仅展示最近") != -1) {
                            friend.setPengYouQuanDayLimitDesc(textParts[i]);
                            break;
                        }
                    }
                }

                if (listViewNode.getChildCount() == 2) {
                    NodeDetail childJoin = NodeUtils.joinChildNode(listViewNode.getChild(1));
                    if (null == childJoin || null == childJoin.getText() || childJoin.getText().length() < 1) {
                        friend.setForbiddenVisitPengYouQuan(true);
                        friend.setPengYouQuanDayLimitDesc("朋友圈被屏蔽");
                    }
                } else {
                    friend.setForbiddenVisitPengYouQuan(true);
                    friend.setPengYouQuanDayLimitDesc("朋友圈被屏蔽");
                }
            }
        }
        return friend;
    }
}
