package com.bwie.majunbao.model;


import com.bwie.majunbao.api.ProductApi;
import com.bwie.majunbao.api.UserApi;
import com.bwie.majunbao.common.Constants;
import com.bwie.majunbao.contract.FenLeiContract;
import com.bwie.majunbao.entity.FenLeiEntity;
import com.bwie.majunbao.entity.LeftFenLeiEntity;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import majunbao.bwie.com.jingdong_base_marster.net.RetrofitUtils;


public class FenLeiModel implements FenLeiContract.FenLeiModel {

    @Override
    public Observable<LeftFenLeiEntity> leftClass() {
        return RetrofitUtils.getInstance().createApi(Constants.BASE_URL,ProductApi.class).leftClass().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<FenLeiEntity> rightClass(String cid) {
        return RetrofitUtils.getInstance().createApi(Constants.BASE_URL,ProductApi.class).rightClass(cid).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

    }

}