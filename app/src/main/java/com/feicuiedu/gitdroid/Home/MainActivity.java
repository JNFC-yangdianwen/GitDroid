package com.feicuiedu.gitdroid.Home;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.feicuiedu.gitdroid.Login.LoginActivity;
import com.feicuiedu.gitdroid.R;
import com.feicuiedu.gitdroid.Utils.ActivityUtils;
import com.feicuiedu.gitdroid.repo.HotRepoFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
/***
 * 登陆后的主界面，使用了drawerLayout(抽屉视图)和navigationView(导航视图)作为布局
 */

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
@Bind(R.id.drawerLayout)DrawerLayout drawerLayout;
    @Bind(R.id.navigationView)NavigationView navigationView;
    MenuItem menuItem;
    @Bind(R.id.toolbar)Toolbar toolbar;
    ActivityUtils activityutils=new ActivityUtils(this);
    private HotRepoFragment hotRepoFragment;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
        //当主界面内容改变时
    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        //drawerLayout.openDrawer(GravityCompat.START);
        //设置扩展包的toolbar
        setSupportActionBar(toolbar);
        //actionbar设置系统自带的toggle
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        //drawerLayout添加toogle监听
        drawerLayout.addDrawerListener(toggle);
        //toggle必须同步才能显示
        toggle.syncState();
         btnLogin = ButterKnife.findById(navigationView.getHeaderView(0), R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityutils.startActivity(LoginActivity.class);
            }
        });
        //navigationView的item设置监听
        menuItem=navigationView.getMenu().findItem( R.id.github_hot_repo);
        menuItem.setChecked(true);
        navigationView.setNavigationItemSelectedListener(this);
        FragmentManager manager = getSupportFragmentManager();
        hotRepoFragment = new HotRepoFragment();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.container,hotRepoFragment);
        fragmentTransaction.commit();
    }
  // navigationView的item的监听
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if (menuItem.isChecked()){
            menuItem.setChecked(false);
        }
       switch (item.getItemId()){
           case R.id.github_hot_repo:
              activityutils.showToast(R.string.hot_repo);
           break;
           case R.id.github_hot_coder:
              activityutils.showToast(R.string.hot_coder);
           break;
           case R.id.github_trend:
             activityutils.showToast(R.string.trend);
           break;
           case R.id.arsenal_my_repo:
            activityutils.showToast(R.string.my_repo);
           break;
           case R.id.arsenal_recommend:

           break;
           case R.id.tips_daily:

           break;
           case R.id.tips_share:

           break;
       }//返回true代表被checked
        return true;
    }
      // 当点击了返回按键时
    @Override
    public void onBackPressed() {
        //如果drawerLayout是开着的，则先要关闭drawerLayout
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            //否则直接执行onBackPressed方法
        super.onBackPressed();
    }
    }
}