package com.example.swee1.pengyouquan.domain;

import com.alibaba.fastjson.JSON;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "friend")
public class FriendBean {
    @Id(autoincrement = true)
    @Unique
    @Property(nameInDb = "id")
    private Long id;
    @Property(nameInDb = "user_id")
    private String userId;
    @Property(nameInDb = "nick_name")
    private String nickName;
    @Property(nameInDb = "phone")
    private String phone;
    @Property(nameInDb = "address")
    private String address;
    @Property(nameInDb = "deleted")
    private boolean deleted;
    @Property(nameInDb = "desc")
    private String desc;
    @Property(nameInDb = "mark_name")
    private String markName;
    @Property(nameInDb = "forbidden_visit_peng_you_quan")
    private boolean forbiddenVisitPengYouQuan;
    @Property(nameInDb = "peng_you_quan_day_limit_desc")
    private String pengYouQuanDayLimitDesc;
    @Property(nameInDb = "peng_you_quan_content")
    private String pengYouQuanContent;
    @Property(nameInDb = "xiang_qing_content")
    private String xiangQingContent;

    @Generated(hash = 2077919909)
    public FriendBean(Long id, String userId, String nickName, String phone, String address, boolean deleted, String desc, String markName,
            boolean forbiddenVisitPengYouQuan, String pengYouQuanDayLimitDesc, String pengYouQuanContent, String xiangQingContent) {
        this.id = id;
        this.userId = userId;
        this.nickName = nickName;
        this.phone = phone;
        this.address = address;
        this.deleted = deleted;
        this.desc = desc;
        this.markName = markName;
        this.forbiddenVisitPengYouQuan = forbiddenVisitPengYouQuan;
        this.pengYouQuanDayLimitDesc = pengYouQuanDayLimitDesc;
        this.pengYouQuanContent = pengYouQuanContent;
        this.xiangQingContent = xiangQingContent;
    }

    @Generated(hash = 152145004)
    public FriendBean() {
    }

    public FriendBean extend(FriendBean... friends) {
        FriendBean friend = this.copy(new FriendBean());
        if (null == friends) {
            return friend;
        }

        for (FriendBean f : friends) {
            friend.setId(null != f.getId() ? f.getId() : friend.getId());
            friend.setUserId(null != f.getUserId() ? f.getUserId() : friend.getUserId());
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

    public <T extends FriendBean> T copy(T bean) {
        bean.setId(getId());
        bean.setUserId(getUserId());
        bean.setNickName(getNickName());
        bean.setPhone(getPhone());
        bean.setAddress(getAddress());
        bean.setDeleted(isDeleted());
        bean.setDesc(getDesc());
        bean.setMarkName(getMarkName());
        bean.setForbiddenVisitPengYouQuan(isForbiddenVisitPengYouQuan());
        bean.setPengYouQuanDayLimitDesc(getPengYouQuanDayLimitDesc());
        bean.setPengYouQuanContent(getPengYouQuanContent());
        bean.setXiangQingContent(getXiangQingContent());
        return bean;
    }

    public String toJSONString() {
        return JSON.toJSONString(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getMarkName() {
        return markName;
    }

    public void setMarkName(String markName) {
        this.markName = markName;
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

    public String getPengYouQuanContent() {
        return pengYouQuanContent;
    }

    public void setPengYouQuanContent(String pengYouQuanContent) {
        this.pengYouQuanContent = pengYouQuanContent;
    }

    public String getXiangQingContent() {
        return xiangQingContent;
    }

    public void setXiangQingContent(String xiangQingContent) {
        this.xiangQingContent = xiangQingContent;
    }

    public boolean getDeleted() {
        return this.deleted;
    }

    public boolean getForbiddenVisitPengYouQuan() {
        return this.forbiddenVisitPengYouQuan;
    }
}
