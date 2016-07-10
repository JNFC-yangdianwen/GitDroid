package com.feicuiedu.gitdroid.Favourite;

import android.support.annotation.NonNull;

import com.feicuiedu.gitdroid.Constans.Repo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangdianwen on 16-7-8.
 * 一个repo转换类，把热门仓库转换成本地repo
 */
public class RepoConverter {

    private RepoConverter(){}
    //转换一个仓库的方法
    public static @NonNull
    LocalRepo convert(@NonNull Repo repo) {
        LocalRepo localRepo = new LocalRepo();
        localRepo.setAvatar(repo.getOwner().getAvatar());
        localRepo.setDescription(repo.getDescription());
        localRepo.setFullName(repo.getFullName());
        localRepo.setId(repo.getId());
        localRepo.setName(repo.getName());
        localRepo.setStarCount(repo.getStargazersCount());
        localRepo.setForkCount(repo.getForksCount());
        return localRepo;
    }
   //转换多个仓库的方法
    public static @NonNull
    List<LocalRepo> convertAll(@NonNull List<Repo> repos) {
        ArrayList<LocalRepo> localRepos = new ArrayList<>();
        for (Repo repo : repos) {
            localRepos.add(convert(repo));
        }
        return localRepos;
    }
}
