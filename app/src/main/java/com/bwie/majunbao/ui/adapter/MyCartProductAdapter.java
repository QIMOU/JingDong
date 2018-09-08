package com.bwie.majunbao.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwie.majunbao.R;
import com.bwie.majunbao.entity.CartEntity;

import java.util.List;


class MyCartProductAdapter extends RecyclerView.Adapter<MyCartProductAdapter.CartViewHolder> {

    private Context context;
    private List<CartEntity.DataBean.ListBean> listBeanList;
    //private CartAllCheckListener cartAllCheckListener;
    //private CartCheckListener checkListener;

    public MyCartProductAdapter(Context context, List<CartEntity.DataBean.ListBean> listBeanList) {
        this.context = context;
        this.listBeanList = listBeanList;
    }


    /*暴露给调用者进行回调*/

   /* public void setCartAllCheckListener(CartAllCheckListener cartAllCheckListener) {
        this.cartAllCheckListener = cartAllCheckListener;
    }

    public void setCheckListener(CartCheckListener checkListener) {
        this.checkListener = checkListener;
    }*/

    @NonNull
    @Override
    public MyCartProductAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_cart_product_adapter, parent, false);
        CartViewHolder viewHolder = new CartViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartEntity.DataBean.ListBean bean = listBeanList.get(position);
        holder.titleTv.setText(bean.getTitle());
        holder.priceTv.setText(bean.getPrice());
       // holder.checkBox.setChecked(bean.isSelected());
        String[] images = bean.getImages().split("\\|");
        if (images!=null&&images.length>0) {
            Glide.with(context).load(images[0]).into(holder.productIv);
        }else {
            holder.productIv.setImageResource(R.mipmap.ic_launcher_round);
        }
    }

   /* @Override
    public void onBindViewHolder(@NonNull final ProductAdapter.CartViewHolder holder, int position) {
        final CartBean.DataBean.ListBean bean = listBeanList.get(position);
        holder.titleTv.setText(bean.getTitle());
        holder.priceTv.setText(bean.getPrice());
        holder.checkBox.setChecked(bean.isSelected());
        String[] images = bean.getImages().split("\\|");
        if (images!=null&&images.length>0) {
            Glide.with(context).load(images[0]).into(holder.productIv);
        }else {
            holder.productIv.setImageResource(R.mipmap.ic_launcher_round);
        }

        // TODO: 2018/8/24 加减器
       *//* holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.checkBox.isChecked()) {
                    bean.setSelected(true);
                }else {
                    bean.setSelected(false);
                }
                if (checkListener!=null) {
                    checkListener.notifyParent();
                }
            }
        });*//*
    }*/

    @Override
    public int getItemCount() {
        return listBeanList.size()==0?0:listBeanList.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {

        private final ImageView productIv;
        private final CheckBox checkBox;
        private final TextView priceTv;
        private final TextView titleTv;

        public CartViewHolder(View itemView) {
            super(itemView);
            productIv = itemView.findViewById(R.id.product_icon);
            checkBox = itemView.findViewById(R.id.productCheckbox);
            priceTv = itemView.findViewById(R.id.price);
            titleTv = itemView.findViewById(R.id.title);
            // TODO: 2018/8/24 加减器没写
        }
    }
}
