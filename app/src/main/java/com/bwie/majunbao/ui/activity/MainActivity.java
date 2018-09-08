package com.bwie.majunbao.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.bwie.majunbao.R;
import com.bwie.majunbao.ui.fragment.CartFragment;
import com.bwie.majunbao.ui.fragment.ClassFragment;
import com.bwie.majunbao.ui.fragment.FindFragment;
import com.bwie.majunbao.ui.fragment.HomeFragment;
import com.bwie.majunbao.ui.fragment.MyFragment;
import com.gyf.barlibrary.ImmersionBar;
import com.hjm.bottomtabbar.BottomTabBar;

import majunbao.bwie.com.jingdong_base_marster.base.base_ui.BaseActivity;

public class MainActivity extends BaseActivity {

    private BottomTabBar mBottomTabBar;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        super.initView();
        mBottomTabBar
                .setFontSize(0)//设置文字大小
                .addTabItem("", R.drawable.selected_drawable_home, HomeFragment.class)
                .addTabItem("", R.drawable.selected_drawable_find, ClassFragment.class)
                .addTabItem("", R.drawable.selected_drawable_class, FindFragment.class)
                .addTabItem("", R.drawable.selected_drawable_cart, CartFragment.class)
                .addTabItem("", R.drawable.selected_drawable_my, MyFragment.class)
                .isShowDivider(false)
                .setOnTabChangeListener(new BottomTabBar.OnTabChangeListener() {
                    @Override
                    public void onTabChange(int position, String name, View view) {
                        if (position == 1) {
                            mBottomTabBar.setSpot(1, false);
                        }
                    }
                });
    }

    @Override
    protected void initData() {
        super.initData();
        //初始化沉浸式
        //ImmersionBar.with(this).init();
        //初始化控件
        mBottomTabBar = findViewById(R.id.bottom_tab_bar);
        mBottomTabBar.init(getSupportFragmentManager());
    }
}
