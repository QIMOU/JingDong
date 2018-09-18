package com.bwie.majunbao.contract;

import com.bwie.majunbao.entity.OrderEntity;
import com.bwie.majunbao.entity.UserEntity;

import io.reactivex.Observable;
import majunbao.bwie.com.jingdong_base_marster.base.mvp.IBaseModel;
import majunbao.bwie.com.jingdong_base_marster.base.mvp.IBasePresenter;
import majunbao.bwie.com.jingdong_base_marster.base.mvp.IBaseView;

public interface OrderContract {
    //presenter
    abstract class OrderPresenter extends IBasePresenter<OrderModel,IOrderView> {
        @Override
        public OrderModel getmModel() {
            return new com.bwie.majunbao.model.OrderModel();
        }
        public abstract void xiadan(String userId,String sessionId,String scheduleId,String amount,String sign);

    }

    //model
    interface OrderModel extends IBaseModel{
        Observable<OrderEntity> xiadan(String userId, String sessionId, String scheduleId, String amount, String sign);
    }
    //view
    interface IOrderView extends IBaseView {
        void success(OrderEntity orderEntity);
        void failure(String msg);
    }
}
