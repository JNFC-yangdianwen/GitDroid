package com.feicuiedu.gitdroid.Login;

import android.util.Log;

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
 * Created by yangdianwen on 16-7-5.
 *
 */
public class LoginPresenter extends MvpNullObjectBasePresenter<LoginView> {
  ;
    private Call<User> userCall;
    private Call<AccessResultToken> authoToken;
    public static final String TAG="LoginPresenter";

    /**
     * login方法
     */
    public void login(String code) {
        getView().showProgress();
        if(authoToken!=null)authoToken.cancel();
        authoToken = GitHubClient.getInstance().getOAuthToken(GitHubApi.CLIENT_ID, GitHubApi.CLIENT_SECRET, code);
        authoToken.enqueue(tokenCallback);
    }
        private Callback<AccessResultToken> tokenCallback = new Callback<AccessResultToken>() {
            @Override
            public void onResponse(Call<AccessResultToken> call, Response<AccessResultToken> response) {
                String accesstoken = response.body().getAccesstoken();
                CurrentUser.setAccessToken(accesstoken);
                if (userCall!=null)userCall.cancel();
                userCall = GitHubClient.getInstance().getUserInfo();
                userCall.enqueue(userCallBack);
                Log.d(TAG, "onResponse: ");
            }
            //失败要显示的内容
            @Override
            public void onFailure(Call<AccessResultToken> call, Throwable t) {
                getView().showMessage("Fail:" + t.getMessage());
                // 失败，重置WebView
                getView().showProgress();
                getView().resetWeb();
            }
        };

    public  Callback<User> userCallBack = new Callback<User>() {
        @Override
        public void onResponse(Call<User> call, Response<User> response) {
            User user = response.body();
            CurrentUser.setUser(user);
            User user1 = CurrentUser.getUser();
            Log.d(TAG, "onResponse: ............"+user1);
            getView().showMessage("登陆成功");
            getView().navigateToMain();
        }
        @Override
        public void onFailure(Call<User> call, Throwable t) {
            CurrentUser.clear();
            getView().showMessage("Fail:"+t.getMessage());
            getView().showProgress();
            getView().resetWeb();
        }
    };
    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (!retainInstance) {
            if (authoToken != null) authoToken.cancel();
            if (userCall != null) userCall.cancel();
        }
    }
}
