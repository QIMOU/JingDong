package com.bwie.majunbao.model;


import com.bwie.majunbao.api.UserApi;
import com.bwie.majunbao.common.Constants;
import com.bwie.majunbao.contract.RegistContract;
import com.bwie.majunbao.entity.RegistEntity;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import majunbao.bwie.com.jingdong_base_marster.net.RetrofitUtils;

public class RegistModel implements RegistContract.RegistModel {

    @Override
    public Observable<RegistEntity> register(String mobile, String pwd, String pwd2, String nickName, String sex, String birthday, String imei, String ua, String screenSize, String os, String email) {
        return RetrofitUtils.getInstance().createApi(Constants.BASE_url,UserApi.class).register(mobile,pwd,pwd2,nickName,sex,birthday,imei,ua,screenSize,os,email).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}