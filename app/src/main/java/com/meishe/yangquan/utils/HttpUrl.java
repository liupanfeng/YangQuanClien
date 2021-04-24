package com.meishe.yangquan.utils;

public class HttpUrl {

    public static final String URL = "http://59.110.142.42:80/";

//    public static final String URL = "http://c5afe621f519.ngrok.io/";

//    public static final String URL = "http://192.168.10.14:8080/";


    public static final String URL_IMAGE = "http://59.110.142.42:80/";


    /*登录*/
    public static final String USER_LOGIN = URL + "app/user/login";

    /**
     * 请求图片验证码
     */
    public static final String USER_GET_IMAGE = URL + "app/verify/code/image";

    /**
     * 请求行情数据
     */
    public static final String HOME_PAGE_GET_QUOTATION = URL + "app/quotation/list";

    /**
     * 请求行情数据
     */
    public static final String HOME_PAGE_GET_QUOTATION_HISTORY = URL + "app/quotation/history";
    /**
     * 最近行情统计
     */
    public static final String HOME_PAGE_GET_NEAR_QUOTATION_HISTORY = URL + "app/quotation/statistics";




    /**
     * 请求市场数据
     */
    public static final String HOME_PAGE_GET_MARKET = URL + "app/market/list";

    /**
     * 请求服务数据
     */
    public static final String HOME_PAGE_GET_SERVICE = URL + "app/service/list";

    /**
     * 请求广告条数据
     */
    public static final String HOME_PAGE_GET_BANNER = URL + "app/home/banner";

    /**
     * 请求行业资讯数据
     */
    public static final String HOME_PAGE_GET_NEWS_LIST = URL + "app/news/list";

    /*行业资讯详情页面*/
    public static final String HOME_PAGE_GET_NEWS_INFO = URL + "app/news/info";


    /*发送短信验证码*/
    public static final String USER_SEND_CODE = URL + "app/verify/code/msg";


    /*获取用户信息*/
    public static final String URL_GET_USER_INFO = URL + "app/user/info";


    /*更新用户信息*/
    public static final String URL_UPDATE_USER = URL + "app/user/update";

    /*注册*/
    public static final String USER_REGISTER = URL + "user/register";

    /*获取开屏广告*/
    public static final String URL_GET_AD = URL + "ad/select";

    /*服务业的滚动列表数据*/
    public static final String URL_SERVICE_MESSAGE = URL + "service_message/select";

    /*服务业的滚动列表数据*/
    public static final String URL_SERVICE_LIST = URL + "server_customer/select";

    /*服务页面资讯数据*/
    public static final String URL_SERVICE_SHEEP_NEWS = URL + "sheep_news/select";

    /*信息页面列表数据*/
    public static final String URL_MESSAGE_LIST = URL + "message/select";

    /*分页的方式获取 信息页面列表数据*/
    public static final String URL_GET_MESSAGE_LIST = URL + "message/get";

    /*完善用户信息*/
    public static final String URL_USER_UPDATE = URL + "user/update";

    /*获取消息中的数据*/
    public static final String URL_MESSAGE_CENTER_LIST = URL + "notification/select";


    /*获取消息中心数据*/
    public static final String URL_MINE_MESSAGE_CENTER = URL + "notification/select";

    /*获取我的商机的数据*/
    public static final String URL_MINE_BUSIJNESS = URL + "business_opportunity/select";

    /*获取评论数据*/
    public static final String URL_COMMENT_LIST = URL + "comment/select";

    /*添加评论数据*/
    public static final String URL_ADD_COMMENT = URL + "comment/add";

    /*添加商机的数据*/
    public static final String URL_MINE_ADD_BUSIJNESS = URL + "business_opportunity/add";

    /*获取版本数据*/
    public static final String URL_MINE_VERSION = URL + "version/select";

    /*上传图片*/
    public static final String URL_PHOTO_UPLOAD = URL + "photo/upload";

    /*Test 上传图片*/
    public static final String TEST_UPLOAD = URL + "fileUpload";


    /*发布消息*/
    public static final String MESSAGE_ADD = URL + "message/add";


    /*发布消息*/
    public static final String MESSAGE_PUBLISH = URL + "message/publish";

    /*token 一直传递不过去 */
    public static final String MESSAGE_TEST = URL + "message/ytest";


    /*push 绑定设备id*/
    public static final String PUSH_UPDATE_DEVICE_ALIAS = URL + "push/updateDeviceAlias";


    /*服务列表点赞*/
    public static final String SERVICE_LIST_ADD_ZAN = URL + "zan/add";


    /*服务列表点赞*/
    public static final String SERVICE_LIST_DELETE_ZAN = URL + "zan/delete";


    ///////////////////////////////////////新的///////////////////////////////////////////////


    /*申请服务*/
    public static final String SHEEP_HOLDER_APPLY_SERVICE = URL + "app/cultural/service/save";

    /*我的业务*/
    public static final String SHEEP_HOLDER_APPLY_MY_BUSINESS = URL + "app/cultural/service/latest";

    /*创建档案*/
    public static final String SHEEP_HOLDER_CREATE_BATCH = URL + "app/cultural/assistant/batch/save";

