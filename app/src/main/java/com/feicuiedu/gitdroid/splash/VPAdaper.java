package com.feicuiedu.gitdroid.splash;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.feicuiedu.gitdroid.splash.PagerView;
import com.feicuiedu.gitdroid.splash.PagerView1;
import com.feicuiedu.gitdroid.splash.PagerView2;

/**
 * Created by yangdianwen on 16-6-28.
 * viewpager的适配器
 */
public class VPAdaper extends PagerAdapter {
    private  View [] views;

    public VPAdaper(Context context) {
        views = new View[]{
                new PagerView(context), new PagerView1(context), new PagerView2(context)};
    }
    @Override
    public int getCount() {
        //返回要滑动的View个数
        return views.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
    //实例化item
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        //将当前视图添加到View中
        View view = views[position];
        container.addView(view);
        //返回当前视图
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //从container中删除指定position的View
        container.removeView(views[position]);
    }
     //对外提供的获取当前的视图
    public View getView(int position) {
        View view = views[position];
        return view;
    }
}
