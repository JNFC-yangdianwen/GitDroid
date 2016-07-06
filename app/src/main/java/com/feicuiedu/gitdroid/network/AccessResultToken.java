package com.feicuiedu.gitdroid.network;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yangdianwen on 16-7-6.
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
