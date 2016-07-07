package com.feicuiedu.gitdroid.ReadMe;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by yangdianwen on 16-7-7.
 */
public interface RepoView extends MvpView {
    void showProgress();
    void hideProgress();
    void setData(String data);
    void setMessage(String msg);


}
