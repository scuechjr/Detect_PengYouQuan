package com.example.swee1.pengyouquan.util;

public interface HttpCallBackListener {

    void onFinish(String content);

    void onError(Throwable t);
}
