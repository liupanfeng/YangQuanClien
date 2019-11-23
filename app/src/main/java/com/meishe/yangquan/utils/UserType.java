package com.meishe.yangquan.utils;


import com.meishe.yangquan.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserType {

    public static HashMap<String,Integer> userType;

    static {
        userType=new HashMap<>();
        userType.put("养殖户",1);
        userType.put("经纪人",2);
        userType.put("售饲料",3);
        userType.put("售兽药",4);
        userType.put("羊五金",5);
        userType.put("羊粪队",6);
        userType.put("羊车服务",7);
        userType.put("剪羊毛",8);
        userType.put("疫苗服务",9);
        userType.put("幼崽经纪人",10);
    }

    public static List<String> getServiceTypeName(){
        List<String> list=new ArrayList<>();
        list.add("经纪人");
        list.add("售饲料");
        list.add("售兽药");
        list.add("羊五金");
        list.add("羊粪队");
        list.add("羊车服务");
        list.add("剪羊毛");
        list.add("疫苗服务");
        list.add("羊崽儿");
        return list;
    }


    public static List<String> getMessageTypeName(){
        List<String> list=new ArrayList<>();
        list.add("卖羊");
        list.add("买羊");
        list.add("售饲料");
        list.add("售兽药");
        list.add("羊五金");
        list.add("羊粪队");
        list.add("羊车队");
        list.add("剪羊毛");
        list.add("疫苗");
        return list;
    }

    public static List<String> getUserTypeName(){
        List<String> list=new ArrayList<>();
        list.add("经纪人");
        list.add("售饲料");
        list.add("售兽药");
        list.add("羊五金");
        list.add("羊粪队");
        list.add("羊车服务");
        list.add("剪羊毛");
        list.add("疫苗服务");
        list.add("幼崽经纪人");
        return list;
    }

    public static List<Integer> getUserTypeIcon(){
        List<Integer> list=new ArrayList<>();
//        list.add(R.mipmap.ic_service_type_yangzhihu);
        list.add(R.mipmap.ic_service_type_jingjiren);
        list.add(R.mipmap.ic_service_type_maisiliao);
        list.add(R.mipmap.ic_service_type_maishouyao);
        list.add(R.mipmap.ic_service_type_yangwujin);
        list.add(R.mipmap.ic_service_type_yangfen);
        list.add(R.mipmap.ic_service_type_chedui);
        list.add(R.mipmap.ic_service_type_jianyangmao);
        list.add(R.mipmap.ic_service_type_yimiao);
        list.add(R.mipmap.ic_service_type_youzaijingjiren);
        return list;
    }

}
