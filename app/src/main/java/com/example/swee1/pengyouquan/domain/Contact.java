package com.example.swee1.pengyouquan.domain;

import com.alibaba.fastjson.JSON;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "contact")
public class Contact {
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

    @Generated(hash = 1860455673)
    public Contact(Long id, String userId, String nickName, String phone, String address, boolean deleted, String desc, String markName,
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

    @Generated(hash = 672515148)
    public Contact() {
    }

    public Contact extend(Contact... contacts) {
        Contact contact = this.copy(new Contact());
        if (null == contacts) {
            return contact;
        }

        for (Contact f : contacts) {
            contact.setId(null != f.getId() ? f.getId() : contact.getId());
            contact.setUserId(null != f.getUserId() ? f.getUserId() : contact.getUserId());
            contact.setNickName(null != f.getNickName() ? f.getNickName() : contact.getNickName());
            contact.setPhone(null != f.getPhone() ? f.getPhone() : contact.getPhone());
            contact.setAddress(null != f.getAddress() ? f.getAddress() : contact.getAddress());
            contact.setDeleted(f.isDeleted() ? f.isDeleted() : contact.isDeleted());
            contact.setDesc(null != f.getDesc() ? f.getDesc() : contact.getDesc());
            contact.setMarkName(null != f.getMarkName() ? f.getMarkName() : contact.getMarkName());
            contact.setMarkName(null != f.getMarkName() ? f.getMarkName() : contact.getMarkName());
            contact.setForbiddenVisitPengYouQuan(f.isForbiddenVisitPengYouQuan() ? f.isForbiddenVisitPengYouQuan() : contact.isForbiddenVisitPengYouQuan());
            contact.setPengYouQuanDayLimitDesc(null != f.getPengYouQuanDayLimitDesc() ? f.getPengYouQuanDayLimitDesc() : contact.getPengYouQuanDayLimitDesc());
            contact.setPengYouQuanContent(null != f.getPengYouQuanContent() ? f.getPengYouQuanContent() : contact.getPengYouQuanContent());
            contact.setXiangQingContent(null != f.getXiangQingContent() ? f.getXiangQingContent() : contact.getXiangQingContent());
        }

        return contact;
    }

    public <T extends Contact> T copy(T bean) {
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
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return this.nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean getDeleted() {
        return this.deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getMarkName() {
        return this.markName;
    }

    public void setMarkName(String markName) {
        this.markName = markName;
    }

    public boolean getForbiddenVisitPengYouQuan() {
        return this.forbiddenVisitPengYouQuan;
    }

    public void setForbiddenVisitPengYouQuan(boolean forbiddenVisitPengYouQuan) {
        this.forbiddenVisitPengYouQuan = forbiddenVisitPengYouQuan;
    }

    public String getPengYouQuanDayLimitDesc() {
        return this.pengYouQuanDayLimitDesc;
    }

    public void setPengYouQuanDayLimitDesc(String pengYouQuanDayLimitDesc) {
        this.pengYouQuanDayLimitDesc = pengYouQuanDayLimitDesc;
    }

    public String getPengYouQuanContent() {
        return this.pengYouQuanContent;
    }

    public void setPengYouQuanContent(String pengYouQuanContent) {
        this.pengYouQuanContent = pengYouQuanContent;
    }

    public String getXiangQingContent() {
        return this.xiangQingContent;
    }

    public void setXiangQingContent(String xiangQingContent) {
        this.xiangQingContent = xiangQingContent;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public boolean isForbiddenVisitPengYouQuan() {
        return forbiddenVisitPengYouQuan;
    }
}
