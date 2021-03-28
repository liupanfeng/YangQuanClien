package com.meishe.yangquan.helper;

import android.text.TextUtils;

import com.meishe.yangquan.App;
import com.meishe.yangquan.activity.LoginActivity;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.MineBreedingArchivesInfo;
import com.meishe.yangquan.bean.MineBreedingArchivesInfoResult;
import com.meishe.yangquan.bean.MineFeedGoldInfo;
import com.meishe.yangquan.bean.MineFeedGoldInfoResult;
import com.meishe.yangquan.bean.MineOrderInfo;
import com.meishe.yangquan.bean.MineOrderInfoResult;
import com.meishe.yangquan.bean.MineUserMessageInfo;
import com.meishe.yangquan.bean.MineUserMessageInfoResult;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.utils.CommonUtils;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.UserManager;

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


    /**
     * 获取养殖档案列表
     */
    private void getBreedingArchivesData() {
        String token = getToken();
        if (TextUtils.isEmpty(token)) {
            return;
        }

        HashMap<String, Object> requestParam = new HashMap<>();
        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_MINE_BREEDING_ARCHIVE, new BaseCallBack<MineBreedingArchivesInfoResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
            }

            @Override
            protected void onSuccess(Call call, Response response, MineBreedingArchivesInfoResult result) {
                if (result != null && result.getCode() == 1) {
                    List<MineBreedingArchivesInfo> data = result.getData();
                    if (data == null) {
                        return;
                    }
                } else {
//                    ToastUtil.showToast(mContext, result.getMsg());
                }
            }

            @Override
            protected void onResponse(Response response) {

            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {

            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, requestParam, token);
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
            ToastUtil.showToast("暂无更多内容！");
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

        void onSuccess(List<? extends BaseInfo> baseInfos,int pageSize,int pageNum);

        void onFailure(Exception e);

        void onError(Exception e);

    }

    protected String getToken() {
        String token = UserManager.getInstance(App.getContext()).getToken();
        if (TextUtils.isEmpty(token)) {
            AppManager.getInstance().finishAllActivity();
            AppManager.getInstance().jumpActivity(App.getContext(), LoginActivity.class);
            return null;
        }
        return token;
    }

}
