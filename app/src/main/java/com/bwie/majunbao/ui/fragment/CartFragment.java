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
import com.bwie.majunbao.presenter.CartPresenter;
import com.bwie.majunbao.ui.adapter.MyCartAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

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


    @Override
    protected void initView() {
        super.initView();
        /*设置刷新控件*/
        setSmartRefresh();
        //网络请求
        presenter.showCart("17415");
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
            List<CartEntity.DataBean> list = cartEntity.getData();
            my_recy.setLayoutManager(new LinearLayoutManager(getActivity()));
            mMyCartAdapter = new MyCartAdapter(getActivity(), list);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
