package com.meishe.yangquan.utils;

import android.util.Log;

import com.meishe.yangquan.bean.ADOpenScreenResult;
import com.meishe.yangquan.bean.AdOpenScreen;
import com.meishe.yangquan.bean.User;
import com.meishe.yangquan.bean.UserResult;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.inter.OnResponseListener;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

public class HttpRequestUtil {

    private static final String TAG=HttpRequestUtil.class.getSimpleName();

    private static final HttpRequestUtil ourInstance = new HttpRequestUtil();

    public OnResponseListener getListener() {
        return listener;
    }

    public void setListener(OnResponseListener listener) {
        this.listener = listener;
    }

    private  OnResponseListener listener;

    public static HttpRequestUtil getInstance() {
        return ourInstance;
    }

    private HttpRequestUtil() {

    }



    private void registerFromServer(String phoneNumber,int mUserType) {

        HashMap<String, Object> requestParam = new HashMap<>();
        requestParam.put(Constants.phoneNumber, phoneNumber);
        requestParam.put(Constants.userType, mUserType);
        OkHttpManager.getInstance().postRequest(HttpUrl.USER_REGISTER, new BaseCallBack<UserResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {

            }

            @Override
            protected void onSuccess(Call call, Response response, UserResult result) {
                if (result != null) {
                    User user = result.getData();
//                    String nickName=user.getNickname();
                    Log.e(TAG, "注册成功");
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
        }, requestParam);
    }


    public void getADFromServer(){
        HashMap<String, Object> requestParam = new HashMap<>();
        OkHttpManager.getInstance().postRequest(HttpUrl.URL_GET_AD, new BaseCallBack<ADOpenScreenResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {

            }

            @Override
            protected void onSuccess(Call call, Response response, ADOpenScreenResult result) {
                if (result != null||result.getStatus()==200) {
                    if (listener!=null){
                        listener.onSuccess(result);
                    }
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
        }, requestParam);
    }


}
