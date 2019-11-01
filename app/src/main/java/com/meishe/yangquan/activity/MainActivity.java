package com.meishe.yangquan.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;

import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.ViewpagerAdapter;
import com.meishe.yangquan.fragment.MessageFragment;
import com.meishe.yangquan.fragment.MineFragment;
import com.meishe.yangquan.fragment.ServiceFragment;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG="MainActivity";
    private String url="http://192.168.10.55:8080/YangQuan/servlet/ServletDemo03";
    private List mFragmentList;
    private List mListTitle;
    private ViewPager mViewPager;
    private TableLayout mTabLayout;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext=this;
        initView();
        initData();
    }

    private void initData() {
        mFragmentList = new ArrayList<>();
        mListTitle = new ArrayList<>();
        ServiceFragment serviceFragment = ServiceFragment.newInstance("", "");
        MessageFragment messageFragment = MessageFragment.newInstance("", "");
        MineFragment mineFragment=MineFragment.newInstance("","");
        mFragmentList.add(serviceFragment);
        mFragmentList.add(messageFragment);
        mFragmentList.add(mineFragment);
        mListTitle.add("服务");
        mListTitle.add("消息");
        mListTitle.add("我的");
        mViewPager.setAdapter(new ViewpagerAdapter(getSupportFragmentManager(),0,mContext, mFragmentList, mListTitle));
//        mTabLayout.setupWithViewPager(mViewPager);//此方法就是让tablayout和ViewPager联动
    }

    private void initView() {
        mViewPager =findViewById(R.id.viewpager);
        mTabLayout=findViewById(R.id.tablayout);
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
