package com.bwie.majunbao.model;

import com.bwie.majunbao.api.ProductApi;
import com.bwie.majunbao.common.Constants;
import com.bwie.majunbao.contract.CartContract;
import com.bwie.majunbao.contract.ProductContract;
import com.bwie.majunbao.entity.CartEntity;
import com.bwie.majunbao.entity.ProductEntity;
import com.bwie.majunbao.entity.UpdateEntity;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import majunbao.bwie.com.jingdong_base_marster.net.RetrofitUtils;

public class CartModel implements CartContract.CartModel {
    //展示
    @Override
    public Observable<CartEntity> showCart(String uid) {
        return RetrofitUtils.getInstance().createApi(Constants.BASE_URL,ProductApi.class).showCart(uid).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    //更新
    @Override
    public Observable<UpdateEntity> updateCart(String uid, String sellerid, String pid, String selected, String num) {
        return RetrofitUtils.getInstance().createApi(Constants.BASE_URL,ProductApi.class).updateCart(uid, sellerid, pid, selected, num).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

    }


}
