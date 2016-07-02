package com.feicuiedu.gitdroid.View;

/**
 * Created by yangdianwen on 16-7-2.
 * 上拉加载的视图
 */
public interface LoadMoreView <T>{
    // 添加更多数据
    void addMoreData(T datas);

    // 隐藏加载更多的视图
    void hideLoadMore();

    // 加载更多 -- 加载中
    void showLoadMoreLoading();

    // 加载更多 -- 加载发生错误
    void showLoadMoreErro(String msg);

    // 加载更多 -- 没有更多数据
    void showLoadMoreEnd();
}
