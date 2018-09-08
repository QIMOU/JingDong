package com.bwie.majunbao.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bwie.majunbao.R;
import com.bwie.majunbao.entity.ProductEntity;
import com.youth.banner.loader.ImageLoader;

import java.util.List;


class HomeFenleiGlidViewAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;
    private int mIndex;//页数下标，表示第几页，从0开始
    private int mPagerSize;//每页显示的最大数量
    private List<ProductEntity.DataBean.FenleiBean> list;
    private GlideImageLoader.ViewHolder holder;

    public HomeFenleiGlidViewAdapter(Context context, int mIndex, int mPagerSize, List<ProductEntity.DataBean.FenleiBean> fenlei) {
        this.context = context;
        this.mIndex = mIndex;
        this.list = fenlei;
        this.mPagerSize = mPagerSize;
        inflater = LayoutInflater.from(context);
    }

    /**
     * 先判断数据集的大小是否足够显示满本页？listData.size() > (mIndex + 1)*mPagerSize
     * 如果满足，则此页就显示最大数量mPagerSize的个数
     * 如果不够显示每页的最大数量，那么剩下几个就显示几个 (listData.size() - mIndex*mPagerSize)
     */

    @Override
    public int getCount() {
        return  list.size() > (mIndex + 1)*mPagerSize ? mPagerSize : (list.size() - mIndex*mPagerSize);
    }

    @Override
    public Object getItem(int i) {
        return list.get(i + mIndex * mPagerSize);
    }

    @Override
    public long getItemId(int i) {
        return i + mIndex * mPagerSize;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        GlideImageLoader.ViewHolder holder=null;
        if (view==null) {
            view = inflater.inflate(R.layout.home_fenlei_glidview_adapter, null, false);
            holder=new GlideImageLoader.ViewHolder();
            // holder.imgUrl = view.findViewById(R.id.home_viewpager_gridView_imgUrl);
            holder.img_1 = view.findViewById(R.id.img_1);
            holder.proName = view.findViewById(R.id.home_viewpager_gridView_proName);
            view.setTag(holder);
        }else {
            holder= (GlideImageLoader.ViewHolder) view.getTag();
        }
        //重新确定position（因为拿到的是总的数据源，数据源是分页加载到每页的GridView上的，为了确保能正确的点对不同页上的item）
        //假设mPagerSize=8，假如点击的是第二页（即mIndex=1）上的第二个位置item(position=1),那么这个item的实际位置就是pos=9
         final int pos= i+mIndex*mPagerSize;
        holder.proName.setText(list.get(pos).getName());
        String[] images = list.get(pos).getIcon().split("\\|");
        if (images!=null&&images.length>0) {
            Glide.with(context).load(images[0]).into(holder.img_1);
        }
       // holder.imgUrl.setImageURI(list.get(pos).getIcon());
        //添加item监听
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "你点击了"+list.get(pos).getName(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }


    /*banner轮播的展示图片*/
    public static class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            /**
             注意：
             1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
             2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
             传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
             切记不要胡乱强转！
             */
            //用fresco加载图片简单用法，记得要写下面的createImageView方法
            Uri uri = Uri.parse((String) path);
            imageView.setImageURI(uri);
        }

    private static class ViewHolder{
        private TextView proName;
       // private SimpleDraweeView imgUrl;
        private ImageView img_1;
    }
}
}
