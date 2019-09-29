package com.bob.android.morph;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.bob.android.morph.bean.MorphEvent;
import com.bob.android.morph.translator.Parser;
import com.bob.android.util.WeexFileUtil;

public class MorphDynamicActivity extends AppCompatActivity {

    private final static String key1 = "morph_home_hot";

    private FrameLayout mFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morph_dynamic);
        mFrameLayout = findViewById(R.id.fm_demo);
    }

    public void show(View view) {
        String jsonData = WeexFileUtil.loadAssetsFileToString(this, "jsonView_demo/jj_recommend_data_1");
        Morph.morph(this, key1, jsonData,
                new Morph.OnMorphResult() {
                    @Override
                    public void onMorphSucc(View view) {
                        completeView(view);
                    }

                    @Override
                    public void onMorphFailed(String msg) {

                    }
                },
                new Parser.OnMorphClick() {
                    @Override
                    public void onClick(MorphEvent event) {

                    }
                });
    }

    private void completeView(View view) {
        mFrameLayout.removeAllViews();
        mFrameLayout.addView(view);
    }
}
