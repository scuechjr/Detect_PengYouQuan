package com.example.swee1.pengyouquan.util;

import com.alibaba.fastjson.JSON;
import com.example.swee1.pengyouquan.PrintUtils;
import com.example.swee1.pengyouquan.domain.Friend;
import com.example.swee1.pengyouquan.domain.Job;

public class JobUtils {
    private static final String HOST = "http://192.168.8.103:8080";
//    private static final String HOST = "http://192.168.1.13:8080";

    public static void saveFriend(final Friend friend) {
        String url = HOST + "/wx/saveFriend";
        String resp = HttpUtils.post(url, JSON.toJSONBytes(friend), false);
        PrintUtils.log("save friend response", resp);
    }

    public static Job getJob() {
        String url = HOST + "/wx/job";
        try {
            String result = HttpUtils.get(url);
            PrintUtils.log("http get job", result);
            return JSON.parseObject(result, Job.class);
        } catch (Exception e) {
            PrintUtils.log("getJob error", e.getMessage());
        }
        return null;
    }
}
