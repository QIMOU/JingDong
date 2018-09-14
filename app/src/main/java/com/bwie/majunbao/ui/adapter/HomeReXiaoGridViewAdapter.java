package com.bwie.majunbao.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bwie.majunbao.R;
import com.bwie.majunbao.entity.ProductEntity;
import com.bwie.majunbao.ui.activity.XiangQingActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
////////////////////////////////////////////////////////////////////
//                          _ooOoo_                               //
//                         o8888888o                              //
//                         88" . "88                              //
//                         (| ^_^ |)                              //
//                         O\  =  /O                              //
//                      ____/`---'\____                           //
//                    .'  \\|     |//  `.                         //
//                   /  \\|||  :  |||//  \                        //
//                  /  _||||| -:- |||||-  \                       //
//                  |   | \\\  -  /// |   |                       //
//                  | \_|  ''\---/''  |   |                       //
//                  \  .-\__  `-`  ___/-. /                       //
//                ___`. .'  /--.--\  `. . ___                     //
//              ."" '<  `.___\_<|>_/___.'  >'"".                  //
//            | | :  `- \`.;`\ _ /`;.`/ - ` : | |                 //
//            \  \ `-.   \_ __\ /__ _/   .-` /  /                 //
//      ========`-.____`-.___\_____/___.-`____.-'========         //
//                           `=---='                              //
//      ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^        //
//         佛祖保佑       永无BUG     永不修改                  //
////////////////////////////////////////////////////////////////////


/**
 * 热销商品
 */
class HomeReXiaoGridViewAdapter extends RecyclerView.Adapter<HomeReXiaoGridViewAdapter.HomeReXiaoViewHolder> {
    private Context context;
    private ProductEntity.DataBean.TuijianBean mTuijianBeanList;
    private ProductEntity.DataBean.TuijianBean.ListBeanX mListBeanX;

    public HomeReXiaoGridViewAdapter(Context context, ProductEntity.DataBean.TuijianBean tuijianBeanList) {
        this.context = context;
        mTuijianBeanList = tuijianBeanList;
    }

    @NonNull
    @Override
    public HomeReXiaoGridViewAdapter.HomeReXiaoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_rexiao_glidview_adapter, parent, false);
        HomeReXiaoViewHolder homeReXiaoViewHolder = new HomeReXiaoViewHolder(view);
        return homeReXiaoViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeReXiaoGridViewAdapter.HomeReXiaoViewHolder holder, final int position) {
        mListBeanX = mTuijianBeanList.getList().get(position);
        holder.mFresco_img.setImageURI(mListBeanX.getImages().split("\\|")[0]);
        Log.i("aaa",mListBeanX.getImages());
        holder.mText_title.setText(mListBeanX.getTitle());
        holder.mText_price.setText(mListBeanX.getPrice());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTuijianBeanList.getList().get(position).getTitle();
                Log.i("bbb",mTuijianBeanList.getList().get(position).getTitle());
                    Intent intent = new Intent(context, XiangQingActivity.class);
                    intent.putExtra("pid",mTuijianBeanList.getList().get(position).getPid()+"");
                    Log.i("aaa",mTuijianBeanList.getList().get(position).getPid()+"");
                    context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTuijianBeanList.getList()==null?0:mTuijianBeanList.getList().size();
    }

    public class HomeReXiaoViewHolder extends RecyclerView.ViewHolder {

        private final SimpleDraweeView mFresco_img;
        private final TextView mText_title;
        private final TextView mText_price;

        public HomeReXiaoViewHolder(View itemView) {
            super(itemView);
            mFresco_img = itemView.findViewById(R.id.rexiao_fresco_img);
            mText_title = itemView.findViewById(R.id.rexiao_text_title);
            mText_price = itemView.findViewById(R.id.rexiao_text_price);
        }
    }
}
