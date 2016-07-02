package com.feicuiedu.gitdroid.splash;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.feicuiedu.gitdroid.R;

/**
 * Created by yangdianwen on 16-6-28.
 * viewpager的子视图继承FrameLayout,重写FrameLayout的三个constructor,在每个constructor都必须调用
 *  init方法
 */
public class PagerView extends FrameLayout {
    public PagerView(Context context) {
        super(context);
        init();
    }

    public PagerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PagerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    //初始化视图,使用布局加载器加载视图
    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.content_pager_0,this,true);
    }

}
