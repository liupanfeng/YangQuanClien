package com.meishe.yangquan.helper;

import android.content.Intent;
import android.text.TextUtils;

import com.meishe.yangquan.App;
import com.meishe.yangquan.activity.LoginActivity;
import com.meishe.yangquan.bean.BUGoodsRefundInfo;
import com.meishe.yangquan.bean.BUGoodsRefundInfoResult;
import com.meishe.yangquan.bean.BUGoodsRefundListInfo;
import com.meishe.yangquan.bean.BUManagerOrderInfo;
import com.meishe.yangquan.bean.BUManagerOrderInfoResult;
import com.meishe.yangquan.bean.BUOrderInfo;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.FeedGoodsInfo;
import com.meishe.yangquan.bean.FeedGoodsInfoListResult;
import com.meishe.yangquan.bean.FeedShoppingInfo;
import com.meishe.yangquan.bean.FeedShoppingInfoResult;
import com.meishe.yangquan.bean.MarketInfo;
import com.meishe.yangquan.bean.MarketResult;
import com.meishe.yangquan.bean.MineBreedingArchivesInfo;
import com.meishe.yangquan.bean.MineBreedingArchivesInfoResult;
import com.meishe.yangquan.bean.MineCollectionInfo;
import com.meishe.yangquan.bean.MineCollectionInfoResult;
import com.meishe.yangquan.bean.MineFeedGoldInfo;
import com.meishe.yangquan.bean.MineFeedGoldInfoResult;
import com.meishe.yangquan.bean.MineMyFansInfo;
import com.meishe.yangquan.bean.MineMyFansInfoResult;
import com.meishe.yangquan.bean.MineMyFocusInfo;
import com.meishe.yangquan.bean.MineMyFocusInfoResult;
import com.meishe.yangquan.bean.MineOrderInfo;
import com.meishe.yangquan.bean.MineOrderInfoResult;
import com.meishe.yangquan.bean.MineUserMessageInfo;
import com.meishe.yangquan.bean.MineUserMessageInfoResult;
import com.meishe.yangquan.bean.QuotationInfo;
import com.meishe.yangquan.bean.QuotationResult;
import com.meishe.yangquan.bean.ServerResult;
import com.meishe.yangquan.bean.ServiceInfo;
import com.meishe.yangquan.bean.ServiceResult;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.utils.CommonUtils;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.UserManager;
import com.meishe.yangquan.utils.Util;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @Author : lpf
 * @CreateDate : 2021/3/27
 * @Description : 数据请求单例
 */
public class DataHelper {
    private static final DataHelper ourInstance = new DataHelper();

    public static DataHelper getInstance() {
        return ourInstance;
    }

    private DataHelper() {
    }


    /**
     * 获取订单数据
     */
    public void getOrderData(final List<BaseInfo> list, int type, final int pageSize, final int pageNumber, boolean isLoadFinish, final boolean isLoadMore) {
        if (!isLoadFinish) {
            return;
        }
        String token = getToken();
        if (TextUtils.isEmpty(token)) {
            return;
        }

        HashMap<String, Object> requestParam = new HashMap<>();
        requestParam.put("listType",type);
        requestParam.put("pageNum",pageNumber);
        requestParam.put("pageSize",pageSize);

        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_FEED_ORDER_LIST, new BaseCallBack<MineOrderInfoResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                  if (mOnCallBackListener!=null){
                      mOnCallBackListener.onFailure(e);
                  }
            }

            @Override
            protected void onSuccess(Call call, Response response, MineOrderInfoResult result) {
                if (result != null && result.getCode() == 1) {
                    List<MineOrderInfo> datas = result.getData();
                    commonResponse(datas, list, isLoadMore, pageSize, pageNumber);
                } else {
                    ToastUtil.showToast(App.getContext(), result.getMsg());
                }
            }

            @Override
            protected void onResponse(Response response) {

            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {
                if (mOnCallBackListener!=null){
                    mOnCallBackListener.onError(e);
                }
            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, requestParam, token);

    }



