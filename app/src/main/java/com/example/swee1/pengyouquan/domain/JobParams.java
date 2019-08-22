package com.example.swee1.pengyouquan.domain;

public class JobParams {
    private String methodName;
    private String id;
    private String text;
    private String desc;
    private String className;
    private String packageName;

    private String lastVisitMarkName;

    private boolean deleteForbiddenVisitPengYouQuan;

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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

    public String getLastVisitMarkName() {
        return lastVisitMarkName;
    }

    public void setLastVisitMarkName(String lastVisitMarkName) {
        this.lastVisitMarkName = lastVisitMarkName;
    }

    public boolean isDeleteForbiddenVisitPengYouQuan() {
        return deleteForbiddenVisitPengYouQuan;
    }

    public void setDeleteForbiddenVisitPengYouQuan(boolean deleteForbiddenVisitPengYouQuan) {
        this.deleteForbiddenVisitPengYouQuan = deleteForbiddenVisitPengYouQuan;
    }
}
