package com.feicuiedu.gitdroid.network;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

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
//    String CALL_BACK="gitdroid";
//    String CLIENT_ID = "42da7ad27e1072117a76";
//  //42da7ad27e1072117a76
//    String CLIENT_SECRET="0d6c0947115aa6ec24498e96fa912ca02fc0457a";
//    // 授权时申请的可访问域
//    String INITIAL_SCOPE = "user,public_repo,repo";
//    //https://github.com/login/oauth/authorize?client_id=aa7a3fb1b42f8c07a232&scope=user,public_repo,repo
//    // WebView来加载此URL,用来显示GitHub的登陆页面
//    String AUTH_URL = "https://github.com/login/oauth/authorize?client_id="+
//            CLIENT_ID + "&"+"scope="+INITIAL_SCOPE;
//     @Headers("Accept: application/json")
//     @FormUrlEncoded
//    @POST("https://github.com/login/oauth/access_token")
//    Call<AccessResultToken> getAuthoToken(@Field("client_id") String client,
//                                          @Field("client_secret") String clientSecret,
//                                          @Field("code") String code);
// GitHub开发者，申请时填写的(重定向返回时的一个标记)
String CALL_BACK = "gitdroid";
    // GitHub开发者，申请就行
    String CLIENT_ID = "42da7ad27e1072117a76";
    String CLIENT_SECRET = "0d6c0947115aa6ec24498e96fa912ca02fc0457a";

    // 授权时申请的可访问域
    String INITIAL_SCOPE = "user,public_repo,repo";

    // WebView来加载此URL,用来显示GitHub的登陆页面
    String AUTH_URL =
            "https://github.com/login/oauth/authorize?client_id=" +
                    CLIENT_ID + "&" + "scope=" + INITIAL_SCOPE;
//    String CALL_BACK = "feicui";
//    // GitHub开发者，申请就行
//    String CLIENT_ID = "aa7a3fb1b42f8c07a232";
//    String CLIENT_SECRET = "841a9cfd15ded1abb9d75ba51d2d8dd0189ebb77";
//
//    // 授权时申请的可访问域
//    String INITIAL_SCOPE = "user,public_repo,repo";
//
//    // WebView来加载此URL,用来显示GitHub的登陆页面
//    String AUTH_URL =
//            "https://github.com/login/oauth/authorize?client_id=" +
//                    CLIENT_ID + "&" + "scope=" + INITIAL_SCOPE;

    /**
     * 获取访问令牌API
     */
    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("https://github.com/login/oauth/access_token")
    Call<AccessResultToken> getOAuthToken(
            @Field("client_id") String client,
            @Field("client_secret") String clientSecret,
            @Field("code") String code);
    @GET("user")
    Call<User> getUserInfo();
}
