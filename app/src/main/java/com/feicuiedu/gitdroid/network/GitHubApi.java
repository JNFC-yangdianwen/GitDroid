package com.feicuiedu.gitdroid.network;

import com.feicuiedu.gitdroid.Constans.RepoResult;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by yangdianwen on 16-7-5.
 */
public interface GitHubApi {

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
    /**
     * 获取访问令牌API
     * @Filed 代表的是表格中的字段名 @FiledMap（Map集合）（调用时传入一个map集合就可以）
     * @Headers 代表请求头   请求token的方法（call模型）
     */
    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("https://github.com/login/oauth/access_token")
    Call<AccessResultToken> getOAuthToken(
            @Field("client_id") String client,
            @Field("client_secret") String clientSecret,
            @Field("code") String code);
    //请求用户信息：
    @GET("user")
    Call<User> getUserInfo();
    //搜索热门仓库的api
    //search/repositories?q={query}{&page,per_page,sort,order}
    //http://api.github.com/search/repositories?q=java&page=1
    @GET("search/repositories")
    Call<RepoResult> getRepoSearch(@Query("q")String language, @Query("page")int pageId);
}
