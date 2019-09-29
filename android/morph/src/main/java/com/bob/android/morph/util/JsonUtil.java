package com.bob.android.morph.util;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * created by cly on 2019-09-24
 */
public class JsonUtil {

    /**
     * It is used for json pretty print
     */
    private static final int JSON_INDENT = 2;
    private static final String EMPTY_JSON = "{}";        // 空的JSON数据
    private static final String EMPTY_JSON_ARRAY = "[]"; // 空的数组(集合)数据
    private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss"; // 默认的日期，时间字段的格式化模式
    private static final boolean DEFAULT_EXCLUDES_FIELDS_WITHOUT_EXPOSE = false; //是否排除未标记字段(默认值)


    /**
     * 将给定的目标对象根据所指定的条件参数转换成JSON格式的字符串
     */
    public static String toJson(Object target) {
        return toJson(target, null, false, null, null, DEFAULT_EXCLUDES_FIELDS_WITHOUT_EXPOSE);
    }

    public static String toJson(Object target, Type targetType) {
        return toJson(target, targetType, false, null, null,
                DEFAULT_EXCLUDES_FIELDS_WITHOUT_EXPOSE);
    }

    public static String toJson(Object target, Type targetType, Double version) {
        return toJson(target, targetType, false, version, null,
                DEFAULT_EXCLUDES_FIELDS_WITHOUT_EXPOSE);
    }

    public static String toJson(Object target, Type targetType, boolean
            excludesFieldsWithoutExpose) {
        return toJson(target, targetType, false, null, null, excludesFieldsWithoutExpose);
    }

    public static String toJson(Object target, Type targetType, Double version, boolean
            excludesFieldsWithoutExpose) {
        return toJson(target, targetType, false, version, null, excludesFieldsWithoutExpose);
    }

    public static String toJson(Object target, boolean excludesFieldsWithoutExpose) {
        return toJson(target, null, false, null, null, excludesFieldsWithoutExpose);
    }

    /**
     * 将给定的目标对象根据所指定的条件参数转换成JSON格式的字符串
     *
     * @param target                      目标对象
     * @param targetType                  目标对象的类型
     * @param isSerializeNulls            是否序列化Null值字段
     * @param version                     字段的版本号注解
     * @param datePattern                 日期字段的格式化模式
     * @param excludesFieldsWithoutExpose 是否排除未标注@Expose注解的字段
     * @return 目标对象的JSON格式的字符串
     */
    public static String toJson(Object target, Type targetType, boolean isSerializeNulls, Double
            version, String datePattern, boolean excludesFieldsWithoutExpose) {
        if (target == null)
            return EMPTY_JSON;
        GsonBuilder builder = new GsonBuilder();
        if (isSerializeNulls)
            builder.serializeNulls();
        if (version != null)
            builder.setVersion(version.doubleValue());
        if (TextUtils.isEmpty(datePattern))
            datePattern = DEFAULT_DATE_PATTERN;
        builder.setDateFormat(datePattern);
        if (excludesFieldsWithoutExpose)
            builder.excludeFieldsWithoutExposeAnnotation();
        return toJson(target, targetType, builder);
    }

