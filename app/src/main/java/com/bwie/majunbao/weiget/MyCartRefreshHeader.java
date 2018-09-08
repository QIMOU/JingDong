package com.bwie.majunbao.weiget;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bwie.majunbao.R;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;


public class MyCartRefreshHeader extends LinearLayout implements RefreshHeader {


    /**
     * 重置
     * 准备刷新
     * 开始刷新
     * 结束刷新
     */
    public static final int STATE_RESET = -1;
    public static final int STATE_PREPARE = 0;
    public static final int STATE_BEGIN = 1;
    public static final int STATE_FINISH = 2;

    public static final int MARGIN_RIGHT = 100;


    private ImageView mImage;
    private AnimationDrawable refreshingAnim;
    private float mCurTranslationX;
    private Drawable mImageDrawable;
    private TextView tv_remain;

    public MyCartRefreshHeader(Context context) {
        this(context, null, 0);
    }

    public MyCartRefreshHeader(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyCartRefreshHeader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = View.inflate(context, R.layout.widget_my_refresh_header, this);
        mImage = (ImageView) view.findViewById(R.id.iv_refresh_header);
        tv_remain = view.findViewById(R.id.tv_remain);
    }

    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;
    }

    @Override
    public void onStartAnimator(RefreshLayout layout, int height, int extendHeight) {

    }

    /**
     * 状态改变时调用。
     * @param refreshLayout
     * @param oldState
     * @param newState
     */
    @Override
    public void onStateChanged(RefreshLayout refreshLayout, RefreshState oldState, RefreshState newState) {
        switch (newState) {
            case PullDownToRefresh: //下拉刷新开始。正在下拉还没松手时调用
                //每次重新下拉时，将图片资源重置为小车
                mImage.setImageResource(R.drawable.anim_mycartpull_refreshing);
                refreshingAnim = (AnimationDrawable) mImage.getDrawable();
                //refreshingAnim.start();
                if (!refreshingAnim.isRunning()) {
                    refreshingAnim.start();
                }
                //mImage.setImageResource(R.drawable.b3p);
                tv_remain.setText("松开更新...");
                break;
            case Refreshing: //正在刷新。只调用一次
                //状态切换为正在刷新状态时，设置图片资源为小车的动画并开始执行
                tv_remain.setText("更新中...");
                mImage.setImageResource(R.drawable.anim_mycartpull_refreshing);
                refreshingAnim = (AnimationDrawable) mImage.getDrawable();
                //refreshingAnim.start();
                if (!refreshingAnim.isRunning()) {
                    refreshingAnim.start();
                }
                break;
            case RefreshFinish://刷新结束
                //刷新结束时调用该动画
               /* mCurTranslationX = mImage.getTranslationX();
                Animation translateAnimation = new TranslateAnimation(mCurTranslationX,1000,0,0);
                translateAnimation.setDuration(500);
                mImage.startAnimation(translateAnimation);*/
                tv_remain.setText("更新完成...");
                break;
        }
    }

    /**
     * 动画结束后调用
     */
    @Override
    public int onFinish(RefreshLayout layout, boolean success) {
        // 结束动画
        if (refreshingAnim != null && refreshingAnim.isRunning()) {
            refreshingAnim.stop();
        }
        return 400;//延时400毫秒关闭下拉刷新
    }


    @Override
    public void setPrimaryColors(int... colors) {

    }

    @Override
    public void onInitialized(RefreshKernel kernel, int height, int extendHeight) {

    }

    /**
     * 下拉过程中不断调用此方法。从小变大的小车
     */

    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {
        // 下拉的百分比小于100%时，不断调用 setScale 方法改变图片大小
        if (percent < 1) {
            mImage.setScaleX(percent);
            mImage.setScaleY(percent);
        }

        //如果小于50提示文字
        if (percent < 0.9) {
            /*mImage.setScaleX(percent);
            mImage.setScaleY(percent);*/
            tv_remain.setText("下拉更新...");
        }else if (percent > 1){
            tv_remain.setText("松手更新...");
        }
    }

    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {

    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }
}
