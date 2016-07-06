package com.feicuiedu.gitdroid.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yangdianwen on 16-7-6.
 * 一个过滤器
 */
public class TokenIncepter implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder=request.newBuilder();
        if (CurrentUser.hasAccessToken()){
             builder.addHeader("Authorization","token "+CurrentUser.getAccessToken());
        }
        Response response = chain.proceed(builder.build());
          if (response.isSuccessful()){
              return response;
          }
        if(response.code() == 401 || response.code() == 403){
            throw new IOException("未经授权的!限制是每分钟10次!");
        }else{
            throw new IOException("响应码:" + response.code());
        }
    }
}
