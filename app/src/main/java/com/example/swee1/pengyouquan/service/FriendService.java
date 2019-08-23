package com.example.swee1.pengyouquan.service;

import com.example.swee1.pengyouquan.dao.PengYouQuanDao;
import com.example.swee1.pengyouquan.domain.Friend;
import com.example.swee1.pengyouquan.domain.FriendBean;

import org.cvte.research.faceapi.greendao.FriendBeanDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FriendService {
    private static Object lock = new Object();
    private static FriendService instance;

    public static FriendService getInstance() {
        if (null == instance) {
            synchronized (lock) {
                if (null == instance) {
                    instance = new FriendService();
                }
            }
        }
        return instance;
    }

    public void add(FriendBean bean) {
        PengYouQuanDao.getDaoSession().insert(bean);
    }

    public List<FriendBean> queryAll() {
        return distinctByUserId(
                PengYouQuanDao.getDaoSession()
                .getFriendBeanDao()
                .queryBuilder()
                .orderAsc(FriendBeanDao.Properties.Id)
                .list()
        );
    }

    public List<FriendBean> queryForbidden() {
        return distinctByUserId(
                PengYouQuanDao.getDaoSession()
                .getFriendBeanDao()
                .queryBuilder()
                .where(FriendBeanDao.Properties.ForbiddenVisitPengYouQuan.eq(true))
                .orderAsc(FriendBeanDao.Properties.Id)
                .list()
        );
    }

    private List<FriendBean> distinctByUserId(List<FriendBean> list) {
        if (null == list) {
            return list;
        }
        Map<String, FriendBean> map = new HashMap<>();
        for (FriendBean item : list) {
            map.put(item.getUserId(), item);
        }
        return new ArrayList<>(map.values());
    }
}
