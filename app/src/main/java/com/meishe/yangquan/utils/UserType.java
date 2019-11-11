package com.meishe.yangquan.utils;

import java.util.HashMap;

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

}
