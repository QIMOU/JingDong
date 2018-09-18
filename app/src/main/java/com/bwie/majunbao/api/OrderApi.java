package com.bwie.majunbao.api;

import com.bwie.majunbao.entity.OrderEntity;
import com.bwie.majunbao.entity.PayEntity;
import com.bwie.majunbao.entity.RegistEntity;
import com.bwie.majunbao.entity.UploadIconEntity;
import com.bwie.majunbao.entity.UserEntity;
import com.google.gson.JsonObject;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface OrderApi {
    /*购票下单*/
    @POST("movie/v1/verify/buyMovieTicket")
    @FormUrlEncoded
    Observable<OrderEntity> xiadan(@Header("userId") String userId, @Header("sessionId") String sessionId, @Field("scheduleId") String scheduleId, @Field("amount") String amount, @Field("sign") String sign);
    /*支付*/
    @POST("movie/v1/verify/pay")
    @FormUrlEncoded
    Observable<String> zhifu(@Header("userId") String userId, @Header("sessionId") String sessionId, @Field("payType") String payType, @Field("orderId") String orderId);
}
