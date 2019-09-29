package com.bob.android.morph.manager.net;

import okhttp3.OkHttpClient;

/**
 * created by cly on 2019-09-25
 */
public class MorphHttpClientHolder {

    private static OkHttpClient instance;

    public static OkHttpClient getInstance() {
        if (instance == null) {
            throw new NullPointerException(" MorphHttpClientHolder not init! ");
        }
        return instance;
    }

    public static void init(OkHttpClient okHttpClient) {
        instance = okHttpClient.newBuilder().build();
    }
}
