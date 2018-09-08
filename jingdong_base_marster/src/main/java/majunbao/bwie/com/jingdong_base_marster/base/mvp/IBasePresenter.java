package majunbao.bwie.com.jingdong_base_marster.base.mvp;

import java.lang.ref.WeakReference;

public abstract class IBasePresenter<M,V> {
    public M mModel;
    public V mView;
    private WeakReference<V> weakReference;

    public abstract M getmModel();

    //绑定
    public void attach(M mModel,V mView){
        this.mModel=mModel;
        //软引用
        weakReference = new WeakReference<>(mView);
        this.mView=weakReference.get();
    }
    //解绑
    public void detach(){
        if (weakReference!=null) {
            weakReference.clear();//清空
            weakReference=null;
        }
    }
}
