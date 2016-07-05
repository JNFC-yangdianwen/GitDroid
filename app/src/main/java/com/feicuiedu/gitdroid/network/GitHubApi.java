package com.feicuiedu.gitdroid.network;

/**
 * Created by yangdianwen on 16-7-5.
 */
public interface GitHubApi {
//    //0 users
//    Client ID
//    42da7ad27e1072117a76
//    Client Secret
//    0d6c0947115aa6ec24498e96fa912ca02fc0457a
    // GitHub开发者，申请就行
//    String CLIENT_ID = "c360b01e5ef05890248b";

    String CLIENT_ID = "42da7ad27e1072117a76";
  //42da7ad27e1072117a76
    // 授权时申请的可访问域
    String INITIAL_SCOPE = "user,public_repo,repo";
    //https://github.com/login/oauth/authorize?client_id=c360b01e5ef05890248b&scope=user,public_repo,repo
    // WebView来加载此URL,用来显示GitHub的登陆页面
    String AUTH_URL =
            "https://github.com/login/oauth/authorize?client_id="+
                    CLIENT_ID + "&"+"scope="+INITIAL_SCOPE;
}
