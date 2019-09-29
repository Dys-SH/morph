package com.bob.android.morph.util;

import android.graphics.Color;
import android.view.View;

import com.bob.android.morph.bean.Condition;
import com.bob.android.morph.bean.WeightAction;
import com.bob.android.morph.translator.ConditionTranslator;
import com.bob.android.morph.translator.DisplayTranslator;
import com.bob.android.morph.translator.MathTranslator;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * created by cly on 2019-09-18
 */
public final class OperationConversionUtil {

    public static int getColor(Object textColor, String jsonData) {
        if (textColor != null) {
            String color;
            if (isArrayObject(textColor)) {
                String jsonColor = JsonUtil.toJson(textColor);
                List<Condition> conditions = JsonUtil.fromJson(jsonColor, new TypeToken<List<Condition>>() {
                }.getType());
                Object object = ConditionTranslator.trans(conditions, jsonData);
                color = object.toString();
            } else {
                color = textColor.toString();
            }
            try {
                return Color.parseColor(color);
            } catch (IllegalArgumentException e) {
                return 0;
            }
        }
        return 0;
    }

    public static String getText(Object textObject, String jsonData) {
        String text = "";
        if (textObject != null) {
            if (isArrayObject(textObject)) {
                String jsonText = JsonUtil.toJson(textObject);
                List<Condition> conditions = JsonUtil.fromJson(jsonText, new TypeToken<List<Condition>>() {
                }.getType());
                Object object = ConditionTranslator.trans(conditions, jsonData);
                text = object.toString();
            } else if (isJsonObject(textObject)) {
                text = String.valueOf(MathTranslator.mathTrans(textObject, jsonData));
            } else {
                text = textObject.toString();
            }
        }
        return text;
    }

    public static int getDisplay(Object displayObject, String jsonData) {
        int display = View.VISIBLE;
        if (displayObject != null) {
            if (isArrayObject(displayObject)) {
                String jsonDisplay = JsonUtil.toJson(displayObject);
                List<Condition> conditions = JsonUtil.fromJson(jsonDisplay, new TypeToken<List<Condition>>() {
                }.getType());
                Object object = ConditionTranslator.trans(conditions, jsonData);
                String displayString = object.toString();
                display = DisplayTranslator.trans(displayString);
            } else {
                display = DisplayTranslator.trans(displayObject.toString());
            }
        }
        return display;
    }

    public static String getAction(WeightAction weightAction, String jsonData) {
        String result = "";

        return result;
    }

    private static boolean isArrayObject(Object object) {
        return object instanceof ArrayList;
    }

    private static boolean isJsonObject(Object object) {
        return object instanceof LinkedTreeMap;
    }
}
