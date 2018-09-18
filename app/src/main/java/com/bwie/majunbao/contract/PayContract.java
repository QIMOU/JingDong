package com.bwie.majunbao.contract;

import com.bwie.majunbao.entity.OrderEntity;
import com.bwie.majunbao.entity.PayEntity;

import io.reactivex.Observable;
import majunbao.bwie.com.jingdong_base_marster.base.mvp.IBaseModel;
import majunbao.bwie.com.jingdong_base_marster.base.mvp.IBasePresenter;
import majunbao.bwie.com.jingdong_base_marster.base.mvp.IBaseView;
import retrofit2.http.Field;
import retrofit2.http.Header;

public interface PayContract {
    //presenter
    abstract class PayPresenter extends IBasePresenter<PayModel,IPayView> {
        @Override
        public PayModel getmModel() {
            return new com.bwie.majunbao.model.PayModel();
        }
        public abstract void zhifu(String userId,  String sessionId, String payType, String orderId);

    }

    //model
    interface PayModel extends IBaseModel{
        Observable<String> zhifu(String userId, String sessionId, String payType, String orderId);
    }
    //view
    interface IPayView extends IBaseView {
        void success(String payEntity);
        void failure(String msg);
    }
}
