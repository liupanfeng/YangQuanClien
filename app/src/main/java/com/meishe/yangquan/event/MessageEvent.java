package com.meishe.yangquan.event;

import com.meishe.yangquan.bean.BaseInfo;

import org.greenrobot.eventbus.EventBus;

/**
 * @Author : lpf
 * @CreateDate : 2021/1/17
 * @Description :
 */
public class MessageEvent {

    /**
     * 常数MESSAGE_TYPE_BASE
     */
    public static final int MESSAGE_TYPE_BASE = 100;

    /**
     * 更细用户信息
     */
    public static final int MESSAGE_TYPE_UPDATE_USER_INFO = MESSAGE_TYPE_BASE + 1;

    /**
     * 更新养殖档案菜单列表
     */
    public static final int MESSAGE_TYPE_UPDATE_BREEDING_ARCHIVING = MESSAGE_TYPE_BASE + 2;
    /**
     * 疫苗总价
     */
    public static final int MESSAGE_TYPE_VACCINE_TOTAL_PRICE = MESSAGE_TYPE_BASE + 3;
    /**
     * 损耗总价
     */
    public static final int MESSAGE_TYPE_LOSS_TOTAL_PRICE = MESSAGE_TYPE_BASE + 4;

    /**
     * 剪羊毛总价
     */
    public static final int MESSAGE_TYPE_CUT_HAIR_TOTAL_PRICE = MESSAGE_TYPE_BASE + 5;

    private int mEventType;

    private BaseInfo mBaseInfo;

    private Object value;

    /**
     * Gets base info.
     * 得到基本信息
     *
     * @return the base info
     */
    public BaseInfo getBaseInfo() {
        return mBaseInfo;
    }

    /**
     * Sets base info.
     * 设置基本信息
     *
     * @param baseInfo the base info
     */
    public void setBaseInfo(BaseInfo baseInfo) {
        this.mBaseInfo = baseInfo;
    }

    /**
     * Gets event type.
     * 获取事件类型
     *
     * @return the event type
     */
    public int getEventType() {
        return mEventType;
    }

    /**
     * Sets event type.
     * 设置事件类型
     *
     * @param eventType the event type
     */
    public MessageEvent setEventType(int eventType) {
        this.mEventType = eventType;
        return this;
    }

    /**
     * Gets obj.
     * 获取目标文件
     *
     * @return the obj
     */
    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * Send event.
     * 发送事件
     *
     * @param baseInfo the base info
     * @param type     the type
     */
    public static void sendEvent(BaseInfo baseInfo, int type) {
        MessageEvent messageEvent = new MessageEvent();
        messageEvent.setEventType(type);
        messageEvent.setBaseInfo(baseInfo);
        EventBus.getDefault().post(messageEvent);
    }


    /**
     * Send event.
     * 发送事件
     *
     * @param type the type
     */
    public static void sendEvent(Object object ,int type) {
        MessageEvent messageEvent = new MessageEvent();
        messageEvent.setEventType(type);
        messageEvent.setValue(object);
        EventBus.getDefault().post(messageEvent);
    }

}
