package com.bwie.majunbao.model;

import com.bwie.majunbao.api.ProductApi;
import com.bwie.majunbao.common.Api;
import com.bwie.majunbao.common.Constants;
import com.bwie.majunbao.contract.ProductContract;
import com.bwie.majunbao.entity.ProductEntity;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import majunbao.bwie.com.jingdong_base_marster.net.RetrofitUtils;

public class ProductModel implements ProductContract.ProductModel {
    @Override
    public Observable<ProductEntity> showProduct() {
        return RetrofitUtils.getInstance().createApi(Constants.BASE_URL,ProductApi.class).showProduct().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
