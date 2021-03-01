package com.meishe.yangquan.manager;

import com.meishe.yangquan.bean.BUShoppingInfo;

/**
 * @Author : lpf
 * @CreateDate : 2021/2/27
 * @Description : 店铺信息管理
 */
public class ShoppingInfoManager {

    private boolean isBaseMessageWriteSuccess;

    private boolean isRealNameWriteSuccess;

    private boolean isApplyShoppingSuccess;

    private boolean isBusinessLicenseWriteSuccess;

    private BUShoppingInfo buShoppingInfo;

    private static final ShoppingInfoManager ourInstance = new ShoppingInfoManager();

    public static ShoppingInfoManager getInstance() {
        return ourInstance;
    }

    private ShoppingInfoManager() {
    }

    public BUShoppingInfo getBuShoppingInfo() {
        return buShoppingInfo;
    }

    public void setBuShoppingInfo(BUShoppingInfo buShoppingInfo) {
        this.buShoppingInfo = buShoppingInfo;
    }



    public boolean isBaseMessageWriteSuccess() {
        return isBaseMessageWriteSuccess;
    }

    public void setBaseMessageWriteSuccess(boolean baseMessageWriteSuccess) {
        isBaseMessageWriteSuccess = baseMessageWriteSuccess;
    }

    public boolean isRealNameWriteSuccess() {
        return isRealNameWriteSuccess;
    }

    public void setRealNameWriteSuccess(boolean realNameWriteSuccess) {
        isRealNameWriteSuccess = realNameWriteSuccess;
    }

    public boolean isApplyShoppingSuccess() {
        return isApplyShoppingSuccess;
    }

    public void setApplyShoppingSuccess(boolean applyShoppingSuccess) {
        isApplyShoppingSuccess = applyShoppingSuccess;
    }

    public boolean isBusinessLicenseWriteSuccess() {
        return isBusinessLicenseWriteSuccess;
    }

    public void setBusinessLicenseWriteSuccess(boolean businessLicenseWriteSuccess) {
        isBusinessLicenseWriteSuccess = businessLicenseWriteSuccess;
    }
}
