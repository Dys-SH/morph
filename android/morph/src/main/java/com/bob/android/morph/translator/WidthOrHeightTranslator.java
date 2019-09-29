package com.bob.android.morph.translator;

import android.support.annotation.Keep;
import android.support.annotation.StringDef;
import android.view.ViewGroup;

import com.bob.android.morph.util.DensityUtil;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * created by cly on 2019-09-03
 */
public class WidthOrHeightTranslator {

    public static int trans(String value) {
        if (WidthOrHeight.match_parent.equals(value)) {
            return ViewGroup.LayoutParams.MATCH_PARENT;
        } else if (WidthOrHeight.wrap_content.equals(value)) {
            return ViewGroup.LayoutParams.WRAP_CONTENT;
        }
        return DensityUtil.dp2px(Float.parseFloat(value));
    }

    @Keep
    @StringDef()
    @Retention(value = RetentionPolicy.SOURCE)
    public @interface WidthOrHeight {
        String match_parent = "fill";
        String wrap_content = "auto";
    }
}