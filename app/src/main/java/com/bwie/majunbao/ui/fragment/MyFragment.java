package com.bwie.majunbao.ui.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.majunbao.R;
import com.bwie.majunbao.contract.LoginContract;
import com.bwie.majunbao.entity.UserEntity;
import com.bwie.majunbao.eventbus.CanCelEventBus;
import com.bwie.majunbao.eventbus.LoginEventBus;
import com.bwie.majunbao.eventbus.RegistEventBus;
import com.bwie.majunbao.eventbus.UploadIconEventBus;
import com.bwie.majunbao.model.LoginModel;
import com.bwie.majunbao.presenter.LoginPresenter;
import com.bwie.majunbao.ui.activity.LoginActivity;
import com.bwie.majunbao.ui.activity.SetUserActivity;
import com.bwie.majunbao.utils.EncryptUtil;
import com.sdsmdg.tastytoast.TastyToast;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import majunbao.bwie.com.jingdong_base_marster.base.base_ui.BaseFragment;
import majunbao.bwie.com.jingdong_base_marster.base.mvp.BaseMvpFragment;
import majunbao.bwie.com.jingdong_base_marster.base.mvp.IBasePresenter;



/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends BaseFragment implements View.OnClickListener {

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
    @BindView(R.id.my_xiamian)
    ImageView myXiamian;
    private String mNickName;
    private String mHeadPic;
    private String mPhone;
    private SharedPreferences mSp;
    private SharedPreferences rSp;
    private String mEncrypt;


    @Override
    protected void initData() {
        super.initData();
        //注册eventbus
        EventBus.getDefault().register(this);

        //设置按钮
        shezhi.setOnClickListener(this);
        //对话按钮
        duihua.setOnClickListener(this);
        //点击头像
        touxiang.setOnClickListener(this);
        //点击登陆注册
        login.setOnClickListener(this);
        //登陆下面
        myXiamian.setOnClickListener(this);
        //rSp = getActivity().getSharedPreferences("showcart", Context.MODE_PRIVATE);
        //rSp.getBoolean("flag",false);

    }

    @Override
    protected void initView() {
        super.initView();
        mSp = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        if (!mSp.equals("")) {
            //得到loginActivity存的值
            String nickName = mSp.getString("nickName", "");//昵称
            String headPic = mSp.getString("headPic", "");//头像
            String Phone = mSp.getString("Phone", "");//账号
            final String pwd = mSp.getString("pwd", "");//密码
            String sessionId = mSp.getString("sessionId", "");//sessionId
            String userId = mSp.getString("userId", "");//userId
            String sex = mSp.getString("sex", "");//性别
            String birthday = mSp.getString("birthday", "");//生日
            //赋值给当前页面的 头像 和昵称
            //判断头像和昵称书否为空
            if (nickName.equals("")&&headPic.equals("")) {//为空
                //把登陆注册还原,表示没登陆
                login.setText("登录/注册>");
                touxiang.setImageResource(R.drawable.mytoptouxiang);
            }else {
                //不为空
                touxiang.setImageURI(Uri.parse(headPic));//赋值头像
                login.setText(nickName);//赋值昵称
                login.setTextColor(Color.parseColor("#365663"));//改变昵称字体颜色
                myfragmentbg.setBackgroundResource(R.drawable.loginafter);//登陆成功改变背景图片
                //获取密码
                //给密码加密,获取新的密码
                mEncrypt = EncryptUtil.encrypt(pwd);
                //重新请求网络数据,并且把sessionid重新赋值
                 new LoginModel().login(Phone, mEncrypt).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<UserEntity>() {
                     @Override
                     public void accept(UserEntity userEntity) throws Exception {
                         //登陆失败,密码错误
                         if (userEntity.getMessage().equals("登陆失败,账号或密码错误")) {
                             //存值判断购物车显示隐藏
                         //    rSp.edit().putBoolean("flag",true).commit();
                             TastyToast.makeText(getActivity(), "登陆信息已过期,请重新登陆", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                             //给登陆注册重新赋值,设置成默认值
                             login.setText("登录/注册>");
                             //给头像重新赋值,设置成默认值
                             touxiang.setImageResource(R.drawable.bg7);
                             //注销更换背景图片
                             myfragmentbg.setBackgroundResource(R.drawable.mybackground1);
                         }
                         //登陆成功,密码正确
                         if (userEntity.getMessage().equals("登陆成功")) {
                             //存值判断购物车显示隐藏
                          //   rSp.edit().putBoolean("flag",true).commit();
                            // TastyToast.makeText(getActivity(), "主人欢迎回来!", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);
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
                             SharedPreferences loginsp = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
                             SharedPreferences.Editor mEditor = loginsp.edit();
                             //存昵称
                             mEditor.putString("nickName",nickName).commit();
                             //存头像
                             mEditor.putString("headPic",headPic).commit();
                             //存账号
                             mEditor.putString("Phone",Phone).commit();
                             //存密码
                             //mEncrypt为加密后的密码
                             mEditor.putString("pwd", pwd).commit();
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
                             Log.i("sessionid",userEntity.getResult().getSessionId());
                             touxiang.setImageURI(Uri.parse(headPic));//赋值头像
                             login.setText(nickName);//赋值昵称
                             login.setTextColor(Color.parseColor("#365663"));//改变昵称字体颜色
                             myfragmentbg.setBackgroundResource(R.drawable.loginafter);//登陆成功改变背景图片
                         }
                     }
                 });
                // presenter.login(Phone,pwd);
                //在执行成功的success方法中把新的sessionId存进去
                //presenter.login(Phone,pwd);
            }
        }
    }

    /*监听*/
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //上传头像
            case R.id.touxiang:
                if (login.getText().toString().contains("登录/注册>")) {
                    TastyToast.makeText(getContext(), "请先进行登陆", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }else {
                    //得到值,传到设置页面
                    Intent intent = new Intent(getActivity(), SetUserActivity.class);
                    intent.putExtra("name", mNickName);
                    intent.putExtra("head", mHeadPic);
                    intent.putExtra("phone", mPhone);
                    startActivity(intent);
                }
                break;
            //设置
            case R.id.shezhi:
                if (login.getText().toString().contains("登录/注册>")) {
                    TastyToast.makeText(getContext(), "请先进行登陆", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                } else {
                    //跳转到设置页面
                    Intent intent = new Intent(getActivity(), SetUserActivity.class);
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
                Toast.makeText(mActivity, "别瞎点", Toast.LENGTH_SHORT).show();
                break;
            //下面
            case R.id.my_xiamian:
                TastyToast.makeText(getActivity(),"该功能暂未开放!",2,TastyToast.ERROR);
                break;
        }
    }

    /**
     * 查找presenter,并返回当前presenter
     * @return
     */

    /*成功的回调*/
 /*   @Override
    public void success(UserEntity userEntity) {
        Log.i("sessionid",userEntity.getResult().getSessionId());
        //把新的sessionid存进去
        mSp.edit().putString("pwd",userEntity.getResult().getSessionId()).commit();
    }*/


    //CanCelEventBus处理事件
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(CanCelEventBus canCelEventBus) {
            //给登陆注册重新赋值,设置成默认值
            login.setText("登录/注册>");
            //给头像重新赋值,设置成默认值
            touxiang.setImageResource(R.drawable.bg7);
            //注销更换背景图片
            myfragmentbg.setBackgroundResource(R.drawable.bfz);
    }

    //接收UploadIconEventbus,并处理事件
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void Event(UploadIconEventBus uploadIconEventBus) {
        if (uploadIconEventBus.headIcon!=null) {
            touxiang.setImageURI(Uri.parse(uploadIconEventBus.headIcon));//给头像赋值
        }
    }

    //接收LoginEventBus,并处理事件
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(LoginEventBus loginEventBus) {
        if (loginEventBus.headIcon!=null&&loginEventBus.nickNick!=null) {
            //不为空
            touxiang.setImageURI(Uri.parse(loginEventBus.headIcon));//赋值头像
            login.setText(loginEventBus.nickNick);//赋值昵称
            login.setTextColor(Color.parseColor("#365663"));//改变昵称字体颜色
            myfragmentbg.setBackgroundResource(R.drawable.loginafter);//登陆成功改变背景图片
            //重新请求网络数据,并且把sessionid重新赋值
           // presenter.login(Phone,pwd);
            //在执行成功的success方法中把新的sessionId存进去
        }
    }



































    //添加布局
    @Override
    protected int setLayoutId() {
        return R.layout.fragment_my;
    }


    /**
     * 创建视图
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }
    /*销毁*/
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

}
