package com.bwie.majunbao.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bwie.majunbao.R;
import com.bwie.majunbao.entity.CartEntity;

import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.CartViewHolder> {

    private Context context;
    private List<CartEntity.DataBean> cartlist;

    public MyCartAdapter(Context context, List<CartEntity.DataBean> cartlist) {
        this.context = context;
        this.cartlist = cartlist;
    }
    @NonNull
    @Override
    public MyCartAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_cart_adapter, parent, false);
        CartViewHolder viewHolder = new CartViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyCartAdapter.CartViewHolder holder, int position) {
        Log.i("cart","asd");
        CartEntity.DataBean bean = cartlist.get(position);
        holder.nameTv.setText(bean.getSellerName());
        //holder.checkbox.setChecked(bean.isSelected());
        holder.productXRY.setLayoutManager(new LinearLayoutManager(context));
        MyCartProductAdapter myCartProductAdapter = new MyCartProductAdapter(context, bean.getList());
        holder.productXRY.setAdapter(myCartProductAdapter);
    }
    @Override
    public int getItemCount() {
        return cartlist==null?0:cartlist.size();
    }
    public class CartViewHolder extends RecyclerView.ViewHolder {

        private  CheckBox checkbox;
        private  TextView nameTv;
        private  RecyclerView productXRY;

        public CartViewHolder(View itemView) {
            super(itemView);
            checkbox = itemView.findViewById(R.id.sellerCheckBox);
            nameTv = itemView.findViewById(R.id.sellerNameTv);
            productXRY = itemView.findViewById(R.id.productXRV);
        }
    }
}
