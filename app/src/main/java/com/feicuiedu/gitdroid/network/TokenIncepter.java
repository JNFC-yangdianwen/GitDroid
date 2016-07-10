package com.feicuiedu.gitdroid.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yangdianwen on 16-7-6.
 * 一个拦截token的过滤器（实现Iterceptor接口）
 */
public class TokenIncepter implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        //接口chain用来存储用户信息（用户名，密码）
        Request request = chain.request();
        Request.Builder builder=request.newBuilder();
        //如果当前用户已经有用户令牌，直接把令牌添加到请求头参数token中
        if (CurrentUser.hasAccessToken()){
             builder.addHeader("Authorization","token "+CurrentUser.getAccessToken());
        }
        //执行请求
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
