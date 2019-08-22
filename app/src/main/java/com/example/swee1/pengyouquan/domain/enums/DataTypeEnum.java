package com.example.swee1.pengyouquan.domain.enums;

public enum DataTypeEnum {
    ID(1, "id"),
    TEXT(2, "text"),
    CONTENT(3, "content"),
    CLASS(4, "class"),
    PACKAGE(5, "package"),
    ;

    private int value;
    private String show;

    DataTypeEnum(int value, String show) {
        this.value = value;
        this.show = show;
    }
}
