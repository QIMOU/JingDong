package majunbao.bwie.com.jingdong_base_marster.base;

import android.app.Application;
import android.content.Context;

public class BaseApp extends Application {

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }
}
