package com.feicuiedu.gitdroid.network;

import com.feicuiedu.gitdroid.Constans.RepoResult;
import com.feicuiedu.gitdroid.ReadMe.RepoContentResult;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by yangdianwen on 16-7-5.
 * 使用retrofit框架实现网络请求
 */
public class GitHubClient implements GitHubApi {
    /***
     * 使用单例设计模式（懒汉式）
     */
    private static GitHubClient client;
    private final GitHubApi gitHubApi;

    //获取GitHubClient的实例的方法
     public static GitHubClient getInstance() {
         if (client == null) {
             client= new GitHubClient();
         }
      return  client;
    }
    //constructor  初始化retrofit对象
    public GitHubClient() {
        //日志拦截器
        HttpLoggingInterceptor interceptor=new HttpLoggingInterceptor();
        //设置拦截器的拦截等级
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient
                .Builder()
                .addInterceptor(interceptor)//添加拦截器拦截log
                .addInterceptor(new TokenIncepter())//添加拦截Token的拦截器（必须添加否则token拿不到）
                .build();
        //初始化retrofit对象
        //https://api.github.com/
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                //添加转换器
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        //创建api接口
        gitHubApi = retrofit.create(GitHubApi.class);
    }
       //获取token令牌
    @Override
    public Call<AccessResultToken> getOAuthToken(@Field("client_id") String client, @Field("client_secret") String clientSecret, @Field("code") String code) {
        return gitHubApi.getOAuthToken(client,clientSecret,code);
    }
    //获取用户信息
    @Override
    public Call<User> getUserInfo() {
        return gitHubApi.getUserInfo();
    }

    @Override
    public Call<RepoResult> getRepoSearch(@Query("q") String language, @Query("page") int pageId) {
        return gitHubApi.getRepoSearch(language,pageId);
    }

    @Override
    public Call<RepoContentResult> getReadme(@Path("owner") String owner, @Path("repo") String repo) {
        return gitHubApi.getReadme(owner, repo);
    }

    @Override
    public Call<ResponseBody> markdown(@Body RequestBody body) {
        return gitHubApi.markdown(body);
    }

}
