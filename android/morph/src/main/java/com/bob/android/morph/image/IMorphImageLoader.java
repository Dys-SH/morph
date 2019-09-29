package com.bob.android.morph.image;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bob.android.morph.translator.ScaleTypeTranslator;


/**
 * created by cly on 2019-09-06
 */
public interface IMorphImageLoader {
    void loadImage(Context context, String model, int radius, CornerType cornerType,
                   @ScaleTypeTranslator.ScaleTypeValue String scaleType, ImageView view);

    void loadDrawable(Context context, String model, int radius, CornerType cornerType, final View view);
}
