package com.bwie.majunbao.model;

import com.bwie.majunbao.api.ProductApi;
import com.bwie.majunbao.common.Constants;
import com.bwie.majunbao.contract.SelectContract;
import com.bwie.majunbao.entity.SelectProductEntity;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import majunbao.bwie.com.jingdong_base_marster.net.RetrofitUtils;

public class SelectModel implements SelectContract.SelectMoldel {
    @Override
    public Observable<SelectProductEntity> queryProduct(String keywords, String page) {
        return RetrofitUtils.getInstance().createApi(Constants.BASE_URL,ProductApi.class).queryProduct(keywords,page).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
