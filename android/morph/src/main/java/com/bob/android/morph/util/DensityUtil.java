package com.bob.android.morph.util;

import android.util.TypedValue;


/**
 * created by cly on 2019-09-06
 */
public class DensityUtil {
    public static int dp2px(float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, ContextUtil.getContext().getResources().getDisplayMetrics());
    }

    public static float px2dp(float pxVal) {
        final float scale = ContextUtil.getContext().getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }
}