    /*获取批次列表*/
    public static final String SHEEP_HOLDER_BATCH_LIST = URL + "app/cultural/assistant/batch/list";

    /*羊助手基础信息填写*/
    public static final String SHEEP_HOLDER_BASE_MESSAGE_BATCH_IN = URL + "app/cultural/assistant/batch/in";

    /*羊管家 获取入栏信息 */
    public static final String SHEEP_HOLDER_BASE_MESSAGE_BATCH_IN_INFO = URL + "app/cultural/assistant/batch/in/info";

    /*羊管家 获取入栏信息 */
    public static final String SHEEP_HOLDER_BASE_MESSAGE_BATCH_OUT_INFO = URL + "app/cultural/assistant/batch/out/list";


    /*羊助手出栏信息填写*/
    public static final String SHEEP_HOLDER_BASE_MESSAGE_BATCH_OUT = URL + "app/cultural/assistant/batch/out";


    /*羊管家 养殖助手 饲料分析列表 */
    public static final String SHEEP_HOLDER_FODDER_ANALYSE_LIST = URL + "app/cultural/assistant/batch/fodder/list";

    /*羊管家 养殖助手-新增饲料记录 */
    public static final String SHEEP_HOLDER_FODDER_SAVE = URL + "app/cultural/assistant/batch/fodder/save";

    /*羊管家 养殖助手-上一次饲料分析记录 */
    public static final String SHEEP_HOLDER_FODDER_LATEST = URL + "app/cultural/assistant/batch/fodder/latest";

    /*羊管家 养殖助手-新增其他数据 */
    public static final String SHEEP_HOLDER_FODDER_OTHER_SAVE = URL + "app/cultural/assistant/batch/other/save";

    /*羊管家 养殖助手-其他记录列表 */
    public static final String SHEEP_HOLDER_FODDER_OTHER_LIST = URL + "app/cultural/assistant/batch/other/list";

    /*羊管家 养殖助手-效益分析接口 */
    public static final String SHEEP_HOLDER_BENEFIT_ANALYSIS = URL + "app/cultural/assistant/batch/analyse/info";
    /*羊管家 养殖助手-保存总收入 */
    public static final String SHEEP_HOLDER_INCOMING_SAVE = URL + "app/cultural/assistant/batch/income/save";

    /*羊管家 养殖助手-获取配料分析标准 */
    public static final String SHEEP_HOLDER_FODDER_ANALYSE = URL + "app/cultural/assistant/batch/fodder/analyse";

    /*羊管家 养殖助手-求助 */
    public static final String SHEEP_HOLDER_APPLY_HELP = URL + "app/cultural/assistant/help/apply";


    /*添加服务*/
    public static final String HOME_PAGE_ADD_SERVICE = URL + "app/service/add";
    /*添加市场*/
    public static final String HOME_PAGE_ADD_MARKET = URL + "app/market/add";

    /*上传文件*/
    public static final String HOME_PAGE_COMMON_FILE_UPLOAD = URL + "common/file/upload";

    /*批量上传文件*/
    public static final String HOME_PAGE_COMMON_FILES_UPLOAD = URL + "/common/files/upload";

    /*获取羊吧列表*/
    public static final String SHEEP_BAR_INFO_LIST = URL + "app/bar/post/list";

    /*上传羊吧信息*/
    public static final String SHEEP_BAR_INFO_SAVE = URL + "app/bar/post/save";


    /*获取一级评论*/
    public static final String SHEEP_BAR_COMMENT_LIST_LEVEL1 = URL + "app/bar/comment/list/level1";
    /*获取二级评论*/
    public static final String SHEEP_BAR_COMMENT_LIST_LEVEL2 = URL + "app/bar/comment/list/level2";

    /*赞帖子，点赞评论，收藏帖子，分享帖子*/
    public static final String SHEEP_BAR_INTERACT = URL + "app/bar/interact";

    /*添加评论*/
    public static final String SHEEP_BAR_ADD_COMMENT = URL + "app/bar/comment/save";


    /*我的积分*/
    public static final String SHEEP_MINE_MY_POINTS = URL + "app/user/wealth/info";

    /*我的-关注*/
    public static final String SHEEP_MINE_FOCUS = URL + "app/user/focus";

    /*我的-建议*/
    public static final String SHEEP_MINE_SUGGEST = URL + "app/suggest/list";
    /*我的-发送建议*/
    public static final String SHEEP_MINE_SUGGEST_SEND = URL + "app/suggest/send";

    /*我的-关注-列表*/
    public static final String SHEEP_MINE_FOCUS_LIST = URL + "app/user/focus/list";

    /*我的-粉丝列表*/
    public static final String SHEEP_MINE_FANS_LIST = URL + "app/user/fans/list";

    /*我的-养殖档案列表*/
    public static final String SHEEP_MINE_BREEDING_ARCHIVE = URL + "app/user/cultural/archive/list";


    /*系统消息*/
    public static final String SHEEP_MINE_SYSTEM_LIST = URL + "app/message/system/list";