    /**
     * 用户版-我的-我的消息
     */
    public void getMyMessageData(final List<BaseInfo> list, int type, final int pageSize, final int pageNumber, boolean isLoadFinish, final boolean isLoadMore) {
        if (!isLoadFinish) {
            return;
        }
        String token = getToken();
        if (TextUtils.isEmpty(token)) {
            return;
        }
        HashMap<String, Object> requestParam = new HashMap<>();
        requestParam.put("pageNum",pageNumber);
        requestParam.put("pageSize",pageSize);
        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_MINE_USER_LIST, new BaseCallBack<MineUserMessageInfoResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                if (mOnCallBackListener!=null){
                    mOnCallBackListener.onFailure(e);
                }
            }

            @Override
            protected void onSuccess(Call call, Response response, MineUserMessageInfoResult result) {
                if (result != null && result.getCode() == 1) {
                    List<MineUserMessageInfo> datas = result.getData();
                    commonResponse( datas, list, isLoadMore, pageSize, pageNumber);
                } else {
                    ToastUtil.showToast(App.getContext(), result.getMsg());
                }
            }

            @Override
            protected void onResponse(Response response) {

            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {
                if (mOnCallBackListener!=null){
                    mOnCallBackListener.onError(e);
                }
            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, requestParam, token);
    }


    /**
     * 获取饲料金数据列表
     * 目前双端用的一个饲料金页面
     * type 这个类型表示收入还是支出
     */
    public void getFeedGoldDataFromServer(final List<BaseInfo> list, int type,
                                              final int pageSize, final int pageNumber,
                                              boolean isLoadFinish, final boolean isLoadMore) {
        String token = getToken();
        if (TextUtils.isEmpty(token)) {
            return;
        }
        HashMap<String, Object> param = new HashMap<>();
        param.put("pageNum", pageNumber);
        param.put("pageSize", pageSize);
        if (type== Constants.TYPE_FEED_GOLD_IN_TYPE){
            param.put("type", "in");
        }else if (type==Constants.TYPE_FEED_GOLD_OUT_TYPE){
            param.put("type", "out");
        }

        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_MINE_FEED_GOLD_LIST, new BaseCallBack<MineFeedGoldInfoResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                if (mOnCallBackListener!=null){
                    mOnCallBackListener.onFailure(e);
                }
            }

            @Override
            protected void onSuccess(Call call, Response response, MineFeedGoldInfoResult result) {
                if (result != null && result.getCode() == 1) {
                    List< MineFeedGoldInfo> datas = result.getData();
                    commonResponse(datas, list, isLoadMore, pageSize, pageNumber);
                } else {
                    ToastUtil.showToast(App.getContext(), result.getMsg());
                }
            }

            @Override
            protected void onResponse(Response response) {

            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {
                if (mOnCallBackListener!=null){
                    mOnCallBackListener.onError(e);
                }
            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, param, token);
    }

    public void getBreedingArchivesData(final List<BaseInfo> list, int type,
                                         final int pageSize, final int pageNumber,
                                         boolean isLoadFinish, final boolean isLoadMore) {
        String token = getToken();
        if (TextUtils.isEmpty(token)) {
            return;
        }
        HashMap<String, Object> param = new HashMap<>();
        param.put("pageNum", pageNumber);
        param.put("pageSize", pageSize);

        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_MINE_BREEDING_ARCHIVE, new BaseCallBack<MineBreedingArchivesInfoResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                if (mOnCallBackListener!=null){
                    mOnCallBackListener.onFailure(e);
                }
            }

            @Override
            protected void onSuccess(Call call, Response response, MineBreedingArchivesInfoResult result) {
                if (result != null && result.getCode() == 1) {
                    List<MineBreedingArchivesInfo> datas = result.getData();
                    commonResponse(datas, list, isLoadMore, pageSize, pageNumber);
                } else {
                    ToastUtil.showToast(App.getContext(), result.getMsg());
                }
            }

            @Override
            protected void onResponse(Response response) {

            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {
                if (mOnCallBackListener!=null){
                    mOnCallBackListener.onError(e);
                }

            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, param, token);
    }


