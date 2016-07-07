package com.feicuiedu.gitdroid.network;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yangdianwen on 16-7-6.
 * 请求token返回的结果（实体类）
 * 使用序列化是为了防止请求时服务器（参数的变化）
 */
public class AccessResultToken {
    @SerializedName("access_token")
    String accesstoken;
    @SerializedName("token_type")
    String tokentype;
    @SerializedName("scope")
    String scope;

    public String getAccesstoken() {
        return accesstoken;
    }

    public String getTokentype() {
        return tokentype;
    }

    public String getScope() {
        return scope;
    }
}
