package com.bwie.majunbao.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bwie.majunbao.R;
import com.bwie.majunbao.contract.OrderContract;
import com.bwie.majunbao.entity.OrderEntity;
import com.bwie.majunbao.eventbus.PayEventBus;
import com.bwie.majunbao.presenter.OrderPresenter;
import com.bwie.majunbao.utils.Md5;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import majunbao.bwie.com.jingdong_base_marster.base.mvp.BaseMvpActivity;
import majunbao.bwie.com.jingdong_base_marster.base.mvp.IBasePresenter;

public class PayOrderActivity extends BaseMvpActivity<OrderContract.OrderModel,OrderContract.OrderPresenter> implements OrderContract.IOrderView,View.OnClickListener {

    @BindView(R.id.pay)
    Button pay;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_pay_order;
    }

    @Override
    protected void initData() {
        super.initData();
        //初始化EventBus
      //  EventBus.getDefault().register(this);
    }

    @Override
    protected void initView() {
        super.initView();
        pay.setOnClickListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public void onClick(View view) {
        SharedPreferences login = getSharedPreferences("login", MODE_PRIVATE);
        String userId = login.getString("userId", "");
        Log.d("xiadan",userId+"userId");
        String sessionId = login.getString("sessionId", "");
        Log.d("xiadan",sessionId+"sessionId");
        if (!userId.equals("")&&!sessionId.equals("")) {
            String s = Md5.MD5(userId+"21movie");
            Log.d("xia",s+"哈哈");
           // Toast.makeText(this, "MD5"+s, Toast.LENGTH_SHORT).show();
            presenter.xiadan(userId,sessionId,"2","1"+"",s);
        }else {
            Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(PayOrderActivity.this,LoginActivity.class));
        }

    }

    @Override
    public void success(OrderEntity orderEntity) {
        Log.d("xiadan",orderEntity.getMessage()+"成功了");
        if (orderEntity.getStatus().equals("0000")) {
            Log.d("xia","单号是:"+orderEntity.getOrderId()+"");
           // EventBus.getDefault().postSticky(new PayEventBus(orderEntity.getOrderId()));
            Intent intent = new Intent(PayOrderActivity.this, PayActivity.class);
            intent.putExtra("OrderId",orderEntity.getOrderId());
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void failure(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public IBasePresenter initBasePresenter() {
        return new OrderPresenter();
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
    protected void onDestroy() {
        super.onDestroy();
        //EventBus.getDefault().unregister(this);
    }
}
