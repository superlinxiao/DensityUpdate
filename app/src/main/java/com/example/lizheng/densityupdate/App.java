package com.example.lizheng.densityupdate;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * @author lizheng
 * create at 2019/1/31
 * description:
 * <p>
 * 修改density后的使用注意点：
 * 1.修改density后，在寻找sw文件夹的时候，仍然按照原始的dp去寻找，不会按照新的最小dp去寻找。
 * <p>
 * 2.修改density后，在xml通过src background加载图片的时候，仍然会去原始的图片文件夹去寻找，
 * 并根据图片在所在文件夹计算出图片的dp，然后将dp*density计算出真正显示在屏幕上的大小。
 * 如果需要设置这个属性，需要将view的宽高用dp固定
 * <p>
 * 3.通过getDrawable的方式获取的资源图片，会按照设定好的density计算dp值，通过setImageResource获取的图片，和在xml直接设置图片的效果一致。
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DensityUtil.setDensity(this, 384f);

        registerActivityLifecycleCallbacks(
                new ActivityLifecycleCallbacks() {
                    @Override
                    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                        DensityUtil.setDefault(activity, false);
                    }

                    @Override
                    public void onActivityStarted(Activity activity) {
                        DensityUtil.setDefault(activity, false);
                    }

                    @Override
                    public void onActivityResumed(Activity activity) {
                    }

                    @Override
                    public void onActivityPaused(Activity activity) {
                    }

                    @Override
                    public void onActivityStopped(Activity activity) {
                    }

                    @Override
                    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                    }

                    @Override
                    public void onActivityDestroyed(Activity activity) {
                    }
                });
    }


}
