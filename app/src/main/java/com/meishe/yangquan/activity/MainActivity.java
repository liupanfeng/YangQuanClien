package com.meishe.yangquan.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.meishe.yangquan.R;
import com.meishe.yangquan.bean.User;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG="MainActivity";
    private String url="http://192.168.10.55:8080/YangQuan/servlet/ServletDemo03";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void getDataAsync() {
//        OkHttpClient client = new OkHttpClient();
//        Request request = new Request.Builder()
//                .url("http://192.168.10.55:8080/YangQuan/ServletTest")
//                .build();
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.d(TAG,"error");
//            }
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if(response.isSuccessful()){//回调的方法执行在子线程。
//                    Log.d(TAG,"response.code()=="+response.code());
//                    Log.d(TAG,"response.message()=="+response.message());
//                    Log.d(TAG,"res=="+response.body().string());
//                }
//            }
//        });

        OkHttpManager.getInstance().getRequest(url, new BaseCallBack<String>() {
            @Override
            protected void OnRequestBefore(Request request) {
                Log.d(TAG,"OnRequestBefore");
            }

            @Override
            protected void onFailure(Call call, IOException e) {
                Log.d(TAG,"onFailure");
            }

            @Override
            protected void onSuccess(Call call, Response response, String user) {
                Log.d(TAG,"response.code()=="+response.code());
                Log.d(TAG,"response.message()=="+response.message());
            }

            @Override
            protected void onResponse(Response response) {
                Log.d(TAG,"onResponse");
            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {
                Log.d(TAG,"onEror");
            }

            @Override
            protected void inProgress(int progress, long total, int id) {
                Log.d(TAG,"inProgress");
            }
        });
    }


    public void request(View view) {

       new Thread(){
           @Override
           public void run() {
               super.run();
               getDataAsync();
           }
       }.start();
    }
}
