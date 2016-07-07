package com.feicuiedu.gitdroid.View;

import com.feicuiedu.gitdroid.Constans.Repo;
import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

/**
 * Created by yangdianwen on 16-7-2.
 */
public interface PagerView extends PtrView<List<Repo>>,LoadMoreView<List<Repo>>, MvpView {
}
