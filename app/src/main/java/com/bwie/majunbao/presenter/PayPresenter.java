package com.bwie.majunbao.presenter;

import android.util.Log;
import android.view.View;

import com.bwie.majunbao.contract.OrderContract;
import com.bwie.majunbao.contract.PayContract;
import com.bwie.majunbao.entity.OrderEntity;
import com.bwie.majunbao.entity.PayEntity;
import com.google.gson.JsonObject;

import io.reactivex.functions.Consumer;


public class PayPresenter extends PayContract.PayPresenter {

    @Override
    public void zhifu(String userId, String sessionId, String payType, String orderId) {
      /*  mModel.zhifu(userId,sessionId,payType,orderId).subscribe(new Consumer<String>() {
            @Override
            public void accept(String payEntity) {
                mView.success(payEntity);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                mView.failure("请求支付失败");
            }
        });*/
      mModel.zhifu(userId, sessionId, payType, orderId).subscribe(new Consumer<String>() {
          @Override
          public void accept(String payEntity) throws Exception {
              Log.e("----------------", "accept: "+payEntity.toString() );
              mView.success(payEntity);
          }
      }, new Consumer<Throwable>() {
          @Override
          public void accept(Throwable throwable) throws Exception {
              Log.d("zhi",throwable.toString()+"haha");
          }
      });
    }
}
