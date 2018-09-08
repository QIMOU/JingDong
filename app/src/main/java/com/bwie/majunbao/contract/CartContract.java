package com.bwie.majunbao.contract;

import com.bwie.majunbao.entity.CartEntity;
import io.reactivex.Observable;
import majunbao.bwie.com.jingdong_base_marster.base.mvp.IBaseModel;
import majunbao.bwie.com.jingdong_base_marster.base.mvp.IBasePresenter;
import majunbao.bwie.com.jingdong_base_marster.base.mvp.IBaseView;

public interface CartContract {
    //presenter
    abstract class CartPresenter extends IBasePresenter<CartModel,ICartView>{
        @Override
        public CartModel getmModel() {
            return new com.bwie.majunbao.model.CartModel();
        }

        public abstract void showCart(String uid);
    }
    //view层
    interface ICartView extends IBaseView {
        //成功
        void success(CartEntity cartEntity);
        //失败
        void failure(String msg);
    }
    //model层
    interface CartModel extends IBaseModel{
        Observable<CartEntity> showCart(String uid);
    }
}
