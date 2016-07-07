package com.feicuiedu.gitdroid.network;

import com.feicuiedu.gitdroid.Constans.RepoResult;
import com.feicuiedu.gitdroid.ReadMe.RepoContentResult;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
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
    // https://api.github.com/repos/square/okhttp/readme
    /**
     * @param owner 仓库的拥有者
     * @param repo 仓库的名称
     * @return 仓库的Readme页面的内容，{@link RepoContentResult#content}将是Markdown格式。
     */
    @GET("repos/{owner}/{repo}/readme")
    Call<RepoContentResult> getReadme(@Path("owner") String owner, @Path("repo") String repo);

    /**
     * 获取一个Markdown内容对应的HTMl页面
     * @param body 请求体，内容来自{@link RepoContentResult#content}
     * @return Call对象
     */
    @Headers({
            "Content-Type:text/plain"
    })
    @POST("/markdown/raw")
    Call<ResponseBody> markdown(@Body RequestBody body);
}
