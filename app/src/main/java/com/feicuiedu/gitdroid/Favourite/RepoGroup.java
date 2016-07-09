package com.feicuiedu.gitdroid.Favourite;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by yangdianwen on 16-7-8.
 *
 */
@DatabaseTable(tableName = "RepoGroup")
public class RepoGroup {
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
    @DatabaseField(id = true)
    private int id;
    @DatabaseField(columnName = "NAME")
    private String name;
    public static List<RepoGroup> DEFAULT_GROUPS;
    public static List<RepoGroup>  getDEFAULT_GROUPS(Context context){
       if (DEFAULT_GROUPS != null) return DEFAULT_GROUPS;
       try {
           //获取数据
           InputStream inputStream = context.getAssets().open("repogroup.json");
           //使用IOUtils工具类
           String group = IOUtils.toString(inputStream);
           Gson gson=new Gson();
           DEFAULT_GROUPS = gson.fromJson(group, new TypeToken<List<RepoGroup>>() {
           }.getType());
           return DEFAULT_GROUPS;
       } catch (IOException e) {
         throw new RuntimeException(e);
       }
   }
}
