package com.example.swee1.pengyouquan.util;

import com.alibaba.fastjson.JSON;
import com.example.swee1.pengyouquan.domain.Config;
import com.example.swee1.pengyouquan.domain.Contact;
import com.example.swee1.pengyouquan.domain.Job;
import com.example.swee1.pengyouquan.domain.JobParams;
import com.example.swee1.pengyouquan.service.ConfigService;
import com.example.swee1.pengyouquan.service.ContactService;

import java.util.List;

public class JobUtils {
    private static final String HOST = "http://192.168.8.103:8080";
//    private static final String HOST = "http://192.168.1.13:8080";

    public static void saveContact(final Contact contact) {
//        String url = HOST + "/wx/saveContact";
//        String resp = HttpUtils.post(url, JSON.toJSONBytes(contact), false);
//        Log.i("save contact response", resp);
        try {
            ContactService.getInstance().add(contact);
        } catch (Exception e) {
            Log.i("error", "save contact error, msg: " + SystemUtils.getStackTraceInfo(e));
        }
    }

    public static boolean export(final String url, final List<Contact> contacts) {
        try {
            String resp = HttpUtils.post(url, JSON.toJSONBytes(contacts), false);
            Log.i("export contact response", resp);
            return true;
        } catch (Exception e) {
            Log.i("error", "export contact error, msg: " + SystemUtils.getStackTraceInfo(e));
        }
        return false;
    }

    public static Job getJob() {
//        String url = HOST + "/wx/job";
//        try {
//            String result = HttpUtils.get(url);
//            Log.i("http get job", result);
//            return JSON.parseObject(result, Job.class);
//        } catch (Exception e) {
//            Log.i("getJob error", e.getMessage());
//        }
//        return null;
        try {
            Config bean = ConfigService.getInstance().getFirst();
            Job job = new Job();
            JobParams params = new JobParams();
            job.setType(bean.getType());
            job.setParams(params);
            params.setDeleteForbiddenVisitPengYouQuan(bean.getDeleteForbiddenVisitPengYouQuan());
            params.setLastVisitMarkName(bean.getStartMarkName());
            return job;
        } catch (Exception e) {
            Log.i("error", "save contact error, msg: " + SystemUtils.getStackTraceInfo(e));
        }
        return null;
    }
}
