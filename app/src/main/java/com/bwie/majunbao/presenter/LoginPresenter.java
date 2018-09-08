package com.bwie.majunbao.presenter;

import com.bwie.majunbao.contract.LoginContract;
import com.bwie.majunbao.entity.UserEntity;

import io.reactivex.functions.Consumer;

public class LoginPresenter extends LoginContract.LoginPresenter {

    @Override
    public void login(String s, String s1) {
        mModel.login(s,s1).subscribe(new Consumer<UserEntity>() {
            @Override
            public void accept(UserEntity userEntity) throws Exception {
                mView.success(userEntity);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                mView.failure("网络有问题");
            }
        });
    }
}
