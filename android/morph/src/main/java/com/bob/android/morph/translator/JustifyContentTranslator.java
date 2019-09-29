package com.bob.android.morph.translator;

import android.support.annotation.StringDef;
import android.text.TextUtils;

import com.google.android.flexbox.JustifyContent;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * created by cly on 2019-09-04
 */
public class JustifyContentTranslator {

    public static int trans(String justifyContent) {
        if (TextUtils.isEmpty(justifyContent)) return JustifyContent.FLEX_START;
        switch (justifyContent) {
            case JustifyContentValue.FLEX_START:
                return JustifyContent.FLEX_START;
            case JustifyContentValue.FLEX_END:
                return JustifyContent.FLEX_END;
            case JustifyContentValue.CENTER:
                return JustifyContent.CENTER;
            case JustifyContentValue.SPACE_BETWEEN:
                return JustifyContent.SPACE_BETWEEN;
            case JustifyContentValue.SPACE_AROUND:
                return JustifyContent.SPACE_AROUND;
            case JustifyContentValue.SPACE_EVENLY:
                return JustifyContent.SPACE_EVENLY;
            default:
                return JustifyContent.FLEX_START;
        }
    }

    @StringDef()
    @Retention(RetentionPolicy.SOURCE)
    private @interface JustifyContentValue {
        String FLEX_START = "flex-start";

        String FLEX_END = "flex-end";

        String CENTER = "center";

        String SPACE_BETWEEN = "space-between";

        String SPACE_AROUND = "space-around";

        String SPACE_EVENLY = "space-evenly";
    }
}
