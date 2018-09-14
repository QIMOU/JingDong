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
import com.bwie.majunbao.api.ProductApi;
import com.bwie.majunbao.common.Api;
import com.bwie.majunbao.common.Constants;
import com.bwie.majunbao.entity.AddCartEntity;
import com.bwie.majunbao.entity.CartEntity;
import com.bwie.majunbao.entity.DelCartEntity;
import com.bwie.majunbao.entity.GengXinEntity;
import com.bwie.majunbao.eventbus.NotifaCartAdapter;
import com.bwie.majunbao.eventbus.NotifyCart;
import com.bwie.majunbao.ui.activity.XiangQingActivity;
import com.bwie.majunbao.weiget.MyCartJiaJianView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.w3c.dom.Text;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import majunbao.bwie.com.jingdong_base_marster.net.RetrofitUtils;


class MyCartProductAdapter extends RecyclerView.Adapter<MyCartProductAdapter.CartViewHolder> {

    private Context context;
    private List<CartEntity.DataBean.ListBean> listBeanList;
    private  List<CartEntity.DataBean> listbean;
    private int mSelected;
    private int mSellerid;
    private int mNum;
    private int mPid;
    private String mNum1;


    public MyCartProductAdapter(Context context, List<CartEntity.DataBean.ListBean> listBeanList, List<CartEntity.DataBean> listbean) {
        this.context = context;
        this.listBeanList = listBeanList;
        this.listbean = listbean;
        //初始化EventBus
        EventBus.getDefault().register(this);
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
        final CartEntity.DataBean.ListBean bean = listBeanList.get(position);
        holder.titleTv.setText(bean.getTitle());
        holder.priceTv.setText(bean.getPrice());
        holder.mJiajianqi.setNumEt(bean.getNum());
        holder.checkBox.setChecked(bean.getSelected()==0?false:true);

       /* holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String pid = listbean.get(position).getList().get(position).getPid()+"";
                delCart(pid);
                Toast.makeText(context, listbean.get(position).getList().get(position).getTitle(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });*/

        String[] images = bean.getImages().split("\\|");
        if (images!=null&&images.length>0) {
            Glide.with(context).load(images[0]).into(holder.productIv);
        }else {
            holder.productIv.setImageResource(R.mipmap.ic_launcher_round);
        }

/*
        if (mNum1!=null){
            int shuzi = Integer.parseInt(mNum1);
            //加减器
            holder.mJiajianqi.setNumEt(shuzi);
        }*/

        //点击checkbox
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.checkBox.isChecked()) {//选中
                   bean.setSelected(1);//真
                }else {
                    bean.setSelected(0);//假
                }

                //发送事件通知
                EventBus.getDefault().post(new NotifaCartAdapter());
            }
        });
        //获取数据进行更新
        mSelected = bean.getSelected();
        mSellerid = bean.getSellerid();
        mNum = bean.getNum();
        mPid = bean.getPid();
        //更逊购物车
        gengXin();

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
        private final MyCartJiaJianView mJiajianqi;

        public CartViewHolder(View itemView) {
            super(itemView);
            productIv = itemView.findViewById(R.id.product_icon);
            checkBox = itemView.findViewById(R.id.productCheckbox);
            priceTv = itemView.findViewById(R.id.price);
            titleTv = itemView.findViewById(R.id.title);
            mJiajianqi = itemView.findViewById(R.id.jiajianqi);
        }
    }

    //处理事件
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void JiaJian(String s) {
        mNum1 = s;
        Log.i("ppp",mNum1);
    }

    //请求网络更新数据
    public void gengXin(){
        Log.i("aaa","17415"+"mSellerid"+mSellerid+"mPid"+mPid+"mSelected"+mSelected+"mNum"+mNum+"");
        RetrofitUtils.getInstance().createApi(Constants.BASE_URL,ProductApi.class).gengXinCart("17415",mSellerid+"",mPid+"",mSelected+"",mNum+"").subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<GengXinEntity>() {
            @Override
            public void accept(GengXinEntity gengXinEntity) throws Exception {
                Log.i("aaa","更新了");
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i("aaa","没更新");
            }
        });
    }
    //删除数据
    public void delCart(String pid){
        RetrofitUtils.getInstance().createApi(Constants.BASE_URL,ProductApi.class).delCart("17415",pid).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<DelCartEntity>() {
            @Override
            public void accept(DelCartEntity delCartEntity) throws Exception {
                Toast.makeText(context, "删除购物车成功", Toast.LENGTH_SHORT).show();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Toast.makeText(context, "删除购物车失败", Toast.LENGTH_SHORT).show();
            }
        });
}
}
