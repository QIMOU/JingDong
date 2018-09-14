package com.bwie.majunbao.app;

import com.facebook.drawee.backends.pipeline.Fresco;

import majunbao.bwie.com.jingdong_base_marster.base.BaseApp;

public class Application extends BaseApp {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
