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
import com.bwie.majunbao.eventbus.NotifaCartAdapter;
import com.bwie.majunbao.eventbus.NotifyCart;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.CartViewHolder> {

    private Context context;
    private List<CartEntity.DataBean> cartlist;

    public MyCartAdapter(Context context, List<CartEntity.DataBean> cartlista) {
        this.context = context;
        cartlist = cartlista;
        EventBus.getDefault().register(this);
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
        final CartEntity.DataBean bean = cartlist.get(position);
        holder.nameTv.setText(bean.getSellerName());
        //holder.checkbox.setChecked(bean.isSelected());
        for (int i = 0; i < bean.getList().size(); i++) {
            if (bean.getList().get(i).getSelected()==0) {//假
                holder.checkbox.setChecked(false);
                break;
            }else if(bean.getList().get(i).getSelected()==1){//真
                holder.checkbox.setChecked(true);
            }
        }

        holder.productXRY.setLayoutManager(new LinearLayoutManager(context));
        MyCartProductAdapter myCartProductAdapter = new MyCartProductAdapter(context, bean.getList(),cartlist);
        holder.productXRY.setAdapter(myCartProductAdapter);
        //长安删除


        //设置商家的checkbox点击事件，逻辑：勾选则子列表全部勾选，取消则全部取消
        holder.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < bean.getList().size(); i++) {
                    if (holder.checkbox.isChecked()) {
                        bean.getList().get(i).setSelected(1);//真
                    }else {
                        bean.getList().get(i).setSelected(0);//假
                    }
                }
                notifyDataSetChanged();
                //发送事件
                EventBus.getDefault().post(new NotifyCart());

            }
        });

    }

    /**
     * 暴露修改之后的最新的集合数据
     * @return
     */
    public List<CartEntity.DataBean> getCartList() {
        return cartlist;
    }
    @Override
    public int getItemCount() {
        return cartlist==null?0:cartlist.size();
    }
    public class CartViewHolder extends RecyclerView.ViewHolder {

        private  CheckBox checkbox;
        private  TextView nameTv;
        private SwipeMenuRecyclerView productXRY;

        public CartViewHolder(View itemView) {
            super(itemView);
            checkbox = itemView.findViewById(R.id.sellerCheckBox);
            nameTv = itemView.findViewById(R.id.sellerNameTv);
            productXRY = itemView.findViewById(R.id.productXRV);
        }
    }
    //处理事件
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event3(NotifaCartAdapter str) {
        notifyDataSetChanged();
        EventBus.getDefault().post(new NotifyCart());
    }

}
