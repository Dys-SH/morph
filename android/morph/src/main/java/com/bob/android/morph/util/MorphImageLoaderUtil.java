package com.bob.android.morph.util;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bob.android.morph.bean.Weight;
import com.bob.android.morph.customView.MorphFlexBoxLayout;
import com.bob.android.morph.image.CornerType;
import com.bob.android.morph.image.MorphImageLoader;

/**
 * created by cly on 2019-09-09
 */
public final class MorphImageLoaderUtil {

    public static void loadImage(Context context, View view, Weight weight) {

        CornerType cornerType = CornerType.ALL;
        int radius = 0;
        if (weight.getCornerRadius() > 0) {
            radius = (int) weight.getCornerRadius();
        } else if (weight.getTopLeftRadius() > 0 && weight.getTopRightRadius() > 0) {
            radius = (int) weight.getTopLeftRadius();
            cornerType = CornerType.TOP;
        } else if (weight.getBottomLeftRadius() > 0 && weight.getBottomRightRadius() > 0) {
            radius = (int) weight.getBottomLeftRadius();
            cornerType = CornerType.BOTTOM;
        }
        if (view instanceof MorphFlexBoxLayout) {
            MorphImageLoader.getInstance().loadDrawable(context, weight.getBackgroundImage(), radius, cornerType, view);
        }

        if (view instanceof ImageView) {
            MorphImageLoader.getInstance().loadImage(context, weight.getSrc(), radius, cornerType, weight.getScaleType(), (ImageView) view);
        }
    }
}
