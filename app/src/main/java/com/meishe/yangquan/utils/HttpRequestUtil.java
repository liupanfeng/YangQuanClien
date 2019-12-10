package com.meishe.yangquan.utils;

import android.content.Context;
import android.util.Log;

import com.googlecode.openbeans.PropertyDescriptor;
import com.meishe.yangquan.App;
import com.meishe.yangquan.R;
import com.meishe.yangquan.bean.ADOpenScreenResult;
import com.meishe.yangquan.bean.AdOpenScreen;
import com.meishe.yangquan.bean.Message;
import com.meishe.yangquan.bean.MessageResult;
import com.meishe.yangquan.bean.ServerCustomerResult;
import com.meishe.yangquan.bean.ServiceMessageResult;
import com.meishe.yangquan.bean.SheepNewsResult;
import com.meishe.yangquan.bean.User;
import com.meishe.yangquan.bean.UserResult;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.inter.OnResponseListener;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

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
    private static Context mContext;
    public static HttpRequestUtil getInstance(Context context) {
        mContext= context;
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


    public void getUserByToken(String token) {

        HashMap<String, Object> requestParam = new HashMap<>();
        requestParam.put(Constants.token, token);
        OkHttpManager.getInstance().postRequest(HttpUrl.URL_GET_USER, new BaseCallBack<UserResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {

            }

            @Override
            protected void onSuccess(Call call, Response response, UserResult result) {
                if (result != null) {
                    User user=result.getData();
                    if (user!=null){
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
        }, requestParam);
    }

    public void uploadUserPhoto(long userId,String key,String content) {

        HashMap<String, Object> requestParam = new HashMap<>();
        requestParam.put("key", key);
        requestParam.put("conetnt", content);
        OkHttpManager.getInstance().postRequest(HttpUrl.URL_PHOTO_UPLOAD, new BaseCallBack<UserResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {

            }

            @Override
            protected void onSuccess(Call call, Response response, UserResult result) {
                if (result != null) {
                    User user=result.getData();
                    if (user!=null){
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
        }, requestParam);
    }


    public void uploadUserInfo(long userId,String type,String content) {

        HashMap<String, Object> requestParam = new HashMap<>();
        requestParam.put("userId", userId);
        requestParam.put("type", type);
        requestParam.put("conetnt", content);
        OkHttpManager.getInstance().postRequest(HttpUrl.URL_UPDATE_USER, new BaseCallBack<UserResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {

            }

            @Override
            protected void onSuccess(Call call, Response response, UserResult result) {
                if (result != null) {
                    User user=result.getData();
                    if (user!=null){
                        UserManager.getInstance(App.getContext()).setUser(user);
                        if (listener!=null){
                            listener.onSuccess(user);
                        }
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
                if (listener!=null){
                    listener.onError(e);
                }

            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, requestParam);
    }

    /**
     * 获取开屏广告
     */
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

    /**
     * 服务循环消息
     * type 1 ：头部循环消息  2. 咨询消息
     */
    public  void  getServiceMessageFromServer(final int type){

        HashMap<String, Object> requestParam = new HashMap<>();
        OkHttpManager.getInstance().postRequest(HttpUrl.URL_SERVICE_MESSAGE, new BaseCallBack<ServiceMessageResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {

            }

            @Override
            protected void onSuccess(Call call, Response response, ServiceMessageResult result) {
                if (result != null||result.getStatus()==200) {
                    if (listener!=null){
                        listener.onSuccess(type,result);
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
        }, requestParam);
    }


    /**
     * 服务列表消息
     */
    public  void  getServiceListFromServer(int userType){

        HashMap<String, Object> requestParam = new HashMap<>();
        requestParam.put("userType",userType);
        OkHttpManager.getInstance().postRequest(HttpUrl.URL_SERVICE_LIST, new BaseCallBack<ServerCustomerResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {

            }

            @Override
            protected void onSuccess(Call call, Response response, ServerCustomerResult result) {
                if (result != null&&result.getStatus()==200) {
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
                if (e instanceof com.google.gson.JsonParseException) {
                    ToastUtil.showToast(mContext, mContext.getString(R.string.data_analysis_error));
                }
            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, requestParam);
    }


    /**
     * 咨询新闻
     * type :2 表示咨询新闻
     */
    public  void  getServiceNewsFromServer(final int type){

        HashMap<String, Object> requestParam = new HashMap<>();
        OkHttpManager.getInstance().postRequest(HttpUrl.URL_SERVICE_SHEEP_NEWS, new BaseCallBack<SheepNewsResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {

            }

            @Override
            protected void onSuccess(Call call, Response response, SheepNewsResult result) {
                if (result != null||result.getStatus()==200) {
                    if (listener!=null){
                        listener.onSuccess(type,result);
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
        }, requestParam);
    }


    /**
     * 发布消息
     */
    public  void  publishMessage(String token, int sheepType, String content, String iconBase64){

        HashMap<String, Object> requestParam = new HashMap<>();
        requestParam.put("token",token);
        requestParam.put("sheepType",sheepType);
        requestParam.put("content",content);
        requestParam.put("iconBase64",iconBase64);
        OkHttpManager.getInstance().postRequest(HttpUrl.MESSAGE_PUBLISH, new BaseCallBack<MessageResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                if (listener!=null){
                    listener.onError(e);
                }
            }

            @Override
            protected void onSuccess(Call call, Response response, MessageResult result) {
                if (result!=null) {
                    if (result.getStatus()!=200){
                        ToastUtil.showToast(mContext,result.getMsg());
                        return;
                    }
                    if (listener!=null){
                        listener.onSuccess(result.getData());
                    }
                }
            }

            @Override
            protected void onResponse(Response response) {

            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {
                if (listener!=null){
                    listener.onError(e);
                }
            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, requestParam);
    }



    /**
     * 信息列表
     */
    public void getMessageListFromServer(int userType){

        HashMap<String, Object> requestParam = new HashMap<>();
        requestParam.put("userType",userType);
        OkHttpManager.getInstance().postRequest(HttpUrl.URL_MESSAGE_LIST, new BaseCallBack<MessageResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {

            }

            @Override
            protected void onSuccess(Call call, Response response, MessageResult result) {
                if (response != null&&response.code()==200) {
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
                if (listener!=null){
                    listener.onError(e);
                }

            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, requestParam);
    }

    /**
     * 完善个人信息
     */
    public  void  updateUserFromServer(Context context) throws Exception{

        HashMap<String, Object> requestParam = new HashMap<>();
        User user=UserManager.getInstance(context).getUser();
        Field[] fields = user.getClass().getDeclaredFields();
        for (int i = 0, len = fields.length; i < len; i++) {
            PropertyDescriptor pd = new PropertyDescriptor(fields[i].getName(), user.getClass());
            Method getMethod = pd.getReadMethod();     //获得get方法
//            getMethod.invoke(user);    //此处为执行该Object对象的get方法
            String varName = fields[i].getName();
            requestParam.put(varName, getMethod.invoke(user));
//            System.out.println(varName + "---" + getMethod.getName());
        }
        OkHttpManager.getInstance().postRequest(HttpUrl.URL_USER_UPDATE, new BaseCallBack<SheepNewsResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {

            }

            @Override
            protected void onSuccess(Call call, Response response, SheepNewsResult result) {
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


    public void uploadFile(File tempFile, String s) {
        HashMap<String, String> requestParam = new HashMap<>();
        OkHttpManager.getInstance().postUploadSingleImage(HttpUrl.TEST_UPLOAD, new BaseCallBack<String>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {

            }

            @Override
            protected void onSuccess(Call call, Response response, String o) {
                Log.d("lpf","onSuccess");
            }

            @Override
            protected void onResponse(Response response) {

            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {
                Log.d("lpf","onEror");
            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        },tempFile,s,requestParam);
    }
}
