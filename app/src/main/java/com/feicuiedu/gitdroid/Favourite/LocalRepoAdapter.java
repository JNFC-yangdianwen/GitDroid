package com.feicuiedu.gitdroid.Favourite;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.feicuiedu.gitdroid.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by yangdianwen on 16-7-8.
 */
public class LocalRepoAdapter extends BaseAdapter {
    List<LocalRepo> datas;

    public LocalRepoAdapter() {
        datas=new ArrayList<>();
    }

    public void setData(@Nullable List<LocalRepo> repos){
        datas.clear();
        datas.addAll(repos);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public LocalRepo getItem(int position) {
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
        LocalRepo repo=getItem(position);
        viewHolder.repoInfo.setText(repo.getDescription());
        viewHolder.repoName.setText(repo.getFullName());
        viewHolder.repoStars.setText(String.format(""+repo.getStarCount()));
        ImageLoader.getInstance().displayImage(repo.getAvatar(),viewHolder.ivIcon);
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
