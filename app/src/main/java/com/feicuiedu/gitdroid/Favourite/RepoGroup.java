package com.feicuiedu.gitdroid.Favourite;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

/**
 * Created by yangdianwen on 16-7-8.
 *这是一个数据库格式的类（使用ormlite管理，添加依赖
 * compile 'com.j256.ormlite:ormlite-android:4.48'
 * compile 'com.j256.ormlite:ormlite-core:4.48'）
 */
@DatabaseTable(tableName = "RepoGroup")
public class RepoGroup implements Serializable {
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
    //字段主键id
    @DatabaseField(id = true)
    private int id;
    @DatabaseField(columnName = "NAME")
    private String name;
    //默认的分类集合
    public static List<RepoGroup> DEFAULT_GROUPS;
    //获取分类的数据库
    public static List<RepoGroup>  getDEFAULT_GROUPS(Context context){
       if (DEFAULT_GROUPS != null) return DEFAULT_GROUPS;
       try {
           //获取数据
           InputStream inputStream = context.getAssets().open("repogroup.json");
           //使用IOUtils工具类
           String group = IOUtils.toString(inputStream);
           //gson解析数据
           Gson gson=new Gson();
           DEFAULT_GROUPS = gson.fromJson(group, new TypeToken<List<RepoGroup>>() {
           }.getType());
           return DEFAULT_GROUPS;
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
   }
}
