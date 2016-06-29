package com.feicuiedu.gitdroid.splash;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.feicuiedu.gitdroid.MainActivity;
import com.feicuiedu.gitdroid.R;
import com.feicuiedu.gitdroid.Utils.ActivityUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yangdianwen on 16-6-28.
 */
public class SplashActivity extends AppCompatActivity {
    @Bind(R.id.btnLogin)Button btnLogin;
    @Bind(R.id.btnEnter)Button btnEnter;
    ActivityUtils activityUtils=new ActivityUtils(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.btnLogin)
    public void login(){

    }
    @OnClick(R.id.btnEnter)
    public void enter(){
        activityUtils.startActivity(MainActivity.class);

    }
}
