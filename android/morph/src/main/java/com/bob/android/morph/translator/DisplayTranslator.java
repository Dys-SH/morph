package com.bob.android.morph.translator;

import android.support.annotation.StringDef;
import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * created by cly on 2019-09-19
 */
public class DisplayTranslator {

    public static int trans(String value) {
        if (DisplayValue.NONE.equals(value)) {
            return View.GONE;
        }
        return View.VISIBLE;
    }

    @StringDef
    @Retention(RetentionPolicy.SOURCE)
    private @interface DisplayValue {
        String NONE = "none";
        String FLEX = "flex";
    }
}
