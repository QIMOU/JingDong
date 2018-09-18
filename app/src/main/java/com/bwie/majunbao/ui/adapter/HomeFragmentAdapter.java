package com.bwie.majunbao.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.majunbao.R;
import com.bwie.majunbao.entity.ProductEntity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.sunfusheng.marqueeview.MarqueeView;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class HomeFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            setTime();
            sendEmptyMessageDelayed(0, 1000);
        }
    };
    private Context context;
    private ProductEntity productEntity;
    private final ArrayList<String> list;
    private BannerViewHolder viewHolder;
    private FenleiViewHolder viewHolder2;
    private PaoViewHolder paoViewHolder;
    private MiaoViewHolder miaoViewHolder;
    private ReViewHolder reViewHolder;
    //多条目
    private static final int TYPE_0 = 1;
    private static final int TYPE_1 = 2;
    private static final int TYPE_2 = 3;
    private static final int TYPE_3 = 4;
    private static final int TYPE_4 = 5;
    private static final int TYPE_5 = 6;


    /*秒杀*/
    private TextView miaosha_time;
    private TextView miaosha_shi;
    private TextView miaosha_minter;
    private TextView miaosha_second;

    private int mPageSize = 8;//每页显示的最大数量
    private int currentPage;//当
    private int totalpage;//总的页数
    //GridView作为一个View对象添加到ViewPager集合中
    private ArrayList<View> viewpagerList;
    private List<ProductEntity.DataBean.BannerBean> data;
    private List<ProductEntity.DataBean.MiaoshaBean.ListBean> mList;
    private ProductEntity.DataBean.TuijianBean mTuijian;
    //GridView作为一个View对象添加到ViewPager集合中


    public HomeFragmentAdapter(Context context, ProductEntity productEntity) {
        this.context = context;
        this.productEntity = productEntity;
        list = new ArrayList<>();
        for (ProductEntity.DataBean.BannerBean dataBean : productEntity.getData().getBanner()) {
            String images = dataBean.getIcon();
            list.add(images);
        }
        Log.i("aaa","轮播"+list.toString());
    }

    //定义接口
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    private OnItemClickListener mItemClickListener;
    //暴露一个方法方便给别的类去调用
    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //轮播
        if (viewType==TYPE_0) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_banner_adapter, parent, false);
            viewHolder = new BannerViewHolder(view);
            return viewHolder;
        }
        /*分类*/
        if (viewType==TYPE_1){
            View view = LayoutInflater.from(context).inflate(R.layout.item_fenlei_adapter, parent, false);
            viewHolder2 = new FenleiViewHolder(view);
            return viewHolder2;
        }
        /*跑马灯*/
        if (viewType==TYPE_2) {
            View view = LayoutInflater.from(context).inflate(R.layout.itme_paomadeng_adapter, parent, false);
            paoViewHolder = new PaoViewHolder(view);
            return paoViewHolder;
        }
        /*秒杀*/
        if (viewType==TYPE_3) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_miaosha_adapter, parent, false);
            miaoViewHolder = new MiaoViewHolder(view);
            return miaoViewHolder;
        }
        /*热销商品*/
        if (viewType==TYPE_4) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_rexiao_adapter, parent, false);
            reViewHolder = new ReViewHolder(view);
            return reViewHolder;
        }
        return null;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        //轮播的
        if (holder instanceof BannerViewHolder) {
            data = productEntity.getData().getBanner();

            viewHolder.home_banner.setImages(list).setImageLoader(new GlideImageLoader()).start();
            //轮播的点击事件
            viewHolder.home_banner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {

                    if (data.get(position).getUrl()!=null&& data.get(position).getUrl().length()>0) {
                        Toast.makeText(context, "点击了"+position, Toast.LENGTH_SHORT).show();
                        if (mItemClickListener!=null) {
                            mItemClickListener.onItemClick(position);
                        }
                    }else {
                        Toast.makeText(context, "没有URL地址", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        //分类
        if (holder instanceof FenleiViewHolder) {
            LayoutInflater inflater = LayoutInflater.from(context);
            //总的页数，取整（这里有三种类型：Math.ceil(3.5)=4:向上取整，只要有小数都+1  Math.floor(3.5)=3：向下取整  Math.round(3.5)=4:四舍五入）
            totalpage = (int) Math.ceil(productEntity.getData().getFenlei().size() * 1.0 / mPageSize);
            //创建集合   grid作为对象添加到集合
            viewpagerList = new ArrayList<>();
            //循环添加
            for (int i = 0; i < totalpage; i++) {
                //每个页面都是inflate出一个新实例
                GridView gridView = (GridView) inflater.inflate(R.layout.home_fenlei_fragment_adapter, viewHolder2.view_pager, false);
                gridView.setAdapter(new HomeFenleiGlidViewAdapter(context,i,mPageSize,productEntity.getData().getFenlei()));
                viewpagerList.add(gridView);
            }
           ((FenleiViewHolder) holder).view_pager.setAdapter(new HomeFenleiViewPagerAdapter(viewpagerList));
            ((FenleiViewHolder) holder).view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    currentPage=position;
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
        /*跑马灯*/
        if (holder instanceof PaoViewHolder) {
            List<String> info = new ArrayList<>();
            info.add("心 慢慢疼 慢慢冷,慢慢等不到爱人！");
            info.add("付出一生 收回几成,情 不能分 不能恨");
            info.add("不能太轻易信任,真爱一回 尽是伤痕");
            info.add("泪 慢慢流 慢慢收,慢慢变成了朋友");
            info.add("寂寞的夜 独自承受,爱 不能久 不能够");
            info.add("不能太容易拥有,伤人的爱 不堪回首");
            ((PaoViewHolder) holder).marqueeView.startWithList(info);

// 在代码里设置自己的动画
            ((PaoViewHolder) holder).marqueeView.startWithList(info, R.anim.anim_bottom_in, R.anim.anim_top_out);
        }
        /*秒杀*/
        if(holder instanceof MiaoViewHolder){
            mList = productEntity.getData().getMiaosha().getList();
            int size = mList.size();
            //handel发送消息进行倒计时
            handler.sendEmptyMessage(0);
            ((MiaoViewHolder) holder).mHome_miaosha_recy.setLayoutManager(new GridLayoutManager(context,size));
            ((MiaoViewHolder) holder).mHome_miaosha_recy.setAdapter(new HomeMiaoShaAdapter(context,mList));
        }
        /*热销*/
        if (holder instanceof ReViewHolder) {
            mTuijian = productEntity.getData().getTuijian();
            ((ReViewHolder) holder).mHome_rexiao_recy.setLayoutManager(new GridLayoutManager(context,2));
            ((ReViewHolder) holder).mHome_rexiao_recy.setAdapter(new HomeReXiaoGridViewAdapter(context,mTuijian));
        }


    }


    @Override
    public int getItemCount() {
        return 5;
    }

    //类型
    @Override
    public int getItemViewType(int position) {
        if (position==0) {
            return TYPE_0;
        } else if (position==1) {
            return TYPE_1;
        } else if (position==2) {
            return TYPE_2;
        }else if (position==3){
            return TYPE_3;
        }else if (position==4){
            return TYPE_4;
        }
        return TYPE_5;
    }



    /*banner轮播的展示图片*/
    public class GlideImageLoader extends ImageLoader {
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

        //提供createImageView 方法，如果不用可以不重写这个方法，主要是方便自定义ImageView的创建
        @Override
        public ImageView createImageView(Context context) {
            //使用fresco，需要创建它提供的ImageView，当然你也可以用自己自定义的具有图片加载功能的ImageView
            SimpleDraweeView simpleDraweeView=new SimpleDraweeView(context);
            return simpleDraweeView;
        }
    }


    /*轮播banner的ViewHolder*/
    private class BannerViewHolder extends RecyclerView.ViewHolder {

        private Banner home_banner;

        public BannerViewHolder(View view) {
            super(view);
            home_banner = view.findViewById(R.id.home_banner);
        }
    }
    /*分类ViewPager的ViewHolder*/
    private class FenleiViewHolder extends RecyclerView.ViewHolder {

        private  ViewPager view_pager;

        public FenleiViewHolder(View view) {
            super(view);
            view_pager = view.findViewById(R.id.home_fenlei_viewpager);
        }
    }
    /*跑马灯京东快报的viewholder*/
    private class PaoViewHolder extends RecyclerView.ViewHolder{

        private final MarqueeView marqueeView;

        public PaoViewHolder(View itemView) {
            super(itemView);
            marqueeView = itemView.findViewById(R.id.marqueeView);
        }
    }
    /*秒杀的viewholder*/
    private class MiaoViewHolder extends RecyclerView.ViewHolder {

        private  RecyclerView mHome_miaosha_recy;


        public MiaoViewHolder(View view) {
            super(view);
            mHome_miaosha_recy = view.findViewById(R.id.home_seckill);
            miaosha_time = view.findViewById(R.id.tv_miaosha_time);
            miaosha_shi = view.findViewById(R.id.tv_miaosha_shi);
            miaosha_minter = view.findViewById(R.id.tv_miaosha_minter);
            miaosha_second = view.findViewById(R.id.tv_miaosha_second);

        }
    }
    /*热销商品*/
    private class ReViewHolder extends RecyclerView.ViewHolder {
        private final RecyclerView mHome_rexiao_recy;

        public ReViewHolder(View view) {
            super(view);
            mHome_rexiao_recy = view.findViewById(R.id.home_rexiao_recy);
        }
    }

    //秒杀倒计时
    public void setTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //df.setTimeZone(TimeZone.getTimeZone("GMT+08:00")); // 不会受系统时区设置的影响,否则时间可能不准确
        Date curDate = new Date(System.currentTimeMillis());
        String format = df.format(curDate);
        StringBuffer buffer = new StringBuffer();
        String substring = format.substring(0, 11);
        buffer.append(substring);
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour % 2 == 0) {
            miaosha_time.setText((hour+9) + "点场");
            buffer.append((hour + 2));
            buffer.append(":00:00");
        } else {
            miaosha_time.setText(((hour+9)) + "点场");
            buffer.append((hour + 1));
            buffer.append(":00:00");
        }
        String totime = buffer.toString();
        try {
            java.util.Date date = df.parse(totime);
            java.util.Date date1 = df.parse(format);
            long defferenttime = date.getTime() - date1.getTime();
            long days = defferenttime / (1000 * 60 * 60 * 24);
            long hours = (defferenttime - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minute = (defferenttime - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
            long seconds = defferenttime % 60000;
            long second = Math.round((float) seconds / 1000);
            miaosha_shi.setText("0" + hours + "");
            if (minute >= 10) {
                miaosha_minter.setText(minute + "");
            } else {
                miaosha_minter.setText("0" + minute + "");
            }
            if (second >= 10) {
                miaosha_second.setText(second + "");
            } else {
                miaosha_second.setText("0" + second + "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
