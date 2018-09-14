package com.bwie.majunbao.presenter;


import android.util.Log;

import com.bwie.majunbao.contract.SelectContract;
import com.bwie.majunbao.entity.SelectProductEntity;

import io.reactivex.functions.Consumer;

public class SelectPresenter extends SelectContract.SelectPresenter {
    @Override
    public void queryProduct(String keywords, String page) {
        mModel.queryProduct(keywords,page).subscribe(new Consumer<SelectProductEntity>() {
            @Override
            public void accept(SelectProductEntity selectProductEntity) throws Exception {
                Log.i("hhh","chenggong");
                Log.i("hhh","chenggong"+selectProductEntity.getMsg());

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i("hhh","shibai");
            }
        });
    }
}
