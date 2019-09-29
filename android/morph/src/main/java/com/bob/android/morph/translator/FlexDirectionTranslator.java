package com.bob.android.morph.translator;

import android.support.annotation.Keep;
import android.support.annotation.StringDef;

import com.google.android.flexbox.FlexDirection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * created by cly on 2019-09-03
 */
public class FlexDirectionTranslator {

    public static int trans(@FlexDirectionType String flexDirection) {
        if (FlexDirectionType.column.equals(flexDirection)) {
            return FlexDirection.COLUMN;
        } else if (FlexDirectionType.column_reverse.equals(flexDirection)) {
            return FlexDirection.COLUMN_REVERSE;
        } else if (FlexDirectionType.row_reverse.equals(flexDirection)) {
            return FlexDirection.ROW_REVERSE;
        }
        return FlexDirection.ROW;
    }

    @Keep
    @StringDef()
    @Retention(value = RetentionPolicy.SOURCE)
    public @interface FlexDirectionType {
        String column = "column";
        String column_reverse = "column-reverse";
        String row = "row";
        String row_reverse = "row-reverse";
    }
}
