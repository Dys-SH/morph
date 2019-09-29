package com.bob.android.morph.translator;

import android.support.annotation.StringDef;

import com.google.android.flexbox.FlexWrap;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * created by cly on 2019-09-10
 */
public class FlexWrapTranslator {

    public static int trans(String value) {
        if (FlexWrapValue.wrap.equals(value)) {
            return FlexWrap.WRAP;
        } else if (FlexWrapValue.WRAP_REVERSE.equals(value)) {
            return FlexWrap.WRAP_REVERSE;
        }
        return FlexWrap.NOWRAP;
    }

    @StringDef
    @Retention(value = RetentionPolicy.SOURCE)
    private @interface FlexWrapValue {
        String NOWRAP = "nowrap";
        String wrap = "wrap";
        String WRAP_REVERSE = "wrap-reverse";
    }
}
