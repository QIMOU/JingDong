package com.bwie.majunbao.model;

import android.util.Log;

import com.bwie.majunbao.api.UserApi;
import com.bwie.majunbao.common.Constants;
import com.bwie.majunbao.contract.LoginContract;
import com.bwie.majunbao.entity.UserEntity;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import majunbao.bwie.com.jingdong_base_marster.net.RetrofitUtils;

public class LoginModel implements LoginContract.LoginModel {
    @Override
    public Observable<UserEntity> login(String mobile, String pwd) {
        Log.i("aaa","执行了LoginModel");
        return RetrofitUtils.getInstance().createApi(Constants.BASE_url,UserApi.class).login(mobile,pwd).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}