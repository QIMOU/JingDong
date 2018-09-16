package com.bwie.majunbao.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.bwie.majunbao.R;
import com.bwie.majunbao.contract.SelectContract;
import com.bwie.majunbao.entity.SelectProductEntity;
import com.bwie.majunbao.presenter.SelectPresenter;
import com.bwie.majunbao.ui.adapter.SelectAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import majunbao.bwie.com.jingdong_base_marster.base.mvp.BaseMvpActivity;
import majunbao.bwie.com.jingdong_base_marster.base.mvp.IBasePresenter;

public class SearchActivity extends BaseMvpActivity<SelectContract.SelectMoldel, SelectContract.SelectPresenter> implements SelectContract.ISelectView {
    @BindView(R.id.select_recy)
    RecyclerView selectRecy;
    @BindView(R.id.refresh_select)
    SmartRefreshLayout refreshSelect;
    private int page = 1;
    private SelectAdapter mSelectAdapter;

    @Override
    protected void initData() {
        super.initData();
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        if (name != null) {
            presenter.queryProduct(name, page+"");
        }
    }

    @Override
    protected void initView() {
        super.initView();
        selectRecy.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void success(SelectProductEntity selectProductEntity) {
        Log.i("ssaaa", selectProductEntity.getMsg() + "成功");
        Log.i("hhh", selectProductEntity.getMsg() + "成功");
        List<SelectProductEntity.DataBean> data = selectProductEntity.getData();
        mSelectAdapter = new SelectAdapter(this, data);
        selectRecy.setAdapter(mSelectAdapter);

    }

    @Override
    public void failure(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public IBasePresenter initBasePresenter() {
        return new SelectPresenter();
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
