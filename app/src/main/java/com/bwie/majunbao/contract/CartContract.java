package com.bwie.majunbao.contract;

import com.bwie.majunbao.entity.CartEntity;
import com.bwie.majunbao.entity.DelCartEntity;
import com.bwie.majunbao.entity.UpdateEntity;

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

        //展示
        public abstract void showCart(String uid);
        //更新
        public abstract void updateCart(String uid,String sellerid,String pid,String selected,String num);
        //删除
        public abstract void delCart(String uid,String pid);
        //重新查询购物车
        public abstract void twoCart(String uid);


    }
    //view层
    interface ICartView extends IBaseView {
        //成功
        void success(CartEntity cartEntity);
        //失败
        void failure(String msg);
        //更新的成功
        void updateSuccess(UpdateEntity updateEntity);
        //删除成功
        void delSuccess(DelCartEntity delCartEntity);
        //重新查询购物车
        void cartsuccess(CartEntity cartEntity);


    }
    //model层
    interface CartModel extends IBaseModel{
        //展示
        Observable<CartEntity> showCart(String uid);
        //更新
        Observable<UpdateEntity> updateCart(String uid,String sellerid,String pid,String selected,String num);
        //删除
        Observable<DelCartEntity> delCart(String uid, String pid);
        //重新查询购物车
        Observable<CartEntity> twoCart(String uid);
    }
}
