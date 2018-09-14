package com.bwie.majunbao.contract;

import com.bwie.majunbao.entity.SelectProductEntity;
import com.bwie.majunbao.model.SelectModel;

import io.reactivex.Observable;
import majunbao.bwie.com.jingdong_base_marster.base.mvp.IBaseModel;
import majunbao.bwie.com.jingdong_base_marster.base.mvp.IBasePresenter;
import majunbao.bwie.com.jingdong_base_marster.base.mvp.IBaseView;

public interface SelectContract {
    //presenter
    abstract class SelectPresenter extends IBasePresenter<SelectModel,ISelectView> {
        @Override
        public SelectModel getmModel() {
            return new SelectModel();
        }
        public abstract void queryProduct(String keywords,String page);
    }
    //model
    interface SelectMoldel extends IBaseModel{
        Observable<SelectProductEntity> queryProduct(String keywords,String page);
    }
    //view
    interface ISelectView extends IBaseView{
        void success(SelectProductEntity selectProductEntity);
        void failure(String msg);
    }
}
