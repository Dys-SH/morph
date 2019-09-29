package com.bob.android.morph.util;

import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * created by cly on 2019-09-06
 */
public final class TextUtil {
    public static boolean isEmpty(String text) {
        return TextUtils.isEmpty(text);
    }

    public static boolean isNotEmpty(String text) {
        return !isEmpty(text);
    }

    /**
     * 判断字符串是否为整数
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        if (TextUtils.isEmpty(str)) {
            return true;
        } else {
            return !pattern.matcher(str).matches();
        }
    }
}
