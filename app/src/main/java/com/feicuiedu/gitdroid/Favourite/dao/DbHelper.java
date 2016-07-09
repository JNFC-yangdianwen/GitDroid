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
 */
public class DbHelper extends OrmLiteSqliteOpenHelper {
    private static final String TABLENAME="repo_favourite.db";
    private static final int version=2;
    private static DbHelper dbHelper;
     public static DbHelper getInstance(Context context) {
         if (dbHelper==null) {
           dbHelper=new DbHelper(context);
         }
        return dbHelper;
    }
    private  final Context context;
    public DbHelper(Context context) {
        super(context, TABLENAME, null, version);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTableIfNotExists(connectionSource,RepoGroup.class);
            TableUtils.createTableIfNotExists(connectionSource,LocalRepo.class);
            new RepoGroupDao(this).creatOrUpdate(RepoGroup.getDEFAULT_GROUPS(context));
            new LocalRepoDao(this).creatOrUpdate(LocalRepo.getDefaultLocalRepos(context));
        } catch (SQLException e) {
         throw new RuntimeException(e);
        }
    }
   //
    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource,RepoGroup.class,true);
            TableUtils.dropTable(connectionSource,LocalRepo.class,true);
            onCreate(database,connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
