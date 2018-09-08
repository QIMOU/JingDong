package com.bwie.majunbao.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.majunbao.entity.ProductEntity;


class HomeFenleiViewPAdapter extends BaseAdapter {
    private Context context;
    private ProductEntity productBean;

    public HomeFenleiViewPAdapter(Context context, ProductEntity productBean) {
        this.context = context;
        this.productBean = productBean;
    }

    @Override
    public int getCount() {
        return productBean.getData().getFenlei().size();
    }

    @Override
    public Object getItem(int i) {
        return productBean.getData().getFenlei().get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
    private class ViewHolder{
        ImageView img1;
        TextView text_name;
    }
}
