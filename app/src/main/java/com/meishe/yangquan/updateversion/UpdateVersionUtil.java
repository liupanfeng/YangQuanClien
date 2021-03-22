package com.meishe.yangquan.updateversion;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;


import com.meishe.yangquan.bean.VersionInfo;
import com.meishe.yangquan.bean.VersionInfoResult;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.UserManager;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;


public class UpdateVersionUtil{

	private static final String TAG = "UpdateVersionUtil";

	public interface UpdateListener{
		void onUpdateReturned(int updateStatus, VersionInfo versionInfo);
	}

	public static void checkVersion(final Context context,final UpdateListener updateListener){
		HashMap<String, Object> param = new HashMap<>();
		String url = HttpUrl.SHEEP_APP_VERSION_UPDATE;
		String token = UserManager.getInstance(context).getToken();
		if (TextUtils.isEmpty(token)){
			return;
		}

		OkHttpManager.getInstance().postRequest(url, new BaseCallBack<VersionInfoResult>() {
			@Override
			protected void OnRequestBefore(Request request) {

			}

			@Override
			protected void onFailure(Call call, IOException e) {
			}

			@Override
			protected void onSuccess(Call call, Response response, VersionInfoResult result) {
				if (result.getCode() != 1) {
					return;
				}
				VersionInfo data = result.getData();
				if (data == null ) {
					return;
				}
				String clientVersionCode = ApkUtils.getVersionName(context);
				String serverVersionCode = data.getVersion();

				int ret = 0;
				try {
					ret = compareVersion(serverVersionCode, clientVersionCode);
				} catch (Exception e) {
					Log.e(TAG,"fail to compareVersion" ,e);
				}

				if( ret > 0){
					int i = NetworkUtil.checkedNetWorkType(context);
					if(i == NetworkUtil.NOWIFI){
						updateListener.onUpdateReturned(UpdateStatus.NOWIFI, data);
					}else if(i == NetworkUtil.WIFI){
						updateListener.onUpdateReturned(UpdateStatus.YES, data);
					}
				}else{
					updateListener.onUpdateReturned(UpdateStatus.NO,null);
				}
			}

			@Override
			protected void onResponse(Response response) {

			}

			@Override
			protected void onEror(Call call, int statusCode, Exception e) {

			}

			@Override
			protected void inProgress(int progress, long total, int id) {

			}
		}, param, token);


//		RequestUtils.getInstance().postAsyn(url, new RequestUtils.DataResultCallback<UpdateVersionResult>() {
//
//			@Override
//			public void onError(Request request, Exception e) {
//				Log.d(TAG, "onError: ");
//			}
//
//			@Override
//			public void onResponse(UpdateVersionResult response) {
//				if(response.getCode() == 1){
//					VersionInfo info = response.getResult().getData();
//
//					String clientVersionCode = ApkUtils.getVersionName(context);
//					String serverVersionCode = info.getAppVersion();
//
//					int ret = 0;
//					try {
//						ret = compareVersion(serverVersionCode, clientVersionCode);
//					} catch (Exception e) {
//						Log.e(TAG,"fail to compareVersion" ,e);
//					}
//
//					if( ret > 0){
//						int i = NetworkUtil.checkedNetWorkType(context);
//						if(i == NetworkUtil.NOWIFI){
//							updateListener.onUpdateReturned(UpdateStatus.NOWIFI, info);
//						}else if(i == NetworkUtil.WIFI){
//							updateListener.onUpdateReturned(UpdateStatus.YES, info);
//						}
//					}else{
//						updateListener.onUpdateReturned(UpdateStatus.NO,null);
//					}
//				}
//			}
//		}, specialParams);

	}


	public static void showDialog(final Context context,final VersionInfo versionInfo){
		if (context == null){
			return;
		}
		UpdateVersionDialog dialog = new UpdateVersionDialog(context);
		dialog.setTitle(versionInfo.getVersion());
		dialog.setDesc(versionInfo.getTips());
		dialog.setButtonClickListener(new UpdateVersionDialog.ButtonClickListener() {
			@Override
			public void onCancelBtnClicked() {

			}

			@Override
			public void onUpdateBtnClicked() {
				String fileUrl = versionInfo.getFileUrl();
				if (TextUtils.isEmpty(fileUrl)){
					return;
				}
				File file=new File(fileUrl);
				String name = file.getName();
				Intent intent = new Intent(context, UpdateVersionService.class);
				intent.putExtra("downloadUrl", fileUrl);
				intent.putExtra("apkName", name);
				context.startService(intent);
			}
		});
		dialog.show();
	}

	public static void collapseStatusBar(Context context) {
		try{
			@SuppressLint("WrongConstant")
			Object statusBarManager = context.getSystemService("statusbar");
			Method collapse;
			if (Build.VERSION.SDK_INT <= 16){
				collapse = statusBarManager.getClass().getMethod("collapse");
			}else{
				collapse = statusBarManager.getClass().getMethod("collapsePanels");
			}
			collapse.invoke(statusBarManager);
		}catch (Exception localException){
			Log.e(TAG,"fail to collapseStatusBar" ,localException);
		}
	}

	/**
	 * 比较版本号的大小,前者大则返回一个正数,后者大返回一个负数,相等则返回0
	 规则是按日期订的例如：2.10.15
	 * @param version1
	 * @param version2
	 * @return
	 */
	public static int compareVersion(String version1, String version2) throws Exception {

		if (version1 == null || version2 == null) {
			throw new Exception("compareVersion error:illegal params.");
		}
		String[] versionArray1 = version1.split("\\.");//注意此处为正则匹配，不能用"."；
		for(int i = 0 ; i<versionArray1.length ; i++){ //如果位数只有一位则自动补零（防止出现一个是04，一个是5 直接以长度比较）
			if(versionArray1[i].length() == 1){
				versionArray1[i] = "0" + versionArray1[i];
			}
		}
		String[] versionArray2 = version2.split("\\.");
		for(int i = 0 ; i<versionArray2.length ; i++){//如果位数只有一位则自动补零
			if(versionArray2[i].length() == 1){
				versionArray2[i] = "0" + versionArray2[i];
			}
		}
		int idx = 0;
		int minLength = Math.min(versionArray1.length, versionArray2.length);//取最小长度值
		int diff = 0;
		while (idx < minLength
				&& (diff = versionArray1[idx].length() - versionArray2[idx].length()) == 0//先比较长度
				&& (diff = versionArray1[idx].compareTo(versionArray2[idx])) == 0) {//再比较字符
			++idx;
		}
		//如果已经分出大小，则直接返回，如果未分出大小，则再比较位数，有子版本的为大；
		return (diff != 0) ? diff : versionArray1.length - versionArray2.length;
	}
}
