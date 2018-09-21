package com.bwie.majunbao.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.bwie.majunbao.R;
import com.bwie.majunbao.albb.PayResult;
import com.bwie.majunbao.contract.PayContract;
import com.bwie.majunbao.eventbus.PayEventBus;
import com.bwie.majunbao.presenter.PayPresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import majunbao.bwie.com.jingdong_base_marster.base.mvp.BaseMvpActivity;
import majunbao.bwie.com.jingdong_base_marster.base.mvp.IBasePresenter;

public class PayActivity extends BaseMvpActivity<PayContract.PayModel, PayContract.PayPresenter> implements PayContract.IPayView {
    @BindView(R.id.zf1)
    RadioButton zf1;
    @BindView(R.id.zf2)
    RadioButton zf2;
    @BindView(R.id.zf3)
    RadioButton zf3;
    @BindView(R.id.zf4)
    RadioButton zf4;
    @BindView(R.id.rg)
    RadioGroup rg;
    private String mOrderId;
    private int j;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what==0) {
                //PayResult payResult = new PayResult(msg.obj);
                PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                // 判断resultStatus 为9000则代表支付成功
                if (payResult.getResultStatus().equals("9000")) {
                    // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                    Toast.makeText(PayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PayActivity.this,PaySuccessActivity.class));
                } else {
                    // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                    Toast.makeText(PayActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                }
            }

        };
    };
    private String mOrderId1;
    private String mUserId;
    private String mSessionId;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_pay;
    }

    @Override
    protected void initView() {
        super.initView();
        Intent intent = getIntent();
        mOrderId1 = intent.getStringExtra("OrderId");
        SharedPreferences login = getSharedPreferences("login", MODE_PRIVATE);
        mUserId = login.getString("userId", "");
        Log.d("xiadan", mUserId +"userId");
        mSessionId = login.getString("sessionId", "");
        Log.d("xiadan", mSessionId +"sessionId");
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.zf1:
                        j=2;
                        break;
                    case R.id.zf2:
                        Toast.makeText(PayActivity.this, "微信支付未开发", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.zf3:
                        Toast.makeText(PayActivity.this, "农业银行支付未开发", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.zf4:
                        Toast.makeText(PayActivity.this, "银联支付未开发", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }


    @Override
    protected void initData() {
        super.initData();
    }

    @Override
    public void success(final String payEntity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("支付商品");
        builder.setMessage("你确定要支付么?");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.e("zhifu", "success: "+payEntity );
                Runnable payRunnable = new Runnable() {
                    @Override
                    public void run() {
                        PayTask alipay = new PayTask(PayActivity.this);
                        Map<String, String> result = alipay.payV2(payEntity, true);
                        Message msg = new Message();
                        msg.what = 0;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    }
                };
                Thread t = new Thread(payRunnable);
                t.start();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
      /* dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
           @Override
           public void onCancel(DialogInterface dialogInterface) {
               Toast.makeText(PayActivity.this, "haha", Toast.LENGTH_SHORT).show();
           }
       });*/

    }

    @Override
    public void failure(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public IBasePresenter initBasePresenter() {
        return new PayPresenter();
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
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void Event(PayEventBus payEventBus) {
        mOrderId = payEventBus.getOrderId();
    }

    public void zhifu(View view) {
        if (!mUserId.equals("")&&!mSessionId.equals("")&&!mOrderId1.equals(null)) {
            presenter.zhifu(mUserId,mSessionId,"2",mOrderId1);
        }
    }
}
