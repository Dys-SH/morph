package com.bob.android.morph.customView;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;

import com.bob.android.morph.bean.Weight;
import com.bob.android.morph.util.MorphImageLoaderUtil;
import com.bob.android.morph.util.OperationConversionUtil;
import com.bob.android.morph.util.TextUtil;
import com.google.android.flexbox.FlexboxLayout;

/**
 * created by cly on 2019-09-06
 */
public class MorphFlexBoxLayout extends FlexboxLayout implements IMorphView {
    private GradientDrawable gd_background = new GradientDrawable();
    private Weight weight;

    private int backgroundColor;
    private String backgroundImage;
    private float cornerRadius;
    private float topLeftRadius;
    private float topRightRadius;
    private float bottomRightRadius;
    private float bottomLeftRadius;
    private int strokeWidth;
    private int strokeColor;
    private boolean isRadiusHalfHeight;
    private boolean isWidthHeightEqual;

    private String jsonData;

    public MorphFlexBoxLayout(Context context) {
        this(context, null);
    }

    public MorphFlexBoxLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MorphFlexBoxLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (isWidthHeightEqual() && getWidth() > 0 && getHeight() > 0) {
            int max = Math.max(getWidth(), getHeight());
            int measureSpec = MeasureSpec.makeMeasureSpec(max, MeasureSpec.EXACTLY);
            super.onMeasure(measureSpec, measureSpec);
            return;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (isRadiusHalfHeight()) {
            setCornerRadius((float) (getHeight() / 2));
        } else {
            setBgSelector();
        }
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setBackgroundImage(String src) {
        this.backgroundImage = src;
    }

    public void setCornerRadius(float cornerRadius) {
        this.cornerRadius = cornerRadius;
    }

    public void setTopLeftRadius(float topLeftRadius) {
        this.topLeftRadius = topLeftRadius;
    }

    public void setTopRightRadius(float topRightRadius) {
        this.topRightRadius = topRightRadius;
    }

    public void setBottomRightRadius(float bottomRightRadius) {
        this.bottomRightRadius = bottomRightRadius;
    }

    public void setBottomLeftRadius(float bottomLeftRadius) {
        this.bottomLeftRadius = bottomLeftRadius;
    }

    public void setCornerRadiusPx(float cornerRadiusPx) {
        this.cornerRadius = cornerRadiusPx;
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = (int) strokeWidth;
    }

    public void setStrokeColor(int strokeColor) {
        this.strokeColor = strokeColor;
    }

    public void setIsRadiusHalfHeight(boolean isRadiusHalfHeight) {
        this.isRadiusHalfHeight = isRadiusHalfHeight;
    }

    public void setIsWidthHeightEqual(boolean isWidthHeightEqual) {
        this.isWidthHeightEqual = isWidthHeightEqual;
    }

    private boolean isRadiusHalfHeight() {
        return isRadiusHalfHeight;
    }

    private boolean isWidthHeightEqual() {
        return isWidthHeightEqual;
    }

    private void setDrawable(GradientDrawable gd, int color, int strokeColor) {
        gd.setColor(color);
        gd.setStroke(strokeWidth, strokeColor);
        if (cornerRadius > 0) {
            gd.setCornerRadius(cornerRadius);
        } else {
            gd.setCornerRadii(new float[]{
                    topLeftRadius, topLeftRadius,
                    topRightRadius, topRightRadius,
                    bottomRightRadius, bottomRightRadius,
                    bottomLeftRadius, bottomLeftRadius
            });
        }
    }

    private void setBgSelector() {
        StateListDrawable bg = new StateListDrawable();
        setDrawable(gd_background, backgroundColor, strokeColor);
        bg.addState(new int[]{-android.R.attr.state_pressed}, gd_background);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {//16
            if (TextUtil.isNotEmpty(backgroundImage)) {
                MorphImageLoaderUtil.loadImage(getContext(), this, weight);
            } else {
                setBackground(bg);
            }
        } else {
            if (TextUtil.isNotEmpty(backgroundImage)) {
                MorphImageLoaderUtil.loadImage(getContext(), this, weight);
            } else {
                setBackgroundDrawable(bg);
            }
        }
    }

    @Override
    public void loadBacAndBorderAndCorner(Weight weight) {
        this.weight = weight;
        //背景
        if (TextUtil.isNotEmpty(weight.getBackgroundImage())) {
            setBackgroundImage(weight.getBackgroundImage());
        }
        int backgroundColor = OperationConversionUtil.getColor(weight.getBackgroundColor(), jsonData);
        if (backgroundColor != 0) {
            setBackgroundColor(backgroundColor);
        }
        //边框
        if (weight.getBorderWidth() > 0) {
            setStrokeWidth(weight.getBorderWidth());
        }
        if (weight.getBorderColor() != 0) {
            setStrokeColor(weight.getBorderColor());
        }
        //圆角
        if (weight.getCornerRadius() > 0) {
            setCornerRadius(weight.getCornerRadius());
        } else {
            setTopLeftRadius(weight.getTopLeftRadius());
            setTopRightRadius(weight.getTopRightRadius());
            setBottomLeftRadius(weight.getBottomLeftRadius());
            setBottomRightRadius(weight.getBottomRightRadius());
        }
    }
}