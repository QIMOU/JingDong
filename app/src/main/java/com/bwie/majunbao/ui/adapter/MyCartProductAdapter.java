package com.bwie.majunbao.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bwie.majunbao.R;
import com.bwie.majunbao.entity.CartEntity;
import com.bwie.majunbao.eventbus.AddCartNotifyEventbus;
import com.bwie.majunbao.eventbus.CartClickEventbus;
import com.bwie.majunbao.eventbus.DelEventBus;
import com.bwie.majunbao.eventbus.NotifutwoEventBus;
import com.bwie.majunbao.eventbus.NotifyEventBus;
import com.bwie.majunbao.eventbus.NotifyfatherAdapter;
import com.bwie.majunbao.eventbus.TotalPriceEventBus;
import com.bwie.majunbao.weiget.MyCartJiaJianView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;



class MyCartProductAdapter extends RecyclerView.Adapter<MyCartProductAdapter.CartViewHolder> {

    private Context context;
    private List<CartEntity.DataBean.ListBean> list;

    public MyCartProductAdapter(Context context, List<CartEntity.DataBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyCartProductAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.my_cart_product_adapter, parent, false);
        CartViewHolder viewHolder = new CartViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CartViewHolder holder, final int position) {
        CartEntity.DataBean.ListBean bean = list.get(position);
        holder.titleTv.setText(bean.getTitle());
        holder.priceTv.setText(bean.getBargainPrice()+"");
        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().postSticky(new DelEventBus(list.get(position).getPid()+""));
            }
        });

        String[] images = bean.getImages().split("\\|");
        if (images!=null&&images.length>0) {
            Glide.with(context).load(images[0]).into(holder.productIv);
        }else {
            holder.productIv.setImageResource(R.mipmap.ic_launcher_round);
        }
        holder.mJiajianqi.setNumEt(bean.getNum());
        holder.mJiajianqi.setJiaJianListener(new MyCartJiaJianView.JiaJianListener() {
            @Override
            public void getNum(int num) {
                list.get(position).setNum(num);
                updateCart(position);
                EventBus.getDefault().postSticky(new TotalPriceEventBus());
            }
        });
        holder.checkBox.setChecked(bean.getSelected()==1?true:false);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("aaa","点击了"+list.get(position).getTitle());
                if (holder.checkBox.isChecked()) {
                    Log.d("aaa","真");
                    list.get(position).setSelected(1);
                }else {
                    Log.d("aaa","假");
                    list.get(position).setSelected(0);
                }
                updateCart(position);
                EventBus.getDefault().postSticky(new NotifyfatherAdapter());
                EventBus.getDefault().postSticky(new NotifyEventBus());
                EventBus.getDefault().postSticky(new TotalPriceEventBus());
            }
        });
    }

    private void updateCart(int position) {
        CartClickEventbus cartClickEventbus = new CartClickEventbus(list.get(position).getPid() + "", list.get(position).getSellerid() + "", list.get(position).getSelected() + "", list.get(position).getNum() + "");
        Log.d("aaa",cartClickEventbus.toString());
        EventBus.getDefault().postSticky(cartClickEventbus);
    }
    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {

        private  ImageView productIv;
        private  CheckBox checkBox;
        private  TextView priceTv;
        private  TextView titleTv;
        private  TextView del;
        private  MyCartJiaJianView mJiajianqi;

        public CartViewHolder(View itemView) {
            super(itemView);
            productIv = itemView.findViewById(R.id.product_icon);
            checkBox = itemView.findViewById(R.id.productCheckbox);
            priceTv = itemView.findViewById(R.id.price);
            titleTv = itemView.findViewById(R.id.title);
            mJiajianqi = itemView.findViewById(R.id.jiajianqi);
            del = itemView.findViewById(R.id.del);
        }
    }

}
