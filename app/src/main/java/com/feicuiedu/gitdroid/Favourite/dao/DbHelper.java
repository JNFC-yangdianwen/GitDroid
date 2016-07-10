package com.feicuiedu.gitdroid.Favourite.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.feicuiedu.gitdroid.Favourite.LocalRepo;
import com.feicuiedu.gitdroid.Favourite.RepoGroup;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by yangdianwen on 16-7-8.
 * 数据库辅助类继承自OrmLiteSqliteOpenHelper
 */
public class DbHelper extends OrmLiteSqliteOpenHelper {
    //数据库名称
    private static final String TABLENAME="repo_favourite.db";
    //版本号
    private static final int version=1;
    private  final Context context;
    //使用单例设计模式，获取DbHelper的实例
    private static DbHelper dbHelper;
     public static DbHelper getInstance(Context context) {
         if (dbHelper==null) {
           dbHelper=new DbHelper(context);
         }
        return dbHelper;
    }

    //constructor传一个上下文即可
    public DbHelper(Context context) {
        super(context, TABLENAME, null, version);
        this.context=context;
    }
      //执行创建数据库的操作
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            //使用TableUtils工具类创建表格（表格未存在）
            TableUtils.createTableIfNotExists(connectionSource,RepoGroup.class);
            TableUtils.createTableIfNotExists(connectionSource,LocalRepo.class);
            //(popmenu)分类数据库
            new RepoGroupDao(this).creatOrUpdate(RepoGroup.getDEFAULT_GROUPS(context));
            //本地数据库
            new LocalRepoDao(this).creatOrUpdate(LocalRepo.getDefaultLocalRepos(context));
        } catch (SQLException e) {
         throw new RuntimeException(e);
        }
    }
   //更新数据
    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            //使用TableUtils工具类删除以前的数据
            TableUtils.dropTable(connectionSource,RepoGroup.class,true);
            TableUtils.dropTable(connectionSource,LocalRepo.class,true);
            //调用onCreat方法重新创建数据库
            onCreate(database,connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
