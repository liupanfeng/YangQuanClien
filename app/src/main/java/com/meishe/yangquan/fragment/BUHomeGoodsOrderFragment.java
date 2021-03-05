package com.meishe.yangquan.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerAdapter;
import com.meishe.yangquan.bean.BUGoodsInfo;
import com.meishe.yangquan.bean.BUGoodsInfoResult;
import com.meishe.yangquan.bean.BaseInfo;
import com.meishe.yangquan.bean.ServerResult;
import com.meishe.yangquan.event.MessageEvent;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.pop.ShowBigPictureView;
import com.meishe.yangquan.utils.CommonUtils;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.UserManager;
import com.meishe.yangquan.wiget.IosDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @Author : LiuPanFeng
 * @CreateDate : 2021/3/3 15:32
 * @Description : 商版-商品订单管理 子碎片
 */
public class BUHomeGoodsOrderFragment extends BaseRecyclerFragment {

    /*订单状态 1已经发布  2等待发布*/
    private int mType;
    private IosDialog mIosDialog;

    public static BUHomeGoodsOrderFragment onInstance(int type) {
        BUHomeGoodsOrderFragment buHomeGoodsFragment = new BUHomeGoodsOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.KEY_PUBLISH_GOODS_TYPE, type);
        buHomeGoodsFragment.setArguments(bundle);
        return buHomeGoodsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mType = arguments.getInt(Constants.KEY_PUBLISH_GOODS_TYPE);
        }
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_bu_home_goods, container, false);
        mRecyclerView = view.findViewById(R.id.recycler);
        mLoading = view.findViewById(R.id.loading);
        initRecyclerView();
        return view;
    }

    @Override
    protected void initData() {

    }


    @Override
    protected void lazyLoad() {
        super.lazyLoad();
//        getGoodsDataFromServer();
    }

    /**
     * 获取商品列表
     */
    private void getGoodsDataFromServer() {
        HashMap<String, Object> param = new HashMap<>();
        param.put("pageNum", 1);
        param.put("pageSize", 300);
        if (mType == 2) {
            param.put("isPublic", 0);
        } else if (mType == 1) {
            param.put("isPublic", 1);
        }
        String token = UserManager.getInstance(mContext).getToken();
        if (TextUtils.isEmpty(token)) {
            return;
        }
//        showLoading();
        OkHttpManager.getInstance().postRequest(HttpUrl.BU_HOME_GOODS_INFO, new BaseCallBack<BUGoodsInfoResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideLoading();
                    }
                });
            }

            @Override
            protected void onSuccess(Call call, Response response, BUGoodsInfoResult result) {
                hideLoading();
                if (result.getCode() != 1) {
                    ToastUtil.showToast(mContext, result.getMsg());
                    return;
                }
                List<BUGoodsInfo> data = result.getData();
                mAdapter.addAll(data);
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
        }, param, token);
    }

    @Override
    protected void initListener() {
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, BaseInfo baseInfo) {
                if (baseInfo instanceof BUGoodsInfo) {
                    int isPublic = ((BUGoodsInfo) baseInfo).getIsPublic();
                    if (view.getId() == R.id.btn_bu_edit_goods) {
                        //编辑商品
                    } else if (view.getId() == R.id.btn_bu_shelves_goods) {
                        //上架商品 或者下架商品  根据商品是否已经上架
                        if (isPublic == 0) {
                            //上架
                            upGoods(((BUGoodsInfo) baseInfo));
                        } else if ((isPublic == 1)) {
                            //下架
                            downGoods(((BUGoodsInfo) baseInfo));
                        }
                    } else if (view.getId() == R.id.btn_bu_delete_goods) {
                        if (isPublic == 0) {
                            //删除

                            int id = ((BUGoodsInfo) baseInfo).getId();
                            showInputIncomingDialog(id);
                        } else if ((isPublic == 1)) {
                            //分享
                        }
                    } else if (view.getId() == R.id.riv_bu_goods_cover) {
                        //查看大图
                        List<String> goodsImageUrls = ((BUGoodsInfo) baseInfo).getGoodsImageUrls();
                        if (CommonUtils.isEmpty(goodsImageUrls)) {
                            return;
                        }
                        String filePath = goodsImageUrls.get(0);
                        ShowBigPictureView showBigPictureView = ShowBigPictureView.create(mContext, filePath);
                        if (showBigPictureView != null && showBigPictureView.isDismiss()) {
                            showBigPictureView.show();
                        }
                    }
                }
            }
        });
    }

    /**
     * 删除商品提示框
     * @param id
     */
    private void showInputIncomingDialog(final int id) {
        final IosDialog.DialogBuilder builder = new IosDialog.DialogBuilder(mContext);
        builder.setTitle("是否确认删除商品");
        builder.setAsureText("确定");
        builder.setCancelText("取消");
        builder.addListener(new IosDialog.OnButtonClickListener() {
            @Override
            public void onAsureClick() {
                mIosDialog.hide();
                deleteGoods(id);
            }

            @Override
            public void onCancelClick() {
                mIosDialog.hide();
            }
        });
        mIosDialog = builder.create();
    }

    /**
     * 删除商品
     * @param id 商品id
     */
    private void deleteGoods(int id) {
        String token = UserManager.getInstance(mContext).getToken();
        if (TextUtils.isEmpty(token)) {
            return;
        }
        HashMap<String, Object> requestParam = new HashMap<>();
        requestParam.put("id", id);

        OkHttpManager.getInstance().postRequest(HttpUrl.BU_HOME_DELETE_GOODS, new BaseCallBack<ServerResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideLoading();
                        ToastUtil.showToast(mContext, "网络异常");
                    }
                });
            }

            @Override
            protected void onSuccess(Call call, Response response, ServerResult result) {
                hideLoading();
                if (result != null && result.getCode() == 1) {
                    ToastUtil.showToast(mContext, "删除商品成功");
                    getGoodsDataFromServer();
                } else {
                    ToastUtil.showToast(mContext, result.getMsg());
                }
            }

            @Override
            protected void onResponse(Response response) {

            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(mContext, "网络异常");
                    }
                });

            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, requestParam, token);
    }

    /**
     * 下架商品
     *
     * @param baseInfo
     */
    private void downGoods(BUGoodsInfo baseInfo) {
        if (baseInfo == null) {
            return;
        }
        int id = baseInfo.getId();
        changeGoodsState(id, 0);
    }

    /**
     * 上架商品
     *
     * @param baseInfo
     */
    private void upGoods(BUGoodsInfo baseInfo) {
        if (baseInfo == null) {
            return;
        }
        int id = baseInfo.getId();
        changeGoodsState(id, 1);
    }


    /**
     * 发布商品
     *
     * @param isPublic 0：下架 1上架
     * @param id       商品id
     */
    private void changeGoodsState(int id, final int isPublic) {

        String token = UserManager.getInstance(mContext).getToken();
        if (TextUtils.isEmpty(token)) {
            return;
        }
        HashMap<String, Object> requestParam = new HashMap<>();

        requestParam.put("isPublic", isPublic);
        requestParam.put("id", id);

        OkHttpManager.getInstance().postRequest(HttpUrl.BU_HOME_GOODS_UN_OR_DOWN, new BaseCallBack<ServerResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideLoading();
                        ToastUtil.showToast(mContext, "网络异常");
                    }
                });
            }

            @Override
            protected void onSuccess(Call call, Response response, ServerResult result) {
                hideLoading();
                if (result != null && result.getCode() == 1) {
                    if (isPublic == 0) {
                        //下架
                        ToastUtil.showToast(mContext, "下架成功");
                    } else if (isPublic == 1) {
                        //上架
                        ToastUtil.showToast(mContext, "上架成功");
                    }
                    getGoodsDataFromServer();
                } else {
                    ToastUtil.showToast(mContext, result.getMsg());
                }
            }

            @Override
            protected void onResponse(Response response) {

            }

            @Override
            protected void onEror(Call call, int statusCode, Exception e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showToast(mContext, "网络异常");
                    }
                });

            }

            @Override
            protected void inProgress(int progress, long total, int id) {

            }
        }, requestParam, token);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this); //解除注册
    }


    /**
     * On message event.
     * 消息事件
     *
     * @param event the event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        int eventType = event.getEventType();
        switch (eventType) {
            case MessageEvent.MESSAGE_TYPE_BU_PUBLISH_GOODS_SUCCESS:
                getGoodsDataFromServer();
                break;
        }
    }

}
