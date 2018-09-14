package com.bwie.majunbao.ui.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.majunbao.R;
import com.bwie.majunbao.contract.CartContract;
import com.bwie.majunbao.entity.CartEntity;
import com.bwie.majunbao.eventbus.NotifyCart;
import com.bwie.majunbao.presenter.CartPresenter;
import com.bwie.majunbao.ui.adapter.MyCartAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import majunbao.bwie.com.jingdong_base_marster.base.mvp.BaseMvpFragment;
import majunbao.bwie.com.jingdong_base_marster.base.mvp.IBasePresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends BaseMvpFragment<CartContract.CartModel, CartContract.CartPresenter> implements CartContract.ICartView {
    @BindView(R.id.my_recy)
    RecyclerView my_recy;
    @BindView(R.id.smart_mian)
    SmartRefreshLayout mSmartMian;
    @BindView(R.id.allCheckbox)
    CheckBox allCheckbox;
    @BindView(R.id.totalPriceTv)
    TextView totalPriceTv;
    @BindView(R.id.buy)
    Button buy;
    Unbinder unbinder;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mSmartMian.finishRefresh();
        }
    };
    private MyCartAdapter mMyCartAdapter;
    private List<CartEntity.DataBean> mList;


    @Override
    protected void initView() {
        super.initView();
        EventBus.getDefault().register(this);
        /*设置刷新控件*/
        setSmartRefresh();
        //网络请求
        presenter.showCart("17415");
        //设置checkbox的点击事件
        setCheckboxClick();
    }

    private void setCheckboxClick() {
        //设置全选按钮的监听
        allCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (allCheckbox.isChecked()) {
                    if (mList!=null&&mList.size()>0) {
                        for (int i = 0; i < mList.size(); i++) {
                            for (int i1 = 0; i1 < mList.get(i).getList().size(); i1++) {
                                mList.get(i).getList().get(i1).setSelected(1);//真
                            }
                        }
                    }
                }else {
                    if (mList!=null&&mList.size()>0) {
                        for (int i = 0; i < mList.size(); i++) {
                            for (int i1 = 0; i1 < mList.get(i).getList().size(); i1++) {
                                mList.get(i).getList().get(i1).setSelected(0);//假
                            }
                        }
                    }
                }

                mMyCartAdapter.notifyDataSetChanged();//全部刷新

                // TODO: 2018/9/8 计算总价
               // totalPrice();
            }

        });
    }

    /*设置刷新控件*/
    private void setSmartRefresh() {
        //自定义刷新的主要代码
        mSmartMian.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //模拟加载过程,延时关闭刷新
                mHandler.sendEmptyMessageDelayed(10, 1300);
            }
        });
        //刷新
        mSmartMian.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
               // allCheckbox.setChecked(false);
                mMyCartAdapter.notifyDataSetChanged();
                //再次网络请求
                presenter.showCart("17415");
                //allCheckbox.setSelected(false);
                notiCheckbox();
               // mMyCartAdapter.notifyDataSetChanged();
                //停止刷新
                refreshlayout.finishRefresh();
            }
        });
        //加载更多
        mSmartMian.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                //停止加载
                refreshLayout.finishLoadMore();
            }
        });
    }

    @Override
    public void success(CartEntity cartEntity) {
        Log.i("cart", "成功");
        //if (cartEntity != null && cartEntity.getData() != null) {
            Log.i("cart", "123");
             mList = cartEntity.getData();
            my_recy.setLayoutManager(new LinearLayoutManager(getActivity()));
            mMyCartAdapter = new MyCartAdapter(getActivity(), mList);
            my_recy.setAdapter(mMyCartAdapter);
        //}


    }

    @Override
    public void failure(String msg) {
        Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_cart;
    }

    @Override
    public IBasePresenter initBasePresenter() {
        return new CartPresenter();
    }

    @Override
    public void failure() {

    }

    @Override
    public void showLoader() {

    }

    @Override
    public void hideLoader() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    //处理事件
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event3(NotifyCart str) {
        System.out.println("=======1");
        notiCheckbox();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    //通知刷新checbox
    public void notiCheckbox(){

        StringBuilder stringBuilder = new StringBuilder();
        if (mMyCartAdapter != null) {
            for (int i = 0; i < mMyCartAdapter.getCartList().size(); i++) {
                for (int i1 = 0; i1 < mMyCartAdapter.getCartList().get(i).getList().size(); i1++) {
                    stringBuilder.append(mMyCartAdapter.getCartList().get(i).getList().get(i1).getSelected()==0?false:true);
                }
            }
        }

        System.out.println("sb=====" + stringBuilder.toString());

        if (stringBuilder.toString().contains("false")) {
            allCheckbox.setChecked(false);
//            totalPrice = 0;
        } else {
            allCheckbox.setChecked(true);
        }
















     /*   Log.i("notiCheckbox","notiCheckbox");
        StringBuilder stringBuilder = new StringBuilder();
        if (mMyCartAdapter != null) {
            for (int i = 0; i < mMyCartAdapter.getCartList().size(); i++) {
                 stringBuilder.append(mMyCartAdapter.getCartList().get(i).isSelected());
        for (int i1 = 0; i1 < mMyCartAdapter.getCartList().get(i).getList().size(); i1++) {
            stringBuilder.append(mMyCartAdapter.getCartList().get(i).getList().get(i1).isSelected());
        }
    }
}

        System.out.println("sb=====" + stringBuilder.toString());

        if (stringBuilder.toString().contains("false")) {
            allCheckbox.setChecked(false);
//            totalPrice = 0;
        } else {
            allCheckbox.setChecked(true);
        }
*/
        // TODO: 2018/9/8 计算总价
        //totalPrice();//计算总价
    }
}