package com.meishe.yangquan.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckNetwork {

	/** 网络不可用 */
	public static final int NONETWORK = 0;
	/** wifi连接 */
	public static final int WIFI = 1;
	/** 移动网络 */
	public static final int NOWIFI = 2;

	/**
	 * 检验网络连接类型，判断是否是wifi连接
	 * @param context
	 * @return <li>没有网络：Network.NONETWORK;</li> <li>wifi 连接：Network.WIFI;</li> <li>mobile 连接：Network.NOWIFI</li>
	 */
	public static int checkNetWorkType(Context context) {

		if (!checkNetWork(context)) {
			return CheckNetwork.NONETWORK;
		}
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
			return CheckNetwork.WIFI;
		else
			return CheckNetwork.NOWIFI;
	}

	/**
	 * 检测网络是否连接
	 * @param context
	 * @return
	 */
	public static boolean checkNetWork(Context context){
		// 1.获得连接设备管理器
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if(cm == null){
			return false;
		}
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return (ni != null && ni.isAvailable());
	}
}
