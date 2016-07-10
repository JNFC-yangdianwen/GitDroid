package com.feicuiedu.gitdroid.Login;

import com.feicuiedu.gitdroid.network.AccessResultToken;
import com.feicuiedu.gitdroid.network.CurrentUser;
import com.feicuiedu.gitdroid.network.GitHubApi;
import com.feicuiedu.gitdroid.network.GitHubClient;
import com.feicuiedu.gitdroid.network.User;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yangdianwen on 16-7-6.
 */
public class LoginPresenter1 extends MvpNullObjectBasePresenter<LoginView> {

    private Call<AccessResultToken> tokenCall;
    private Call<User> userCall;

    /**
     * 此方法是本Presenter中最重要的方法。视图会调用这个方法来触发登录用例。
     *
     * @param code 用户登录GitHub后，GitHub给我们的访问令牌。
     */
    public void login(String code) {
        getView().showProgress();
        if (tokenCall != null) tokenCall.cancel();
        //获取call模型
        tokenCall = GitHubClient.getInstance().getOAuthToken(GitHubApi.CLIENT_ID, GitHubApi.CLIENT_SECRET, code);
        tokenCall.enqueue(tokenCallback);
    }

    // 获取AccessToken的回调
    private Callback<AccessResultToken> tokenCallback = new Callback<AccessResultToken>() {
        @Override public void onResponse(Call<AccessResultToken> call, Response<AccessResultToken> response) {
            // 保存token到内存里面
            String token = response.body().getAccesstoken();
            CurrentUser.setAccessToken(token);
            //如果userCall
            if (userCall != null) userCall.cancel();
            userCall = GitHubClient.getInstance().getUserInfo();
            userCall.enqueue(userCallback);
        }
        @Override public void onFailure(Call<AccessResultToken> call, Throwable t) {
            getView().showMessage("Fail:" + t.getMessage());
            // 失败，重置WebView
            getView().showProgress();
            getView().resetWeb();

        }
    };
    // 获取用户信息的回调
    private Callback<User> userCallback = new Callback<User>() {
        @Override public void onResponse(Call<User> call, Response<User> response) {
            // 保存user到内存里面
            User user = response.body();
            CurrentUser.setUser(user);
            // 导航至主页面
            getView().showMessage("登陆成功");
            getView().navigateToMain();
        }

        @Override public void onFailure(Call<User> call, Throwable t) {
            // 清除缓存的用户信息，
            CurrentUser.clear();
            getView().showMessage("Fail:" + t.getMessage());
            // 重置WebView
            getView().showProgress();
            getView().resetWeb();
        }
    };

    @Override public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (!retainInstance) {
            if (tokenCall != null) tokenCall.cancel();
            if (userCall != null) userCall.cancel();
        }
    }
}

