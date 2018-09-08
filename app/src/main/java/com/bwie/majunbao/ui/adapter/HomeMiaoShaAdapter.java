package com.bwie.majunbao.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bwie.majunbao.R;
import com.bwie.majunbao.entity.ProductEntity;
import com.bwie.majunbao.ui.activity.XiangQingActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 *                             _ooOoo_
 *                            o8888888o
 *                            88" . "88
 *                            (| -_- |)
 *                            O\  =  /O
 *                         ____/`---'\____
 *                       .'  \\|     |//  `.
 *                      /  \\|||  :  |||//  \
 *                     /  _||||| -:- |||||-  \
 *                     |   | \\\  -  /// |   |
 *                     | \_|  ''\---/''  |   |
 *                     \  .-\__  `-`  ___/-. /
 *                   ___`. .'  /--.--\  `. . __
 *                ."" '<  `.___\_<|>_/___.'  >'"".
 *               | | :  `- \`.;`\ _ /`;.`/ - ` : | |
 *               \  \ `-.   \_ __\ /__ _/   .-` /  /
 *          ======`-.____`-.___\_____/___.-`____.-'======
 *                             `=---='
 *          ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 *                     佛祖保佑        永无BUG
 *            佛曰:
 *                   写字楼里写字间，写字间里程序员；
 *                   程序人员写程序，又拿程序换酒钱。
 *                   酒醒只在网上坐，酒醉还来网下眠；
 *                   酒醉酒醒日复日，网上网下年复年。
 *                   但愿老死电脑间，不愿鞠躬老板前；
 *                   奔驰宝马贵者趣，公交自行程序员。
 *                   别人笑我忒疯癫，我笑自己命太贱；
 *                   不见满街漂亮妹，哪个归得程序员？
*/




public class HomeMiaoShaAdapter extends RecyclerView.Adapter<HomeMiaoShaAdapter.TextHolder> {
    private Context context;
    private List<ProductEntity.DataBean.MiaoshaBean.ListBean> list;

    public HomeMiaoShaAdapter(Context context, java.util.List<ProductEntity.DataBean.MiaoshaBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public TextHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.home_miaosha_glidview_adapter, null);
        return new TextHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TextHolder holder, final int position) {

        holder.sdv.setImageURI(list.get(position).getImages().split("\\|")[0]);
        holder.tv.setText("$"+list.get(position).getPrice()+"");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,XiangQingActivity.class);
                intent.putExtra("pid",list.get(position).getPid());
                intent.putExtra("miaosha_url",list.get(position).getDetailUrl());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TextHolder extends RecyclerView.ViewHolder {

        private final SimpleDraweeView sdv;
        private final TextView tv;

        public TextHolder(View itemView) {
            super(itemView);
            sdv = itemView.findViewById(R.id.sdv);
            tv = itemView.findViewById(R.id.tv);
        }
    }
}
