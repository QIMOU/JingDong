package com.bwie.majunbao.model;

import com.bwie.majunbao.api.OrderApi;
import com.bwie.majunbao.common.Api;
import com.bwie.majunbao.common.Constants;
import com.bwie.majunbao.contract.OrderContract;
import com.bwie.majunbao.contract.PayContract;
import com.bwie.majunbao.entity.OrderEntity;
import com.bwie.majunbao.entity.PayEntity;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import majunbao.bwie.com.jingdong_base_marster.net.RetrofitUtils;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class PayModel implements PayContract.PayModel {


    @Override
    public Observable<String> zhifu(String userId, String sessionId, String payType, String orderId) {
        return  new Retrofit.Builder().addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Constants.BASE_url)
                .build()
                .create(OrderApi.class)
                .zhifu(userId,sessionId,payType,orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

       // return RetrofitUtils.getInstance().createApi(Constants.BASE_url,OrderApi.class).zhifu(userId,sessionId,payType,orderId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
