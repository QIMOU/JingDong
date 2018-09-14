package com.bwie.majunbao.api;

import com.bwie.majunbao.entity.RegistEntity;
import com.bwie.majunbao.entity.UploadIconEntity;
import com.bwie.majunbao.entity.UserEntity;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UserApi {
    /*登陆*/
    @POST("user/v1/login")
    @FormUrlEncoded
    Observable<UserEntity> login(@Field("phone") String mobile, @Field("pwd") String pwd);
    /*注册*/
    @POST("user/v1/registerUser")
    @FormUrlEncoded
    Observable<RegistEntity> register(@Field("phone") String mobile, @Field("pwd") String pwd, @Field("pwd2") String pwd2, @Field("nickName") String nickName, @Field("sex") String sex, @Field("birthday") String birthday, @Field("imei") String imei, @Field("ua") String ua, @Field("screenSize") String screenSize, @Field("os") String os, @Field("email") String email);
    //上传头像
//    @POST("user/v1/verify/uploadHeadPic")
//    @Multipart
//    Observable<UploadIconEntity> uploadIcon(@Field("phone") String mobile, @Field("pwd") String pwd,@Field("userid") String userid, @Field("sessionId") String sessionId, @Part MultipartBody.Part path);
   /* @POST("user/v1/verify/uploadHeadPic")
    @Multipart
    Observable<UploadIconEntity> uploadIcon(@HeaderMap HashMap<String,String> headers, @Part MultipartBody.Part path);*/
    @POST("user/v1/verify/uploadHeadPic")
    @Multipart
    Observable<UploadIconEntity> uploadIcon(@Header("sessionId") String sessionId,@Header("userId") String userId, @Part MultipartBody.Part path);
}
