package com.feicuiedu.gitdroid.repo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.feicuiedu.gitdroid.Constans.Languages;
import com.feicuiedu.gitdroid.Constans.Repo;
import com.feicuiedu.gitdroid.Presenter.Presenter;
import com.feicuiedu.gitdroid.R;
import com.feicuiedu.gitdroid.View.FooterView;
import com.feicuiedu.gitdroid.View.PagerView;
import com.hannesdorfmann.mosby.mvp.MvpFragment;
import com.mugen.Mugen;
import com.mugen.MugenCallbacks;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by yangdianwen on 16-6-30.
 * 每一个fragment的样式都是listview
 */
public class RepoListFragment extends MvpFragment<PagerView,Presenter> implements PagerView {
    @Bind(R.id.ptrClassicFrameLayout)
    PtrClassicFrameLayout ptrFrameLayout;
    @Bind(R.id.lvRepos)
    ListView listView;
    @Bind(R.id.emptyView)
    TextView emptyView;
    @Bind(R.id.errorView) TextView errorView;
    List<Repo> datas;
    Languages languages;
    private LanguageAdapter adapter;
    private FooterView footerView; // 上拉加载更多的视图
    @Nullable
    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_repo_list, container, false);
    }
    //实例化RepoListFragment的方法
    public static RepoListFragment getInstanceFragment(Languages language){
        RepoListFragment f = new RepoListFragment();
               Bundle args = new Bundle();
               args.putSerializable("index", language);
                f.setArguments(args);
                return f;
    }
    @Override
    public Presenter createPresenter() {
        return new Presenter(languages);
    }
    @Override public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        adapter=new LanguageAdapter();
        listView.setAdapter(adapter);
        // 下拉刷新
        ptrFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            @Override public void onRefreshBegin(PtrFrameLayout frame) {
                getPresenter().loadData();
            }
        });
        //创建presenter对象
        footerView = new FooterView(getContext());
        // 上拉加载更多(listview滑动动最后的位置了，就可以loadmore)
        Mugen.with(listView, new MugenCallbacks() {
            @Override
            public void onLoadMore() {
                Toast.makeText(getContext(), "loadmore", Toast.LENGTH_SHORT).show();
                getPresenter().loadMoreData();
            }
            // 是否正在加载，此方法用来避免重复加载
            @Override public boolean isLoading() {
                return listView.getFooterViewsCount() > 0 && footerView.isLoading();
            }
            // 是否所有数据都已加载
            @Override public boolean hasLoadedAllItems() {
                return listView.getFooterViewsCount() > 0 && footerView.isComplete();
            }
        }).start();
    }
    @OnClick({R.id.emptyView, R.id.errorView})
    public void autoRefresh() {
        ptrFrameLayout.autoRefresh();
    }
    @Override public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
    // 这是拉刷新视图的实现----------------------------------------------------------------
    //显示内容视图
    @Override public void showContentView() {
        ptrFrameLayout.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
    }
   //显示错误视图
    @Override public void showErroView(String msg) {
        ptrFrameLayout.setVisibility(View.GONE);
        emptyView.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
    }
    //显示空视图
    @Override public void showEmptyView() {
        ptrFrameLayout.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
    }

    @Override public void showMessage(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
    //view刷新数据的方法
    @Override public void refreshData(List<Repo> datas) {
        adapter.clear();
        adapter.addAll(datas);
    }//view停止刷新的方法
    @Override public void stopRefresh() {
        ptrFrameLayout.refreshComplete(); // 下拉刷新完成
    }
    //-----------------------------------------------------------------------------------  ------------------------------------------------------------------------------------
    // 这是上拉加载更多视图层实现------
    @Override public void addMoreData(List<Repo> datas) {
        adapter.addAll(datas);
    }
//
//    @Override
//    public void addMoreData(String datas) {
//
//    }
       //隐藏footerview的方法
    @Override public void hideLoadMore() {
        listView.removeFooterView(footerView);
    }
     //显示加载更多的方法
    @Override public void showLoadMoreLoading() {
        if (listView.getFooterViewsCount() == 0) {
            listView.addFooterView(footerView);
        }
        footerView.showLoading();
    }
    //显示加载错误的方法
    @Override
    public void showLoadMoreErro(String msg) {
        if (listView.getFooterViewsCount() == 0) {
            listView.addFooterView(footerView);
        }
        footerView.showError(msg);
    }
    //加载结束
    @Override public void showLoadMoreEnd() {
        if (listView.getFooterViewsCount() == 0) {
            listView.addFooterView(footerView);
        }
        footerView.showComplete();
    }
}