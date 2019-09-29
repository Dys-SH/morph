package com.bob.android.morph.util;

import android.view.View;
import android.view.ViewGroup;

import com.bob.android.morph.bean.Weight;


/**
 * created by cly on 2019-09-09
 */
public final class MarginOrPaddingUtil {

    public static void setMarginsAndPadding(View view, Weight weight, ViewGroup.MarginLayoutParams marginLayoutParams) {
        int margin = weight.getMargin();
        if (margin > 0) {
            marginLayoutParams.setMargins(margin, margin, margin, margin);
        } else {
            marginLayoutParams.setMargins(weight.getMarginLeft(), weight.getMarginTop(), weight.getMarginRight(), weight.getMarginBottom());
        }
        int padding = weight.getPadding();
        if (padding > 0) {
            view.setPadding(padding, padding, padding, padding);
        } else {
            view.setPadding(weight.getPaddingLeft(), weight.getPaddingTop(), weight.getPaddingRight(), weight.getPaddingBottom());
        }
    }
}
