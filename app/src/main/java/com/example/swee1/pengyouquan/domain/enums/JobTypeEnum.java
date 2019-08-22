package com.example.swee1.pengyouquan.domain.enums;

public enum JobTypeEnum {
//    GET_CURRENT_NODE_STREE(1, "获取当前页面节点树"),
//    GET_NODE_BY_ID(2, "根据ID获取当前页面节点树"),
//    GET_NODE_BY_TEXT(3, "根据TEXT获取当前页面节点树"),
//    GET_CLICK_NODE_BY_ID(4, "根据ID获取当前页面可点击节点树"),
//    GET_CLICK_NODE_BY_TEXT(5, "根据TEXT获取当前页面可点击节点树"),
//    GET_CLICK_NODES_BY_ID(6, "根据ID获取当前页面可点击节点列表树"),
//    GET_CLICK_NODES_BY_TEXT(7, "根据TEXT获取当前页面可点击节点列表树"),
//    CLICK_NODE_BY_ID(8, "根据ID点击当前页面指定节点"),
//    CLICK_NODE_BY_TEXT(9, "根据TEXT点击当前页面指定节点"),
//    CLICK_NODES_BY_ID(10, "根据ID点击当前页面指定节点列表"),
//    CLICK_NODES_BY_TEXT(11, "根据TEXT点击当前页面指定节点列表"),
    START(1, "开始执行"),
    RESTART(2, "重新执行"),
    STOP(3, "停止执行"),
    RPC(4, "远程方法调用"),
    ;

    private int value;
    private String show;

    JobTypeEnum(int value, String show) {
        this.value = value;
        this.show = show;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getShow() {
        return show;
    }

    public void setShow(String show) {
        this.show = show;
    }
}
