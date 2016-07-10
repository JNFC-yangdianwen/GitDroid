package com.feicuiedu.gitdroid.network;

/**
 * Created by yangdianwen on 16-7-6.
 */
/**
 * This class is a very simple implementation to cache current user's information.
 * I didn't persist these data in database or {@link android.content.SharedPreferences},
 * you can try to do it yourself.
 *
 * <p/>
 * 此类是一个用来缓存当前用户信息的极简单的实现。
 *
 * 我没有使用数据库或SharedPreferences来持久化这些数据，
 * 你可以自己尝试来做。
 */
public class CurrentUser {
    // 此类不可实例化
    private CurrentUser(){}

    private static String accessToken;

    private static User user;
     //设置用户令牌
    public static void setAccessToken(String accessToken){
        CurrentUser.accessToken = accessToken;
    }
    public static void setUser(User user){
        CurrentUser.user = user;
    }
    //清除缓存的信息
    public static void clear(){
        accessToken = null;
        user = null;
    }
   //获取用户令牌
    public static String getAccessToken(){
        return accessToken;
    }

    public static User getUser() {
        return user;
    }

    // 当前是否有访问令牌
    public static boolean hasAccessToken(){
        return accessToken != null;
    }
   //判断用户令牌和用户名是否为null
    public static boolean isEmpty(){
        return accessToken == null || user == null;
    }
}
