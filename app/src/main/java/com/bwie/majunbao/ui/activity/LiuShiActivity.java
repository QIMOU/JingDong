package com.bwie.majunbao.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.majunbao.R;
import com.example.library.AutoFlowLayout;
import com.example.library.FlowAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LiuShiActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.edit_one)
    EditText editOne;
    @BindView(R.id.one_tv_cancle)
    TextView oneTvCancle;
    @BindView(R.id.one_autoFlowLayout2)
    AutoFlowLayout oneAutoFlowLayout2;
    @BindView(R.id.one_autoFlowLayout)
    AutoFlowLayout oneAutoFlowLayout;
    @BindView(R.id.sousuo)
    ImageView sousuo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liu_shi);
        ButterKnife.bind(this);
        //设置监听
        setListen();
        //创建集合
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                list.add("xxx");
            } else {
                list.add("马俊宝");
            }
        }
        //设置方法
        setAutoLayout(list, true);
        oneAutoFlowLayout.setOnItemClickListener(new AutoFlowLayout.OnItemClickListener() {
            @Override
            public void onItemClick(int i, View view) {
                Intent intent = new Intent(LiuShiActivity.this, SearchActivity.class);
                TextView tv = view.findViewById(R.id.auto_tv);
                intent.putExtra("name", tv.getText().toString());
                startActivity(intent);
            }
        });
    }

    private void setAutoLayout(final ArrayList<String> list, boolean b) {
        if (b) {
            oneAutoFlowLayout2.setAdapter(new FlowAdapter(list) {

                private TextView auto_tv;

                @Override
                public View getView(int i) {
                    View view = View.inflate(LiuShiActivity.this, R.layout.autoflowlayout, null);
                    for (int j = 0; j < list.size(); j++) {
                        auto_tv = view.findViewById(R.id.auto_tv);
                        auto_tv.setText(list.get(i));
                    }
                    return view;
                }
            });
        } else {
            oneAutoFlowLayout.setAdapter(new FlowAdapter(list) {

                private TextView auto_tv;

                @Override
                public View getView(int i) {
                    View view = View.inflate(LiuShiActivity.this, R.layout.autoflowlayout, null);
                    for (int j = 0; j < list.size(); j++) {
                        auto_tv = view.findViewById(R.id.auto_tv);
                        auto_tv.setText(list.get(i));
                        list.clear();
                    }
                    return view;
                }
            });
        }
    }

    /*设置监听*/
    private void setListen() {
        sousuo.setOnClickListener(this);
        oneTvCancle.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sousuo:
                List<String> list1 = new ArrayList<>();
                list1.add(editOne.getText().toString());
                Intent intent = new Intent(this, SearchActivity.class);
                intent.putExtra("name", editOne.getText().toString());
                startActivity(intent);
                editOne.getText().clear();
                boolean falg = false;
                setAutoLayout((ArrayList<String>) list1, falg);
                break;
            case R.id.one_tv_cancle:
                finish();
                break;
        }
    }
}
