package com.example.swee1.pengyouquan.service;

import com.example.swee1.pengyouquan.dao.PengYouQuanDao;
import com.example.swee1.pengyouquan.domain.ConfigBean;

import org.cvte.research.faceapi.greendao.ConfigBeanDao;

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

    public void add(ConfigBean bean) {
        PengYouQuanDao.getDaoSession().insert(bean);
    }

    public void update(ConfigBean bean) {
        PengYouQuanDao.getDaoSession().update(bean);
    }

    public ConfigBean getById(Long id) {
        return PengYouQuanDao.getDaoSession()
                .getConfigBeanDao()
                .queryBuilder()
                .where(ConfigBeanDao.Properties.Id.eq(id))
                .unique();
    }

    public ConfigBean getFirst() {
        return PengYouQuanDao.getDaoSession()
                .getConfigBeanDao()
                .queryBuilder()
                .unique();
    }

}
