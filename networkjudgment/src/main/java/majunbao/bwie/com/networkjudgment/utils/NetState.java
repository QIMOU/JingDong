package majunbao.bwie.com.networkjudgment.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


import org.greenrobot.eventbus.EventBus;

import majunbao.bwie.com.networkjudgment.eventbus.FailureEventBus;
import majunbao.bwie.com.networkjudgment.eventbus.SuccessEventBus;

public class NetState extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent arg1) {
        ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo gprs = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if(!gprs.isConnected() && !wifi.isConnected())
        {
            /*AlertDialog.Builder ab = new AlertDialog.Builder(context);
            ab.setMessage("网络连接断开，请检查网络");
            ab.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub
                    dialog.dismiss();
                }
            }).show();*/
            EventBus.getDefault().post(new FailureEventBus(0));
        }
        else{
            if (gprs.isConnected()&&!wifi.isConnected()) {
                EventBus.getDefault().post(new SuccessEventBus(2));
            }else if (wifi.isConnected()&&!gprs.isConnected()) {
                EventBus.getDefault().post(new SuccessEventBus(3));
            }
        }
    }


}
