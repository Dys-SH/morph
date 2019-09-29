package com.bob.android.morph.util;

import android.os.Handler;
import android.os.Looper;

/**
 * Create by cly on 18/12/10
 */
public class MainThreadDelivery {
    private final Handler handler = new Handler(Looper.getMainLooper());
    private static MainThreadDelivery mDelivery = new MainThreadDelivery();

    public static MainThreadDelivery getInstance() {
        return mDelivery;
    }

    /**
     * 主线程执行Runnable
     */
    public void deliver(Runnable r) {
        handler.post(r);
    }
}
