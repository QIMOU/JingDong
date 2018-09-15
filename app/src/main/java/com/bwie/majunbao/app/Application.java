package com.bwie.majunbao.app;

import android.os.Build;
import android.os.StrictMode;

import com.facebook.drawee.backends.pipeline.Fresco;

import majunbao.bwie.com.jingdong_base_marster.base.BaseApp;

public class Application extends BaseApp {
    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        Fresco.initialize(this);
    }
}
