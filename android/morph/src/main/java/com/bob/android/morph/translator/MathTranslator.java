package com.bob.android.morph.translator;

import android.support.annotation.StringDef;

import com.bob.android.morph.bean.Math;
import com.bob.android.morph.util.JsonUtil;
import com.google.gson.internal.LinkedTreeMap;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * created by cly on 2019-09-19
 */
public class MathTranslator {

    public static float mathTrans(Object mathObject, String jsonData) {
        String mathJson = JsonUtil.toJson(mathObject);
        Math math = JsonUtil.fromJson(mathJson, Math.class);
        Object left = math.getLeft();
        Object right = math.getRight();
        float leftFloat;
        if (isJsonObject(left)) {
            leftFloat = mathTrans(left, jsonData);
        } else {
            leftFloat = Float.parseFloat(JsonDataTranslator.transJsonData(jsonData, left.toString()));
        }
        float rightFloat;
        if (isJsonObject(right)) {
            rightFloat = mathTrans(right, jsonData);
        } else {
            rightFloat = Float.parseFloat(JsonDataTranslator.transJsonData(jsonData, right.toString()));
        }
        return math(leftFloat, rightFloat, math.getMath());
    }

    private static boolean isJsonObject(Object object) {
        return object instanceof LinkedTreeMap;
    }

    private static float math(float leftValue, float rightValue, String math) {
        switch (math) {
            case MathValue.ADD:
                return leftValue + rightValue;
            case MathValue.SUBTRACTION:
                return leftValue - rightValue;
            case MathValue.MULTIPLICATION:
                return leftValue * rightValue;
            case MathValue.DIVISION:
                return leftValue / rightValue;
        }
        return 0;
    }

    @StringDef
    @Retention(RetentionPolicy.SOURCE)
    private @interface MathValue {
        String ADD = "+";
        String SUBTRACTION = "-";
        String MULTIPLICATION = "*";
        String DIVISION = "/";
    }
}
