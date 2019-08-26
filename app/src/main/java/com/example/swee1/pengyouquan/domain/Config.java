package com.example.swee1.pengyouquan.domain;

import com.alibaba.fastjson.JSON;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

@Entity(nameInDb = "config")
public class Config {
    @Id(autoincrement = true)
    @Unique
    private Long id;
    @Property(nameInDb = "type")
    private Integer type;
    @Property(nameInDb = "run")
    private boolean run;
    @Property(nameInDb = "start_mark_name")
    private String startMarkName;
    @Property(nameInDb = "delete_forbidden_visit_peng_you_quan")
    private boolean deleteForbiddenVisitPengYouQuan;

    @Generated(hash = 674441728)
    public Config(Long id, Integer type, boolean run, String startMarkName,
            boolean deleteForbiddenVisitPengYouQuan) {
        this.id = id;
        this.type = type;
        this.run = run;
        this.startMarkName = startMarkName;
        this.deleteForbiddenVisitPengYouQuan = deleteForbiddenVisitPengYouQuan;
    }

    @Generated(hash = 589037648)
    public Config() {
    }

    public String toJSONString() {
        return JSON.toJSONString(this);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public boolean getRun() {
        return this.run;
    }

    public void setRun(boolean run) {
        this.run = run;
    }

    public String getStartMarkName() {
        return this.startMarkName;
    }

    public void setStartMarkName(String startMarkName) {
        this.startMarkName = startMarkName;
    }

    public boolean getDeleteForbiddenVisitPengYouQuan() {
        return this.deleteForbiddenVisitPengYouQuan;
    }

    public void setDeleteForbiddenVisitPengYouQuan(
            boolean deleteForbiddenVisitPengYouQuan) {
        this.deleteForbiddenVisitPengYouQuan = deleteForbiddenVisitPengYouQuan;
    }
}
