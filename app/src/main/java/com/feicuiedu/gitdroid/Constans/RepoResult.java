package com.feicuiedu.gitdroid.Constans;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yangdianwen on 16-7-7.
 * 搜索结果
 */
public class RepoResult {
    //    "total_count": 2103761,
//            "incomplete_results": false,
//            "items":[]
    // 总量
    @SerializedName("total_count")
    private int totalCount;

    // 仓库列表
    @SerializedName("items")
    private List<Repo> repoList;

    @SerializedName("incomplete_results")
    private boolean incompleteResults;

    public int getTotalCount() {
        return totalCount;
    }

    public boolean isIncompleteResults() {
        return incompleteResults;
    }

    public List<Repo> getRepoList() {
        return repoList;
    }
}
