package com.bwie.majunbao.contract;

import com.bwie.majunbao.entity.FenLeiEntity;
import com.bwie.majunbao.entity.LeftFenLeiEntity;

import io.reactivex.Observable;
import majunbao.bwie.com.jingdong_base_marster.base.mvp.IBaseModel;
import majunbao.bwie.com.jingdong_base_marster.base.mvp.IBasePresenter;
import majunbao.bwie.com.jingdong_base_marster.base.mvp.IBaseView;

public interface FenLeiContract {

    abstract class FenLeiPresenter extends IBasePresenter<FenLeiModel,IFenLeiView> {
        @Override
        public FenLeiModel getmModel() {
            return new com.bwie.majunbao.model.FenLeiModel();
        }

        public abstract void rightClass(String cid);
        public abstract void leftClass();
    }
    //model
    interface FenLeiModel extends IBaseModel {
        //左边
        Observable<LeftFenLeiEntity> leftClass();
        //右边
        Observable<FenLeiEntity> rightClass(String cid);
    }
    //view
    interface IFenLeiView extends IBaseView {
        void success(FenLeiEntity fenLeiEntity);
        void failure(String msg);
        void lsuccess(LeftFenLeiEntity leftFenLeiEntity);
    }
}
