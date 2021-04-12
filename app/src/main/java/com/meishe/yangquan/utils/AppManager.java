package com.meishe.yangquan.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.Stack;

import static android.app.Activity.RESULT_OK;


public class AppManager {

//    private static List<BaseFragment>  fragments = new ArrayList<>();
    private static Stack<Activity> activityStack = new Stack<>();
    private volatile static AppManager instance = new AppManager();

    private static Stack<Activity> activityStackForEdit = new Stack<>();

    private static Stack<Activity> activityStackForLocation = new Stack<>();

    private AppManager() {
    }

    /**
     * 单一实例
     */
    public static AppManager getInstance() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        activityStack.add(activity);
    }

//    public void addFragment(BaseFragment fragment){
//        fragments.add(fragment);
//    }
//
//    public List<BaseFragment> getFragments(){
//        return fragments;
//    }
//
//    public void removeFragment(BaseFragment fragment){
//        if (fragment!=null && fragments!=null){
//            fragments.remove(fragment);
//        }
//    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        return activityStack.lastElement();
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        if(activityStack.empty())
            return;

        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }


    /**
     * 待结果回调的activity
     *
     * @param intent
     * @param activity
     * @param bundle
     */
    public void finishActivityByCallBack(Intent intent, Activity activity, Bundle bundle) {
        intent.putExtras(bundle);
        activity.setResult(RESULT_OK, intent);
        AppManager.getInstance().finishActivity(activity);
    }


    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    public int activietySizes() {
        if (null != activityStack) {
            return activityStack.size();
        }
        return 0;
    }


    /**
     * 用于跳转
     *
     * @param activity
     * @param cls
     * @param bundle
     */
    public void jumpActivity(Activity activity, Class<? extends Activity> cls, Bundle bundle) {
        Intent intent = new Intent(activity, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        activity.startActivity(intent);
    }

    /**
     * 用于跳转
     *
     * @param activity
     * @param cls
     * @param bundle
     */
    public void jumpActivity(Context activity, Class<? extends Activity> cls, Bundle bundle) {
        Intent intent = new Intent(activity, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        activity.startActivity(intent);
    }
    /**
     * 用于跳转
     *  @param activity
     * @param cls
     */
    public void jumpActivity(Context activity, Class<? extends Activity> cls) {
        Intent intent = new Intent(activity, cls);
        activity.startActivity(intent);
    }

    /**
     * 用于跳转
     *  @param activity
     * @param cls
     */
    public void jumpActivity(Context activity, Class<? extends Activity> cls,int flag) {
        Intent intent = new Intent(activity, cls);
        intent.setFlags(flag);
        activity.startActivity(intent);
    }

    public void jumpActivity(Activity activity, Class<? extends Activity> cls) {
        jumpActivity(activity,cls,null);
    }

    /**
     * 待结果回调的跳转
     *
     * @param activity
     * @param cls
     * @param bundle
     * @param requstcode
     */
    public void jumpActivityForResult(Activity activity, Class<? extends Activity> cls, Bundle bundle, int requstcode) {
        Intent intent = new Intent(activity, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        activity.startActivityForResult(intent, requstcode);
    }

    public void addEditActivity(Activity activity) {
        activityStackForEdit.add(activity);
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishEditActivity() {
        if(activityStackForEdit.empty())
            return;

        Activity activity = activityStackForEdit.lastElement();
        if(activity != null){
            activityStackForEdit.remove(activity);
            activity.finish();
        }
    }

    public void finishAllEditActivity() {
        for (int i = 0, size = activityStackForEdit.size(); i < size; i++) {
            if (null != activityStackForEdit.get(i)) {
                activityStackForEdit.get(i).finish();
            }
        }
        activityStackForEdit.clear();
    }

    public void addLocationActivity(Activity activity){
        activityStackForLocation.add(activity);
    }

    public void finishAllLocationActivity() {
        for (int i = 0, size = activityStackForLocation.size(); i < size; i++) {
            if (null != activityStackForLocation.get(i)) {
                activityStackForLocation.get(i).finish();
            }
        }
        activityStackForLocation.clear();
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishLastLocationActivity() {
        if(activityStackForLocation.empty())
            return;

        Activity activity = activityStackForLocation.lastElement();
        finishLocationActivity(activity);
    }

    public void finishLocationActivity(Activity activity) {
        if (activity != null) {
            activityStackForLocation.remove(activity);
            activity.finish();
        }
    }

}