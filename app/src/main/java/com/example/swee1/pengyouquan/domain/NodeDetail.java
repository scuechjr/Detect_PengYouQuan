package com.example.swee1.pengyouquan.domain;

import android.graphics.Rect;
import android.view.accessibility.AccessibilityNodeInfo;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

public class NodeDetail {
    private Integer index;
    private String text;
    private String resourceId;
    private String className;
    private String packageName;
    private String contentDesc;
    private Boolean checkable;
    private Boolean checked;
    private Boolean clickable;
    private Boolean enabled;
    private Boolean focusable;
    private Boolean scrollable;
    private Boolean longClickable;
    private Boolean password;
    private Boolean selected;
    private Rect boundsInParent;
    private Rect boundsInScreen;
    private List<NodeDetail> children;
    private AccessibilityNodeInfo orgin;

    public String toJSONString() {
        return JSON.toJSONString(copy(new NodeDetail()));
    }

    public NodeDetail copy(NodeDetail bean) {
        bean.setIndex(getIndex());
        bean.setText(getText());
        bean.setResourceId(getResourceId());
        bean.setClassName(getClassName());
        bean.setPackageName(getPackageName());
        bean.setContentDesc(getContentDesc());
        bean.setCheckable(isCheckable());
        bean.setChecked(isChecked());
        bean.setClickable(isClickable());
        bean.setEnabled(isEnabled());
        bean.setFocusable(isFocusable());
        bean.setScrollable(isScrollable());
        bean.setLongClickable(isLongClickable());
        bean.setPassword(isPassword());
        bean.setSelected(isSelected());
        bean.setBoundsInParent(getBoundsInParent());
        bean.setBoundsInScreen(getBoundsInScreen());
        List<NodeDetail> children = getChildren();
        if (null != children) {
            List<NodeDetail> list = new ArrayList<>();
            for (NodeDetail item : children) {
                list.add(item.copy(new NodeDetail()));
            }
            bean.setChildren(list);
        }
        return bean;
    }


    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getContentDesc() {
        return contentDesc;
    }

    public void setContentDesc(String contentDesc) {
        this.contentDesc = contentDesc;
    }

    public Boolean isCheckable() {
        return checkable;
    }

    public void setCheckable(Boolean checkable) {
        this.checkable = checkable;
    }

    public Boolean isChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Boolean isClickable() {
        return clickable;
    }

    public void setClickable(Boolean clickable) {
        this.clickable = clickable;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean isFocusable() {
        return focusable;
    }

    public void setFocusable(Boolean focusable) {
        this.focusable = focusable;
    }

    public Boolean isScrollable() {
        return scrollable;
    }

    public void setScrollable(Boolean scrollable) {
        this.scrollable = scrollable;
    }

    public Boolean isLongClickable() {
        return longClickable;
    }

    public void setLongClickable(Boolean longClickable) {
        this.longClickable = longClickable;
    }

    public Boolean isPassword() {
        return password;
    }

    public void setPassword(Boolean password) {
        this.password = password;
    }

    public Boolean isSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public Rect getBoundsInParent() {
        return boundsInParent;
    }

    public void setBoundsInParent(Rect boundsInParent) {
        this.boundsInParent = boundsInParent;
    }

    public Rect getBoundsInScreen() {
        return boundsInScreen;
    }

    public void setBoundsInScreen(Rect boundsInScreen) {
        this.boundsInScreen = boundsInScreen;
    }

    public List<NodeDetail> getChildren() {
        return children;
    }

    public void setChildren(List<NodeDetail> children) {
        this.children = children;
    }

    public AccessibilityNodeInfo getOrgin() {
        return orgin;
    }

    public void setOrgin(AccessibilityNodeInfo orgin) {
        this.orgin = orgin;
    }
}
