package com.bob.android.morph;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bob.android.util.WeexFileUtil;


public class MorphActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morph);
    }

    public void flexLayout(View view) {
        String jsonView = WeexFileUtil.loadAssetsFileToString(this, "view_demo/flex_layout");
        Intent intent = new Intent(this, MorphDemoActivity.class);
        intent.putExtra("json_view", jsonView);
        startActivity(intent);
    }

    public void textView(View view) {
        String jsonView = WeexFileUtil.loadAssetsFileToString(this, "view_demo/text_layout");
        Intent intent = new Intent(this, MorphDemoActivity.class);
        intent.putExtra("json_view", jsonView);
        startActivity(intent);
    }

    public void imageView(View view) {
        String jsonView = WeexFileUtil.loadAssetsFileToString(this, "view_demo/image_layout");
        Intent intent = new Intent(this, MorphDemoActivity.class);
        intent.putExtra("json_view", jsonView);
        startActivity(intent);
    }

    public void safeDemo(View view) {
        String jsonView = WeexFileUtil.loadAssetsFileToString(this, "jsonView_demo/safe_json");
        Intent intent = new Intent(this, MorphDemoActivity.class);
        intent.putExtra("json_view", jsonView);
        startActivity(intent);
    }

    public void jjRecommendDemo(View view) {
        Intent intent = new Intent(this, JJRecommendActivity.class);
        intent.putExtra("code", 1);
        startActivity(intent);
    }

    public void jjNewsDemo(View view) {
        Intent intent = new Intent(this, JJRecommendActivity.class);
        intent.putExtra("code", 2);
        startActivity(intent);
    }
}
