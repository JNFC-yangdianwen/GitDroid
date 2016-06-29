package com.feicuiedu.gitdroid;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.feicuiedu.gitdroid.Utils.ActivityUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
@Bind(R.id.drawerLayout)DrawerLayout drawerLayout;
    @Bind(R.id.navigationView)NavigationView navigationView;
    ActivityUtils activityutils=new ActivityUtils(this);
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
//        drawerLayout.openDrawer(GravityCompat.START);
        //navigationView的item设置监听
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
       switch (item.getItemId()){
           case R.id.github_hot_repo:
              activityutils.showToast(R.string.hot_repo);
           break; case R.id.github_hot_coder:
              activityutils.showToast(R.string.hot_coder);
           break; case R.id.github_trend:
             activityutils.showToast(R.string.trend);
           break; case R.id.arsenal_my_repo:
            activityutils.showToast(R.string.my_repo);
           break; case R.id.arsenal_recommend:
               Toast.makeText(MainActivity.this, "最热门", Toast.LENGTH_SHORT).show();
           break; case R.id.tips_daily:
               Toast.makeText(MainActivity.this, "最热门", Toast.LENGTH_SHORT).show();
           break; case R.id.tips_share:
               Toast.makeText(MainActivity.this, "最热门", Toast.LENGTH_SHORT).show();
           break;
       }
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