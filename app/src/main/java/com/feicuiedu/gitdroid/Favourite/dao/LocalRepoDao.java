package com.feicuiedu.gitdroid.Favourite.dao;

import com.feicuiedu.gitdroid.Favourite.LocalRepo;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by yangdianwen on 16-7-8.
 * 本地仓库的操作类dao
 */
public class LocalRepoDao {
    //
    private Dao<LocalRepo,Long> dao;
    //dao的constrouctor，必须要一个dbhelper（里面封装了创建数据库，更新数据。。。的操作）
    public LocalRepoDao(DbHelper dbHelper) {
        try {
            //指定操作的数据库对象(初始化dao)
           dao= dbHelper.getDao(LocalRepo.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //添加或更新一条数据
    public void creatOrUpdate(LocalRepo localRepo){
        try {
            dao.createOrUpdate(localRepo);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //添加或更新一组数据
    public void creatOrUpdate(List<LocalRepo> localRepos){
        for (LocalRepo localrepo :localRepos) {
               creatOrUpdate(localrepo);
        }
    }
    //查询指定分类的仓库
    public List<LocalRepo> queryForGroupId(int repogroupID) {
        try {
            return    dao.queryForEq(LocalRepo.COLUMN_GROUP_ID,repogroupID);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //查询未分类
    public List<LocalRepo> queryForNoGroup() {
        try {
            //queryBuilder()查询参数，查询未分类
       return  dao.queryBuilder().where().isNull(LocalRepo.COLUMN_GROUP_ID).query();
        } catch (SQLException e) {
            throw new   RuntimeException(e);
        }
    }
    //查询所有的repo
    public List<LocalRepo> queryForAll(){
        try {
          return   dao.queryForAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //执行删除操作
    public void delete(LocalRepo repo) {
        try {
            dao.delete(repo);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
