package com.meishe.yangquan.utils;

public class Util {

    private static long lastClickTime2;

    /**
     *  是否是快速双击
     * @return 是否是快速双击
     */
    public static boolean isFastDoubleClick() {
        boolean flag = false;
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime2;
        if ( 0 < timeD && timeD < 800) {
            flag = true;
        }
        lastClickTime2 = time;
        return flag;
    }

}
