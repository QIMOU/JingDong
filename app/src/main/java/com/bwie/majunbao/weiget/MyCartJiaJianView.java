package com.bwie.majunbao.weiget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.majunbao.R;

import org.greenrobot.eventbus.EventBus;


public class MyCartJiaJianView extends LinearLayout {

    private TextView jiaTv,jiantv;
    private EditText numEt;
    private int num = 1;
    public MyCartJiaJianView(Context context) {
        this(context,null);
    }

    public MyCartJiaJianView(Context context, @Nullable AttributeSet attrs) {
        this(context,  attrs,0);
    }

    public MyCartJiaJianView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    /**
     * 初始化自定义的布局
     */
    private void init(Context context) {

        View view = LayoutInflater.from(context).inflate(R.layout.mycartjiajian,this,true);
//        addView(view);
        jiantv = view.findViewById(R.id.jian);
        jiaTv = view.findViewById(R.id.jia);
        numEt = view.findViewById(R.id.num);



        numEt.setText(num+"");



        jiaTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                num++;
                numEt.setText(num+"");
                String s = numEt.getText().toString();
                EventBus.getDefault().post(s);
            }
        });
        jiantv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                num--;
                if (num<=0){
                    Toast.makeText(getContext(), "数量不能小于1", Toast.LENGTH_SHORT).show();
                    num = 1;
                }
                String s = numEt.getText().toString();
                EventBus.getDefault().post(s);
            }
        });

    }
    public void setNumEt(int n) {
        numEt.setText(n+"");
        num = Integer.parseInt(numEt.getText().toString());
    }

}