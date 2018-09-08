package com.bwie.majunbao.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.majunbao.R;
import com.bwie.majunbao.ui.activity.SetUserActivity;
import com.sdsmdg.tastytoast.TastyToast;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
            holder.mText.setBackgroundColor(R.drawable.my_login_background_shaper);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TastyToast.makeText(context, list.get(position), TastyToast.LENGTH_LONG, TastyToast.WARNING);
                if (list.get(position).equals("退出登录")) {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("nickName","2");
                    hashMap.put("touxiang","2");
                    hashMap.put("background","2");
                    EventBus.getDefault().postSticky(hashMap);
                    if (Activity.class.isInstance(context)) {
                        // 转化为activity，然后finish就行了
                        SetUserActivity activity = (SetUserActivity) context;
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
