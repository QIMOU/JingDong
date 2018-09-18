package com.bwie.majunbao.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.majunbao.R;
import com.bwie.majunbao.eventbus.CanCelEventBus;
import com.bwie.majunbao.ui.activity.SetUserActivity;
import com.sdsmdg.tastytoast.TastyToast;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import majunbao.bwie.com.jingdong_base_marster.utils.SharedPreferencesUtils;

public class MySetUserAdapter extends RecyclerView.Adapter<MySetUserAdapter.MyViewHolder> {
    private Context context;
    private List<String> list;

     public MySetUserAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MySetUserAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_setuser_adapter, parent, false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final MySetUserAdapter.MyViewHolder holder, final int position) {
        holder.mText.setText(list.get(position));
        if (list.get(position).equals("退出登录")) {
           // holder.mText.setBackgroundColor(R.drawable.my_login_background_shaper);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //提示点击的信息
                Toast.makeText(context,list.get(position), Toast.LENGTH_SHORT).show();
                if (list.get(position).equals("退出登录")) {
                    //创建注销的eventvtbus
                    CanCelEventBus canCelEventBus = new CanCelEventBus();
                    //发送事件注销,恢复初始值
                    EventBus.getDefault().post(canCelEventBus);
                    if (Activity.class.isInstance(context)) {
                        // 转化为activity，然后finish就行了
                        SetUserActivity activity = (SetUserActivity) context;
                        SharedPreferences sp = context.getSharedPreferences("login", context.MODE_PRIVATE);
                        SharedPreferences registsp = context.getSharedPreferences("regist", context.MODE_PRIVATE);
                        if (sp!=null||registsp!=null){
                            //清空登陆
                            SharedPreferences.Editor editor = sp.edit();
                            editor.clear();
                            editor.commit();
                            //清空注册
                            SharedPreferences.Editor registeditor = registsp.edit();
                            registeditor.clear();
                            registeditor.commit();
                        }
                        //关闭设置页面,返回到myfragment
                        activity.finish();
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView mText;

        public MyViewHolder(View itemView) {
            super(itemView);
            mText = itemView.findViewById(R.id.text1);
        }
    }
}
