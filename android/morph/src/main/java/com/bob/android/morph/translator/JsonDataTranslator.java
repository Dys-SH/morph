package com.bob.android.morph.translator;

import android.text.TextUtils;

import com.bob.android.morph.util.JsonUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.regex.Pattern;

/**
 * created by cly on 2019-09-05
 */
public class JsonDataTranslator {
    private static final String TAG = "@cly_jsonParser";

    private static final String START = "<?";
    private static final String END = "?>";

    public static String transJsonData(String jsonData, String value) {
        if (TextUtils.isEmpty(value)) {
            return value;
        }
        if (value.startsWith(START) && value.endsWith(END)) {
            String extra = value.substring(START.length(), value.length() - END.length());
            return translate(jsonData, extra);
        }
        return value;
    }

    private static String translate(String jsonData, String extra) {
        String[] strings = extra.split("\\.");
        int length = strings.length;
        int i = 0;
        JsonArray jsonArray = new JsonArray();
        JsonObject jsonObject = new JsonParser().parse(jsonData).getAsJsonObject();
        String node = strings[i];
        String nextNode;
        while (i + 1 < length) {
            nextNode = strings[i + 1];
            if (isNumeric(node)) {
                jsonObject = jsonArray.get(Integer.parseInt(node)).getAsJsonObject();
            } else if (isNumeric(nextNode)) {
                jsonArray = jsonObject.getAsJsonArray(node);
            } else {
                jsonObject = jsonObject.getAsJsonObject(node);
            }
            i++;
            node = strings[i];
        }
        JsonElement jsonElement = jsonObject.get(node);
        if (jsonElement.isJsonObject() || jsonElement.isJsonArray()) {
            return JsonUtil.toJson(jsonElement);
        }
        return jsonElement.getAsString();
    }

    private static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        if (TextUtils.isEmpty(str)) {
            return false;
        } else {
            return pattern.matcher(str).matches();
        }
    }

}
