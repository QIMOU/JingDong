package com.bwie.majunbao.model;

import com.bwie.majunbao.api.ProductApi;
import com.bwie.majunbao.common.Constants;
import com.bwie.majunbao.contract.XiangContract;
import com.bwie.majunbao.entity.XiangQingEntity;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import majunbao.bwie.com.jingdong_base_marster.net.RetrofitUtils;

public class XiangModel implements XiangContract.XiangModel {
    @Override
    public Observable<XiangQingEntity> XiangQing(String pid) {
        return RetrofitUtils.getInstance().createApi(Constants.BASE_URL,ProductApi.class).XiangQing(pid).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
