package com.feicuiedu.gitdroid.repo;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.feicuiedu.gitdroid.Constans.Languages;

import java.util.List;
/**
 * Created by yangdianwen on 16-6-30.
 * 这是一个主界面的viewpager的adapter,继承FragmentPagerAdapter
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Languages> languages;
    //constructor
    public MyFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
       languages=Languages.getLanguage(context);
    }
    //获取viewpager的item,每个item都是一个fragment
    @Override
    public Fragment getItem(int position) {
        return RepoListFragment.getInstanceFragment(languages.get(position));
    }
    //获取数据长度
    @Override
    public int getCount() {
        return languages.size();
    }
    //获取每个viewpager的tile
    @Override
    public CharSequence getPageTitle(int position) {
        return languages.get(position).getName();
    }
}
