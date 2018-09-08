package majunbao.bwie.com.jingdong_base_marster.base.mvp;

import majunbao.bwie.com.jingdong_base_marster.base.base_ui.BaseActivity;

public abstract class BaseMvpActivity<M extends IBaseModel,P extends IBasePresenter> extends BaseActivity implements IBaseView {
    public M model;
    public P presenter;
    @Override
    protected void initData() {
        super.initData();
        presenter = (P) initBasePresenter();
        if (presenter!=null) {
            model = (M) presenter.getmModel();
            if (model!=null) {
                presenter.attach(model,this);
            }
        }

    }
    //销毁
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter!=null) {
            presenter.detach();
        }
    }
}
