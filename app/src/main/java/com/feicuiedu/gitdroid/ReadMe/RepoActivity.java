package com.feicuiedu.gitdroid.ReadMe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.feicuiedu.gitdroid.Constans.Repo;
import com.feicuiedu.gitdroid.R;
import com.feicuiedu.gitdroid.Utils.ActivityUtils;
import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by yangdianwen on 16-7-8.
 */
public class RepoActivity extends MvpActivity<RepoView,RepoPresenter> implements RepoView {
  @Bind(R.id.progressBar)ProgressBar progressBar;
    @Bind(R.id.ivIcon) ImageView ivIcon;
    @Bind(R.id.webView)WebView webView;
    @Bind(R.id.toolbar)Toolbar toolbar;
    @Bind(R.id.tvRepoInfo)TextView tvRepoInfo;
    @Bind(R.id.tvRepoStars)TextView tvRepoStars;
    @Bind(R.id.tvRepoName)TextView tvRepoName;
      ActivityUtils activityUtils;
    private Repo repo;
   public static final String KEY="key_repo";
    @NonNull
    @Override
    public RepoPresenter createPresenter() {
        return new RepoPresenter();
    }
    public static void open(Context context, Repo repo){
        Intent intent=new Intent(context,RepoActivity.class);
        intent.putExtra(KEY,repo);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_info);
        activityUtils=new ActivityUtils(this);
        getPresenter().getReadme(repo);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        repo = (Repo) getIntent().getSerializableExtra(KEY);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(repo.getName());
        ImageLoader.getInstance().displayImage(repo.getOwner().getAvatar(),ivIcon);
        tvRepoInfo.setText(repo.getDescription());
        tvRepoName.setText(repo.getFullName());
        tvRepoStars.setText(String.format("star: %d  fork: %d", repo.getStargazersCount(), repo.getForksCount()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    //显示视图的处理
    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void setData(String data) {
        webView.loadData(data,"text/html","UTF-8");
    }

    @Override
    public void setMessage(String msg) {
      activityUtils.showToast(msg);
    }
}
