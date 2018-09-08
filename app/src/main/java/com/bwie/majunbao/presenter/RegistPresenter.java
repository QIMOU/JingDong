package com.bwie.majunbao.presenter;

import com.bwie.majunbao.contract.RegistContract;
import com.bwie.majunbao.entity.RegistEntity;

import io.reactivex.functions.Consumer;

public class RegistPresenter extends RegistContract.RegistPresenter {


    @Override
    public void register(String mobile, String pwd, String pwd2, String nickName, String sex, String birthday, String imei, String ua, String screenSize, String os, String email) {
        mModel.register(mobile,pwd,pwd2,nickName,sex,birthday,imei,ua,screenSize,os,email).subscribe(new Consumer<RegistEntity>() {
            @Override
            public void accept(RegistEntity registEntity) throws Exception {
                mView.success(registEntity);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        });
    }
}