    /**
     * 用户版-我的-我的关注列表
     */
    public void getFocusData(final List<BaseInfo> list, int type,
                              final int pageSize, final int pageNumber,
                              boolean isLoadFinish, final boolean isLoadMore) {

        String token = getToken();
        if (TextUtils.isEmpty(token)) {
            return;
        }
        HashMap<String, Object> param = new HashMap<>();
        param.put("pageNum", pageNumber);
        param.put("pageSize", pageSize);

        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_MINE_FOCUS_LIST, new BaseCallBack<MineMyFocusInfoResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                if (mOnCallBackListener!=null){
                    mOnCallBackListener.onFailure(e);
                }
            }

            @Override
            protected void onSuccess(Call call, Response response, MineMyFocusInfoResult result) {
                if (result != null && result.getCode() == 1) {
                    List<MineMyFocusInfo> datas = result.getData();
                    commonResponse(datas, list, isLoadMore, pageSize, pageNumber);
                } else {
                    ToastUtil.showToast(App.getContext(), result.getMsg());
                }
            }

            @Override
            protected void onResponse(Response response) {

            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {
                if (mOnCallBackListener!=null){
                    mOnCallBackListener.onError(e);
                }
            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, param, token);

    }



    /**
     * 用户版-我的-我的粉丝列表
     */
    public void getFansData(final List<BaseInfo> list, int type,
                             final int pageSize, final int pageNumber,
                             boolean isLoadFinish, final boolean isLoadMore) {
        String token = getToken();
        if (TextUtils.isEmpty(token)) {
            return;
        }
        HashMap<String, Object> param = new HashMap<>();
        param.put("pageNum", pageNumber);
        param.put("pageSize", pageSize);

        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_MINE_FANS_LIST, new BaseCallBack<MineMyFansInfoResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                if (mOnCallBackListener!=null){
                    mOnCallBackListener.onFailure(e);
                }
            }

            @Override
            protected void onSuccess(Call call, Response response, MineMyFansInfoResult result) {
                if (result != null && result.getCode() == 1) {
                    List<MineMyFansInfo> datas = result.getData();
                    commonResponse(datas, list, isLoadMore, pageSize, pageNumber);
                } else {
                    ToastUtil.showToast(App.getContext(), result.getMsg());
                }

            }

            @Override
            protected void onResponse(Response response) {

            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {
                if (mOnCallBackListener!=null){
                    mOnCallBackListener.onError(e);
                }
            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, param, token);

    }



    /**
     *用户版-我的-收藏数据列表
     * type 1:店铺  2：商品
     */
    public void getCollectionDataFromServer(final List<BaseInfo> list, final int type,
                                            final int pageSize, final int pageNumber,
                                            boolean isLoadFinish, final boolean isLoadMore) {

        String token = getToken();
        if (TextUtils.isEmpty(token)) {
            return;
        }
        HashMap<String, Object> param = new HashMap<>();
        param.put("pageNum", pageNumber);
        param.put("pageSize", pageSize);
        param.put("objectType", type);
        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_APP_COLLECTION_LIST, new BaseCallBack<MineCollectionInfoResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                if (mOnCallBackListener!=null){
                    mOnCallBackListener.onFailure(e);
                }
            }

            @Override
            protected void onSuccess(Call call, Response response, MineCollectionInfoResult result) {
                if (result != null && result.getCode() == 1) {
                    List<MineCollectionInfo> datas = result.getData();
                    if (!CommonUtils.isEmpty(datas)){
                        for (int i = 0; i < datas.size(); i++) {
                            MineCollectionInfo mineCollectionInfo = datas.get(i);
                            if (mineCollectionInfo==null){
                                continue;
                            }
                            if (type==1){
                                //店铺
                                mineCollectionInfo.setType(Constants.TYPE_COMMON_MINE_COLLECT_SHOPPING);
                            }else if (type==2){
                                //商品
                                mineCollectionInfo.setType(Constants.TYPE_COMMON_MINE_COLLECT_GOODS);
                            }
                        }
                    }
                    commonResponse(datas, list, isLoadMore, pageSize, pageNumber);
                } else {
                    ToastUtil.showToast(App.getContext(), result.getMsg());
                }
            }

            @Override
            protected void onResponse(Response response) {

            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {
                if (mOnCallBackListener!=null){
                    mOnCallBackListener.onError(e);
                }
            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, param, token);
    }


