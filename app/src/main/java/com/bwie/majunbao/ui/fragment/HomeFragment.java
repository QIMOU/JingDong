package com.bwie.majunbao.ui.fragment;


import android.content.Intent;
import android.graphics.Color;
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
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.majunbao.R;
import com.bwie.majunbao.contract.ProductContract;
import com.bwie.majunbao.entity.ProductEntity;
import com.bwie.majunbao.presenter.ProductPresenter;
import com.bwie.majunbao.ui.activity.HomeBannerClickActivity;
import com.bwie.majunbao.ui.activity.LiuShiActivity;
import com.bwie.majunbao.ui.adapter.HomeFragmentAdapter;
import com.gyf.barlibrary.ImmersionBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import majunbao.bwie.com.jingdong_base_marster.base.base_ui.BaseFragment;
import majunbao.bwie.com.jingdong_base_marster.base.mvp.BaseMvpFragment;
import majunbao.bwie.com.jingdong_base_marster.base.mvp.IBasePresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseMvpFragment<ProductContract.ProductModel, ProductContract.ProductPresenter> implements ProductContract.IProductView, View.OnClickListener {
    @BindView(R.id.home_recy)
    RecyclerView home_recy;
    @BindView(R.id.smart_mian)
    SmartRefreshLayout mSmartMian;
    @BindView(R.id.sao)
    ImageView sao;
    @BindView(R.id.tv_home)
    TextView tvHome;
    @BindView(R.id.xx_home)
    ImageView xxHome;
    @BindView(R.id.llSearch)
    LinearLayout head_search_rr;
    Unbinder unbinder;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mSmartMian.finishRefresh();
        }
    };
    private HomeFragmentAdapter homeFragmentAdapter;
    private int mDistanceY;

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData() {
        super.initData();
        //初始化控件
    }


    @Override
    protected void initView() {
        super.initView();
        /*设置刷新控件*/
        setSmartRefresh();
        //网络请求
        presenter.showProduct();
        //设置recyclerview展示的样式
        home_recy.setLayoutManager(new LinearLayoutManager(getActivity()));
        //设置recyclerview下滑搜索栏
        setHomeRecysearch();
        tvHome.setOnClickListener(this);
    }

    private void setHomeRecysearch() {
        home_recy.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //防止item乱跳
                //staggeredGridLayoutManager.invalidateSpanAssignments();
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //滑动的距离
                mDistanceY += dy;
                //toolbar的高度
                int toolbarHeight = head_search_rr.getBottom();

                //当滑动的距离 <= toolbar高度的时候，改变Toolbar背景色的透明度，达到渐变的效果
                if (mDistanceY < toolbarHeight) {
                    float scale = (float) mDistanceY / toolbarHeight;
                    float alpha = scale * 255;
                    head_search_rr.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
                    tvHome.setBackgroundColor(Color.argb((int) alpha, 255, 255, 255));
                } else {
                    //将标题栏的颜色设置为完全不透明状态
                    tvHome.setBackgroundResource(R.drawable.home_search_tvhome);
                    head_search_rr.setBackgroundResource(R.color.white);
                }
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
                //停止刷新
                refreshlayout.finishRefresh();

                homeFragmentAdapter.notifyDataSetChanged();
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

    /**
     * 成功
     *
     * @param productEntity
     */
    @Override
    public void success(final ProductEntity productEntity) {
        Log.i("aaa", "成功");
        Log.i("aaa", productEntity.getMsg());
        //适配器
        homeFragmentAdapter = new HomeFragmentAdapter(getActivity(), productEntity);
        //设置适配器
        home_recy.setAdapter(homeFragmentAdapter);

        //
        homeFragmentAdapter.setItemClickListener(new HomeFragmentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //  Log.i("click","点击了么?");
                Intent intent = new Intent(getActivity(), HomeBannerClickActivity.class);
                Log.i("aaa", "轮播url" + productEntity.getData().getBanner().get(position).getUrl());
                intent.putExtra("banner_url", productEntity.getData().getBanner().get(position).getUrl());
                startActivity(intent);
            }
        });
    }

    /**
     * 失败
     *
     * @param msg
     */
    @Override
    public void failure(String msg) {
        Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public IBasePresenter initBasePresenter() {
        return new ProductPresenter();
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

    //搜索的点击事件
    @Override
    public void onClick(View view) {
        startActivity(new Intent(getActivity(),LiuShiActivity.class));
    }
}
