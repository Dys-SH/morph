package com.bob.android;

import android.app.Application;

import com.bob.android.morph.Morph;

/**
 * created by cly on 2019-09-29
 */
public class MorphApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Morph.init(this, null);
    }
}
