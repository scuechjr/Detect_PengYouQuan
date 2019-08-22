package com.example.swee1.pengyouquan.util;

import android.app.KeyguardManager;
import android.content.Context;
import android.os.PowerManager;

public class SystemUtils {
    private static final String WAKE_LOCK_TAG = "wxpengyouquan:wakelock";

    public static void wakeUpAndUnlock(Context context){
        // 屏锁管理器
        KeyguardManager km= (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock kl = km.newKeyguardLock("unLock");
        // 解锁
        kl.disableKeyguard();
        // 获取电源管理器对象
        PowerManager pm=(PowerManager) context.getSystemService(Context.POWER_SERVICE);
        // 获取PowerManager.WakeLock对象，后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, WAKE_LOCK_TAG);
        // 点亮屏幕
        wl.acquire();
        // 释放
        wl.release();
    }
}
