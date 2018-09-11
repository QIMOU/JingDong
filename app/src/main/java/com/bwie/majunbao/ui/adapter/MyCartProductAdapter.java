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
import com.bwie.majunbao.eventbus.NotifaCartAdapter;
import com.bwie.majunbao.eventbus.NotifyCart;

import org.greenrobot.eventbus.EventBus;

import java.util.List;


class MyCartProductAdapter extends RecyclerView.Adapter<MyCartProductAdapter.CartViewHolder> {

    private Context context;
    private List<CartEntity.DataBean.ListBean> listBeanList;


    public MyCartProductAdapter(Context context, List<CartEntity.DataBean.ListBean> listBeanList) {
        this.context = context;
        this.listBeanList = listBeanList;
    }


    @NonNull
    @Override
    public MyCartProductAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_cart_product_adapter, parent, false);
        CartViewHolder viewHolder = new CartViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CartViewHolder holder, int position) {
        final CartEntity.DataBean.ListBean bean = listBeanList.get(position);
        holder.titleTv.setText(bean.getTitle());
        holder.priceTv.setText(bean.getPrice());
        holder.checkBox.setChecked(bean.isSelected());
        String[] images = bean.getImages().split("\\|");
        if (images!=null&&images.length>0) {
            Glide.with(context).load(images[0]).into(holder.productIv);
        }else {
            holder.productIv.setImageResource(R.mipmap.ic_launcher_round);
        }

        //点击checkbox
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.checkBox.isChecked()) {//选中
                    bean.setSelected(true);
                } else {//非选中
                    bean.setSelected(false);
                }
                //发送事件通知
                EventBus.getDefault().post(new NotifaCartAdapter());
            }
        });
    }


    @Override
    public int getItemCount() {
        return listBeanList==null?0:listBeanList.size();
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
