package com.feicuiedu.gitdroid;

import android.app.Application;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/**
 * Created by yangdianwen on 16-7-6.
 * 这是一个加载图片的类，使用第三方的Universal_image_loader
 */
public class GitDroidApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //配置信息
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.ic_avatar)
                .showImageOnLoading(R.drawable.ic_avatar)
                .showImageOnFail(R.drawable.ic_avatar)
                .displayer(new RoundedBitmapDisplayer(getResources().getDimensionPixelOffset(R.dimen.dp_10)))
                .cacheInMemory(true) // 打开内存缓存
                .cacheOnDisk(true) // 打开硬盘缓存
                .resetViewBeforeLoading(true)// 在ImageView加载前清除它上面之前的图片
                .build();
        //配置信息，内存大小，5m
        ImageLoaderConfiguration configuration=new ImageLoaderConfiguration
                .Builder(this).memoryCacheSize(5*1024*1024)
                .defaultDisplayImageOptions(options)
                .build();
        //初始化imageloader
        ImageLoader.getInstance().init(configuration);
    }
}
