package com.bwie.majunbao.model;

import com.bwie.majunbao.api.UserApi;
import com.bwie.majunbao.common.Constants;
import com.bwie.majunbao.contract.UploadContract;
import com.bwie.majunbao.entity.UploadIconEntity;

import java.io.File;

import io.reactivex.Observable;
import majunbao.bwie.com.jingdong_base_marster.net.RetrofitUtils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UploadModel implements UploadContract.UploadModel {
    @Override
    public Observable<UploadIconEntity> uploadIcon(String sessionId, String userId, File file) {
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("image", file.getName(), RequestBody.create(
                MediaType.parse("image/*"), file));//image/**/
        return RetrofitUtils.getInstance().createApi(Constants.BASE_url,UserApi.class).uploadIcon(sessionId,userId,filePart);
    }
}
