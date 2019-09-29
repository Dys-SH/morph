package com.bob.android.morph.image;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bob.android.morph.translator.ScaleTypeTranslator;

/**
 * created by cly on 2019-09-04
 */
public class MorphImageLoader implements IMorphImageLoader {
    private static MorphImageLoader sMorphImageLoader = new MorphImageLoader();

    public static MorphImageLoader getInstance() {
        return sMorphImageLoader;
    }

    @Override
    public void loadImage(Context context, String model,
                          int radius, CornerType cornerType,
                          String scaleType, ImageView view) {
        CornersTransformation cornersTransformation = new CornersTransformation(radius, 0, cornerType);
        RequestOptions requestOptions = new RequestOptions();
        if (ScaleTypeTranslator.ScaleTypeValue.CENTER.equals(scaleType)) {
            requestOptions.transform(cornersTransformation);
        } else {
            CropTransformation cropTransformation = new CropTransformation(view.getWidth(), view.getHeight(), CropType.CENTER);
            requestOptions.transform(new MultiTransformation<>(cropTransformation, cornersTransformation));
        }
        Glide.with(context)
                .load(new GlideUrl(model.trim()))
                .apply(requestOptions)
                .into(view);
    }

    @Override
    public void loadDrawable(Context context, String model, int radius, CornerType cornerType, final View view) {
        CornersTransformation cornersTransformation = new CornersTransformation(radius, 0, cornerType);
        CropTransformation cropTransformation = new CropTransformation(view.getWidth(), view.getHeight(), CropType.CENTER);
        RequestOptions requestOptions = new RequestOptions()
                .transform(new MultiTransformation<>(cropTransformation, cornersTransformation));
        Glide.with(context)
                .load(new GlideUrl(model.trim()))
                .apply(requestOptions)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            view.setBackground(resource);
                        } else {
                            view.setBackgroundDrawable(resource);
                        }
                    }
                });
    }

}

