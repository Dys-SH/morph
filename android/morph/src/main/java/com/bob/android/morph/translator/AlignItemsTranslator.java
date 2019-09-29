package com.bob.android.morph.translator;

import android.support.annotation.StringDef;
import android.text.TextUtils;

import com.google.android.flexbox.AlignItems;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * created by cly on 2019-09-04
 */
public class AlignItemsTranslator {

    public static int trans(String alignItems) {
        if (TextUtils.isEmpty(alignItems)) return AlignItems.FLEX_START;
        switch (alignItems) {
            case AlignItemsValue.FLEX_START:
                return AlignItems.FLEX_START;
            case AlignItemsValue.FLEX_END:
                return AlignItems.FLEX_END;
            case AlignItemsValue.CENTER:
                return AlignItems.CENTER;
            case AlignItemsValue.BASELINE:
                return AlignItems.BASELINE;
            case AlignItemsValue.STRETCH:
                return AlignItems.STRETCH;
            default:
                return AlignItems.FLEX_START;
        }
    }

    @StringDef
    @Retention(RetentionPolicy.SOURCE)
    private @interface AlignItemsValue {
        String FLEX_START = "flex-start";

        String FLEX_END = "flex-end";

        String CENTER = "center";

        String BASELINE = "baseline";

        String STRETCH = "stretch";
    }
}
