package majunbao.bwie.com.jingdong_base_marster.base.mvp;

import majunbao.bwie.com.jingdong_base_marster.base.base_ui.BaseFragment;

public abstract class BaseMvpFragment<M extends IBaseModel,P extends IBasePresenter> extends BaseFragment implements IBaseView {
    public M model;
    public P presenter;
    @Override
    protected void initView() {
        super.initView();
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
    public void onDestroyOptionsMenu() {
        super.onDestroyOptionsMenu();
        if (presenter!=null) {
            presenter.detach();
        }
    }
}
