package com.bwie.majunbao.presenter;

import com.bwie.majunbao.contract.CartContract;
import com.bwie.majunbao.entity.CartEntity;

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
}
