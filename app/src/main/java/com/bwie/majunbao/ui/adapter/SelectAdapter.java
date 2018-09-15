package com.bwie.majunbao.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bwie.majunbao.R;
import com.bwie.majunbao.entity.SelectProductEntity;
import com.bwie.majunbao.ui.activity.SearchActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class SelectAdapter extends RecyclerView.Adapter<SelectAdapter.SelectViewHolder> {

    private Context context;
    private List<SelectProductEntity.DataBean> list;

    public SelectAdapter(Context context, List<SelectProductEntity.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public SelectAdapter.SelectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.select_product_adapter, parent, false);
        return new SelectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectAdapter.SelectViewHolder holder, int position) {
        holder.mTitle.setText(list.get(position).getTitle());
        holder.mPrice.setText(list.get(position).getPrice()+"");
        holder.mImg.setImageURI(list.get(position).getImages().split("\\|")[0]);
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    public class SelectViewHolder extends RecyclerView.ViewHolder {

        private  TextView mTitle;
        private  TextView mPrice;
        private  SimpleDraweeView mImg;

        public SelectViewHolder(View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.text_title);
            mPrice = itemView.findViewById(R.id.text_price);
            mImg = itemView.findViewById(R.id.select_img);
        }
    }
}
