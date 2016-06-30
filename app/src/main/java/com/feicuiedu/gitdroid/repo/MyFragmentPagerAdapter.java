package com.feicuiedu.gitdroid.repo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by yangdianwen on 16-6-30.
 * 这是一个主界面的viewpager的adapter,继承FragmentPagerAdapter
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<String> data;
    //constructor
    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        data=new ArrayList<>();
        for (int i = 0; i <10 ; i++) {
            data.add("java"+i);
        }
    }
    //获取viewpager的item,每个item都是一个fragment
    @Override
    public Fragment getItem(int position) {
        return RepoListFragment.getInstanceFragment(data.get(position));
    }
    //获取数据长度
    @Override
    public int getCount() {
        return data.size();
    }
    //获取每个viewpager的tile
    @Override
    public CharSequence getPageTitle(int position) {
        return data.get(position);
    }
}
