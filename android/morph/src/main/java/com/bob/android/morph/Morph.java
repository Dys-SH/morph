package com.bob.android.morph;

import android.content.Context;
import android.view.View;

import com.bob.android.morph.manager.net.MorphHttpClientHolder;
import com.bob.android.morph.manager.net.MorphKeyManager;
import com.bob.android.morph.translator.Parser;
import com.bob.android.morph.util.ContextUtil;
import com.bob.android.morph.util.MainThreadDelivery;

import okhttp3.OkHttpClient;

/**
 * todo 1、异常处理 2、性能优化（1、jsonData缓存策略。2、jsonView复用）
 * created by cly on 2019-09-24
 */
public final class Morph {

    public static void init(Context context, OkHttpClient okHttpClient) {
        ContextUtil.init(context);
//        MorphHttpClientHolder.init(okHttpClient);
    }

    public static void morph(final Context context, String key, final String jsonData,
                             final OnMorphResult onMorphResult, final Parser.OnMorphClick onMorphClick) {
        MorphKeyManager.getInstance().queryJsonView(key, new MorphKeyManager.OnQueryJsonViewCallBack() {
            @Override
            public void onSucc(String jsonView) {
                final View view = Parser.parser(context, jsonView, jsonData, onMorphClick);
                if (onMorphResult != null) {
                    MainThreadDelivery.getInstance().deliver(new Runnable() {
                        @Override
                        public void run() {
                            onMorphResult.onMorphSucc(view);
                        }
                    });
                }
            }
        });
    }

    public interface OnMorphResult {
        void onMorphSucc(View view);

        void onMorphFailed(String msg);
    }
}
