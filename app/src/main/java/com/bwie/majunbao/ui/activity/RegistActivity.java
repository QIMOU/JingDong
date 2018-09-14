package com.bwie.majunbao.ui.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.majunbao.R;
import com.bwie.majunbao.contract.RegistContract;
import com.bwie.majunbao.entity.RegistEntity;
import com.bwie.majunbao.eventbus.RegistEventBus;
import com.bwie.majunbao.presenter.RegistPresenter;
import com.bwie.majunbao.utils.EncryptUtil;
import com.sdsmdg.tastytoast.TastyToast;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import majunbao.bwie.com.jingdong_base_marster.base.mvp.BaseMvpActivity;
import majunbao.bwie.com.jingdong_base_marster.base.mvp.IBasePresenter;

public class RegistActivity extends BaseMvpActivity<RegistContract.RegistModel, RegistContract.RegistPresenter> implements RegistContract.IRegistView {

    @BindView(R.id.phone)
    EditText mobile;
    @BindView(R.id.pwd)
    EditText pwd;
    @BindView(R.id.duanxinyzm)
    TextView duanxinyzm;
    @BindView(R.id.lianxikefu)
    TextView lianxikefu;
    @BindView(R.id.nickName)
    TextView nickName;
    @BindView(R.id.sex)
    TextView sex;
    @BindView(R.id.birthday)
    TextView birthday;
    @BindView(R.id.imei)
    TextView imei;
    @BindView(R.id.ua)
    TextView ua;
    @BindView(R.id.screenSize)
    TextView screenSize;
    @BindView(R.id.os)
    TextView os;
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.pwd2)
    TextView pwd2;

    //成员变量
    private String phone;
    private String pwda;
    private String mNickName1;
    private int mRandomnum;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_regist;
    }

    @Override
    protected void initData() {
        super.initData();
        mRandomnum = (int) (Math.random() * 1000);
    }
    public void register(View view) {
        //得到edittext输入的值
        phone = mobile.getText().toString();//账号
        pwda = pwd.getText().toString();//密码
        //把密码进行加密
        String encrypt = EncryptUtil.encrypt(pwda);//加密后的密码
        //把密码赋值到 确认密码上
        pwd2.setText(encrypt);
        //得到确认密码的字符串
        String pwdb = pwd2.getText().toString();
        //得到必输入昵称
        mNickName1 = nickName.getText().toString()+mRandomnum;
        //得到性别
        String sex1 = sex.getText().toString();
        //得到生日
        //以及注册比输入的
        String birthday1 = birthday.getText().toString();
        String imei1 = imei.getText().toString();
        String ua1 = ua.getText().toString();
        String screenSize1 = screenSize.getText().toString();
        String os1 = os.getText().toString();
        String email1 = email.getText().toString();

        //判断账号密码不能为空
        if (!phone.equals("")&&!pwda.equals("")) {//不为空
            //请求网络注册接口,并且传入必传参数
            presenter.register(phone, encrypt,pwdb,mNickName1,sex1,birthday1,imei1,ua1,screenSize1,os1,email1);
        }else {//空
            Toast.makeText(this, "账号或密码不能为空", Toast.LENGTH_SHORT).show();
        }
        initData();
    }

    @Override
    public void success(RegistEntity registEntity) {
        if (registEntity.getMessage().equals("注册失败,手机号已存在")) {
            TastyToast.makeText(getApplicationContext(), "注册失败,手机号已存在", TastyToast.LENGTH_LONG, TastyToast.ERROR);
        }
        if (registEntity.getMessage().equals("请正确填写手机号")) {
            TastyToast.makeText(getApplicationContext(), "请正确填写手机号", TastyToast.LENGTH_LONG, TastyToast.ERROR);
        }
        if (registEntity.getMessage().equals("注册失败,昵称已存在")) {
            TastyToast.makeText(getApplicationContext(), "注册失败,昵称已存在", TastyToast.LENGTH_LONG, TastyToast.ERROR);
        }
        //注册成功,密码正确
        if (registEntity.getMessage().equals("注册成功")) {
            TastyToast.makeText(getApplicationContext(), "注册成功", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
            //获取getsp
           /* SharedPreferences registsp = getSharedPreferences("regist", MODE_PRIVATE);
            registsp.edit().putString("Phone",phone).commit();//把注册的账号存在注册的sp中
            registsp.edit().putString("pwd",encrypt).commit();//把注册的密码存在注册的sp中
            registsp.edit().putString("sex",sex+"").commit();//把注册的性别存在注册的sp中
            registsp.edit().putString("birthday",birthday+"").commit();//把注册的生日存在注册的sp中*/
            RegistEventBus registEventBus = new RegistEventBus(phone, pwda, sex + "", birthday + "");
            //通过EventBus传给登陆页面
            EventBus.getDefault().post(registEventBus);
            Log.i("mjb",registEventBus.phone);
            Log.i("mjb",registEventBus.pwd);
            Log.i("mjb",registEventBus.sex);
            //关闭当前页面,返回到登陆页面,并且进行赋值
            finish();
        }
    }

    @Override
    public void failure(String msg) {

    }

    @Override
    public IBasePresenter initBasePresenter() {
        return new RegistPresenter();
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
    protected void onDestroy() {
        super.onDestroy();
        if (presenter!=null) {
            presenter.detach();
            presenter=null;
        }

    }
}
