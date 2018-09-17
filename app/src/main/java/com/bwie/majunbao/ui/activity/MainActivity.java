package com.bwie.majunbao.ui.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bwie.majunbao.R;
import com.bwie.majunbao.eventbus.IntentActivityEventbus;
import com.bwie.majunbao.ui.adapter.MyCartAdapter;
import com.bwie.majunbao.ui.adapter.MyFragmentAdapter;
import com.bwie.majunbao.ui.fragment.CartFragment;
import com.bwie.majunbao.ui.fragment.ClassFragment;
import com.bwie.majunbao.ui.fragment.FindFragment;
import com.bwie.majunbao.ui.fragment.HomeFragment;
import com.bwie.majunbao.ui.fragment.MyFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import majunbao.bwie.com.jingdong_base_marster.base.base_ui.BaseActivity;

public class MainActivity extends BaseActivity {


    @BindView(R.id.fram)
    ViewPager mViewPager;
    @BindView(R.id.rb1)
    RadioButton rb1;
    @BindView(R.id.rb2)
    RadioButton rb2;
    @BindView(R.id.rb3)
    RadioButton rb3;
    @BindView(R.id.rb4)
    RadioButton rb4;
    @BindView(R.id.rb5)
    RadioButton rb5;
    @BindView(R.id.radio_group)
    RadioGroup radioGroup;
    private ArrayList<Fragment> list;
    private CartFragment mCartFragment;
    private HomeFragment mHomeFragment;
    private FindFragment mFindFragment;
    private ClassFragment mClassFragment;
    private MyFragment mMyFragment;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        super.initView();
        //设置点击
        //创建集合
        list = new ArrayList<Fragment>();
        //创建fragment
        mHomeFragment = new HomeFragment();
        mFindFragment = new FindFragment();
        mClassFragment = new ClassFragment();
        mCartFragment = new CartFragment();
        mMyFragment = new MyFragment();
        //把fragment加入集合
        list.add(mHomeFragment);
        list.add(mFindFragment);
        list.add(mClassFragment);
        list.add(mCartFragment);
        list.add(mMyFragment);
        //创建适配器
        MyFragmentAdapter adapter=new MyFragmentAdapter(getSupportFragmentManager(),list);
        //添加到视频日期
        mViewPager.setAdapter(adapter);
        setOnClick();
    }

    private void setOnClick() {
        //点击radiogroup进行监听
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.rb1:
                        mViewPager.setCurrentItem(0);
                        break;
                    case R.id.rb2:
                        mViewPager.setCurrentItem(1);
                        break;
                    case R.id.rb3:
                        mViewPager.setCurrentItem(2);
                        break;
                    case R.id.rb4:
                        mViewPager.setCurrentItem(3);
                        break;
                    case R.id.rb5:
                        mViewPager.setCurrentItem(4);
                        break;
                }
            }
        });
        //设置默认显示第一条
        radioGroup.check(R.id.rb1);
        //设置切换不需要刷新,4代表总共5个页面
        mViewPager.setOffscreenPageLimit(4);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initData() {
        super.initData();
        //注册
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
    //处理事件
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void Event(IntentActivityEventbus intentActivityEventbus) {
        mViewPager.setCurrentItem(3);
        radioGroup.check(R.id.rb4);
    }
}