    /**
     * 获取店铺列表信息
     */
    public void getShoppingData(final List<BaseInfo> list, int type,
                                final int pageSize, final int pageNumber,
                                boolean isLoadFinish, final boolean isLoadMore) {
        String token = getToken();
        if (TextUtils.isEmpty(token)) {
            return;
        }
        HashMap<String, Object> param = new HashMap<>();
        if (type==1){
            param.put("mainCategory","饲料");
        }else if (type==2){
            param.put("mainCategory","玉米");
        } else if (type==3){
            param.put("mainCategory","五金电料");
        }
        param.put("pageSize",pageSize);
        param.put("pageNum",pageNumber);

        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_SHOP_LIST, new BaseCallBack<FeedShoppingInfoResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                if (mOnCallBackListener!=null){
                    mOnCallBackListener.onFailure(e);
                }
            }

            @Override
            protected void onSuccess(Call call, Response response, FeedShoppingInfoResult result) {
                if (result != null && result.getCode() == 1) {
                    List<FeedShoppingInfo> dataList = result.getData();
                    commonResponse(dataList, list, isLoadMore, pageSize, pageNumber);
                } else {
                    ToastUtil.showToast(App.getContext(), result.getMsg());
                }
            }


            @Override
            protected void onResponse(Response response) {
            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {
                if (mOnCallBackListener!=null){
                    mOnCallBackListener.onError(e);
                }
            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, param, token);
    }


