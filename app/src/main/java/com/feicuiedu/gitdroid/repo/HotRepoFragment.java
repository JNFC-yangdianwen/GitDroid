package com.feicuiedu.gitdroid.repo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feicuiedu.gitdroid.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 *Created by yangdianwen on 16-6-30.
 * 热门仓库
 * viewpager的每个item的样式都是一个Fragment
 */
public class HotRepoFragment extends Fragment {
    @Bind(R.id.viewPager)ViewPager viewPager;
    @Bind(R.id.tabLayout)TabLayout tabLayout;
    private static final String TAG = "HotRepoFragment";
    private MyFragmentPagerAdapter adapter;
    //加载Fragment的布局样式
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //inflater布局加载器
        //container父容器
        return  inflater.inflate(R.layout.fragment_hot_repo, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        //实例化出viewpager的一个adapter,使用系统自带的adapter
        adapter=new MyFragmentPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        //设置tabLayout随着viewpager的滑动而移动
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //解绑ButterKnife
        ButterKnife.unbind(this);
    }
}
