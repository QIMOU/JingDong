package com.bwie.majunbao.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.majunbao.R;
import com.bwie.majunbao.contract.ProductContract;
import com.bwie.majunbao.entity.ProductEntity;
import com.bwie.majunbao.ui.activity.LoginActivity;
import com.bwie.majunbao.ui.activity.SetUserActivity;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.RoundingParams;
import com.sdsmdg.tastytoast.TastyToast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import majunbao.bwie.com.jingdong_base_marster.base.mvp.BaseMvpFragment;
import majunbao.bwie.com.jingdong_base_marster.base.mvp.IBasePresenter;

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

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends BaseMvpFragment<ProductContract.ProductModel, ProductContract.ProductPresenter> implements ProductContract.IProductView, View.OnClickListener {

    @BindView(R.id.shezhi)
    ImageView shezhi;
    @BindView(R.id.duihua)
    ImageView duihua;
    @BindView(R.id.ll1)
    LinearLayout ll1;
    @BindView(R.id.touxiang)
    ImageView touxiang;
    @BindView(R.id.login)
    TextView login;
    @BindView(R.id.myfragmentbg)
    RelativeLayout myfragmentbg;
    Unbinder unbinder;
    private String mNickName;
    private String mHeadPic;
    private String mPhone;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_my;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected void initData() {
        super.initData();
        EventBus.getDefault().register(this);
        //设置按钮
        shezhi.setOnClickListener(this);
        //对话按钮
        duihua.setOnClickListener(this);
        //点击头像
        touxiang.setOnClickListener(this);
        //点击登陆注册
        login.setOnClickListener(this);
    }

    //eventbus处理事件
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(HashMap<String, String> hashMap) {
        //给登陆注册重新赋值,设置成当前用户的昵称
        mNickName = hashMap.get("nickName");
        if (mNickName!=null) {
            login.setText(mNickName);
        }else {
            login.setText("登录/注册>");
        }
        //给默认头像设置成当前用户的头像
        mHeadPic = hashMap.get("headPic");
        touxiang.setImageURI(Uri.parse(mHeadPic));
        //手机号传给设置
        mPhone = hashMap.get("Phone");
        //判断数据是否符合要求,符合,即登陆成功改变背景图片
        if (hashMap.get("background").equals("1")) {
            myfragmentbg.setBackgroundResource(R.drawable.bfz);
        }

    }
    //eventbus
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event2(HashMap<String, String> hashMap) {
        //给登陆注册重新赋值,设置成当前用户的昵称
        if (hashMap.get("nickName").equals("2")) {
            login.setText("登录/注册>");
        }
        //给默认头像设置成当前用户的头像
        if (hashMap.get("touxiang").equals("2")) {
            touxiang.setImageResource(R.drawable.bg7);
        }
        //判断数据是否符合要求,符合,即登陆成功改变背景图片
        if (hashMap.get("background").equals("2")) {
            myfragmentbg.setBackgroundResource(R.drawable.bfy);
        }
        TastyToast.makeText(getContext(), "注销成功", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
    }



    /*监听*/
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //上传头像
            case R.id.touxiang:

                break;
            //设置
            case R.id.shezhi:
                if (login.getText().toString().contains("登录/注册>")) {
                    TastyToast.makeText(getContext(), "请先进行登陆", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }else {
                    //得到值,传到设置页面
                    Intent intent = new Intent(getActivity(), SetUserActivity.class);
                    intent.putExtra("name",mNickName);
                    intent.putExtra("head",mHeadPic);
                    intent.putExtra("phone",mPhone);
                    startActivity(intent);
                }
                break;
            //登陆注册
            case R.id.login:
                        if (login.getText().toString().contains("登录/注册>")) {
                            startActivity(new Intent(getActivity(), LoginActivity.class));
                        } else {
                            Toast.makeText(getActivity(), "已经登录,无需登录", Toast.LENGTH_SHORT).show();
                        }
                break;
            //对话
            case R.id.duihua:

                break;
        }
    }

    @Override
    public IBasePresenter initBasePresenter() {
        return null;
    }

    /*成功的回调*/
    @Override
    public void success(ProductEntity productEntity) {

    }

    //失败的回调
    @Override
    public void failure(String msg) {

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


    /*销毁*/
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

}
