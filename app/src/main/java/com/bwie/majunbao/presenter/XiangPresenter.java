package com.bwie.majunbao.presenter;

import com.bwie.majunbao.contract.XiangContract;
import com.bwie.majunbao.entity.XiangQingEntity;

import io.reactivex.functions.Consumer;

public class XiangPresenter extends XiangContract .XiangPresenter{

    @Override
    public void XiangQing(String pid) {
        mModel.XiangQing(pid).subscribe(new Consumer<XiangQingEntity>() {
            @Override
            public void accept(XiangQingEntity xiangQingEntity) throws Exception {
                mView.success(xiangQingEntity);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                mView.failure("网络错误");
            }
        });
    }
}
