package com.bob.android.morph;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.bob.android.morph.bean.MorphEvent;
import com.bob.android.morph.translator.Parser;
import com.bob.android.util.WeexFileUtil;


public class JJRecommendActivity extends AppCompatActivity {
    private ViewGroup rootView;
    private String jsonView1, jsonView2, jsonData1, jsonData2;

    private String currentJsonView, currentJsonData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jjrecommend);
        rootView = findViewById(R.id.root_view);
        int code = getIntent().getIntExtra("code", 1);
        if (code == 1) {
            jsonView1 = WeexFileUtil.loadAssetsFileToString(this, "jsonView_demo/jj_recommend1");
            jsonView2 = WeexFileUtil.loadAssetsFileToString(this, "jsonView_demo/jj_recommend2");
            jsonData1 = WeexFileUtil.loadAssetsFileToString(this, "jsonView_demo/jj_recommend_data_1");
            jsonData2 = WeexFileUtil.loadAssetsFileToString(this, "jsonView_demo/jj_recommend_data_2");
        } else if (code == 2) {
            jsonView1 = WeexFileUtil.loadAssetsFileToString(this, "jsonView_demo/jj_news1");
            jsonView2 = WeexFileUtil.loadAssetsFileToString(this, "jsonView_demo/jj_news2");
            jsonData1 = WeexFileUtil.loadAssetsFileToString(this, "jsonView_demo/jj_news_data_1");
            jsonData2 = WeexFileUtil.loadAssetsFileToString(this, "jsonView_demo/jj_news_data_2");
        }
        currentJsonView = jsonView1;
        currentJsonData = jsonData1;
    }

    public void view1(View view) {
        parser(jsonView1, currentJsonData);
    }

    public void view2(View view) {
        parser(jsonView2, currentJsonData);
    }

    public void data1(View view) {
        parser(currentJsonView, jsonData1);
    }

    public void data2(View view) {
        parser(currentJsonView, jsonData2);
    }

    private void parser(String jsonView, String jsonData) {
        currentJsonView = jsonView;
        currentJsonData = jsonData;
        if (rootView.getChildCount() > 0) {
            rootView.removeAllViews();
        }
        long startTime = System.currentTimeMillis();
        View view = Parser.parser(this, jsonView, jsonData, new Parser.OnMorphClick() {
            @Override
            public void onClick(MorphEvent event) {
                Log.d("@cly_json", event.toString());
            }
        });
        rootView.addView(view);
        Log.d("@cly_parser_duration", " 耗时 -- " + (System.currentTimeMillis() - startTime));
    }
}
