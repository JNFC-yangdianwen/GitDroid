package com.feicuiedu.gitdroid.Favourite.dao;

import com.feicuiedu.gitdroid.Favourite.RepoGroup;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by yangdianwen on 16-7-8.
 * 一个操作分类的数据库
 */
public class RepoGroupDao {

 private  Dao<RepoGroup,Long> dao;
    public RepoGroupDao(DbHelper dbHelper) {
        try {
         dao=dbHelper.getDao(RepoGroup.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //查询所有的仓库类别
    public List<RepoGroup> queryForAll(){
     try {
       return   dao.queryForAll();
     } catch (SQLException e) {
         throw new RuntimeException(e);
     }
 }
    //更新或添加一条数据
    public   void creatOrUpdate(RepoGroup repoGroup){
        try {
            dao.createOrUpdate(repoGroup);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //更新或添加多条数据
    public   void creatOrUpdate(List<RepoGroup> repoGroups){
        for (RepoGroup repogroup:repoGroups) {
                creatOrUpdate(repogroup);
        }
    }
    public RepoGroup queryForId(long id){
        try {
          return   dao.queryForId(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
