package com.bob.android.morph.translator;

import android.support.annotation.StringDef;
import android.view.Gravity;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * created by cly on 2019-09-09
 */
public class TextAlignTranslator {

    public static int trans(String value) {
        if (TextAlignValue.CENTER.equals(value)) {
            return Gravity.CENTER;
        }
        return Gravity.TOP;
    }

    @StringDef
    @Retention(RetentionPolicy.SOURCE)
    private @interface TextAlignValue {
        String CENTER = "center";
    }
}
