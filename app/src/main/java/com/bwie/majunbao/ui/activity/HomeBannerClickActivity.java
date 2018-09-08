package com.bwie.majunbao.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import com.bwie.majunbao.R;

public class HomeBannerClickActivity extends AppCompatActivity {

    private WebView web_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_banner_click);
        web_view = findViewById(R.id.home_banner_webview);
        //获取intent值
        Intent intent = getIntent();
        String url = intent.getStringExtra("banner_url");
        if (url!=null) {
            web_view.loadUrl(url);
        }
    }
}
