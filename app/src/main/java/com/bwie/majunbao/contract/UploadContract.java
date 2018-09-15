package com.bwie.majunbao.contract;

import com.bwie.majunbao.entity.CartEntity;
import com.bwie.majunbao.entity.UploadIconEntity;

import java.io.File;

import io.reactivex.Observable;
import majunbao.bwie.com.jingdong_base_marster.base.mvp.IBaseModel;
import majunbao.bwie.com.jingdong_base_marster.base.mvp.IBasePresenter;
import majunbao.bwie.com.jingdong_base_marster.base.mvp.IBaseView;

public interface UploadContract {
    //presenter
    abstract class UploadPresenter extends IBasePresenter<UploadModel,IUploadView>{
        @Override
        public UploadModel getmModel() {
            return new com.bwie.majunbao.model.UploadModel();
        }

        public abstract void uploadIcon(String sessionId, String userId, File file);
    }
    //view层
    interface IUploadView extends IBaseView {
        //成功
        void success(UploadIconEntity uploadIconEntity);
        //失败
        void failure(String msg);
    }
    //model层
    interface UploadModel extends IBaseModel{
        Observable<UploadIconEntity> uploadIcon(String sessionId, String userId, File file);
    }
}
