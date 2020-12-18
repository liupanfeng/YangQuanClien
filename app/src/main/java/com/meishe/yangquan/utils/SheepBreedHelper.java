package com.meishe.yangquan.utils;

import android.content.Context;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liupanfeng
 * @desc
 * @date 2020/12/18 15:30
 */
public class SheepBreedHelper {

    private Context mContext;

    private static  SheepBreedHelper ourInstance ;
    private SharedPreferencesUtil sharedPreferencesUtil ;
    public SheepBreedHelper(Context context){
        this.mContext=context;
        sharedPreferencesUtil= SharedPreferencesUtil.getInstance(mContext);
    }
    public static SheepBreedHelper getInstance(Context context) {
        if (ourInstance==null){
            ourInstance=new SheepBreedHelper(context);
        }
        return ourInstance;
    }


    public List<String> getTitleList() {
        String titleList = sharedPreferencesUtil.getString("title_list");
        Gson gson = GetGson.getInstance().mGson;
        List list = gson.fromJson(titleList, List.class);
        return list;
    }

    public void setTitleList(String title) {

        String titleList = sharedPreferencesUtil.getString("title_list");
        Gson gson = GetGson.getInstance().mGson;
        List list = gson.fromJson(titleList, List.class);
        if (list==null){
            list=new ArrayList();
        }
        list.add(title);

        String jsonStr = gson.toJson(list);
        sharedPreferencesUtil.putString("title_list",jsonStr);
    }

}
