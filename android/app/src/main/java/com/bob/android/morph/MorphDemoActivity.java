package com.bob.android.morph;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.bob.android.morph.translator.Parser;


public class MorphDemoActivity extends AppCompatActivity {

    private ViewGroup mViewGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morph_demo);
        mViewGroup = findViewById(R.id.root_view);

        String jsonView = getIntent().getStringExtra("json_view");
        String jsonData = "";
        View view = Parser.parser(this, jsonView, jsonData, null);
        mViewGroup.addView(view);
    }
}
