package com.bob.android.morph.translator;

import android.graphics.Typeface;
import android.support.annotation.Keep;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * created by cly on 2019-09-04
 */
public class TextStyleTranslator {

    public static Typeface trans(String style) {
        if (TextStyle.bold.equals(style)) {
            return Typeface.DEFAULT_BOLD;
        }
        return Typeface.DEFAULT;
    }

    @Keep
    @StringDef
    @Retention(value = RetentionPolicy.SOURCE)
    public @interface TextStyle {
        String bold = "bold";
    }
}
