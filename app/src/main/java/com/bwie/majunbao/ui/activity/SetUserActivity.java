package com.bwie.majunbao.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.majunbao.R;
import com.bwie.majunbao.ui.adapter.MySetUserAdapter;
import com.facebook.drawee.view.SimpleDraweeView;

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
    private ArrayList<String> mList;
    private ArrayList<String> mTlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_user);
        ButterKnife.bind(this);
        //得到传值
        Intent intent = getIntent();
        //昵称
        String name = intent.getStringExtra("name");
        //头像
        String head = intent.getStringExtra("head");
        //手机号
        String phone = intent.getStringExtra("phone");
        //为以上赋值
        textName.setText(name);
        imgTou.setImageURI(head);
        textUser.setText("用户名:"+phone);
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
    }
}
