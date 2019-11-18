package com.meishe.yangquan.utils;

import android.content.Context;
import android.content.res.Resources;

import com.meishe.yangquan.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class UserType {

    public static HashMap<String,Integer> userType;

    static {
        userType=new HashMap<>();
        userType.put("养殖户",1);
        userType.put("经纪人",2);
        userType.put("售卖饲料",3);
        userType.put("售卖兽药",4);
        userType.put("羊五金",5);
        userType.put("羊粪对",6);
        userType.put("羊车队",7);
        userType.put("剪羊毛",8);
        userType.put("疫苗对",9);
        userType.put("贷款服务",10);
    }

    public static List<String> getUserTypeName(){
        List<String> list=new ArrayList<>();
        Iterator<Map.Entry<String, Integer>> iterator = userType.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, Integer> next = iterator.next();
            list.add(next.getKey());
        }
        return list;
    }

    public static List<Integer> getUserTypeIcon(){
        List<Integer> list=new ArrayList<>();
        list.add(R.mipmap.ic_service_type_yangzhihu);
        list.add(R.mipmap.ic_service_type_jingjiren);
        list.add(R.mipmap.ic_service_type_maisiliao);
        list.add(R.mipmap.ic_service_type_maishouyao);
        list.add(R.mipmap.ic_service_type_yangwujin);
        list.add(R.mipmap.ic_service_type_yangfen);
        list.add(R.mipmap.ic_service_type_chedui);
        list.add(R.mipmap.ic_service_type_jianyangmao);
        list.add(R.mipmap.ic_service_type_yimiao);
        list.add(R.mipmap.ic_service_type_daikuan);
        return list;
    }

}
