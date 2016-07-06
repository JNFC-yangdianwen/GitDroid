package com.feicuiedu.gitdroid.Login;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by yangdianwen on 16-7-5.
 * 登陆view接口继承mvpview
 */
public interface LoginView extends MvpView {
    // 显示进度条
    void showProgress();
    // 重置webView
    void resetWeb();
    void showMessage(String msg);
    // 导航至主页面
    void navigateToMain();
}
