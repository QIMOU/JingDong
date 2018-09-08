package com.bwie.majunbao.ui.activity;

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

    private String phone;
    private String encrypt;
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

        phone = mobile.getText().toString();
        String pwda = pwd.getText().toString();
        //加密
        encrypt = EncryptUtil.encrypt(pwda);
        pwd2.setText(encrypt);
        String pwdb = pwd2.getText().toString();
        mNickName1 = nickName.getText().toString()+mRandomnum;
        String sex1 = sex.getText().toString();
        String birthday1 = birthday.getText().toString();
        String imei1 = imei.getText().toString();
        String ua1 = ua.getText().toString();
        String screenSize1 = screenSize.getText().toString();
        String os1 = os.getText().toString();
        String email1 = email.getText().toString();
        if (!phone.equals("")||pwda.equals("")) {
            presenter.register(phone, encrypt,pwdb,mNickName1,sex1,birthday1,imei1,ua1,screenSize1,os1,email1);
        }else {
            Toast.makeText(this, "不能为空", Toast.LENGTH_SHORT).show();
        }
        initData();


    }

    @Override
    public void success(RegistEntity registEntity) {
        //Toast.makeText(this, "成功", Toast.LENGTH_SHORT).show();
        //Log.i("regist",registEntity.getMessage());
        //Log.i("regist",registEntity.getStatus());
        if (registEntity.getMessage().equals("注册失败,手机号已存在")) {
            TastyToast.makeText(getApplicationContext(), "注册失败,手机号已存在", TastyToast.LENGTH_LONG, TastyToast.ERROR);
        }
        if (registEntity.getMessage().equals("请正确填写手机号")) {
            TastyToast.makeText(getApplicationContext(), "请正确填写手机号", TastyToast.LENGTH_LONG, TastyToast.ERROR);
        }
        if (registEntity.getMessage().equals("注册失败,昵称已存在")) {
            TastyToast.makeText(getApplicationContext(), "注册失败,昵称已存在", TastyToast.LENGTH_LONG, TastyToast.ERROR);
        }
        //登陆成功,密码正确
        if (registEntity.getMessage().equals("注册成功")) {
            TastyToast.makeText(getApplicationContext(), "注册成功", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
            //Toast.makeText(this, "登陆失败,账号或密码错误", Toast.LENGTH_SHORT).show();
            //hashmap把上面的值存起来
            HashMap<String, String> hashMap = new HashMap<>();
            String decrypt = EncryptUtil.decrypt(encrypt);
            hashMap.put("phone",phone);
            hashMap.put("encrypt",decrypt);
            //发送事件
            EventBus.getDefault().postSticky(hashMap);
            //关闭当前页面
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
