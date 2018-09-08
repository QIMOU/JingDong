package com.bwie.majunbao.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import com.bwie.majunbao.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import majunbao.bwie.com.jingdong_base_marster.base.base_ui.BaseActivity;

public class XiangQingActivity extends BaseActivity {
    @BindView(R.id.web_view)
    WebView webView;
    ////////////////////////////////////////////////////////////////////
    //                          _ooOoo_                               //
    //                         o8888888o                              //
    //                         88" . "88                              //
    //                         (| ^_^ |)                              //
    //                         O\  =  /O                              //
    //                      ____/`---'\____                           //
    //                    .'  \\|     |//  `.                         //
    //                   /  \\|||  :  |||//  \                        //
    //                  /  _||||| -:- |||||-  \                       //
    //                  |   | \\\  -  /// |   |                       //
    //                  | \_|  ''\---/''  |   |                       //
    //                  \  .-\__  `-`  ___/-. /                       //
    //                ___`. .'  /--.--\  `. . ___                     //
    //              ."" '<  `.___\_<|>_/___.'  >'"".                  //
    //            | | :  `- \`.;`\ _ /`;.`/ - ` : | |                 //
    //            \  \ `-.   \_ __\ /__ _/   .-` /  /                 //
    //      ========`-.____`-.___\_____/___.-`____.-'========         //
    //                           `=---='                              //
    //      ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^        //
    //         佛祖保佑       永无BUG     永不修改                  //
    ////////////////////////////////////////////////////////////////////

    @Override
    protected int setLayoutId() {
        return R.layout.activity_xiang_qing;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        super.initView();
        //初始化eventbus
        //EventBus.getDefault().register(this);
        //获取intent值
        Intent intent = getIntent();
        String url = intent.getStringExtra("miaosha_url");
        if (url!=null) {
            webView.loadUrl(url);
        }
    }

 /*   @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void event(String miaosha_url){
        Log.i("ddd",miaosha_url+"123");
        if (miaosha_url!=null) {
            webView.loadUrl(miaosha_url);
        }
    }*/


    public void detailsCar(View view) {
    }

    public void detailsAddcar(View view) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //EventBus.getDefault().unregister(this);
    }
}
