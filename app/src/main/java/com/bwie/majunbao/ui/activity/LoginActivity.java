package com.bwie.majunbao.ui.activity;

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
    private SharedPreferences mSp;
    private SharedPreferences.Editor mEditor;
    private boolean mFlag;
    private boolean mFlag2=false;


    @Override
    protected int setLayoutId() {
        return R.layout.activity_login;
    }
    //eventbus处理事件
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(HashMap<String, String> hashMap) {
        //给登陆注册重新赋值,设置成当前用户的昵称
        String phone = hashMap.get("phone");
        mobileEt.setText(phone);
        //给默认头像设置成当前用户的头像
        String encrypt = hashMap.get("encrypt");
        pwdEt.setText(encrypt);
    }
    @Override
    protected void initData() {
        super.initData();
        EventBus.getDefault().register(this);
        //存储信息sp
      /*  mSp = getSharedPreferences("userlogin", MODE_PRIVATE);
        mFlag = mSp.getBoolean("flag", false);
        mEditor = mSp.edit();

        if (!mFlag){

        }else{
            mEditor.putBoolean("flag",false).commit();
            finish();
        }*/
      //  mFlag = mEditor.putBoolean("flag", false);

        //登陆
        loginUser.setOnClickListener(this);
        //新用户注册
        xinyonghuzc.setOnClickListener(this);
        //返回
        mylogintopback.setOnClickListener(this);
    }

    /*登陆成功*/
    @Override
    public void success(UserEntity userEntity) {
        Log.i("成功",userEntity.getMessage());
        //登陆失败,密码错误
        if (userEntity.getMessage().equals("登陆失败,账号或密码错误")) {
            TastyToast.makeText(getApplicationContext(), "登录失败,账号或密码错误", TastyToast.LENGTH_LONG, TastyToast.ERROR);
        }
        //登陆成功,密码正确
        if (userEntity.getMessage().equals("登陆成功")) {
            TastyToast.makeText(getApplicationContext(), "登录成功", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
            //Toast.makeText(this, "登陆失败,账号或密码错误", Toast.LENGTH_SHORT).show();
            //昵称
            String nickName = userEntity.getResult().getUserInfo().getNickName();
            //头像
            String headPic = userEntity.getResult().getUserInfo().getHeadPic();
            //手机号
            String Phone = userEntity.getResult().getUserInfo().getPhone();
            //hashmap把上面的值存起来
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("nickName",nickName);
            hashMap.put("headPic",headPic);
            hashMap.put("Phone",Phone);
            hashMap.put("background","1");
            //发送事件
            EventBus.getDefault().postSticky(hashMap);
            //关闭当前页面
            finish();
        }
        //Log.i("成功",userEntity.getResult().toString());
      /*  mEditor.putBoolean("flag",true).commit();
        mEditor.putString("username",userEntity.getResult().getUserInfo().getNickName()).commit();
        mEditor.putString("userpng",userEntity.getResult().getUserInfo().getHeadPic()).commit();*/

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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //登陆
            case R.id.login_user:
                //获取账号
                String user = mobileEt.getText().toString();
                //获取密码
                String pwd = pwdEt.getText().toString();
                //给密码加密
                String encrypt = EncryptUtil.encrypt(pwd);
                Log.i("mmm","账号"+user);
                Log.i("mmm","密码"+encrypt);
                //Log.i("aaa","执行了点击");
                presenter.login(user,encrypt);
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
}