    /**
     * 用户版-获取市场数据
     * @param listType 最新还是推荐
     * @param typeId 请求的数据类型 出售羊苗 购买羊苗 等
     */
    public void getMarketDataFromServer(final List<BaseInfo> list,
                                         final int pageSize, final int pageNumber,
                                         boolean isLoadFinish, final boolean isLoadMore,final int listType,final int typeId) {
        String token=getToken();
        HashMap<String, Object> param = new HashMap<>();
        param.put("typeId", typeId);
        param.put("listType", listType);
        param.put("pageSize", pageSize);
        param.put("pageNum", pageNumber);

        OkHttpManager.getInstance().postRequest(HttpUrl.HOME_PAGE_GET_MARKET, new BaseCallBack<MarketResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                if (mOnCallBackListener!=null){
                    mOnCallBackListener.onFailure(e);
                }
            }

            @Override
            protected void onSuccess(Call call, Response response, MarketResult result) {
                if (result != null && result.getCode() == 1) {
                    List<MarketInfo> datas = result.getData();
                    for (int i = 0; i < datas.size(); i++) {
                        MarketInfo marketInfo = datas.get(i);
                        marketInfo.setType(typeId);
                    }
                    commonResponse(datas, list, isLoadMore, pageSize, pageNumber);
                } else {
                    ToastUtil.showToast(App.getContext(), result.getMsg());
                }

            }

            @Override
            protected void onResponse(Response response) {

            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {
                if (mOnCallBackListener!=null){
                    mOnCallBackListener.onError(e);
                }
            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, param, token);
    }


    /**
     * 用户版-首页-获取服务数据
     * @param listType 最新还是推荐
     * @param typeId 请求的数据类型 剪羊毛  打疫苗 等
     */
    public void getServiceDataFromServer(final List<BaseInfo> list,
                                          final int pageSize, final int pageNumber,
                                          boolean isLoadFinish, final boolean isLoadMore,int listType,final int typeId) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("typeId", typeId);
        param.put("listType", listType);
        param.put("pageNum", pageNumber);
        param.put("pageSize", pageSize);
        String token = getToken();

        OkHttpManager.getInstance().postRequest(HttpUrl.HOME_PAGE_GET_SERVICE, new BaseCallBack<ServiceResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                if (mOnCallBackListener!=null){
                    mOnCallBackListener.onFailure(e);
                }
            }

            @Override
            protected void onSuccess(Call call, Response response, ServiceResult result) {
                if (result != null && result.getCode() == 1) {
                    List<ServiceInfo> datas = result.getData();
                    for (int i = 0; i < datas.size(); i++) {
                        ServiceInfo serviceInfo = datas.get(i);
                        serviceInfo.setServerType(typeId);
                    }
                    commonResponse(datas, list, isLoadMore, pageSize, pageNumber);
                } else {
                    ToastUtil.showToast(App.getContext(), result.getMsg());
                }



            }

            @Override
            protected void onResponse(Response response) {

            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {
                if (mOnCallBackListener!=null){
                    mOnCallBackListener.onError(e);
                }
            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, param, token);
    }


    /**
     * 用户版-首页-获取行情数据
     */
    public void getQuotationDataFromServer(final List<BaseInfo> list, final int type,
                                           final int pageSize, final int pageNumber,
                                           final boolean isLoadMore) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("typeId", type);
        param.put("pageNum", pageNumber);
        param.put("pageSize", pageSize);
        String token = getToken();
        OkHttpManager.getInstance().postRequest(HttpUrl.HOME_PAGE_GET_QUOTATION, new BaseCallBack<QuotationResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                if (mOnCallBackListener!=null){
                    mOnCallBackListener.onFailure(e);
                }
            }

            @Override
            protected void onSuccess(Call call, Response response, QuotationResult result) {
                if (result != null && result.getCode() == 1) {
                    List<QuotationInfo> dataList = result.getData();
                    if (!CommonUtils.isEmpty(dataList)){
                        for (int i = 0; i < dataList.size(); i++) {
                            QuotationInfo quotationInfo = dataList.get(i);
                            if (quotationInfo==null){
                                continue;
                            }
                            quotationInfo.setType(type);
                        }
                    }
                    commonResponse(dataList, list, isLoadMore, pageSize, pageNumber);
                } else {
                    ToastUtil.showToast(App.getContext(), result.getMsg());
                }
            }

            @Override
            protected void onResponse(Response response) {

            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {
                if (mOnCallBackListener!=null){
                    mOnCallBackListener.onError(e);
                }
            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, param, token);
    }


    /**
     * 获取饲料商品-列表信息
     */
    public void getShoppingGoodsData(final List<BaseInfo> list,
                                final int pageSize, final int pageNumber,
                                final boolean isLoadMore,int listType,final int shoppingId) {

        HashMap<String, Object> param = new HashMap<>();
        param.put("shopId",shoppingId);
        String orderByType="";
        switch (listType){
            case Constants.TYPE_FEED_FOODS_MULTIPLE:
                 orderByType="mod_date";
                break;
            case Constants.TYPE_FEED_FOODS_SALES:

                orderByType="sell_amount";
                break;
            case Constants.TYPE_FEED_FOODS_PRICE:

                orderByType="price";
                break;
        }

        param.put("orderBy",orderByType);
        param.put("order","desc");
        param.put("pageSize",pageSize);
        param.put("pageNum",pageNumber);

        String token = getToken();

        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_GOODS_LIST, new BaseCallBack<FeedGoodsInfoListResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                if (mOnCallBackListener!=null){
                    mOnCallBackListener.onFailure(e);
                }
            }

            @Override
            protected void onSuccess(Call call, Response response, FeedGoodsInfoListResult result) {
                if (result != null && result.getCode() == 1) {
                    List<FeedGoodsInfo> dataList = result.getData();
                    commonResponse(dataList, list, isLoadMore, pageSize, pageNumber);
                } else {
                    ToastUtil.showToast(App.getContext(), result.getMsg());
                }
            }


            @Override
            protected void onResponse(Response response) {

            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {
                if (mOnCallBackListener!=null){
                    mOnCallBackListener.onError(e);
                }
            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, param, token);
    }







    ////////////////////////////////////////////下面是商版接口///////////////////////////////////////////////

    /**
     * 商版-获取订单列表数据
     *一下是接口定义不能修改
     * 0 待支付
     * 1 待发货
     * 2 已发货
     * 3 已完成
     */
    public void getGoodsDataFromServer(final List<BaseInfo> list, final int type,
                                        final int pageSize, final int pageNumber,
                                        final boolean isLoadMore) {

        String token = getToken();
        HashMap<String, Object> param = new HashMap<>();
        param.put("listType", type);
        param.put("pageNum",pageNumber);
        param.put("pageSize", pageSize);

        OkHttpManager.getInstance().postRequest(HttpUrl.BU_HOME_ORDER_LIST, new BaseCallBack<BUManagerOrderInfoResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                if (mOnCallBackListener!=null){
                    mOnCallBackListener.onFailure(e);
                }
            }

            @Override
            protected void onSuccess(Call call, Response response, BUManagerOrderInfoResult result) {
                if (result != null && result.getCode() == 1) {
                    BUManagerOrderInfo dataList = result.getData();
                    List<BUOrderInfo> elements=null;
                    if (dataList!=null){
                        elements = dataList.getElements();
                    }
                    if (!CommonUtils.isEmpty(elements)){
                        for (int i = 0; i < elements.size(); i++) {
                            BUOrderInfo buManagerOrderInfo = elements.get(i);
                            if (buManagerOrderInfo==null){
                                continue;
                            }
                            buManagerOrderInfo.setState(type);
                        }
                    }
                    commonResponse(elements, list, isLoadMore, pageSize, pageNumber);
                } else {
                    ToastUtil.showToast(App.getContext(), result.getMsg());
                }
            }

            @Override
            protected void onResponse(Response response) {

            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {
                if (mOnCallBackListener!=null){
                    mOnCallBackListener.onError(e);
                }
            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, param, token);
    }



    /**
     * 商版-获取退货列表数据
     *一下是接口定义不能修改
     * 0 退货中
     * 1 已完成
     */
    public void getRefundDataFromServer(final List<BaseInfo> list, final int type,
                                       final int pageSize, final int pageNumber,
                                       final boolean isLoadMore) {

        String token = getToken();
        HashMap<String, Object> param = new HashMap<>();
        param.put("listType", type);
        param.put("pageNum",pageNumber);
        param.put("pageSize", pageSize);

        OkHttpManager.getInstance().postRequest(HttpUrl.BU_HOME_BACK_GOODS_LIST, new BaseCallBack<BUGoodsRefundInfoResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                if (mOnCallBackListener!=null){
                    mOnCallBackListener.onFailure(e);
                }
            }

            @Override
            protected void onSuccess(Call call, Response response, BUGoodsRefundInfoResult result) {
                if (result != null && result.getCode() == 1) {
                    BUGoodsRefundInfo dataList = result.getData();
                    List<BUGoodsRefundListInfo> elements=null;
                    if (dataList!=null){
                        elements = dataList.getElements();
                    }
                    if (!CommonUtils.isEmpty(elements)){
                        for (int i = 0; i < elements.size(); i++) {
                            BUGoodsRefundListInfo buGoodsRefundListInfo = elements.get(i);
                            if (buGoodsRefundListInfo==null){
                                continue;
                            }
                            buGoodsRefundListInfo.setState(type);
                        }
                    }
                    commonResponse(elements, list, isLoadMore, pageSize, pageNumber);
                } else {
                    ToastUtil.showToast(App.getContext(), result.getMsg());
                }
            }

            @Override
            protected void onResponse(Response response) {

            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {
                if (mOnCallBackListener!=null){
                    mOnCallBackListener.onError(e);
                }
            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, param, token);
    }




    /////////////////////////////////////用户版 跟CommonFragment 没有关系//////////////////////////////////


    /**
     * 用户版本-取消订单
     */
    public void doCancelOrder(int orderId,String option) {
        String token = getToken();
        HashMap<String, Object> param = new HashMap<>();
        param.put("orderId",orderId);
        param.put("option",option);

        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_APP_USER_ORDER_CANCEL, new BaseCallBack<ServerResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                if (mOnCallBackListener!=null){
                    mOnCallBackListener.onFailure(e);
                }
            }

            @Override
            protected void onSuccess(Call call, Response response, ServerResult result) {
                if (result != null && result.getCode() == 1) {
                    if (mOnCallBackListener!=null){
                        mOnCallBackListener.onSuccess("订单已取消");
                    }
                } else {
                    ToastUtil.showToast(App.getContext(), result.getMsg());
                }
            }


            @Override
            protected void onResponse(Response response) {

            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {
                if (mOnCallBackListener!=null){
                    mOnCallBackListener.onError(e);
                }
            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, param, token);
    }



    /**
     * 用户版本-支付订单
     */
    public void doPayOrder(int orderId,String paymentCode) {
        String token = getToken();
        HashMap<String, Object> param = new HashMap<>();
        param.put("orderId",orderId);
        param.put("paymentCode",paymentCode);

        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_APP_USER_ORDER_PAY, new BaseCallBack<ServerResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                if (mOnCallBackListener!=null){
                    mOnCallBackListener.onFailure(e);
                }
            }

            @Override
            protected void onSuccess(Call call, Response response, ServerResult result) {
                if (result != null && result.getCode() == 1) {
                    if (mOnCallBackListener!=null){
                        mOnCallBackListener.onSuccess("订单支付成功");
                    }
                } else {
                    ToastUtil.showToast(App.getContext(), result.getMsg());
                }
            }


            @Override
            protected void onResponse(Response response) {

            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {
                if (mOnCallBackListener!=null){
                    mOnCallBackListener.onError(e);
                }
            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, param, token);
    }


    /**
     * 用户版本-确认收货
     */
    public void confirmReceiveGoods(int orderId) {

        String token = getToken();
        HashMap<String, Object> param = new HashMap<>();
        param.put("orderId",orderId);

        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_APP_USER_ORDER_RECEIVED, new BaseCallBack<ServerResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                if (mOnCallBackListener!=null){
                    mOnCallBackListener.onFailure(e);
                }
            }

            @Override
            protected void onSuccess(Call call, Response response, ServerResult result) {
                if (result != null && result.getCode() == 1) {
                    if (mOnCallBackListener!=null){
                        mOnCallBackListener.onSuccess("已经确认收货！");
                    }
                } else {
                    ToastUtil.showToast(App.getContext(), result.getMsg());
                }
            }


            @Override
            protected void onResponse(Response response) {

            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {
                if (mOnCallBackListener!=null){
                    mOnCallBackListener.onError(e);
                }
            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, param, token);
    }


    /**
     * 用户版本-申请退款
     */
    public void doApplyRefund(int orderId,String option) {
        String token = getToken();
        HashMap<String, Object> param = new HashMap<>();
        param.put("orderId",orderId);
        param.put("option",option);

        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_APP_USER_ORDER_BACHK_GOODS, new BaseCallBack<ServerResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                if (mOnCallBackListener!=null){
                    mOnCallBackListener.onFailure(e);
                }
            }

            @Override
            protected void onSuccess(Call call, Response response, ServerResult result) {
                if (result != null && result.getCode() == 1) {
                    if (mOnCallBackListener!=null){
                        mOnCallBackListener.onSuccess("退货已经申请！");
                    }
                } else {
                    ToastUtil.showToast(App.getContext(), result.getMsg());
                }
            }


            @Override
            protected void onResponse(Response response) {

            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {
                if (mOnCallBackListener!=null){
                    mOnCallBackListener.onError(e);
                }
            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, param, token);
    }



    /**
     * 通用的数据返回处理
     * @param datas
     * @param list
     * @param isLoadMore
     * @param pageSize
     * @param pageNumber
     */
    private void commonResponse( List<? extends BaseInfo> datas, List<BaseInfo> list, boolean isLoadMore, int pageSize, int pageNumber) {
        if (CommonUtils.isEmpty(datas)&&list.size()==0){
            if (mOnCallBackListener!=null){
                mOnCallBackListener.onShowNoData();
                return;
            }
        }
        if (CommonUtils.isEmpty(datas) && isLoadMore && list.size() >0) {
            if (mOnCallBackListener!=null){
                mOnCallBackListener.onSuccess();
            }
            return;
        }

        if (mOnCallBackListener!=null){
            mOnCallBackListener.onSuccess(datas,pageSize,pageNumber);
        }
    }



    private OnCallBackListener mOnCallBackListener;

    public void setOnCallBackListener(OnCallBackListener onCallBackListener){
        this.mOnCallBackListener=onCallBackListener;
    }

    public interface OnCallBackListener{

        void onShowNoData();

        void onSuccess();

        void onSuccess(String content);

        void onSuccess(List<? extends BaseInfo> baseInfos,int pageSize,int pageNum);

        void onFailure(Exception e);

        void onError(Exception e);

    }

    protected String getToken() {
        String token = UserManager.getInstance(App.getContext()).getToken();
        if (TextUtils.isEmpty(token)) {
            AppManager.getInstance().finishAllActivity();
            AppManager.getInstance().jumpActivity(App.getContext(), LoginActivity.class, Intent.FLAG_ACTIVITY_NEW_TASK);
            return null;
        }
        return token;
    }

}
