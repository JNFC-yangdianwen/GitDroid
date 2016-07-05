package com.feicuiedu.gitdroid.Login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.feicuiedu.gitdroid.R;
import com.feicuiedu.gitdroid.network.GitHubApi;
import com.hannesdorfmann.mosby.mvp.MvpActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by yangdianwen on 16-7-5.
 */
public class LoginActivity extends MvpActivity<LoginView,LoginPresenter> {
    @Bind(R.id.toolbar)Toolbar toolbar;
    @Bind(R.id.webView) WebView webView;
    @Bind(R.id.gifImageView) GifImageView gifImageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
        initWebView();
    }

    private void initWebView() {
        // 删除所有的Cookie，主要是为了清除以前的登录历史记录
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        webView.loadUrl(GitHubApi.AUTH_URL);
        webView.setFocusable(true);
        webView.setFocusableInTouchMode(true);
        webView.setWebChromeClient(webChomeClient);
        webView.setWebViewClient(webViewClient);
    }
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

            return super.shouldOverrideUrlLoading(view, url);
        }
    };
}
