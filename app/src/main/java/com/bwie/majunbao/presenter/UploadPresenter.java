package com.bwie.majunbao.presenter;

import com.bwie.majunbao.contract.CartContract;
import com.bwie.majunbao.contract.UploadContract;
import com.bwie.majunbao.entity.CartEntity;
import com.bwie.majunbao.entity.UploadIconEntity;

import java.io.File;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class UploadPresenter extends UploadContract.UploadPresenter {


    @Override
    public void uploadIcon(String sessionId, String userId, File file) {
        mModel.uploadIcon(sessionId,userId,file).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<UploadIconEntity>() {
            @Override
            public void accept(UploadIconEntity uploadIconEntity) throws Exception {
                mView.success(uploadIconEntity);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                mView.failure("上传失败");
            }
        });
    }
}
