package com.meishe.yangquan.utils;

import com.google.gson.Gson;

public class GetGson {
    private static final GetGson ourInstance = new GetGson();

    public static GetGson getInstance() {
        return ourInstance;
    }

    public Gson mGson;

    private GetGson() {
        mGson=new Gson();
    }
}
