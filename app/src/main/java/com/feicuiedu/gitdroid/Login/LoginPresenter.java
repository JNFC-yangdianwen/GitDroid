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
 * Created by yangdianwen on 16-7-5.
 *  处理登陆的presenter，继承MvpPresenter（MvpNullObjectBasePresenter<View>）
 *  此类主要是处理用户的token
 */
public class LoginPresenter extends MvpNullObjectBasePresenter<LoginView> {
    private Call<User> userCall;
    private Call<AccessResultToken> authoToken;
    public static final String TAG="LoginPresenter";

    /**
     * login方法，此方法是本类中的最重要的方法，
     *   用户登陆github后给用户的访问令牌
     */
    public void login(String code) {
        getView().showProgress();
        //为了确保每个用户的令牌都不一样
        if(authoToken!=null)authoToken.cancel();
        //获取call模型
        authoToken = GitHubClient.getInstance().getOAuthToken(GitHubApi.CLIENT_ID, GitHubApi.CLIENT_SECRET, code);
        //使用call模型处理异步请求
        authoToken.enqueue(tokenCallback);
    }
        private Callback<AccessResultToken> tokenCallback = new Callback<AccessResultToken>() {

            @Override
            public void onResponse(Call<AccessResultToken> call, Response<AccessResultToken> response) {
                //响应成功就可以拿到token
                String accesstoken = response.body().getAccesstoken();
                //设置当前用户的token
                CurrentUser.setAccessToken(accesstoken);
                //拿到用户的token之后就可以去获取用户信息，同样使用call模型处理异步请求
                if (userCall!=null)userCall.cancel();
                //获取用户信息的call模型
                userCall = GitHubClient.getInstance().getUserInfo();
                //执行异步请求
                userCall.enqueue(userCallBack);
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
        //响应成功之后就可以获取到用户的基本信息,拿到基本信息之后然后设置到user实体类
        @Override
        public void onResponse(Call<User> call, Response<User> response) {
            User user = response.body();
            //设置当前用户的信息
            CurrentUser.setUser(user);
            //调用view显示
            getView().showMessage("登陆成功");
            //导航之主界面
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
