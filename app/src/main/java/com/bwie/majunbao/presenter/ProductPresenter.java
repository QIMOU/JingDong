package com.bwie.majunbao.presenter;

import com.bwie.majunbao.contract.ProductContract;
import com.bwie.majunbao.entity.ProductEntity;

import io.reactivex.functions.Consumer;

public class ProductPresenter extends ProductContract.ProductPresenter {

    @Override
    public void showProduct() {
        mModel.showProduct().subscribe(new Consumer<ProductEntity>() {
            @Override
            public void accept(ProductEntity productEntity) throws Exception {
                mView.success(productEntity);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                mView.failure("网络请求失败");
            }
        });
    }
}
