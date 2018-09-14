package com.bwie.majunbao.contract;

import com.bwie.majunbao.entity.XiangQingEntity;

import io.reactivex.Observable;
import majunbao.bwie.com.jingdong_base_marster.base.mvp.IBaseModel;
import majunbao.bwie.com.jingdong_base_marster.base.mvp.IBasePresenter;
import majunbao.bwie.com.jingdong_base_marster.base.mvp.IBaseView;

public interface XiangContract {
    //presenter
    abstract class XiangPresenter extends IBasePresenter<XiangModel,IXiangView> {
        @Override
        public XiangModel getmModel() {
            return new com.bwie.majunbao.model.XiangModel();
        }
        public abstract void XiangQing(String pid);

    }

    //model
    interface XiangModel extends IBaseModel{
        Observable<XiangQingEntity> XiangQing(String pid);
    }
    //view
    interface IXiangView extends IBaseView {
        void success(XiangQingEntity xiangQingEntity);
        void failure(String msg);
    }
}
