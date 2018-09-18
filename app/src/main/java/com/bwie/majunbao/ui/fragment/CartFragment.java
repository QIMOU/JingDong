package com.bwie.majunbao.ui.fragment;


import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bwie.majunbao.R;
import com.bwie.majunbao.contract.CartContract;
import com.bwie.majunbao.entity.CartEntity;
import com.bwie.majunbao.entity.DelCartEntity;
import com.bwie.majunbao.entity.UpdateEntity;
import com.bwie.majunbao.eventbus.AddCartNotifyEventbus;
import com.bwie.majunbao.eventbus.CartClickEventbus;
import com.bwie.majunbao.eventbus.DelEventBus;
import com.bwie.majunbao.eventbus.NotifutwoEventBus;
import com.bwie.majunbao.eventbus.NotifyEventBus;
import com.bwie.majunbao.eventbus.NotifyfatherAdapter;
import com.bwie.majunbao.eventbus.TotalPriceEventBus;
import com.bwie.majunbao.presenter.CartPresenter;
import com.bwie.majunbao.ui.activity.PayOrderActivity;
import com.bwie.majunbao.ui.adapter.MyCartAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
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
public class CartFragment extends BaseMvpFragment<CartContract.CartModel, CartContract.CartPresenter> implements CartContract.ICartView, View.OnClickListener {
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
    @BindView(R.id.ll2)
    LinearLayout ll2;
    @BindView(R.id.ll)
    LinearLayout ll;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            presenter.showCart("17415");
            mSmartMian.finishRefresh();
        }
    };
    private MyCartAdapter mMyCartAdapter;
    private List<CartEntity.DataBean> mList;
    private CartEntity.DataBean.ListBean mBean;
    private String mAddpid;
    private List<CartEntity.DataBean> mDataBeanList;

    @Override
    protected void initView() {
        super.initView();
        /*设置刷新控件*/
        setSmartRefresh();
        //网络请求
        presenter.showCart("17415");
        setSelected();
        buy.setOnClickListener(this);
    }

    private void setSelected() {
        allCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (allCheckbox.isChecked()) {
                    for (int i = 0; i < mList.size(); i++) {
                        for (int i1 = 0; i1 < mList.get(i).getList().size(); i1++) {
                            mBean = mList.get(i).getList().get(i1);
                            mList.get(i).getList().get(i1).setSelected(1);
                            Log.d("aaa", "点击allcheckbox" + "第" + i + "个" + mList.get(i).getList().get(i1).getTitle() + "----" + mList.get(i).getList().get(i1).getSelected());
                            String selected = mBean.getSelected() + "";
                            String num = mBean.getNum() + "";
                            String pid = mBean.getPid() + "";
                            String sellerid = mBean.getSellerid() + "";
                            Log.d("aaa", "全选中后数据是" + "selected" + selected + "num" + num + "pid" + pid + "sellerid" + sellerid + "---" + mBean.getTitle());
                            if (num != null && pid != null && selected != null && sellerid != null) {
                                Log.d("aaa", "执行到了全选后");
                                presenter.updateCart("17415", sellerid, pid, selected, num);
                                try {
                                    Thread.sleep(80);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                } else {
                    for (int i = 0; i < mList.size(); i++) {
                        for (int i1 = 0; i1 < mList.get(i).getList().size(); i1++) {
                            mBean = mList.get(i).getList().get(i1);
                            mList.get(i).getList().get(i1).setSelected(0);
                            Log.d("aaa", "点击allcheckbox" + "第" + i + "个" + mList.get(i).getList().get(i1).getTitle() + "----" + mList.get(i).getList().get(i1).getSelected());
                            String selected = mBean.getSelected() + "";
                            String num = mBean.getNum() + "";
                            String pid = mBean.getPid() + "";
                            String sellerid = mBean.getSellerid() + "";
                            if (num != null && pid != null && selected != null && sellerid != null) {
                                presenter.updateCart("17415", sellerid, pid, selected, num);
                                try {
                                    Thread.sleep(80);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                            ;
                        }
                    }

                }
                mMyCartAdapter.notifyDataSetChanged();
                //计算
                jSPrice();
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        //注册
        EventBus.getDefault().register(this);
        my_recy.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void success(CartEntity cartEntity) {
            mList = cartEntity.getData();
            mMyCartAdapter = new MyCartAdapter(getActivity(), mList);
            my_recy.setAdapter(mMyCartAdapter);

            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < mList.size(); i++) {
                for (int i1 = 0; i1 < mList.get(i).getList().size(); i1++) {
                    builder.append(mList.get(i).getList().get(i1).getSelected());
                    Log.d("bbb", mList.get(i).getList().get(i1).getPid() + "pid是");
                    Log.d("bbb", mAddpid + "mpid");
                }
            }
            if (builder.toString().contains("0")) {
                allCheckbox.setChecked(false);
            } else {
                allCheckbox.setChecked(true);
            }
            jSPrice();

    }

    @Override
    public void failure(String msg) {
        //Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
        Log.d("error","购物车请求失败");

    }

    @Override
    public void updateSuccess(UpdateEntity updateEntity) {
        Log.d("aaa", "成功" + updateEntity.getMsg());
    }

    //删除成功
    @Override
    public void delSuccess(DelCartEntity delCartEntity) {
        Log.d("ccc", "成功" + delCartEntity.getMsg());
        presenter.twoCart("17415");
    }

    //重新查询购物车
    @Override
    public void cartsuccess(CartEntity cartEntity) {
        if (cartEntity!=null&&cartEntity.getData().size()>0) {
            Log.d("ccc","重新查询成功");
        }else {
            ll.setVisibility(View.GONE);
            ll2.setVisibility(View.VISIBLE);
        }
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
        EventBus.getDefault().unregister(this);
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

    /*设置刷新控件*/
    private void setSmartRefresh() {
        mSmartMian.setEnableLoadMore(false);
        //自定义刷新的主要代码
        mSmartMian.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //模拟加载过程,延时关闭刷新
                mHandler.sendEmptyMessageDelayed(10, 1300);
                presenter.showCart("17415");
            }
        });
    /*    //刷新
        mSmartMian.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                //停止刷新
                refreshlayout.finishRefresh();
            }
        });*/
    }

    private void jSPrice() {
        double mZong = 0;
        for (int i = 0; i < mList.size(); i++) {
            for (int i1 = 0; i1 < mList.get(i).getList().size(); i1++) {
                if (mList.get(i).getList().get(i1).getSelected() == 1) {
                    CartEntity.DataBean.ListBean bean = mList.get(i).getList().get(i1);
                    int num = bean.getNum();
                    double bargainPrice = bean.getBargainPrice();
                    mZong += num * bargainPrice;
                    Log.d("aaa", "zongjia" + mZong);
                }
            }
        }
        totalPriceTv.setText("总价:" + mZong + "$");
    }

    //处理事件
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void Event(CartClickEventbus cartClickEventbus) {
        String num = cartClickEventbus.getNum();
        String pid = cartClickEventbus.getPid();
        String selected = cartClickEventbus.getSelected();
        String sellerid = cartClickEventbus.getSellerid();
        if (num != null && pid != null && selected != null && sellerid != null) {
            presenter.updateCart("17415", sellerid, pid, selected, num);
        }
    }

    //处理事件
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void Event(NotifyfatherAdapter notifyfatherAdapter) {
        mMyCartAdapter.notifyDataSetChanged();

    }

    //处理事件
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void Event(AddCartNotifyEventbus addCartNotifyEventbus) {
        Log.d("bbbb", "走到了添加的方法");
        mAddpid = addCartNotifyEventbus.getPid();
        // Log.d("bbbb", "走到了添加的方法2");
        //重新查询购物车
        presenter.showCart("17415");
    }

    //处理事件
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void Event(NotifyEventBus notifyEventbus) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < mList.size(); i++) {
            for (int i1 = 0; i1 < mList.get(i).getList().size(); i1++) {
                builder.append(mList.get(i).getList().get(i1).getSelected());
            }
        }
        if (builder.toString().contains("0")) {
            allCheckbox.setChecked(false);
        } else {
            allCheckbox.setChecked(true);
        }
    }

    //处理事件
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void Event(TotalPriceEventBus totalPriceEventBus) {
        //计算
        jSPrice();
    }
    //处理事件
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void Event(DelEventBus delEventBus) {
        //计算
        String pid = delEventBus.getPid();
        presenter.delCart("17415",pid);
    }

    //下单
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getActivity(), PayOrderActivity.class);
        startActivity(intent);
    }
}