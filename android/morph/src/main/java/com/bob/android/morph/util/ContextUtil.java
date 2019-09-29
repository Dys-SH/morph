package com.bob.android.morph.util;

import android.content.Context;

/**
 * created by cly on 2019-09-24
 */
public class ContextUtil {
    private static Context mContext;

    public static void init(Context appContext) {
        mContext = appContext.getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }
}
