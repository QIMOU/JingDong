package com.bwie.majunbao.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.majunbao.R;
import com.bwie.majunbao.eventbus.RegistEventBus;
import com.bwie.majunbao.eventbus.UploadIconEventBus;
import com.bwie.majunbao.ui.adapter.MySetUserAdapter;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SetUserActivity extends AppCompatActivity {

    @BindView(R.id.set_user_recy)
    RecyclerView setUserRecy;
    @BindView(R.id.img_tou)
    SimpleDraweeView imgTou;
    @BindView(R.id.text_name)
    TextView textName;
    @BindView(R.id.mylogintopback)
    ImageView mylogintopback;
    @BindView(R.id.text_user)
    TextView textUser;
    @BindView(R.id.setziliao)
    LinearLayout setziliao;
    private ArrayList<String> mList;
    private SharedPreferences mSp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_user);
        ButterKnife.bind(this);
        //注册eventbus
        EventBus.getDefault().register(this);
        //得到loginsp存的值
        mSp = getSharedPreferences("login", Context.MODE_PRIVATE);
        String nickName = mSp.getString("nickName", "");//昵称
        String headPic = mSp.getString("headPic", "");//头像
        String Phone = mSp.getString("Phone", "");//账号
        String pwd = mSp.getString("pwd", "");//密码
        String sessionId = mSp.getString("sessionId", "");//sessionId
        String userId = mSp.getString("userId", "");//userId
        String sex = mSp.getString("sex", "");//性别
        String birthday = mSp.getString("birthday", "");//生日
        if(nickName.equals("")&&headPic.equals("")&&Phone.equals("")){

        }else {
            //为以上赋值
            textName.setText(nickName);//给昵称赋值
            imgTou.setImageURI(headPic);//给头像赋值
            textUser.setText("用户名:" + Phone);//给账号赋值
        }

        //设置recyclerview的样式
        setUserRecy.setLayoutManager(new LinearLayoutManager(this));
        //创建集合,自定义数据
        mList = new ArrayList<>();
        mList.add("会员俱乐部");
        mList.add("PLUSS会员");
        mList.add("小白信用");
        mList.add("地址管理");
        mList.add("增票资质");
        mList.add("实名认证");
        mList.add("账户安全");
        mList.add("支付设置");
        mList.add("关联账号");
        mList.add("设置");
        mList.add("我的定制");
        mList.add("退出登录");
        //设置分割线
        setUserRecy.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        //设置适配器
        setUserRecy.setAdapter(new MySetUserAdapter(this, mList));

        setziliao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击第一个条目,跳转到设置信息页面
                Intent intent = new Intent(SetUserActivity.this, SetZiLiaoActivity.class);
                startActivity(intent);
            }
        });


        //返回键
        mylogintopback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //接收UploadIconEventbus,并处理事件
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void Event(UploadIconEventBus uploadIconEventBus) {
        Log.i("mjb",uploadIconEventBus.headIcon);
        if (uploadIconEventBus.headIcon!=null) {
            Log.i("mjb","执行了uoload处理事件");
            imgTou.setImageURI(Uri.parse(uploadIconEventBus.headIcon));//给头像赋值
        }
    }
}
