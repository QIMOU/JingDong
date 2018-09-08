package com.bwie.majunbao.contract;

import com.bwie.majunbao.entity.ProductEntity;

import io.reactivex.Observable;
import majunbao.bwie.com.jingdong_base_marster.base.mvp.IBaseModel;
import majunbao.bwie.com.jingdong_base_marster.base.mvp.IBasePresenter;
import majunbao.bwie.com.jingdong_base_marster.base.mvp.IBaseView;

public interface ProductContract {
    //presenter
    abstract class ProductPresenter extends IBasePresenter<ProductModel,IProductView>{
        @Override
        public ProductModel getmModel() {
            return new com.bwie.majunbao.model.ProductModel();
        }

        public abstract void showProduct();
    }
    //view层
    interface IProductView extends IBaseView {
        //成功
        void success(ProductEntity productEntity);
        //失败
        void failure(String msg);
    }
    //model层
    interface ProductModel extends IBaseModel{
        Observable<ProductEntity> showProduct();
    }
}
