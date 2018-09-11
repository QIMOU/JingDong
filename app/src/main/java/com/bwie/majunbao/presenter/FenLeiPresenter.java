package com.bwie.majunbao.presenter;

import com.bwie.majunbao.contract.FenLeiContract;
import com.bwie.majunbao.entity.FenLeiEntity;
import com.bwie.majunbao.entity.LeftFenLeiEntity;

import io.reactivex.functions.Consumer;

public class FenLeiPresenter extends FenLeiContract.FenLeiPresenter {


    @Override
    public void rightClass(String cid) {
        mModel.rightClass(cid).subscribe(new Consumer<FenLeiEntity>() {
            @Override
            public void accept(FenLeiEntity fenLeiEntity) throws Exception {
                mView.success(fenLeiEntity);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                mView.failure("右网络有问题");
            }
        });
    }

    @Override
    public void leftClass() {
        mModel.leftClass().subscribe(new Consumer<LeftFenLeiEntity>() {
            @Override
            public void accept(LeftFenLeiEntity leftFenLeiEntity) throws Exception {
                mView.lsuccess(leftFenLeiEntity);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                mView.failure("左网络有问题");
            }
        });
    }
}
