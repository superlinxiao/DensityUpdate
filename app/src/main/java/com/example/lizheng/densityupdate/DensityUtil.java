package com.example.lizheng.densityupdate;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.Log;


public class DensityUtil {

    private static float appDensity;
    private static float appScaledDensity;
    private static DisplayMetrics appDisplayMetrics;
    //    public static float overrideAppDensity;
    /**
     * 用来参照的的width
     */
    private static float WIDTH;

    public static void setDensity(@NonNull final Application application, float width) {
        appDisplayMetrics = application.getResources().getDisplayMetrics();
        WIDTH = width;
        //        registerActivityLifecycleCallbacks(application);

        if (appDensity == 0) {
            //初始化的时候赋值
            appDensity = appDisplayMetrics.density;
            appScaledDensity = appDisplayMetrics.scaledDensity;

            //添加字体变化的监听
            application.registerComponentCallbacks(
                    new ComponentCallbacks() {
                        @Override
                        public void onConfigurationChanged(Configuration newConfig) {
                            //字体改变后,将appScaledDensity重新赋值
                            if (newConfig != null && newConfig.fontScale > 0) {
                                appScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                            }
                        }

                        @Override
                        public void onLowMemory() {
                        }
                    });
        }
    }

    public static void setDefault(Activity activity, boolean isCancel) {
        setAppOrientation(activity, isCancel);
    }

    private static void setAppOrientation(Activity activity, boolean cancelMath) {
        DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        if (cancelMath) {
            activityDisplayMetrics.density = appDisplayMetrics.density;
            activityDisplayMetrics.scaledDensity = appDisplayMetrics.scaledDensity;
            activityDisplayMetrics.densityDpi = appDisplayMetrics.densityDpi;
            return;
        }

        float targetDensity = 0;
        try {
            targetDensity =
                    Math.min(appDisplayMetrics.widthPixels, appDisplayMetrics.heightPixels) / WIDTH;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        float targetScaledDensity = targetDensity * (appScaledDensity / appDensity);
        Log.i("densityTest", "targetDensity:" + targetDensity);
        int targetDensityDpi = (int) (160 * targetDensity);
        //        overrideAppDensity = targetDensity;

        /**
         * 最后在这里将修改过后的值赋给系统参数
         *
         * <p>只修改Activity的density值
         */
        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.scaledDensity = targetScaledDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;
    }
}
