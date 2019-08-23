package com.example.swee1.pengyouquan.util;

import com.alibaba.fastjson.JSON;
import com.example.swee1.pengyouquan.PrintUtils;
import com.example.swee1.pengyouquan.domain.ConfigBean;
import com.example.swee1.pengyouquan.domain.Friend;
import com.example.swee1.pengyouquan.domain.FriendBean;
import com.example.swee1.pengyouquan.domain.Job;
import com.example.swee1.pengyouquan.domain.JobParams;
import com.example.swee1.pengyouquan.service.ConfigService;
import com.example.swee1.pengyouquan.service.FriendService;

public class JobUtils {
    private static final String HOST = "http://192.168.8.103:8080";
//    private static final String HOST = "http://192.168.1.13:8080";

    public static void saveFriend(final FriendBean friend) {
//        String url = HOST + "/wx/saveFriend";
//        String resp = HttpUtils.post(url, JSON.toJSONBytes(friend), false);
//        PrintUtils.log("save friend response", resp);
        try {
            FriendService.getInstance().add(friend);
        } catch (Exception e) {
            PrintUtils.log("error", "save friend error, msg: " + NodeUtils.getStackTraceInfo(e));
        }
    }

    public static Job getJob() {
//        String url = HOST + "/wx/job";
//        try {
//            String result = HttpUtils.get(url);
//            PrintUtils.log("http get job", result);
//            return JSON.parseObject(result, Job.class);
//        } catch (Exception e) {
//            PrintUtils.log("getJob error", e.getMessage());
//        }
//        return null;
        try {
            ConfigBean bean = ConfigService.getInstance().getFirst();
            Job job = new Job();
            JobParams params = new JobParams();
            job.setType(bean.getType());
            job.setParams(params);
            params.setDeleteForbiddenVisitPengYouQuan(bean.getDeleteForbiddenVisitPengYouQuan());
            params.setLastVisitMarkName(bean.getStartMarkName());
            return job;
        } catch (Exception e) {
            PrintUtils.log("error", "save friend error, msg: " + NodeUtils.getStackTraceInfo(e));
        }
        return null;
    }
}
