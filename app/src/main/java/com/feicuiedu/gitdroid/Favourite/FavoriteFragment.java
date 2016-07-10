package com.feicuiedu.gitdroid.Favourite;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.feicuiedu.gitdroid.Favourite.dao.DbHelper;
import com.feicuiedu.gitdroid.Favourite.dao.LocalRepoDao;
import com.feicuiedu.gitdroid.Favourite.dao.RepoGroupDao;
import com.feicuiedu.gitdroid.R;
import com.feicuiedu.gitdroid.Utils.ActivityUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yangdianwen on 16-7-8.
 * 收藏的fragment
 */
public class FavoriteFragment extends Fragment implements PopupMenu.OnMenuItemClickListener{
@Bind(R.id.tvGroupType)TextView tvGroupType;
@Bind(R.id.listView)ListView listView;
    private PopupMenu popupMenu;
    private LocalRepoAdapter adapter;
    private RepoGroupDao repoGroupDao;
    private LocalRepoDao localRepoDao;
    ActivityUtils activityUtils;
    private LocalRepo currentlocalRepo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        adapter=new LocalRepoAdapter();
        listView.setAdapter(adapter);
        //适配器设置全部数据
        adapter.setData(localRepoDao.queryForAll());
        //listview的上下文菜单
        registerForContextMenu(listView);
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //类别的操作
        repoGroupDao=new RepoGroupDao(DbHelper.getInstance(getContext()));
        //本地仓库的操作
        localRepoDao=new LocalRepoDao(DbHelper.getInstance(getContext()));
        activityUtils=new ActivityUtils(getActivity());
    }
    //上下文菜单
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() ==R.id.listView) {
            //得到listview身上的item
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
            int position = info.position;
            currentlocalRepo = adapter.getItem(position);
            //上下文菜单的样式
            MenuInflater menuInflater = getActivity().getMenuInflater();
            menuInflater.inflate(R.menu.menu_context_favorite,menu);
            //上下文菜单的子菜单
            SubMenu subMenu = menu.findItem(R.id.sub_menu_move).getSubMenu();
            // 从本地数据库拿出所有分类
            List<RepoGroup> localGroups = repoGroupDao.queryForAll();
            for (RepoGroup repoGroup : localGroups) {
                // 都加到menu_group_move这个组上
                subMenu.add(R.id.menu_group_move, repoGroup.getId(), Menu.NONE, repoGroup.getName());
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        long id = item.getItemId();
        if (id == R.id.delete) {
            //执行删除
            localRepoDao.delete(currentlocalRepo);
            resetData();
            return true;
        }
        //获取分类的id
        int groupId = item.getGroupId();
        //执行移动
        if (groupId==R.id.menu_group_move){
            if (id==R.id.repo_group_no){
                //移动到未分类
                currentlocalRepo.setRepoGroup(null);
            }else {
                //移动到指定的分类（除了未分类）
                currentlocalRepo.setRepoGroup(repoGroupDao.queryForId(id));
            }
            //更新数据
            localRepoDao.creatOrUpdate(currentlocalRepo);
            //重置数据
            resetData();
            return true;
        }

        return super.onContextItemSelected(item);
    }
    //显示popmenu
    @OnClick(R.id.btnFilter)
    public void showPopMenu(View view){
        popupMenu = new PopupMenu(getContext(),view);
        //popmenu的布局
        popupMenu.inflate(R.menu.menu_popup_repo_groups);
        Menu menu = popupMenu.getMenu();
        //分类列表
        List<RepoGroup> repoGroups = repoGroupDao.queryForAll();
        //popmenu添加子菜单
        for (RepoGroup repogroup:repoGroups) {
            menu.add(Menu.NONE,repogroup.getId(),Menu.NONE,repogroup.getName());
        }
        //必须调用show否则无法显示
        popupMenu.show();
        //popmenu的监听
        popupMenu.setOnMenuItemClickListener(this);
    }
 private int repogroupID;
    //popmenu的点击事件
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        tvGroupType.setText(item.getTitle());
        repogroupID = item.getItemId();
        resetData();
        return true;
    }
    //重置数据
    private void resetData() {
        adapter.setData(localRepoDao.queryForAll());
        switch (repogroupID){
            case R.id.repo_group_all:
                adapter.setData(localRepoDao.queryForAll());
                break;
            case R.id.repo_group_no:
                adapter.setData(localRepoDao.queryForNoGroup());
                break;
            default:
                adapter.setData(localRepoDao.queryForGroupId(repogroupID));
                break;
        }
    }
}
