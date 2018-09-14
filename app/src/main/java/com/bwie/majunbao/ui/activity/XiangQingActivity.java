package com.bwie.majunbao.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.majunbao.R;
import com.bwie.majunbao.api.ProductApi;
import com.bwie.majunbao.common.Constants;
import com.bwie.majunbao.contract.XiangContract;
import com.bwie.majunbao.entity.XiangQingEntity;
import com.bwie.majunbao.entity.AddCartEntity;
import com.bwie.majunbao.presenter.XiangPresenter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import majunbao.bwie.com.jingdong_base_marster.base.mvp.BaseMvpActivity;
import majunbao.bwie.com.jingdong_base_marster.base.mvp.IBasePresenter;
import majunbao.bwie.com.jingdong_base_marster.net.RetrofitUtils;

public class XiangQingActivity extends BaseMvpActivity<XiangContract.XiangModel, XiangContract.XiangPresenter> implements XiangContract.IXiangView {
    @BindView(R.id.detail_banner)
    Banner detailBanner;
    @BindView(R.id.detail_price)
    TextView detailPrice;
    @BindView(R.id.detail_title)
    TextView detailTitle;
    @BindView(R.id.xia)
    ImageView xia;
    private String mPid;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_xiang_qing;
    }

    @Override
    protected void initView() {
        super.initView();
        //获取intent值
        Intent intent = getIntent();
        mPid = intent.getStringExtra("pid");
        if (mPid != null) {
            presenter.XiangQing(mPid);
        }
        //点击购物车图片
        xia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(XiangQingActivity.this, "该功能暂未开放", Toast.LENGTH_SHORT).show();
            }
        });
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter!=null) {
            presenter=null;
        }
    }

    @Override
    public void success(XiangQingEntity xiangQingEntity) {
        String title = xiangQingEntity.getData().getTitle();
        double bargainPrice = xiangQingEntity.getData().getBargainPrice();
        //标题
        detailTitle.setText(title);
        //价钱
        detailPrice.setText("￥" + bargainPrice);
        List<String> path = new ArrayList<>();
        String images = xiangQingEntity.getData().getImages();
        String[] split = images.split("\\|");
        for (String s : split) {
            path.add(s);
        }
        detailBanner.setImages(path)
                //设置自动轮播
                .isAutoPlay(false)
                .setImageLoader(new MyLoader())
                //设置轮播为显示数字
                .setBannerStyle(BannerConfig.NUM_INDICATOR)
                .start();
    }

    /*点击添加,添加到购物车*/
    public void detailsAddcar(View view) {
        RetrofitUtils.getInstance().createApi(Constants.BASE_URL,ProductApi.class).addCart("17415",mPid).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<AddCartEntity>() {
            @Override
            public void accept(AddCartEntity addCartEntity) throws Exception {
                Toast.makeText(XiangQingActivity.this, "添加购物车成功", Toast.LENGTH_SHORT).show();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Toast.makeText(XiangQingActivity.this, "添加购物车失败", Toast.LENGTH_SHORT).show();
            }
        });


    }


    /*轮播图的工具类*/
    public class MyLoader extends ImageLoader {
        @Override
        public ImageView createImageView(Context context) {
            return new SimpleDraweeView(context);
        }

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            imageView.setImageURI(Uri.parse((String) path));
        }
    }

    @Override
    public void failure(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public IBasePresenter initBasePresenter() {
        return new XiangPresenter();
    }

    @Override
    public void failure() {

    }

    @Override
    public void showLoader() {

    }

    @Override
    public void hideLoader() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
