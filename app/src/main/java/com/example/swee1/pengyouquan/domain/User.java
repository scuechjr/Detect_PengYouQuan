package com.example.swee1.pengyouquan.domain;

public class User {
    private String id;
    private String nickName;
    private String phone;
    private String address;
    private boolean deleted;
    private String desc;
    private String markName;

    public <T extends User> T copy(T bean) {
        bean.setId(getId());
        bean.setNickName(getNickName());
        bean.setPhone(getPhone());
        bean.setAddress(getAddress());
        bean.setDeleted(isDeleted());
        bean.setDesc(getDesc());
        bean.setMarkName(getMarkName());
        return bean;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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
}
