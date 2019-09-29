package com.bob.android.morph.translator;

import android.support.annotation.Keep;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * created by cly on 2019-09-03
 */
public class TypeTranslator {

    public static boolean isFlexLayout(@Type String type) {
        return Type.FlexLayout.equals(type);
    }

    public static boolean isTextView(@Type String type) {
        return Type.TextView.equals(type);
    }

    public static boolean isImageView(@Type String type) {
        return Type.ImageView.equals(type);
    }

    @Keep
    @StringDef()
    @Retention(value = RetentionPolicy.SOURCE)
    public @interface Type {
        String FlexLayout = "view";
        String TextView = "text";
        String ImageView = "image";
    }
}