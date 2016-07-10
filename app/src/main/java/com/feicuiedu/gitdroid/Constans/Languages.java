package com.feicuiedu.gitdroid.Constans;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

/**
 * Created by yangdianwen on 16-7-7.
 * 读取本地语言数据库
 */
public class Languages implements Serializable {
    String path;
    String name;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    //默认语言选项
    private static List<Languages> DEFAULT_LANGS;
   public static List<Languages> getLanguage(Context context){
        if (DEFAULT_LANGS != null)return  DEFAULT_LANGS;
        try {
            //读取本地数据库
            InputStream inputStream = context.getAssets().open("langs.json");
            //使用第三方的io流（compile 'commons-io:commons-io:2.4'）
            //返回的是json字符串数据
            String content = IOUtils.toString(inputStream);
            //gson解析数据
            Gson gson = new Gson();
            DEFAULT_LANGS = gson.fromJson(content, new TypeToken<List<Languages>>(){}.getType());
            return DEFAULT_LANGS;
        } catch (IOException e) {
            //出错跑异常
            throw new RuntimeException(e);
        }
    }
}
