package com.bwie.majunbao.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.majunbao.R;
import com.bwie.majunbao.contract.FenLeiContract;
import com.bwie.majunbao.entity.FenLeiEntity;
import com.bwie.majunbao.entity.LeftFenLeiEntity;
import com.bwie.majunbao.presenter.FenLeiPresenter;
import com.bwie.majunbao.ui.activity.LiuShiActivity;
import com.bwie.majunbao.ui.adapter.LeftAdapter;
import com.bwie.majunbao.ui.adapter.RightAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import majunbao.bwie.com.jingdong_base_marster.base.mvp.BaseMvpFragment;
import majunbao.bwie.com.jingdong_base_marster.base.mvp.IBasePresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClassFragment extends BaseMvpFragment<FenLeiContract.FenLeiModel, FenLeiContract.FenLeiPresenter> implements FenLeiContract.IFenLeiView {
    @BindView(R.id.lv_menu)
    ListView lvMenu;
    @BindView(R.id.lv_home)
    ListView lvHome;
    Unbinder unbinder;
    @BindView(R.id.sao)
    ImageView sao;
    @BindView(R.id.tv_home)
    TextView tvHome;
    @BindView(R.id.xx_home)
    ImageView xxHome;
    @BindView(R.id.llSearch)
    LinearLayout llSearch;
    private RightAdapter mRightAdapter;
    private LeftAdapter mLeftAdapter;
    private String cid;

    @Override
    public void success(final FenLeiEntity fenLeiEntity) {
      /*  Log.i("aaa", "右成功fenlei");
        Log.i("aaa", "右分类"+fenLeiEntity.getMsg());*/
        //*右边展示*//*
        mRightAdapter = new RightAdapter(getActivity(), fenLeiEntity.getData());
        lvHome.setAdapter(mRightAdapter);
        lvHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), fenLeiEntity.getData().get(i).getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void initView() {
        super.initView();
        presenter.leftClass();
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        presenter.rightClass(cid);
        //搜索框
        tvHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转到搜索页面
                startActivity(new Intent(getActivity(),LiuShiActivity.class));
            }
        });
    }

    @Override
    public void failure(String msg) {
        Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void lsuccess(final LeftFenLeiEntity leftFenLeiEntity) {
       // Log.i("aaa", "左分类成功");
       // Log.i("aaa", "做分类" + leftFenLeiEntity.getMsg());
        //左边展示
        mLeftAdapter = new LeftAdapter(getActivity(), leftFenLeiEntity.getData());
        lvMenu.setAdapter(mLeftAdapter);

        lvMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                cid = leftFenLeiEntity.getData().get(i).getCid();
                mLeftAdapter.setSelectedPosition(i);
                mLeftAdapter.notifyDataSetInvalidated();
                presenter.rightClass(cid);
                //Toast.makeText(mActivity, leftFenLeiEntity.getData().get(i).getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected int setLayoutId() {
        return R.layout.fragment_class;
    }

    @Override
    public IBasePresenter initBasePresenter() {
        return new FenLeiPresenter();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
