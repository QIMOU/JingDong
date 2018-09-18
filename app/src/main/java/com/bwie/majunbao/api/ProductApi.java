package com.bwie.majunbao.api;

import com.bwie.majunbao.entity.CartEntity;
import com.bwie.majunbao.entity.DelCartEntity;
import com.bwie.majunbao.entity.FenLeiEntity;
import com.bwie.majunbao.entity.GengXinEntity;
import com.bwie.majunbao.entity.LeftFenLeiEntity;
import com.bwie.majunbao.entity.ProductEntity;
import com.bwie.majunbao.entity.SelectProductEntity;
import com.bwie.majunbao.entity.UpdateEntity;
import com.bwie.majunbao.entity.UploadIconEntity;
import com.bwie.majunbao.entity.XiangQingEntity;
import com.bwie.majunbao.entity.AddCartEntity;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ProductApi {
    /*首页广告（轮播图+京东秒杀+最底部的为你推荐）*/
    @POST("home/getHome")
    Observable<ProductEntity> showProduct();
    /*查询购物车*/
    @POST("product/getCarts")
    @FormUrlEncoded
    Observable<CartEntity> showCart(@Field("uid") String uid);
    /*左边分类*/
    @POST("product/getCatagory")
    Observable<LeftFenLeiEntity> leftClass();
    /*右边分类*/
    @POST("product/getProductCatagory")
    @FormUrlEncoded
    Observable<FenLeiEntity> rightClass(@Field("cid") String cid);
    //搜索商品
    @POST("product/searchProducts")
    @FormUrlEncoded
    Observable<SelectProductEntity> queryProduct(@Field("keywords") String keyword,@Field("page") String page);
    //发现
    @POST("satinApi?type=1&page=1")
    Observable<CartEntity> FaXian();
    //商品详情
    /*product/getProductDetail*/
    @POST("product/getProductDetail")
    @FormUrlEncoded
    Observable<XiangQingEntity> XiangQing(@Field("pid") String pid);
    //添加购物车
    @POST("product/addCart")
    @FormUrlEncoded
    Observable<AddCartEntity> addCart(@Field("uid") String uid, @Field("pid") String pid);
    //更新购物车
    @POST("product/updateCarts")
    @FormUrlEncoded
    Observable<UpdateEntity> updateCart(@Field("uid") String uid, @Field("sellerid") String sellerid, @Field("pid") String pid, @Field("selected") String selected, @Field("num") String num);
    //删除购物车
    @POST("product/deleteCart")
    @FormUrlEncoded
    Observable<DelCartEntity> delCart(@Field("uid") String uid, @Field("pid") String pid);
    /*查询购物车*/
    @POST("product/getCarts")
    @FormUrlEncoded
    Observable<CartEntity> twoCart(@Field("uid") String uid);

}
