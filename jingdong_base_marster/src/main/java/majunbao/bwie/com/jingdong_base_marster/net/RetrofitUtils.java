package majunbao.bwie.com.jingdong_base_marster.net;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtils {
    private static RetrofitUtils retrofitUtils;
    private final OkHttpClient okHttpClient;

    /**
     * 双重检验锁
     * @return
     */
    public static RetrofitUtils getInstance() {
        if (retrofitUtils==null) {
            synchronized (RetrofitUtils.class){
                if (retrofitUtils==null) {
                    retrofitUtils=new RetrofitUtils();
                }
            }
        }
        return retrofitUtils;
    }


    private RetrofitUtils() {
        //设置日志拦截器
        HttpLoggingInterceptor httpLoggingInterceptor=new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)//设置日志拦截器
                .writeTimeout(5,TimeUnit.SECONDS)//写入超时时间
                .readTimeout(5,TimeUnit.SECONDS)//读取超时时间
                .connectTimeout(5,TimeUnit.SECONDS)//超时时间
               // .addInterceptor(new HeaderInterceptor())//头部拦截器
                .build();
    }
    //retrofit-rxjava-rxandroid
    public <T> T createApi(String baseUrl,Class<T> cls){
        Retrofit retrofit=new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(cls);
    }
}
