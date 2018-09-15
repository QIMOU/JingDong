package com.bwie.majunbao.presenter;

import com.bwie.majunbao.contract.CartContract;
import com.bwie.majunbao.entity.CartEntity;
import com.bwie.majunbao.entity.UpdateEntity;

import io.reactivex.functions.Consumer;


public class CartPresenter extends CartContract.CartPresenter {


    @Override
    public void showCart(String uid) {
        mModel.showCart(uid).subscribe(new Consumer<CartEntity>() {
            @Override
            public void accept(CartEntity cartEntity) throws Exception {
                mView.success(cartEntity);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                mView.failure("网络请求失败");
            }
        });
    }
//更新
    @Override
    public void updateCart(String uid, String sellerid, String pid, String selected, String num) {
        mModel.updateCart(uid, sellerid, pid, selected, num).subscribe(new Consumer<UpdateEntity>() {
            @Override
            public void accept(UpdateEntity updateEntity) throws Exception {
                mView.updateSuccess(updateEntity);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                mView.failure("更新失败");
            }
        });
    }
}
