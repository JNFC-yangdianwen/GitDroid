package com.feicuiedu.gitdroid.ReadMe;

import android.util.Base64;

import com.feicuiedu.gitdroid.Constans.Repo;
import com.feicuiedu.gitdroid.network.GitHubClient;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yangdianwen on 16-7-7.
 */
public class RepoPresenter extends MvpNullObjectBasePresenter<RepoView>{

    private Call<RepoContentResult> repoContentResultCall;
    private Call<ResponseBody> mdCall;
    //获取readme
    public void getReadme(Repo repo){
        getView().showProgress();
        String login = repo.getOwner().getLogin();
        String name = repo.getName();
        if (repoContentResultCall != null)repoContentResultCall.cancel();
        repoContentResultCall = GitHubClient.getInstance().getReadme(login, name);
        repoContentResultCall.enqueue(repoContentResultCallback);
    }
    private Callback<RepoContentResult>  repoContentResultCallback = new Callback<RepoContentResult>() {
        @Override
        public void onResponse(Call<RepoContentResult> call, Response<RepoContentResult> response) {
            //获取内容
            String content = response.body().getContent();
            //Base64解码,变成字节数足组
            byte[] decode = Base64.decode(content, Base64.DEFAULT);
            //把字节数组变成字符串数据
            String mdContent=new String(decode);
            //根据md文档格式变成html格式
            RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), mdContent);
            if (mdCall != null) mdCall.cancel();
            mdCall = GitHubClient.getInstance().markdown(requestBody);
            mdCall.enqueue(mdCallBack);
        }
        @Override
        public void onFailure(Call<RepoContentResult> call, Throwable t) {
              getView().hideProgress();
            getView().setMessage(t.getMessage());
        }
    };
    private Callback<ResponseBody>mdCallBack = new Callback<ResponseBody>() {
        @Override
        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
            try {
                String htmlContent = response.body().string();
                getView().setData(htmlContent);
                getView().hideProgress();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onFailure(Call<ResponseBody> call, Throwable t) {
            getView().hideProgress();
            getView().setMessage(t.getMessage());
        }
    };
}



