package com.bob.android.morph.translator;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * created by cly on 2019-09-09
 */
public class ScaleTypeTranslator {

    @StringDef
    @Retention(RetentionPolicy.SOURCE)
    public @interface ScaleTypeValue {
        String CENTER = "center";
        String ASPECT_Fill = "aspectFill";
    }
}
