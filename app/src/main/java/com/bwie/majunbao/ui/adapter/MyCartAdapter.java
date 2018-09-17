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
import com.bwie.majunbao.contract.UploadContract;
import com.bwie.majunbao.entity.CartEntity;
import com.bwie.majunbao.eventbus.CartClickEventbus;
import com.bwie.majunbao.eventbus.IntentActivityEventbus;
import com.bwie.majunbao.eventbus.NotifyEventBus;
import com.bwie.majunbao.eventbus.NotifyfatherAdapter;
import com.bwie.majunbao.eventbus.TotalPriceEventBus;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.CartViewHolder> {
    private Context context;
    private List<CartEntity.DataBean> cartlist;
    private CartEntity.DataBean.ListBean mBean1;

    public MyCartAdapter(Context context, List<CartEntity.DataBean> cartlista) {
        this.context = context;
        cartlist = cartlista;
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
        final CartEntity.DataBean bean = cartlist.get(position);
        holder.nameTv.setText(bean.getSellerName());
        holder.productXRY.setLayoutManager(new LinearLayoutManager(context));
        final MyCartProductAdapter myCartProductAdapter = new MyCartProductAdapter(context, bean.getList());
        holder.productXRY.setAdapter(myCartProductAdapter);

        for (int i = 0; i < bean.getList().size(); i++) {
            if (bean.getList().get(i).getSelected()==1?true:false) {
                holder.checkbox.setChecked(true);
            }
            else {
                holder.checkbox.setChecked(false);
                break;
            }
        }

        holder.checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.checkbox.isChecked()) {
                    for (int i = 0; i < bean.getList().size(); i++) {
                        mBean1 = bean.getList().get(i);
                        bean.getList().get(i).setSelected(1);
                        Log.d("aaa","点击父级checkbox"+ bean.getList().get(i).getTitle()+"----"+bean.getList().get(i).getSelected());
                        Log.d("aaa","真");
                    }
                }else {
                    for (int i = 0; i < bean.getList().size(); i++) {
                        mBean1 = bean.getList().get(i);
                        bean.getList().get(i).setSelected(0);
                        Log.d("aaa","点击父级checkbox"+ bean.getList().get(i).getTitle()+"----"+bean.getList().get(i).getSelected());
                        Log.d("aaa","假");
                    }
                }
                myCartProductAdapter.notifyDataSetChanged();
                updateCart();
                EventBus.getDefault().postSticky(new NotifyEventBus());
            //    EventBus.getDefault().postSticky(new NotifyEventBus());
                EventBus.getDefault().postSticky(new TotalPriceEventBus());
            }
        });



    }


    private void updateCart() {
        CartClickEventbus cartClickEventbus = new CartClickEventbus(mBean1.getPid() + "", mBean1.getSellerid() + "", mBean1.getSelected() + "", mBean1.getNum() + "");
        Log.d("aaa",cartClickEventbus.toString());
        EventBus.getDefault().postSticky(cartClickEventbus);
    }

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
}
