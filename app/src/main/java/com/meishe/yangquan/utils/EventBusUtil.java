package com.meishe.yangquan.utils;

import com.meishe.yangquan.event.MessageEvent;

import org.greenrobot.eventbus.EventBus;

/**
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/4/21 15:00
 * @Description : 事件传递Util
 */
public class EventBusUtil {

    public static void sendEvent(int type,int listType,int subType){
        EventBus.getDefault().post(new MessageEvent().setEventType(type).setSubType(subType).setListType(listType));
    }

}
