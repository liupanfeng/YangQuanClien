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
import com.meishe.yangquan.bean.BUManagerOrderInfo;
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
import java.util.ArrayList;
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
@Deprecated
public class BUHomeGoodsOrderFragment extends BaseRecyclerFragment {

    /*订单状态 1 待付 2 待发货 3 已发货 4 已完成 */
    private int mType;
    private IosDialog mIosDialog;

    public static BUHomeGoodsOrderFragment onInstance(int type) {
        BUHomeGoodsOrderFragment buHomeGoodsFragment = new BUHomeGoodsOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.KEY_ORDER_STATE_TYPE, type);
        buHomeGoodsFragment.setArguments(bundle);
        return buHomeGoodsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mType = arguments.getInt(Constants.KEY_ORDER_STATE_TYPE);
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
        List<BUManagerOrderInfo> datas=new ArrayList<>();

//        BUManagerOrderInfo buOrderInfo=new BUManagerOrderInfo();
//        buOrderInfo.setState(mType);
//        datas.add(buOrderInfo);
//
//        buOrderInfo=new BUManagerOrderInfo();
//        buOrderInfo.setState(mType);
//        datas.add(buOrderInfo);
//
//        buOrderInfo = new BUManagerOrderInfo();
//        buOrderInfo.setState(mType);
//        datas.add(buOrderInfo);

        mAdapter.addAll(datas);

        getGoodsDataFromServer();

    }


    @Override
    protected void lazyLoad() {
        super.lazyLoad();
    }



    /**
     * 获取商品列表
     */
    private void getGoodsDataFromServer() {

        String token = UserManager.getInstance(mContext).getToken();
        if (TextUtils.isEmpty(token)) {
            return;
        }

        HashMap<String, Object> param = new HashMap<>();
        param.put("listType", 0);
        param.put("pageNum",1);
        param.put("pageSize", 30);

//        showLoading();
        OkHttpManager.getInstance().postRequest(HttpUrl.BU_HOME_ORDER_LIST, new BaseCallBack<BUGoodsInfoResult>() {
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
                        } else if ((isPublic == 1)) {
                            //下架
                        }
                    } else if (view.getId() == R.id.btn_bu_delete_goods) {
                        if (isPublic == 0) {
                            //删除

                            int id = ((BUGoodsInfo) baseInfo).getId();
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
//                getGoodsDataFromServer();
                break;
        }
    }

}
