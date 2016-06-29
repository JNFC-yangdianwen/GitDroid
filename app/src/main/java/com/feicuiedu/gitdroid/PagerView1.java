package com.feicuiedu.gitdroid;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

/**
 * Created by yangdianwen on 16-6-28.
 */
public class PagerView1 extends FrameLayout {
    public PagerView1(Context context) {
        super(context);
        init();
    }

    public PagerView1(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PagerView1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.content_pager_1,this,true);
    }

}

