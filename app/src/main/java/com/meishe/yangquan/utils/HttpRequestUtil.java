package com.meishe.yangquan.utils;

import android.content.Context;

import com.meishe.yangquan.App;
import com.meishe.yangquan.R;
import com.meishe.yangquan.bean.UserInfo;
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

    private static final String TAG = HttpRequestUtil.class.getSimpleName();

    private static final HttpRequestUtil ourInstance = new HttpRequestUtil();


    public OnResponseListener getListener() {
        return listener;
    }

    public void setListener(OnResponseListener listener) {
        this.listener = listener;
    }

    private OnResponseListener listener;
    private static Context mContext;

    public static HttpRequestUtil getInstance(Context context) {
        mContext = context;
        return ourInstance;
    }

    private HttpRequestUtil() {

    }


    public void getUserByToken(String token) {

        HashMap<String, Object> requestParam = new HashMap<>();
        OkHttpManager.getInstance().postRequest(HttpUrl.URL_GET_USER_INFO, new BaseCallBack<UserResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {

            }

            @Override
            protected void onSuccess(Call call, Response response, UserResult result) {
                if (result != null) {
                    UserInfo user = result.getData();
                    if (user != null) {
                        UserManager.getInstance(App.getContext()).setUser(user);
                    }
                }
            }

            @Override
            protected void onResponse(Response response) {

            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {
                if (e instanceof com.google.gson.JsonParseException) {
                    ToastUtil.showToast(mContext, mContext.getString(R.string.data_analysis_error));
                }
            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, requestParam, token);
    }

}
