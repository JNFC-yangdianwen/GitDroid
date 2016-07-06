package com.feicuiedu.gitdroid.Login;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.feicuiedu.gitdroid.Home.MainActivity;
import com.feicuiedu.gitdroid.R;
import com.feicuiedu.gitdroid.Utils.ActivityUtils;
import com.feicuiedu.gitdroid.network.GitHubApi;
import com.hannesdorfmann.mosby.mvp.MvpActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by yangdianwen on 16-7-5.
 */
public class LoginActivity extends MvpActivity<LoginView,LoginPresenter> implements LoginView{
    @Bind(R.id.toolbar)Toolbar toolbar;
    @Bind(R.id.webView) WebView webView;
    @Bind(R.id.gifImageView) GifImageView gifImageView;
    private ActivityUtils activityUtils;
    private static final String TAG = "LoginActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        activityUtils=new ActivityUtils(this);
    }

    @NonNull
    @Override
    public LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        // 显示标题栏左上角的返回按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        initWebView();
    }

    private void initWebView() {
        // 删除所有的Cookie，主要是为了清除以前的登录历史记录
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        //设置webview加载的路径
        webView.loadUrl(GitHubApi.AUTH_URL);
        //设置webview的焦点：用来控制webview
        webView.setFocusable(true);
        webView.setFocusableInTouchMode(true);
        webView.setWebChromeClient(webChomeClient);
        webView.setWebViewClient(webViewClient);
    }
    //webview的浏览器客户端，主要是用来显示webview和隐藏gifImageView
      private WebChromeClient webChomeClient=new WebChromeClient(){

          @Override
          public void onProgressChanged(WebView view, int newProgress) {
              super.onProgressChanged(view, newProgress);
              if (newProgress == 100) {
                  webView.setVisibility(View.VISIBLE);
                  gifImageView.setVisibility(View.GONE);
              }
          }
      };
    //webview的重定向
    private WebViewClient webViewClient=new WebViewClient(){
        // 登陆成功后github将重定向一个url路径
        // 我们从中对比，取出code(临时授权值, 在真正授权时一定要的值)
        // 进行授权接口操作,且获取到你的基本信息
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Uri uri = Uri.parse(url);
            if (uri.getScheme().equals(GitHubApi.CALL_BACK)){
                   //获取授权吗
                String code = uri.getQueryParameter("code");
                Log.d(TAG, "shouldOverrideUrlLoading: "+code);
               getPresenter().login(code);
                return true;
            }
            return super.shouldOverrideUrlLoading(view, url);
        }
    };
    // 显示进度的方法
    @Override
    public void showProgress() {
        gifImageView.setVisibility(View.VISIBLE);
    }

    @Override
    public void resetWeb() {
      initWebView();
    }

    @Override
    public void showMessage(String msg) {
         activityUtils.showToast(msg);
    }

    @Override
    public void navigateToMain() {
     activityUtils.startActivity(MainActivity.class);
    }
}
