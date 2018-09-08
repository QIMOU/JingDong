package com.bwie.majunbao.api;

import com.bwie.majunbao.entity.RegistEntity;
import com.bwie.majunbao.entity.UserEntity;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserApi {
    /*登陆*/
    @POST("user/v1/login")
    @FormUrlEncoded
    Observable<UserEntity> login(@Field("phone") String mobile, @Field("pwd") String pwd);
    /*注册*/
    @POST("user/v1/registerUser")
    @FormUrlEncoded
    Observable<RegistEntity> register(@Field("phone") String mobile, @Field("pwd") String pwd, @Field("pwd2") String pwd2, @Field("nickName") String nickName, @Field("sex") String sex, @Field("birthday") String birthday, @Field("imei") String imei, @Field("ua") String ua, @Field("screenSize") String screenSize, @Field("os") String os, @Field("email") String email);

}
