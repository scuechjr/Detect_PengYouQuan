package com.example.swee1.pengyouquan.domain;

public class JobContext {
    private boolean run;
    private boolean running;
    private String lastVisitMarkName;
    private boolean firstCatLastVisitMarkName;
    private boolean deleteForbiddenVisitPengYouQuan;

    private long scheduleInterval = 1000;

    public boolean isRun() {
        return run;
    }

    public void setRun(boolean run) {
        this.run = run;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public String getLastVisitMarkName() {
        return lastVisitMarkName;
    }

    public void setLastVisitMarkName(String lastVisitMarkName) {
        this.lastVisitMarkName = lastVisitMarkName;
    }

    public boolean isFirstCatLastVisitMarkName() {
        return firstCatLastVisitMarkName;
    }

    public void setFirstCatLastVisitMarkName(boolean firstCatLastVisitMarkName) {
        this.firstCatLastVisitMarkName = firstCatLastVisitMarkName;
    }

    public boolean isDeleteForbiddenVisitPengYouQuan() {
        return deleteForbiddenVisitPengYouQuan;
    }

    public void setDeleteForbiddenVisitPengYouQuan(boolean deleteForbiddenVisitPengYouQuan) {
        this.deleteForbiddenVisitPengYouQuan = deleteForbiddenVisitPengYouQuan;
    }

    public long getScheduleInterval() {
        return scheduleInterval;
    }

    public void setScheduleInterval(long scheduleInterval) {
        this.scheduleInterval = scheduleInterval;
    }
}
