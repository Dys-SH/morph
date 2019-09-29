package com.bob.android.morph.manager.net;

import com.bob.android.morph.bean.RespJsonView;
import com.bob.android.morph.manager.MorphConstants;
import com.bob.android.morph.util.JsonUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * created by cly on 2019-09-26
 */
public class MorphKeyManager {

    private static MorphKeyManager instance;

    public synchronized static MorphKeyManager getInstance() {
        if (instance == null) {
            instance = new MorphKeyManager();
        }
        return instance;
    }

    public void queryJsonView(String key, final OnQueryJsonViewCallBack onQueryJsonViewCallBack) {
        Request request = new Request.Builder().url(getKeyUrl(key)).build();
        MorphHttpClientHolder.getInstance().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    ResponseBody body = response.body();
                    if (body != null) {
                        RespJsonView respJsonView = JsonUtil.fromJson(body.string(), RespJsonView.class);
                        if (respJsonView.getResultCode() == MorphConstants.NET_SUCC) {
                            onQueryJsonViewCallBack.onSucc(respJsonView.getDatas());
                        }
                    }
                }
            }
        });
    }

    private String getKeyUrl(String key) {
        return MorphConstants.KEY_DETAIL_URL + key;
    }

    public interface OnQueryJsonViewCallBack {
        void onSucc(String jsonView);
    }
}
