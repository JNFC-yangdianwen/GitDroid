package com.feicuiedu.gitdroid.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yangdianwen on 16-7-5.
 */
public class GitHubClient {
    private static GitHubClient client;
     public static GitHubClient getInstance() {
         if (client == null) {
             client= new GitHubClient();
         }
      return  client;
    }

    public GitHubClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }
}
