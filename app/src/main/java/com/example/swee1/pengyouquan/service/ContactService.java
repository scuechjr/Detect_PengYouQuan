package com.example.swee1.pengyouquan.service;

import com.example.swee1.pengyouquan.dao.PengYouQuanDao;
import com.example.swee1.pengyouquan.domain.Contact;

import org.cvte.research.faceapi.greendao.ContactDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactService {
    private static Object lock = new Object();
    private static ContactService instance;

    public static ContactService getInstance() {
        if (null == instance) {
            synchronized (lock) {
                if (null == instance) {
                    instance = new ContactService();
                }
            }
        }
        return instance;
    }

    public void add(Contact bean) {
        PengYouQuanDao.getDaoSession().insert(bean);
    }

    public List<Contact> queryAll() {
        return distinctByUserId(
                PengYouQuanDao.getDaoSession()
                .getContactDao()
                .queryBuilder()
                .orderAsc(ContactDao.Properties.Id)
                .list()
        );
    }

    public List<Contact> queryForbidden() {
        return distinctByUserId(
                PengYouQuanDao.getDaoSession()
                .getContactDao()
                .queryBuilder()
                .where(ContactDao.Properties.ForbiddenVisitPengYouQuan.eq(true))
                .orderAsc(ContactDao.Properties.Id)
                .list()
        );
    }

    private List<Contact> distinctByUserId(List<Contact> list) {
        if (null == list) {
            return list;
        }
        Map<String, Contact> map = new HashMap<>();
        for (Contact item : list) {
            map.put(item.getUserId(), item);
        }
        return new ArrayList<>(map.values());
    }
}
