package com.example.swee1.pengyouquan.service;

import com.example.swee1.pengyouquan.dao.PengYouQuanDao;
import com.example.swee1.pengyouquan.domain.Config;

import org.cvte.research.faceapi.greendao.ConfigDao;

public class ConfigService {
    private static Object lock = new Object();
    private static ConfigService instance;

    public static ConfigService getInstance() {
        if (null == instance) {
            synchronized (lock) {
                if (null == instance) {
                    instance = new ConfigService();
                }
            }
        }
        return instance;
    }

    public void add(Config bean) {
        PengYouQuanDao.getDaoSession().insert(bean);
    }

    public void update(Config bean) {
        PengYouQuanDao.getDaoSession().update(bean);
    }

    public Config getById(Long id) {
        return PengYouQuanDao.getDaoSession()
                .getConfigDao()
                .queryBuilder()
                .where(ConfigDao.Properties.Id.eq(id))
                .unique();
    }

    public Config getFirst() {
        return PengYouQuanDao.getDaoSession()
                .getConfigDao()
                .queryBuilder()
                .unique();
    }

}
