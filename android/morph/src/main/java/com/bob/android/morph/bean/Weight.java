package com.bob.android.morph.bean;

import android.graphics.Color;
import android.graphics.Typeface;

import com.bob.android.morph.translator.AlignItemsTranslator;
import com.bob.android.morph.translator.FlexDirectionTranslator;
import com.bob.android.morph.translator.FlexWrapTranslator;
import com.bob.android.morph.translator.JustifyContentTranslator;
import com.bob.android.morph.translator.TextAlignTranslator;
import com.bob.android.morph.translator.TextStyleTranslator;
import com.bob.android.morph.translator.TypeTranslator;
import com.bob.android.morph.translator.WidthOrHeightTranslator;
import com.bob.android.morph.util.DensityUtil;
import com.bob.android.morph.util.TextUtil;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * created by cly on 2019-09-03
 */
public class Weight {
    private String type;

    //通用属性
    private String width;
    private String height;
    private int padding;
    @SerializedName("padding-left")
    private float paddingLeft;
    @SerializedName("padding-right")
    private float paddingRight;
    @SerializedName("padding-top")
    private float paddingTop;
    @SerializedName("padding-bottom")
    private float paddingBottom;
    private float margin;
    @SerializedName("margin-left")
    private float marginLeft;
    @SerializedName("margin-top")
    private float marginTop;
    @SerializedName("margin-right")
    private float marginRight;
    @SerializedName("margin-bottom")
    private float marginBottom;

    @SerializedName("border-width")
    private float borderWidth;
    @SerializedName("border-color")
    private String borderColor;

    @SerializedName("border-radius")
    private float cornerRadius;
    @SerializedName("border-top-left-radius")
    private float topLeftRadius;
    @SerializedName("border-top-right-radius")
    private float topRightRadius;
    @SerializedName("border-bottom-left-radius")
    private float bottomLeftRadius;
    @SerializedName("border-bottom-right-radius")
    private float bottomRightRadius;

    @SerializedName("background-color")
    private Object backgroundColor;
    @SerializedName("background-image")
    private String backgroundImage;

    @SerializedName("display")
    private Object display;

    //flexLayout特有属性
    @SerializedName("flex-direction")
    private String flexDirection;
    @SerializedName("align-items")
    private String alignItems;//副轴对齐方式
    @SerializedName("justify-content")
    private String justifyContent;//主轴对齐方式
    @SerializedName("flex-wrap")
    private String flexWrap;
    @SerializedName("flex")
    private int flexGrow;

    //imageView特有属性
    private String src;
    @SerializedName("scale-type")
    private String scaleType;

    //textView特有属性
    private Object text;
    @SerializedName("font-size")
    private int textSize;
    @SerializedName("color")
    private Object textColor;
    @SerializedName("font-weight")
    private String textStyle;
    @SerializedName("text-align")
    private String textAlign;
    @SerializedName("lines")
    private int lines;
    @SerializedName("line-height")
    private int lineHeight;

    private List<Weight> children;

    private WeightAction action;

    public boolean isFlexLayout() {
        return TypeTranslator.isFlexLayout(type);
    }

    public boolean isTextView() {
        return TypeTranslator.isTextView(type);
    }

    public boolean isImageView() {
        return TypeTranslator.isImageView(type);
    }

    public int getWidth() {
        return WidthOrHeightTranslator.trans(width);
    }

    public int getHeight() {
        return WidthOrHeightTranslator.trans(height);
    }

    public int getPadding() {
        return DensityUtil.dp2px(padding);
    }

    public int getPaddingLeft() {
        return DensityUtil.dp2px(paddingLeft);
    }

    public int getPaddingRight() {
        return DensityUtil.dp2px(paddingRight);
    }

    public int getPaddingTop() {
        return DensityUtil.dp2px(paddingTop);
    }

    public int getPaddingBottom() {
        return DensityUtil.dp2px(paddingBottom);
    }

    public int getMargin() {
        return DensityUtil.dp2px(margin);
    }

    public int getMarginLeft() {
        return DensityUtil.dp2px(marginLeft);
    }

    public int getMarginTop() {
        return DensityUtil.dp2px(marginTop);
    }

    public int getMarginRight() {
        return DensityUtil.dp2px(marginRight);
    }

    public int getMarginBottom() {
        return DensityUtil.dp2px(marginBottom);
    }

    public Object getBackgroundColor() {
        return backgroundColor;
    }

    public String getBackgroundImage() {
        return backgroundImage;
    }

    public int getFlexDirection() {
        return FlexDirectionTranslator.trans(flexDirection);
    }

    public int getAlignItems() {
        return AlignItemsTranslator.trans(alignItems);
    }

    public int getJustifyContent() {
        return JustifyContentTranslator.trans(justifyContent);
    }

    public String getSrc() {
        return src.trim();
    }

    public void setSrc(String trueSrc) {
        this.src = trueSrc;
    }

    public Object getText() {
        return text;
    }

    public int getTextSize() {
        return textSize == 0 ? 15 : textSize;
    }

    public Object getTextColor() {
        return textColor;
    }

    public Typeface getTextStyle() {
        return TextStyleTranslator.trans(textStyle);
    }

    public List<Weight> getChildren() {
        return children;
    }

    public WeightAction getAction() {
        return action;
    }

    public float getBorderWidth() {
        return DensityUtil.dp2px(borderWidth);
    }

    public int getBorderColor() {
        if (TextUtil.isNotEmpty(borderColor)) {
            try {
                return Color.parseColor(borderColor);
            } catch (Exception e) {
                return 0;
            }
        }
        return 0;
    }

    public int getTextAlign() {
        return TextAlignTranslator.trans(textAlign);
    }

    public int getLines() {
        return lines;
    }

    public float getCornerRadius() {
        return DensityUtil.dp2px(cornerRadius);
    }

    public float getTopLeftRadius() {
        return DensityUtil.dp2px(topLeftRadius);
    }

    public float getTopRightRadius() {
        return DensityUtil.dp2px(topRightRadius);
    }

    public float getBottomRightRadius() {
        return DensityUtil.dp2px(bottomRightRadius);
    }

    public float getBottomLeftRadius() {
        return DensityUtil.dp2px(bottomLeftRadius);
    }

    public String getScaleType() {
        return scaleType;
    }

    public int getFlexWrap() {
        return FlexWrapTranslator.trans(flexWrap);
    }

    public int getFlexGrow() {
        return flexGrow;
    }

    public int getLineHeight() {
        return DensityUtil.dp2px(lineHeight);
    }

    public Object getDisplay() {
        return display;
    }
}
