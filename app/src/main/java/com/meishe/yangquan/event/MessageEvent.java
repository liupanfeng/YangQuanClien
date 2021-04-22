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


    /**
     * 发布商品成功
     */
    public static final int MESSAGE_TYPE_BU_PUBLISH_GOODS_SUCCESS = MESSAGE_TYPE_BASE + 6;

    /**
     * 更细店铺信息
     */
    public static final int MESSAGE_TYPE_UPDATE_SHOPPING_INFO = MESSAGE_TYPE_BASE + 7;
    /**
     * 更新羊吧信息
     */
    public static final int MESSAGE_TYPE_UPDATE_SHEEP_BAR = MESSAGE_TYPE_BASE + 8;

    /**
     * 发起二级评论
     */
    public static final int MESSAGE_TYPE_DO_SECOND_COMMENT = MESSAGE_TYPE_BASE + 9;
    /**
     * 更新商品数量
     */
    public static final int MESSAGE_TYPE_FEED_GOODS_AMOUNT = MESSAGE_TYPE_BASE + 10;

    /**
     * 用户版-订单列表
     */
    public static final int MESSAGE_TYPE_ORDER_LIST = MESSAGE_TYPE_BASE + 11;

    private int mEventType;

    /*这个字段用来放置请求参数，没必要的话不用使用*/
    private int mListType;

    /*这个字段用来放置请求参数，没必要的话不用使用*/
    private int mSubType;

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

    public int getListType() {
        return mListType;
    }

    public MessageEvent setListType(int listType) {
        this.mListType = listType;
        return this;
    }

    public MessageEvent setSubType(int subType) {
        this.mSubType = subType;
        return this;
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