    /*用户消息*/
    public static final String SHEEP_MINE_USER_LIST = URL + "app/message/user/list";

    /*我的-饲料金总额*/
    public static final String SHEEP_MINE_FEED_GOLD = URL + "app/user/gold/info";
    /*我的-饲料金列表*/
    public static final String SHEEP_MINE_FEED_GOLD_LIST = URL + "app/user/gold/list";
    /*用户版本-店铺信息*/
    public static final String SHEEP_SHOP_LIST = URL + "app/user/shop/list";

    /*（用户）商品列表 */
    public static final String SHEEP_GOODS_LIST = URL + "app/user/goods/list";

    /*（用户）商品信息 */
    public static final String SHEEP_GOODS_INFO = URL + "app/user/goods/info";

    /*（用户）添加到购物车 */
    public static final String SHEEP_SHOPPING_CAR_ADD = URL + "app/user/shoppingCar/add";

    /*（用户）从 购物车移除 */
    public static final String SHEEP_SHOPPING_CAR_REMOVE = URL + "app/user/shoppingCar/remove";

    /*（用户）购物车商品列表 */
    public static final String SHEEP_SHOPPING_CAR_LIST = URL + "app/user/shoppingCar/list";
    /*我的-设置密码*/
    public static final String SHEEP_MINE_SETTING_PASSWORD = URL + "app/user/update/paymentCode";
    /*饲料-提交订单*/
    public static final String SHEEP_FEED_ORDER_COMMIT = URL + "app/user/order/commit";
    /*饲料-订单列表*/
    public static final String SHEEP_FEED_ORDER_LIST = URL + "app/user/order/list";

    /*我的-版本更新*/
    public static final String SHEEP_APP_VERSION_UPDATE = URL + "app/version/latest";

    /*用户驾驶信息是否完善*/
    public static final String SHEEP_APP_DRIVER_INFO = URL + "app/user/driver/info";

    /*用户版-收藏用户列表*/
    public static final String SHEEP_APP_COLLECTION_LIST = URL + "app/user/collection/list";



    /*用户版-收藏-店铺或者商品*/
    public static final String SHEEP_APP_COLLECTION = URL + "app/user/collect";


    /*用户版-订单-支付订单*/
    public static final String SHEEP_APP_USER_ORDER_PAY = URL + "app/user/order/pay";

    /*用户版-订单-取消订单*/
    public static final String SHEEP_APP_USER_ORDER_CANCEL = URL + "app/user/order/cancel";


    /*用户版-订单-确认收货*/
    public static final String SHEEP_APP_USER_ORDER_RECEIVED = URL + "app/user/order/received";


    /*用户版-订单-申请退货*/
    public static final String SHEEP_APP_USER_ORDER_BACHK_GOODS = URL + "app/user/order/backGoods";


    /*用户版-订单-获取最新的收货地址*/
    public static final String SHEEP_APP_USER_ORDER_RECEIVER_LATEST = URL + "app/user/receiverInfo/latest";

    /*用户版-我的-删除养殖档案*/
    public static final String SHEEP_APP_USER_DELETE_CULTURAL_DELETE = URL + "app/user/cultural/delete";

    /*用户版-我的-（用户）订单 取消/退货 进度*/
    public static final String SHEEP_APP_USER_ORDER_PROGRESS = URL + "app/user/order/progress";





    //////////////////////////////////////////商版//////////////////////////////////////////////

    /*入驻的商家*/
    public static final String BU_HOME_SHOPPING_USER_LIST = URL + "app/owner/list";

    /*（商户）保存、更新店铺信息*/
    public static final String BU_HOME_APPLY_SHOPPING_SANE = URL + "app/owner/shop/save";

    /*（商户）获取店铺信息 申请的店铺的数据*/
    public static final String BU_HOME_APPLY_SHOPPING_INFO = URL + "app/owner/shop/info";

    /*（商户）获取商品信息*/
    public static final String BU_HOME_GOODS_INFO = URL + "app/owner/goods/list";
    /*（商户）添加商品*/
    public static final String BU_HOME_GOODS_ADD = URL + "app/owner/goods/save";

    /*（（商户）上 下 架商品 */
    public static final String BU_HOME_GOODS_UN_OR_DOWN = URL + "app/owner/goods/show";

    /*（商户）删除商品 */
    public static final String BU_HOME_DELETE_GOODS = URL + "app/owner/goods/delete";

    /*（商户）订单列表 */
    public static final String BU_HOME_ORDER_LIST = URL + "app/owner/order/list";

    /*（商户）店铺数据  店铺首页数据 */
    public static final String BU_HOME_SHOPPING_DATA = URL + "app/owner/shop/data";

    /*（商户）退货列表 */
    public static final String BU_HOME_BACK_GOODS_LIST = URL + "app/owner/order/backGoods/list";
    /*（商户）订单改价 */
    public static final String BU_HOME_ORDER_CHANGE_PRICE = URL + "/app/owner/order/price/update";

    /*（商户）订单改价 */
    public static final String BU_HOME_ORDER_SEND_GOODS = URL + "app/owner/order/sendGoods";



}
