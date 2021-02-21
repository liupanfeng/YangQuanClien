package com.meishe.yangquan.utils;

public class HttpUrl {

    public static final String URL = "http://59.110.142.42:80/";

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


}
