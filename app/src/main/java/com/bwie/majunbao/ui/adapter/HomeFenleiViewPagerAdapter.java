package com.bwie.majunbao.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

class HomeFenleiViewPagerAdapter extends PagerAdapter {
    private List<View> viewLists;//View就是GridView

    public HomeFenleiViewPagerAdapter(List<View> viewLists) {
        this.viewLists = viewLists;
    }
/**
 *这个方法，是从ViewGroup中移出当前View
 */
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(viewLists.get(position));
    }

/**
 * 将当前View添加到ViewGroup容器中
 * 这个方法，return一个对象，这个对象表明了PagerAdapter适配器选择哪个对象放在当前的ViewPager中
 */

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(viewLists.get(position));
        return viewLists.get(position);
    }

    /**
     * 这个方法，是获取当前窗体界面数
     */
    @Override
    public int getCount() {
        return viewLists!=null?viewLists.size():0;
    }

    /**
     * 用于判断是否由对象生成界面
     */
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;//官方文档要求这么写
    }
}
