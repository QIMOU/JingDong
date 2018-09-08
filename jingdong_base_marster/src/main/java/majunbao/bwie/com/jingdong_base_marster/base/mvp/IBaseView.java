package majunbao.bwie.com.jingdong_base_marster.base.mvp;

public interface IBaseView {
    IBasePresenter initBasePresenter();

    //失败
    void failure();
    //显示
    void showLoader();
    //隐藏
    void hideLoader();
}
