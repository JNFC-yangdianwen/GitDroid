package com.feicuiedu.gitdroid.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.lang.ref.WeakReference;

/**
 * Created by yangdianwen on 16-6-29.
 */
public class ActivityUtils {
    //弱引用，防止该类被不正当使用
    private WeakReference<Activity> activityWeakReference;
    private WeakReference<android.support.v4.app.Fragment> fragmentWeakReference;

    private Toast toast;
    //  activity泛型的构造器
    public ActivityUtils(Activity activity) {
        activityWeakReference = new WeakReference<>(activity);
    }
     //Fragment泛型的构造器
    public ActivityUtils(android.support.v4.app.Fragment fragment){
        fragmentWeakReference = new WeakReference<>(fragment);
    }
    //获取Activity对象的方法
    private Activity getActivity() {
           //如果弱引用activity对象不为空，则返回activity对象
        if (activityWeakReference != null)
            return activityWeakReference.get();
        if (fragmentWeakReference != null) {
            android.support.v4.app.Fragment fragment = fragmentWeakReference.get();
            return fragment == null? null : fragment.getActivity();
        }
        return null;
    }
    //自定义信息 弹吐司对话框
    public void showToast(CharSequence msg){
        Activity activity = getActivity();
        if (activity != null){
            if (toast == null) toast = Toast.makeText(activity, msg, Toast.LENGTH_SHORT);
            toast.setText(msg);
            toast.show();
        }
    }
    //吐司对话框所显示的信息 使用value中的string资源信息
    @SuppressWarnings("SameParameterValue")
    public void showToast(int resId){
        Activity activity = getActivity();
        if (activity != null) {
            String msg = activity.getString(resId);
            showToast(msg);
        }
    }
      //开启跳转的意图
    public void startActivity(Class<? extends Activity> clazz){
        Activity activity = getActivity();
        if (activity == null) return;
        Intent intent = new Intent(activity, clazz);
        activity.startActivity(intent);
    }

    /**
     * Unfortunately Android doesn't have an official API to retrieve the height of
     * StatusBar. This is just a way to hack around, may not work on some devices.
     *
     * @return The height of StatusBar.
     */
    public int getStatusBarHeight() {
        Activity activity = getActivity();
        if (activity == null) return 0;

        Resources resources = getActivity().getResources();
        int result = 0;
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId);
        }
        LogUtils.v("getStatusBarHeight: " + result);
        return result;
    }

    public int getScreenWidth() {
        Activity activity = getActivity();
        if (activity == null) return 0;

        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }

    public int getScreenHeight() {
        Activity activity = getActivity();
        if (activity == null) return 0;
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.heightPixels;
    }

    public void hideSoftKeyboard(){
        Activity activity = getActivity();
        if (activity == null) return;

        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
