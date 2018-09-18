package com.bwie.majunbao.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.majunbao.R;
import com.bwie.majunbao.contract.LoginContract;
import com.bwie.majunbao.contract.ProductContract;
import com.bwie.majunbao.entity.ProductEntity;
import com.bwie.majunbao.entity.UserEntity;
import com.bwie.majunbao.eventbus.LoginEventBus;
import com.bwie.majunbao.eventbus.RegistEventBus;
import com.bwie.majunbao.presenter.LoginPresenter;
import com.bwie.majunbao.utils.EncryptUtil;
import com.sdsmdg.tastytoast.TastyToast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import majunbao.bwie.com.jingdong_base_marster.base.mvp.BaseMvpActivity;
import majunbao.bwie.com.jingdong_base_marster.base.mvp.IBasePresenter;
import majunbao.bwie.com.jingdong_base_marster.utils.SharedPreferencesUtils;

public class LoginActivity extends BaseMvpActivity<LoginContract.LoginModel,LoginContract.LoginPresenter> implements LoginContract.ILoginView, View.OnClickListener {

    @BindView(R.id.mylogintopback)
    ImageView mylogintopback;
    @BindView(R.id.mobile_et)
    EditText mobileEt;
    @BindView(R.id.pwd_et)
    EditText pwdEt;
    @BindView(R.id.eye_btn)
    ImageView eyeBtn;
    @BindView(R.id.login_user)
    Button loginUser;
    @BindView(R.id.duanxinyzm)
    TextView duanxinyzm;
    @BindView(R.id.xinyonghuzc)
    TextView xinyonghuzc;

    /*成员变量*/
    private String mUser;//登陆的账号
    private String mPwd;//登陆后的密码
    private SharedPreferences rSp;




    @Override
    protected int setLayoutId() {
        return R.layout.activity_login;
    }
    //RegistEventBus处理事件
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(RegistEventBus registEventBus) {
        if (registEventBus.phone!=null&&registEventBus.pwd!=null) {
            //给登陆注册重新赋值,设置成当前用户的账号
            mobileEt.setText(registEventBus.phone);
            //给登陆注册重新赋值,设置成当前用户的密码
            pwdEt.setText(registEventBus.pwd);
        }
    }


    @Override
    protected void initData() {
        super.initData();
        //注册eventbus
        EventBus.getDefault().register(this);
 /*       EventBus.getDefault().register(this);
        //存储信息sp
        mSp = getSharedPreferences("login", MODE_PRIVATE);
        mEditor = mSp.edit();*/
        //登陆
        loginUser.setOnClickListener(this);
        //新用户注册
        xinyonghuzc.setOnClickListener(this);
        //返回
        mylogintopback.setOnClickListener(this);
       // rSp = getSharedPreferences("showcart", Context.MODE_PRIVATE);
      //  rSp.getBoolean("flag",false);

    }

    @Override
    protected void initView() {
        super.initView();
    }

    /*登陆成功*/
    @Override
    public void success(UserEntity userEntity) {
        //登陆失败,密码错误
        if (userEntity.getMessage().equals("登陆失败,账号或密码错误")) {
            //存值判断购物车显示隐藏
           // rSp.edit().putBoolean("flag",false).commit();
            TastyToast.makeText(getApplicationContext(), "登录失败,账号或密码错误", TastyToast.LENGTH_LONG, TastyToast.ERROR);
        }
        //登陆成功,密码正确
        if (userEntity.getMessage().equals("登陆成功")) {
            //存值判断购物车显示隐藏
            //rSp.edit().putBoolean("flag",true).commit();
            TastyToast.makeText(getApplicationContext(), "登录成功", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
            //获取昵称
            String nickName = userEntity.getResult().getUserInfo().getNickName();
            //获取头像
            String headPic = userEntity.getResult().getUserInfo().getHeadPic();
            //获取账号
            String Phone = userEntity.getResult().getUserInfo().getPhone();
            //获取sessionid
            String sessionId = userEntity.getResult().getSessionId();
            //获取userId,userId为数字需要+""转成字符串
            String userId = userEntity.getResult().getUserId()+"";
            //获取性别
            String sex = userEntity.getResult().getUserInfo().getSex()+"";
            //获取生日
            String birthday = userEntity.getResult().getUserInfo().getBirthday()+"";
            //在下面获取登陆密码
            //通过sp存值把以下数据存进sp中
            SharedPreferences loginsp = getSharedPreferences("login", MODE_PRIVATE);
            SharedPreferences.Editor mEditor = loginsp.edit();
            //存昵称
            mEditor.putString("nickName",nickName).commit();
            //存头像
            mEditor.putString("headPic",headPic).commit();
            //存账号
            mEditor.putString("Phone",Phone).commit();
            //存密码
            //mEncrypt为加密后的密码
            mEditor.putString("pwd",mPwd).commit();
            //存sessionId
            mEditor.putString("sessionId",sessionId).commit();
            //存userId
            mEditor.putString("userId",userId).commit();
            //存生日
            mEditor.putString("birthday",birthday).commit();
            //存性别
            mEditor.putString("sex",sex).commit();
            Log.i("sessionid",userEntity.getResult().getSessionId());
            //把新的sessionid存进去
            mEditor.putString("sessionId",userEntity.getResult().getSessionId()).commit();
            //发送事件到登陆LoginEventBus
            LoginEventBus loginEventBus = new LoginEventBus(userId, sessionId, nickName, sex, Phone, headPic);
            EventBus.getDefault().post(loginEventBus);
            //关闭当前页面,把村的值赋给MyFragment
            finish();
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //登陆
            case R.id.login_user:
                //获取账号
                mUser = mobileEt.getText().toString();
                //获取密码
                mPwd = pwdEt.getText().toString();
                //给密码加密,获取新的密码
                String mEncrypt = EncryptUtil.encrypt(mPwd);
                //进行网络请求判断是否登陆成功
                presenter.login(mUser, mEncrypt);
                break;
            //注册
            case R.id.xinyonghuzc:
                //新用户注册
                xinyonghuzc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(LoginActivity.this,RegistActivity.class));
                    }
                });
                break;
            //返回
            case R.id.mylogintopback:
                finish();
                break;
        }
    }

    @Override
    public void failure(String msg) {

    }

    @Override
    public IBasePresenter initBasePresenter() {
        return new LoginPresenter();
    }

    @Override
    public void failure() {

    }

    @Override
    public void showLoader() {

    }

    @Override
    public void hideLoader() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


}
