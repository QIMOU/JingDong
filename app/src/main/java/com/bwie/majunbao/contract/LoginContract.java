package com.bwie.majunbao.contract;

import com.bwie.majunbao.entity.UserEntity;

import io.reactivex.Observable;
import majunbao.bwie.com.jingdong_base_marster.base.mvp.IBaseModel;
import majunbao.bwie.com.jingdong_base_marster.base.mvp.IBasePresenter;
import majunbao.bwie.com.jingdong_base_marster.base.mvp.IBaseView;

public interface LoginContract {

    abstract class LoginPresenter extends IBasePresenter<LoginModel,ILoginView> {
        @Override
        public LoginModel getmModel() {
            return new com.bwie.majunbao.model.LoginModel();
        }

        public abstract void login(String s, String s1);
    }
    //model
    interface LoginModel extends IBaseModel {
        Observable<UserEntity> login(String mobile, String pwd);
    }
    //view
    interface ILoginView extends IBaseView {
        void success(UserEntity userEntity);
        void failure(String msg);
    }
}
