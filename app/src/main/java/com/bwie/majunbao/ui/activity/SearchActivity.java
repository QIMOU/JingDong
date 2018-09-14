package com.bwie.majunbao.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bwie.majunbao.R;
import com.bwie.majunbao.contract.SelectContract;
import com.bwie.majunbao.entity.SelectProductEntity;
import com.bwie.majunbao.presenter.SelectPresenter;

import majunbao.bwie.com.jingdong_base_marster.base.mvp.BaseMvpActivity;
import majunbao.bwie.com.jingdong_base_marster.base.mvp.IBasePresenter;

public class SearchActivity extends BaseMvpActivity<SelectContract.SelectMoldel, SelectContract.SelectPresenter> implements SelectContract.ISelectView{
    @Override
    protected int setLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public void success(SelectProductEntity selectProductEntity) {

    }

    @Override
    public void failure(String msg) {

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
}
