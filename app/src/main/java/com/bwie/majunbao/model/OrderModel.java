package com.bwie.majunbao.model;

import com.bwie.majunbao.api.OrderApi;
import com.bwie.majunbao.common.Constants;
import com.bwie.majunbao.contract.OrderContract;
import com.bwie.majunbao.entity.OrderEntity;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import majunbao.bwie.com.jingdong_base_marster.net.RetrofitUtils;

public class OrderModel implements OrderContract.OrderModel {

    @Override
    public Observable<OrderEntity> xiadan(String userId, String sessionId, String scheduleId, String amount, String sign) {
        return RetrofitUtils.getInstance().createApi(Constants.BASE_url,OrderApi.class).xiadan(userId,sessionId,scheduleId,amount,sign).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
