package com.bwie.majunbao.presenter;

import android.util.Log;

import com.bwie.majunbao.contract.OrderContract;
import com.bwie.majunbao.entity.OrderEntity;

import io.reactivex.functions.Consumer;


public class OrderPresenter extends OrderContract.OrderPresenter{

    @Override
    public void xiadan(String userId, String sessionId, String scheduleId, String amount, String sign) {
        mModel.xiadan(userId,sessionId,scheduleId,amount,sign).subscribe(new Consumer<OrderEntity>() {
            @Override
            public void accept(OrderEntity orderEntity) throws Exception {
                mView.success(orderEntity);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                mView.failure(throwable+"原因");
                Log.d("xiadan",throwable.toString());
            }
        });
    }
}
