package com.meishe.yangquan.activity;

import android.content.Intent;
import android.os.Bundle;

import com.meishe.yangquan.utils.permission.PermissionsActivity;
import com.meishe.yangquan.utils.permission.PermissionsChecker;

import java.util.List;

/**
 * @author liupanfeng
 * @desc
 * @date 2020/11/17 10:34
 */
public abstract class BasePermissionActivity extends BaseActivity{

    static final int REQUEST_CODE = 110;
    /**
     * 权限列表
     */
    List<String> permissionList;
    /**
     * 权限检测器
     */
    private PermissionsChecker mPermissionsChecker;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        permissionList = initPermissions();
        super.onCreate(saveInstanceState);
    }

    /**
     * 获取activity需要的权限列表
     *
     * @return 权限列表
     */
    protected abstract List<String> initPermissions();


    /**
     * 获取权限后
     */
    protected abstract void hasPermission();

    /**
     * 没有允许权限
     */
    protected abstract void nonePermission();

    /**
     * 用户选择了不再提示
     */
    protected abstract void noPromptPermission();


    public boolean hasAllPermission() {
        if (mPermissionsChecker == null) {
            mPermissionsChecker = new PermissionsChecker(this);
        }
        return !mPermissionsChecker.lacksPermissions(permissionList);
    }

    public void checkPermissions() {
        if (mPermissionsChecker == null) {
            mPermissionsChecker = new PermissionsChecker(this);
        }
        permissionList = mPermissionsChecker.checkPermission(permissionList);
        String[] permissions = new String[permissionList.size()];
        permissionList.toArray(permissions);
        if (!permissionList.isEmpty()) {
            startPermissionsActivity(REQUEST_CODE, permissions);
        }
    }

    private void startPermissionsActivity(int code, String... permission) {
        PermissionsActivity.startActivityForResult(this, code, permission);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case PermissionsActivity.PERMISSIONS_DENIED:
                nonePermission();
                break;
            case PermissionsActivity.PERMISSIONS_GRANTED:
                hasPermission();
                break;
            case PermissionsActivity.PERMISSIONS_No_PROMPT:
                noPromptPermission();
                break;
        }
    }



}
