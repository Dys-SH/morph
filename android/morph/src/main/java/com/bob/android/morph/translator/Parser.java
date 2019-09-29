package com.bob.android.morph.translator;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import com.bob.android.morph.bean.MorphEvent;
import com.bob.android.morph.bean.Weight;
import com.bob.android.morph.customView.MorphFlexBoxLayout;
import com.bob.android.morph.customView.MorphImageView;
import com.bob.android.morph.customView.MorphTextView;
import com.bob.android.morph.util.JsonUtil;
import com.bob.android.morph.util.MarginOrPaddingUtil;
import com.bob.android.morph.util.OperationConversionUtil;
import com.google.android.flexbox.FlexboxLayout;

import java.util.List;

/**
 * created by cly on 2019-09-03
 */
public class Parser {

    public static View parser(Context context, String jsonView, String jsonData, OnMorphClick onMorphClick) {
        Weight weight = JsonUtil.fromJson(jsonView, Weight.class);
        return parserLayout(context, weight, jsonData, onMorphClick);
    }

    private static MorphFlexBoxLayout parserLayout(Context context, Weight weight, String jsonData, OnMorphClick onMorphClick) {
        MorphFlexBoxLayout flexboxLayout = new MorphFlexBoxLayout(context);
        flexboxLayout.setJsonData(jsonData);
        FlexboxLayout.LayoutParams layoutParams = new FlexboxLayout.LayoutParams(weight.getWidth(), weight.getHeight());
        MarginOrPaddingUtil.setMarginsAndPadding(flexboxLayout, weight, layoutParams);
        if (weight.getFlexGrow() == 1) {
            layoutParams.setFlexGrow(1);
        }
        flexboxLayout.setLayoutParams(layoutParams);
        flexboxLayout.setFlexDirection(weight.getFlexDirection());
        flexboxLayout.setJustifyContent(weight.getJustifyContent());
        flexboxLayout.setAlignItems(weight.getAlignItems());
        flexboxLayout.setFlexWrap(weight.getFlexWrap());
        flexboxLayout.loadBacAndBorderAndCorner(weight);
        initAction(flexboxLayout, weight, jsonData, onMorphClick);

        List<Weight> weightList = weight.getChildren();
        if (weightList != null) {
            for (Weight weightChild : weightList) {
                View view = null;
                if (weightChild.isFlexLayout()) {
                    view = parserLayout(context, weightChild, jsonData, onMorphClick);
                } else if (weightChild.isTextView()) {
                    view = parserTv(context, weightChild, jsonData, onMorphClick);
                } else if (weightChild.isImageView()) {
                    view = parserIv(context, weightChild, jsonData, onMorphClick);
                }
                if (view != null) {
                    flexboxLayout.addView(view);
                }
            }
        }
        flexboxLayout.setVisibility(OperationConversionUtil.getDisplay(weight.getDisplay(), jsonData));
        return flexboxLayout;
    }

    private static MorphTextView parserTv(Context context, Weight weight, String jsonData, OnMorphClick onMorphClick) {
        MorphTextView textView = new MorphTextView(context);
        textView.setJsonData(jsonData);
        ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(weight.getWidth(), weight.getHeight());
        MarginOrPaddingUtil.setMarginsAndPadding(textView, weight, layoutParams);
        textView.setLayoutParams(layoutParams);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, weight.getTextSize());
        textView.setText(JsonDataTranslator.transJsonData(jsonData, OperationConversionUtil.getText(weight.getText(), jsonData)));

        int textColor = OperationConversionUtil.getColor(weight.getTextColor(), jsonData);
        if (textColor != 0) {
            textView.setTextColor(textColor);
        }
        textView.setTypeface(weight.getTextStyle());
        textView.setGravity(weight.getTextAlign());
        if (weight.getLines() > 0) {
            textView.setLines(weight.getLines());
        }
        if (weight.getLineHeight() > 0) {
            textView.setLineHeight(weight.getLineHeight());
        }
        textView.loadBacAndBorderAndCorner(weight);
        initAction(textView, weight, jsonData, onMorphClick);
        textView.setVisibility(OperationConversionUtil.getDisplay(weight.getDisplay(), jsonData));
        return textView;
    }

    private static MorphImageView parserIv(Context context, Weight weight, String jsonData, OnMorphClick onMorphClick) {
        MorphImageView imageView = new MorphImageView(context);
        ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(weight.getWidth(), weight.getHeight());
        MarginOrPaddingUtil.setMarginsAndPadding(imageView, weight, layoutParams);
        imageView.setLayoutParams(layoutParams);
        weight.setSrc(JsonDataTranslator.transJsonData(jsonData, weight.getSrc()));
//        MorphImageLoaderUtil.loadImage(context, imageView, weight);
        imageView.setSrc(weight);
//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        Glide.with(context)
//                .load("https://j3.dfcfw.com/images/AppHome/SYL_JN/161903.png")
//                .into(imageView);
        initAction(imageView, weight, jsonData, onMorphClick);
        imageView.setVisibility(OperationConversionUtil.getDisplay(weight.getDisplay(), jsonData));
        return imageView;
    }

    private static void initAction(View view, final Weight weight, final String jsonData, final OnMorphClick onMorphClick) {
        if (weight.getAction() != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onMorphClick != null)
                        onMorphClick.onClick(ActionTranslator.trans(weight.getAction(), jsonData));
                }
            });
        }
    }

    public interface OnMorphClick {
        void onClick(MorphEvent event);
    }
}