    /**
     * 将给定的目标对象根据所指定的条件参数转换成JSON格式的字符串。
     * 该方法转换发生错误时，不会抛出任何异常。若发生错误时，对象返回"{}"，集合或数组对象返回 "[]"， 其本基本类型，返回相应的基本值
     *
     * @param target     目标对象。
     * @param targetType 目标对象的类型
     * @param builder    可定制的Gson构建器
     * @return 目标对象的JSON格式的字符串
     */
    public static String toJson(Object target, Type targetType, GsonBuilder builder) {
        if (target == null)
            return EMPTY_JSON;
        Gson gson = null;
        if (builder == null) {
            gson = new Gson();
        } else {
            gson = builder.create();
        }
        String result = EMPTY_JSON;
        try {
            if (targetType == null) {
                result = gson.toJson(target);
            } else {
                result = gson.toJson(target, targetType);
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (target instanceof Collection<?> || target instanceof Iterator<?> || target
                    instanceof Enumeration<?> || target.getClass().isArray()) {
                result = EMPTY_JSON_ARRAY;
            }
        }
        return result;
    }


    /*--------------------------------------------------------------------------
    | 将给定的目标对象根据所指定的条件参数转换成JSON格式的字符串
    --------------------------------------------------------------------------*/
    public static <T> T fromJson(String json, Class<T> cls) {
        return fromJson(json, cls, null);
    }

    public static <T> T fromJson(String json, Type type) {
        return fromJson(json, type, null);
    }

    public static <T> T fromJson(String json, TypeToken<T> token) {
        return fromJson(json, token, null);
    }

    /**
     * 将给定的JSON字符串转换成指定的类型对象
     *
     * @param json        给定的JSON字符串
     * @param cls         要转换的目标类
     * @param datePattern 日期格式
     * @return 给定的JSON字符串表示的指定的类型对象
     */
    public static <T> T fromJson(String json, Class<T> cls, String datePattern) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        GsonBuilder builder = new GsonBuilder();
        if (TextUtils.isEmpty(datePattern)) {
            datePattern = DEFAULT_DATE_PATTERN;
        }
        builder.setDateFormat(datePattern);
        builder.registerTypeAdapter(new TypeToken<Map<String, Object>>() {
        }.getType(), new MapTypeAdapter()).create();

        Gson gson = builder.create();
        try {
            return gson.fromJson(json, cls);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T fromJson(String json, Type type, String datePattern) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        GsonBuilder builder = new GsonBuilder();
        if (TextUtils.isEmpty(datePattern)) {
            datePattern = DEFAULT_DATE_PATTERN;
        }
        builder.setDateFormat(datePattern);
        builder.registerTypeAdapter(new TypeToken<Map<String, Object>>() {
        }.getType(), new MapTypeAdapter()).create();

        Gson gson = builder.create();
        try {
            return gson.fromJson(json, type);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将给定的JSON字符串转换成指定的类型对象
     *
     * @param json        给定的JSON字符串
     * @param token       类型
     * @param datePattern 日期格式
     * @return 给定的JSON字符串表示的指定的类型对象
     */
    public static <T> T fromJson(String json, TypeToken<T> token, String datePattern) {
        return fromJson(json, token.getType(), datePattern);
    }

    public static boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }

    public static boolean isJsonArray(String json) {
        try {
            new JSONArray(json);
        } catch (JSONException ex) {
            return false;
        }
        return true;
    }

    public static Map<String, Object> jsonToMap(JSONObject json) throws JSONException {
        Map<String, Object> retMap = new HashMap<String, Object>();

        if (json != JSONObject.NULL) {
            retMap = toMap(json);
        }
        return retMap;
    }

    public static <T> List<T> jsonToList(String json, Class<T> cls) {
        try {
            JSONArray jsonArray = new JSONArray(json);
            List<T> list = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                T t = fromJson(jsonArray.getString(i), cls);
                list.add(t);
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Map<String, Object> toMap(JSONObject object) throws JSONException {
        Map<String, Object> map = new HashMap<String, Object>();

        Iterator<String> keysItr = object.keys();
        while (keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);

            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    private static List<Object> toList(JSONArray array) throws JSONException {
        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }


    /**
     * 解决 gson 默认将 int 转换成 double 问题
     */
    public static class MapTypeAdapter extends TypeAdapter<Object> {

        @Override
        public Object read(JsonReader in) throws IOException {
            JsonToken token = in.peek();
            switch (token) {
                case BEGIN_ARRAY:
                    List<Object> list = new ArrayList<Object>();
                    in.beginArray();
                    while (in.hasNext()) {
                        list.add(read(in));
                    }
                    in.endArray();
                    return list;

                case BEGIN_OBJECT:
                    Map<String, Object> map = new LinkedTreeMap<String, Object>();
                    in.beginObject();
                    while (in.hasNext()) {
                        map.put(in.nextName(), read(in));
                    }
                    in.endObject();
                    return map;

                case STRING:
                    return in.nextString();

                case NUMBER:
                    /**
                     * 改写数字的处理逻辑，将数字值分为整型与浮点型。
                     */
                    double dbNum = in.nextDouble();

                    // 数字超过long的最大值，返回浮点类型
                    if (dbNum > Long.MAX_VALUE) {
                        return dbNum;
                    }

                    // 判断数字是否为整数值
                    long lngNum = (long) dbNum;
                    if (dbNum == lngNum) {
                        return lngNum;
                    } else {
                        return dbNum;
                    }

                case BOOLEAN:
                    return in.nextBoolean();

                case NULL:
                    in.nextNull();
                    return null;

                default:
                    throw new IllegalStateException();
            }
        }

        @Override
        public void write(JsonWriter out, Object value) throws IOException {
            // 序列化无需实现
        }

    }

    public static String transToJson(String json) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }

        if (!JsonUtil.isJSONValid(json)) {
            return json;
        }

        try {
            json = json.trim();
            if (json.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(json);
                return jsonObject.toString(JSON_INDENT);
            }
            if (json.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(json);
                return jsonArray.toString(JSON_INDENT);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }


}
