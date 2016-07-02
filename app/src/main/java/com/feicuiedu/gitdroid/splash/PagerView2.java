package com.feicuiedu.gitdroid.splash;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.feicuiedu.gitdroid.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by yangdianwen on 16-6-28.--
 * 该类同viewpager,viewpager1
 * 设置pager2的widget动画
 */
public class PagerView2 extends FrameLayout {
    //绑定pager2的图片资源
    @Bind(R.id.ivBubble1)ImageView imageView1;
    @Bind(R.id.ivBubble2)ImageView imageView2;
    @Bind(R.id.ivBubble3)ImageView imageView3;
    public PagerView2(Context context) {
        super(context);
        init();
    }
    public PagerView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public PagerView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.content_pager_2,this,true);
        ButterKnife.bind(this);
        //初始化pager2的时候3张图片资源是不可见的
        imageView1.setVisibility(GONE);
        imageView2.setVisibility(GONE);
        imageView3.setVisibility(GONE);

    }
    //使用动画的方式加载出图片资源，使用第三方的YOYO实现动画效果
    public void ShowAnimation(){
    // 如果imgeview1不可见，或者是第一次打开应用，则进行动画加载pager2 的三张图片
    if (imageView1.getVisibility()!=VISIBLE){
        postDelayed(new Runnable() {
            @Override
            public void run() {
                //with方法：动画的效果
                //duration：动画的时间
                //playOn：设置的目的图片
                YoYo.with(Techniques.FadeIn).duration(300).playOn(imageView1);
                imageView1.setVisibility(VISIBLE);
            }
        },50);
        postDelayed(new Runnable() {
            @Override
            public void run() {
                YoYo.with(Techniques.FadeIn).duration(300).playOn(imageView2);
                imageView2.setVisibility(VISIBLE);
            }
        },500);
        postDelayed(new Runnable() {
            @Override
            public void run() {
                YoYo.with(Techniques.FadeIn).duration(300).playOn(imageView3);
                imageView3.setVisibility(VISIBLE);
            }
        },1050);
    }
}
}