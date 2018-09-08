package com.bwie.majunbao.contract;

import com.bwie.majunbao.entity.RegistEntity;

import io.reactivex.Observable;
import majunbao.bwie.com.jingdong_base_marster.base.mvp.IBaseModel;
import majunbao.bwie.com.jingdong_base_marster.base.mvp.IBasePresenter;
import majunbao.bwie.com.jingdong_base_marster.base.mvp.IBaseView;

public interface RegistContract {

    abstract class RegistPresenter extends IBasePresenter<RegistModel,IRegistView> {
        @Override
        public RegistModel getmModel() {
            return new com.bwie.majunbao.model.RegistModel();
        }

        public abstract void register(String mobile,String pwd,  String pwd2,  String nickName,  String sex,  String birthday,  String imei,  String ua, String screenSize,  String os, String email);
    }
    //model
    interface RegistModel extends IBaseModel {
        Observable<RegistEntity> register(String mobile,String pwd,  String pwd2,  String nickName,  String sex,  String birthday,  String imei,  String ua, String screenSize,  String os, String email);
    }
    //view
    interface IRegistView extends IBaseView {
        void success(RegistEntity registEntity);
        void failure(String msg);
    }
}
