package com.feicuiedu.gitdroid.Presenter;

import com.feicuiedu.gitdroid.Constans.Languages;
import com.feicuiedu.gitdroid.Constans.Repo;
import com.feicuiedu.gitdroid.Constans.RepoResult;
import com.feicuiedu.gitdroid.View.PagerView;
import com.feicuiedu.gitdroid.network.GitHubClient;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yangdianwen on 16-7-2.
 */
public class Presenter extends MvpNullObjectBasePresenter<PagerView> {
    private Languages languages;
    int pageId=0;
    private Call<RepoResult> repoSearchCall;

    public Presenter(Languages languages) {
        this.languages = languages;
    }

    //下拉刷新的方法
    public  void loadData(){
        getView().hideLoadMore();
        getView().showContentView();
        pageId=1;
        repoSearchCall = GitHubClient.getInstance().getRepoSearch(languages.getPath(), pageId);
        repoSearchCall.enqueue(repoSearchCallBack);
    }
    //上拉加载更多方法
    public void loadMoreData(){
        getView().showLoadMoreLoading();
        repoSearchCall = GitHubClient.getInstance().getRepoSearch(languages.getPath(), pageId);
        repoSearchCall.enqueue(loaderMoreCallBack);
    }
    //下拉刷新的call模型
    private Callback<RepoResult> repoSearchCallBack=new Callback<RepoResult>() {
        @Override
        public void onResponse(Call<RepoResult> call, Response<RepoResult> response) {
            /***
             * 1.如果响应为空，则显示错误视图，
             * 2.如果搜到的仓库个数小与0，则显示空视图
             * 3.如果响应成功，并且有数据，则显示ok
             */
            //响应成功刷新结束
            getView().stopRefresh();
            RepoResult body = response.body();
            if (body == null) {
                getView().showErroView("fail.......");
                return;
            }
            if (body.getTotalCount()<0) {
                getView().refreshData(null);
                getView().showEmptyView();
                return;
            }
            List<Repo> repoList = body.getRepoList();
            getView().refreshData(repoList);
            pageId=2;

        }
        @Override
        public void onFailure(Call<RepoResult> call, Throwable t) {
            getView().stopRefresh();
            getView().showErroView(t.getMessage());
        }
    };
    //上拉加载的call模型
    private Callback<RepoResult>loaderMoreCallBack=new Callback<RepoResult>() {
        @Override
        public void onResponse(Call<RepoResult> call, Response<RepoResult> response) {
            //首先隐藏视图
            getView().hideLoadMore();
            RepoResult body = response.body();
            //如果数据为空则显示错误视图
            if (body == null) {
              getView().showLoadMoreErro("error....");
                return;
            }
            //如果没有搜索到仓库，则显示没有更多数据
            if (body.getTotalCount()<0) {
             getView().showLoadMoreEnd();
                return;
            }
               //显示数据
            List<Repo> repoList = body.getRepoList();
            getView().addMoreData(repoList);
            pageId++;

        }

        @Override
        public void onFailure(Call<RepoResult> call, Throwable t) {
            getView().hideLoadMore();
            getView().showLoadMoreErro(t.getMessage());
        }
    };
    }

