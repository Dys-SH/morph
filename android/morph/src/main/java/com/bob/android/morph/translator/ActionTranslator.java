package com.bob.android.morph.translator;

import com.bob.android.morph.bean.MorphEvent;
import com.bob.android.morph.bean.WeightAction;
import com.bob.android.morph.util.JsonUtil;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;

/**
 * created by cly on 2019-09-24
 */
public final class ActionTranslator {

    public static MorphEvent trans(WeightAction weightAction, String jsonData) {
        MorphEvent event = new MorphEvent();
        event.setId(weightAction.getId());
        Object objectData = weightAction.getData();
        if (objectData != null) {
            if (isArrayObject(objectData) || isJsonObject(objectData)) {
                event.setData(JsonUtil.toJson(objectData));
            } else {
                event.setData(JsonDataTranslator.transJsonData(jsonData, objectData.toString()));
            }
        }
        return event;
    }

    private static boolean isArrayObject(Object object) {
        return object instanceof ArrayList;
    }

    private static boolean isJsonObject(Object object) {
        return object instanceof LinkedTreeMap;
    }
}
