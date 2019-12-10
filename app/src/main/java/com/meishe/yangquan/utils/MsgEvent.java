package com.meishe.yangquan.utils;

public class MsgEvent {

    private String eventType; //区分事件的类型
    private String eventMessage;  //事件的实体类

    public MsgEvent(String type, String message){
        eventType = type;
        eventMessage = message;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventMessage() {
        return eventMessage;
    }

    public void setEventMessage(String eventMessage) {
        this.eventMessage = eventMessage;
    }
}
