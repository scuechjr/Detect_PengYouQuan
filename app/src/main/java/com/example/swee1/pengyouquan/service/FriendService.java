package com.example.swee1.pengyouquan.service;

import com.example.swee1.pengyouquan.dao.PengYouQuanDao;
import com.example.swee1.pengyouquan.domain.FriendBean;

import org.cvte.research.faceapi.greendao.FriendBeanDao;

import java.util.List;

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
        return PengYouQuanDao.getDaoSession()
                .getFriendBeanDao()
                .queryBuilder()
                .orderAsc(FriendBeanDao.Properties.Id)
                .list();
    }
}
