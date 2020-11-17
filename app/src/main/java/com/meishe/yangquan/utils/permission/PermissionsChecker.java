package com.meishe.yangquan.utils.permission;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liupanfeng
 * @desc 权限检测器
 * @date 2020/11/13 18:17
 */
public class PermissionsChecker {

    private final Context mContext;

    /**
     * 初始化构造器
     * @param context
     */
    public PermissionsChecker(Context context) {
        mContext = context.getApplicationContext();
    }

    public List<String> checkPermission(List<String> permissions) {
        List<String> newList = new ArrayList<>();
        for (String permission : permissions) {
            if (lacksPermission(permission)){
                newList.add(permission);
            }
        }
        return  newList;
    }


    /**
     * 判断权限集合
     * @param permissions
     * @return
     */
    public boolean lacksPermissions(String... permissions) {
        for (String permission : permissions) {
            if (lacksPermission(permission)){
                return true;
            }
        }
        return false;
    }


    /**
     * 判断权限集合
     * @param permissions
     * @return
     */
    public boolean lacksPermissions(List<String> permissions) {
        for (String permission : permissions) {
            if (lacksPermission(permission)){
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否缺少权限 判断是否缺少这个缺陷
     * @param permission
     * @return
     */
    private boolean lacksPermission(String permission) {
        return ContextCompat.checkSelfPermission(mContext, permission) ==
                PackageManager.PERMISSION_DENIED;
    }

}
