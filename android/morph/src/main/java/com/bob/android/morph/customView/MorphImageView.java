package com.bob.android.morph.customView;


import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.bob.android.morph.bean.Weight;
import com.bob.android.morph.util.MorphImageLoaderUtil;

/**
 * // TODO: 2019-09-10 加载速度变慢
 * 在onLayout加载图片 确定获取到宽高  glide加载（Failed to load resource）很大概率加载失败
 * created by cly on 2019-09-10
 */
public class MorphImageView extends AppCompatImageView {
    private Weight weight;
    private boolean hasLoad;

    public MorphImageView(Context context) {
        this(context, null);
    }

    public MorphImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MorphImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        hasLoad = false;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        loadImage();
    }

    public void setSrc(Weight weight) {
        this.weight = weight;
    }

    private void loadImage() {
        if (!hasLoad) {
            MorphImageLoaderUtil.loadImage(getContext(), this, weight);
            hasLoad = true;
        }
    }
}
