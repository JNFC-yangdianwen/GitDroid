package com.feicuiedu.gitdroid.splash;


import android.animation.ArgbEvaluator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.feicuiedu.gitdroid.PagerView2;
import com.feicuiedu.gitdroid.R;
import com.feicuiedu.gitdroid.VPAdaper;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;

/**
 * Created by yangdianwen on 16-6-28.
 */
public class SplashPagerFragment extends Fragment {
    private static final String TAG = "SplashPagerFragment";
    //绑定FrameLayout中的widget的id
    @Bind(R.id.layoutPhone)FrameLayout layoutPhone;
    @Bind(R.id.ivPhone) ImageView ivPhone;
    @Bind(R.id.ivPhoneBlank)ImageView ivPhoneBlank;
    //绑定颜色资源
    @BindColor(R.color.colorGreen) int colorGreen;
    @BindColor(R.color.colorRed) int colorRed;
    @BindColor(R.color.colorYellow) int colorYellow;
    //绑定widget的id
    @Bind(R.id.content) FrameLayout frameLayout;
    //初始化splash里的控件
    private VPAdaper vpAdaper;
    @Bind(R.id.viewPager)ViewPager viewPager;
    @Bind(R.id.indicator)CircleIndicator indicator;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash_pager, container, false);
        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //butterknife 绑定视图
        ButterKnife.bind(this,view);
        vpAdaper=new VPAdaper(getContext());
        viewPager.setAdapter(vpAdaper);
        viewPager.addOnPageChangeListener(pageListener);
        viewPager.addOnPageChangeListener(phoneListener);
        //先设定viewpager，然后在设定viewpager的指示器
        indicator.setViewPager(viewPager);
    }
    private ViewPager.OnPageChangeListener phoneListener=new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    //从第一页到第二页时都是0
                    if (position==0){
                        //在移动过程中实时scale（缩放）
                        float scale=0.3f+positionOffset*0.7f;
                        layoutPhone.setScaleX(scale);
                        layoutPhone.setScaleY(scale);
                        //在移动过程中font的变化，
                        ivPhone.setAlpha(positionOffset);
                        int scroll= (int) ((positionOffset-1)*200);
                        //移动过程中的动画效果
                        layoutPhone.setTranslationX(scroll);
                       return;
                    }
                    //从第二页到第三页时的position都是1,手机图片和viewpager一起移动
                    if (position==1){
                        layoutPhone.setTranslationX(-positionOffsetPixels);
                        return;
                    }
                }

                @Override
                public void onPageSelected(int position) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            };
    private ViewPager.OnPageChangeListener pageListener=new ViewPager.OnPageChangeListener() {
        //定义一个颜色取值器
        ArgbEvaluator evaluator = new ArgbEvaluator();
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
           if (position==0){
               //设置属性动画，用颜色取值器取出颜色
               // evaluate方法的三个参数：
               //1.position变化的比例（0.1-1.0）
               //2.startValue
               //3.endValue
               int color = (int) evaluator.evaluate(positionOffset,colorGreen,colorRed);
               //frameLayout设置动画效果
               frameLayout.setBackgroundColor(color);
               return;
           }
            if (position==1){
                int color = (int) evaluator.evaluate(positionOffset,colorRed,colorYellow);
                frameLayout.setBackgroundColor(color);
                return;
            }

        }

        @Override
        public void onPageSelected(int position) {
            if (position==2){
                PagerView2 pagerview= (PagerView2) vpAdaper.getView(position);
                pagerview.ShowAnimation();
            }
        }
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
