package com.meishe.yangquan.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * All rights Reserved, Designed By www.meishesdk.com
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/3/19 14:20
 * @Description :
 * @Copyright : www.meishesdk.com Inc. All rights reserved.
 */
public class LocationUtil {


    /**
     * 启动高德地图，从我的位置到addressName
     *
     * @param addressName 终点
     */
    public static void openGaode(Context context,String addressName) {
        try {
            Intent intent = Intent.getIntent("androidamap://route?sourceApplication=softname&sname=我的位置&dname=" +
                    addressName + "&dev=0&m=0&t=1");
            if (isAvilible(context, "com.autonavi.minimap")) {
                context.startActivity(intent);
            } else {
                Toast.makeText(context, "高德地图未安装", Toast.LENGTH_SHORT).show();
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * 启动百度地图，从我的位置到addressName
     *
     * @param addressName 终点
     */
    public static void openBaidu(Context context,String addressName) {
        try {
            Intent intent = Intent.getIntent("intent://map/direction?origin=我的位置&destination=" + addressName +
                    "&mode=driving&src=yourCompanyName|yourAppName#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
            if (isAvilible(context, "com.baidu.BaiduMap")) {
                context.startActivity(intent);
            } else {
                Toast.makeText(context, "百度地图未安装", Toast.LENGTH_SHORT).show();
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }


    /**
     * 打开腾讯地图（公交出行，起点位置使用地图当前位置）
     *
     * 公交：type=bus，policy有以下取值
     * 0：较快捷 、 1：少换乘 、 2：少步行 、 3：不坐地铁
     * 驾车：type=drive，policy有以下取值
     * 0：较快捷 、 1：无高速 、 2：距离短
     * policy的取值缺省为0
     *
     * @param dlat  终点纬度
     * @param dlon  终点经度
     * @param dname 终点名称
     */
    public static void openTencent(Context context,double dlat, double dlon, String dname) {
        if (isAvilible(context, "com.tencent.map")) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("qqmap://map/routeplan?type=bus&from=我的位置&fromcoord=0,0"
                    + "&to=" + dname
                    + "&tocoord=" + dlat + "," + dlon
                    + "&policy=1&referer=myapp"));
            context.startActivity(intent);
        } else {
            ToastUtil.showToast("腾讯地图未安装");
        }
    }

    /**
     * 启动百度地图，从我的位置到指定经纬度位置
     *
     * @param lat 纬度
     * @param lng 经度
     */
    public void go2Baidu(Context context,double lat, double lng) {
        String latlng = lat + "," + lng;
        try {
            Uri uri = Uri.parse("baidumap://map/direction?destination=latlng:" + "目的地lat" + "," + "目的地lng" + "|name:" + "目的地名称" + "&mode=driving");
            Intent intent = Intent.getIntent("intent://map/direction?origin=我的位置&destination=" + latlng + "&mode=driving&src=yourCompanyName|yourAppName#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
            if (isAvilible(context, "com.baidu.BaiduMap")) {
                context.startActivity(intent);
            } else {
                Toast.makeText(context, "百度地图未安装", Toast.LENGTH_SHORT).show();
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    /**
     * 检查手机上是否安装了指定的packageName的软件
     *
     * @param context
     * @param packageName 应用包名
     * @return
     */
    public static boolean isAvilible(Context context, String packageName) {
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名
        return packageNames.contains(packageName);
    }

}

