package com.example.swee1.pengyouquan.domain;

import com.alibaba.fastjson.JSON;

public class Friend extends User {
    private boolean forbiddenVisitPengYouQuan;
    private String pengYouQuanDayLimitDesc;
    private String pengYouQuanContent;
    private String xiangQingContent;

    public Friend extend(Friend... friends) {
        Friend friend = this.copy(new Friend());
        if (null == friends) {
            return friend;
        }

        for (Friend f : friends) {
            friend.setId(null != f.getId() ? f.getId() : friend.getId());
            friend.setNickName(null != f.getNickName() ? f.getNickName() : friend.getNickName());
            friend.setPhone(null != f.getPhone() ? f.getPhone() : friend.getPhone());
            friend.setAddress(null != f.getAddress() ? f.getAddress() : friend.getAddress());
            friend.setDeleted(f.isDeleted() ? f.isDeleted() : friend.isDeleted());
            friend.setDesc(null != f.getDesc() ? f.getDesc() : friend.getDesc());
            friend.setMarkName(null != f.getMarkName() ? f.getMarkName() : friend.getMarkName());
            friend.setMarkName(null != f.getMarkName() ? f.getMarkName() : friend.getMarkName());
            friend.setForbiddenVisitPengYouQuan(f.isForbiddenVisitPengYouQuan() ? f.isForbiddenVisitPengYouQuan() : friend.isForbiddenVisitPengYouQuan());
            friend.setPengYouQuanDayLimitDesc(null != f.getPengYouQuanDayLimitDesc() ? f.getPengYouQuanDayLimitDesc() : friend.getPengYouQuanDayLimitDesc());
            friend.setPengYouQuanContent(null != f.getPengYouQuanContent() ? f.getPengYouQuanContent() : friend.getPengYouQuanContent());
            friend.setXiangQingContent(null != f.getXiangQingContent() ? f.getXiangQingContent() : friend.getXiangQingContent());
        }

        return friend;
    }

    public <T extends Friend> T copy(T bean) {
        bean = super.copy(bean);
        bean.setForbiddenVisitPengYouQuan(isForbiddenVisitPengYouQuan());
        bean.setPengYouQuanDayLimitDesc(getPengYouQuanDayLimitDesc());
        bean.setPengYouQuanContent(getPengYouQuanContent());
        bean.setXiangQingContent(getXiangQingContent());
        return bean;
    }

    public String getXiangQingContent() {
        return xiangQingContent;
    }

    public void setXiangQingContent(String xiangQingContent) {
        this.xiangQingContent = xiangQingContent;
    }

    public boolean isForbiddenVisitPengYouQuan() {
        return forbiddenVisitPengYouQuan;
    }

    public void setForbiddenVisitPengYouQuan(boolean forbiddenVisitPengYouQuan) {
        this.forbiddenVisitPengYouQuan = forbiddenVisitPengYouQuan;
    }

    public String getPengYouQuanDayLimitDesc() {
        return pengYouQuanDayLimitDesc;
    }

    public void setPengYouQuanDayLimitDesc(String pengYouQuanDayLimitDesc) {
        this.pengYouQuanDayLimitDesc = pengYouQuanDayLimitDesc;
    }

    public String toJSONString() {
        return JSON.toJSONString(this);
    }

    public String getPengYouQuanContent() {
        return pengYouQuanContent;
    }

    public void setPengYouQuanContent(String pengYouQuanContent) {
        this.pengYouQuanContent = pengYouQuanContent;
    }
}
