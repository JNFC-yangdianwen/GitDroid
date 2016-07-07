package com.feicuiedu.gitdroid.repo;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.feicuiedu.gitdroid.Constans.Repo;
import com.feicuiedu.gitdroid.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by yangdianwen on 16-7-7.
 * listview item的
 */
public class LanguageAdapter extends BaseAdapter {
    List<Repo> datas;

    public LanguageAdapter() {
        datas=new ArrayList<>();
    }
    public void addAll(Collection<Repo> repos){
        datas.addAll(repos);
        notifyDataSetChanged();
    }
    public void clear(){
        datas.clear();
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Repo getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
           convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_repo, parent, false);
           convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        Repo repo=getItem(position);
        viewHolder.repoInfo.setText(repo.getDescription());
        viewHolder.repoName.setText(repo.getFullName());
        viewHolder.repoStars.setText(repo.getStargazersCount()+"");
        ImageLoader.getInstance().displayImage(repo.getOwner().getAvatar(),viewHolder.ivIcon);
        return convertView;
    }
    class ViewHolder{
        @Bind(R.id.tvRepoInfo)TextView repoInfo;
        @Bind(R.id.tvRepoName)TextView repoName;
        @Bind(R.id.tvRepoStars)TextView repoStars;
        @Bind(R.id.ivIcon)ImageView ivIcon;

        public ViewHolder(View view) {
            ButterKnife.bind(this,view);
        }
    }
}
