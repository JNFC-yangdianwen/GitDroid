package com.feicuiedu.gitdroid.repo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.feicuiedu.gitdroid.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by yangdianwen on 16-6-30.
 * 每一个fragment的样式都是listview
 */
public class RepoListFragment extends Fragment {
    @Bind(R.id.ptrClassicFrameLayout)PtrClassicFrameLayout pcfl;
    private List<String>data=new ArrayList<>();;
    @Bind(R.id.lvRepos)ListView listview;
    private ArrayAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (int i = 0; i <20 ; i++) {
            data.add("第"+i+"项");
        }
    }
    //创建view
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_repo_list,container,false);
    }
    //当view被创建时,绑定ButterKnife
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        //创建ArrayAdapter
        adapter=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1);
        //listview的setAdapter
        listview.setAdapter(adapter);
        //下拉刷新 设置数据处理的方式
        pcfl.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                //加载数据的方法
                //创建一个线程,模拟网络的耗时操作
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        loadData(20);
                    }
                }).start();
            }
        });
    }
    //加载数据的方法
    private void loadData(final int size) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                   data.clear();
                    for (int i = 0; i <size ; i++) {
                        data.add("刷新数据"+i);
                    }
            }
        }).start();
        //刷新组件对数据的处理
        pcfl.post(new Runnable() {
            @Override
            public void run() {
               adapter.clear();
                adapter.addAll(data);
            }
        });
        //通知adapter数据改变
        adapter.notifyDataSetChanged();
        //刷新组件调用刷新完成方法,隐藏headerLayout
        pcfl.refreshComplete();
    }
    //实例化fragment的方法
    public static Fragment getInstanceFragment(String index){
        //创建一个RepoListFragment对象
        RepoListFragment hotRepoFragment = new RepoListFragment();
        //实例化一个Bundle参数
         Bundle args = new Bundle();
        //使用Bundle序列化
                args.putSerializable("index",index);
        //RepoListFragment对象设置Argument参数
               hotRepoFragment.setArguments(args);
        //返回一个RepoListFragment对象
                return hotRepoFragment;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        //解绑butterknife
        ButterKnife.unbind(this);
    }
}
