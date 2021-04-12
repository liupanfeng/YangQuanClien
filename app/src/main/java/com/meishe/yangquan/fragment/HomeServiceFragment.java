package com.meishe.yangquan.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.meishe.yangquan.R;
import com.meishe.yangquan.activity.PublishServiceActivity;
import com.meishe.yangquan.bean.HomeCheckDriverInfoResult;
import com.meishe.yangquan.bean.Message;
import com.meishe.yangquan.bean.MessageResult;
import com.meishe.yangquan.bean.ServerResult;
import com.meishe.yangquan.http.BaseCallBack;
import com.meishe.yangquan.http.OkHttpManager;
import com.meishe.yangquan.pop.HomeTipDriverInfoView;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.utils.Constants;
import com.meishe.yangquan.utils.HttpUrl;
import com.meishe.yangquan.utils.ToastUtil;
import com.meishe.yangquan.utils.Util;
import com.meishe.yangquan.view.HorizontalExpandMenu;
import com.meishe.yangquan.wiget.CustomButton;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

import static com.meishe.yangquan.utils.Constants.TAB_TYPE_SERVICE;

/**
 * @author liupanfeng
 * @desc 主页-服务页面
 * @date 2020/11/26 10:43
 */
public class HomeServiceFragment extends BaseRecyclerFragment implements View.OnClickListener {


    /*剪羊毛*/
    private CustomButton mCutWool;
    /*打疫苗*/
    private CustomButton mVaccine;
    /*拉羊粪*/
    private CustomButton mSheepDung;
    /*找车辆*/
    private CustomButton mLookCar;


    private int mServiceType = Constants.TYPE_SERVICE_CUT_WOOL;

    private HomeContentFragment mHomeContentFragment;

    private TextView tv_service_content;
    private HorizontalExpandMenu expand_menu;
    private View card_common_publish;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_home_service, container, false);
        mCutWool = view.findViewById(R.id.btn_cut_wool);
        mVaccine = view.findViewById(R.id.btn_vaccine);
        mSheepDung = view.findViewById(R.id.btn_sheep_dung);
        mLookCar = view.findViewById(R.id.btn_look_car);

        mRecyclerView = view.findViewById(R.id.recycler);
        card_common_publish = view.findViewById(R.id.card_common_publish);
        tv_service_content = view.findViewById(R.id.tv_service_content);
        expand_menu = view.findViewById(R.id.expand_menu);
        tv_service_content.setSelected(true);
        tv_service_content.setAlpha(0);


        return view;
    }

    @Override
    protected void initListener() {
        mCutWool.setOnClickListener(this);
        mVaccine.setOnClickListener(this);
        mSheepDung.setOnClickListener(this);
        mLookCar.setOnClickListener(this);
        card_common_publish.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {


                if (mServiceType == Constants.TYPE_SERVICE_LOOK_CAR) {
                    checkDriverMessage();
                    return;
                }

                Bundle bundle = new Bundle();
                bundle.putInt("service_type", mServiceType);
                //发布服务
                AppManager.getInstance().jumpActivity(getActivity(), PublishServiceActivity.class, bundle);
            }
        });

        expand_menu.setOnExpandMenuListener(new HorizontalExpandMenu.OnExpandMenuListener() {
            @Override
            public void onExpand(boolean isExpand, int time) {
                if (isExpand) {
                    ValueAnimator objectAnimator = ObjectAnimator.ofFloat(1f, 0f);
                    objectAnimator.setDuration(time);
                    objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float animatedValue = (float) animation.getAnimatedValue();
                            tv_service_content.setAlpha(animatedValue);
                        }
                    });
                    objectAnimator.start();
                } else {
                    ValueAnimator objectAnimator = ObjectAnimator.ofFloat(0f, 1f);
                    objectAnimator.setDuration(time);
                    objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float animatedValue = (float) animation.getAnimatedValue();
                            tv_service_content.setAlpha(animatedValue);
                        }
                    });
                    objectAnimator.start();
                }
            }
        });

    }

    /**
     * 检测驾驶信息
     */
    private void checkDriverMessage() {
        String token = getToken();
        if (TextUtils.isEmpty(token)) {
            return;
        }

        HashMap<String, Object> requestParam = new HashMap<>();
        OkHttpManager.getInstance().postRequest(HttpUrl.SHEEP_APP_DRIVER_INFO, new BaseCallBack<HomeCheckDriverInfoResult>() {
            @Override
            protected void OnRequestBefore(Request request) {

            }

            @Override
            protected void onFailure(Call call, IOException e) {
            }

            @Override
            protected void onSuccess(Call call, Response response, HomeCheckDriverInfoResult result) {
                if (result != null && result.getCode() == 1) {
                    HomeCheckDriverInfoResult.HomeCheckDriverInfo data = result.getData();
                    if (data.isDriverInfoComplete()) {
                        Bundle bundle = new Bundle();
                        bundle.putInt("service_type", mServiceType);
                        //发布服务
                        AppManager.getInstance().jumpActivity(getActivity(), PublishServiceActivity.class, bundle);
                    } else {
                        HomeTipDriverInfoView homeTipDriverInfoView = HomeTipDriverInfoView.
                                create(mContext, "提示", "您还没有完善驾驶信息，\n现在去完善?",
                                        new HomeTipDriverInfoView.OnAttachListener() {
                                            @Override
                                            public void onClickConfirm() {

                                            }

                                            @Override
                                            public void onClickCancel() {

                                            }
                                        });

                        if (!homeTipDriverInfoView.isShow()) {
                            homeTipDriverInfoView.show();
                        }
                    }
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

    @Override
    protected void initData() {

    }

    @Override
    protected void lazyLoad() {
        super.lazyLoad();
        HomeContentFragment contentFragment = HomeContentFragment.newInstance(mServiceType, TAB_TYPE_SERVICE);
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, contentFragment).commit();
        if (mServiceType == Constants.TYPE_SERVICE_CUT_WOOL) {
            selectCutWool();
        } else if (mServiceType == Constants.TYPE_SERVICE_VACCINE) {
            selectVaccine();
        } else if (mServiceType == Constants.TYPE_SERVICE_SHEEP_DUNG) {
            selectSheepDung();
        } else if (mServiceType == Constants.TYPE_SERVICE_LOOK_CAR) {
            selectLookCar();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cut_wool:
                mServiceType = Constants.TYPE_SERVICE_CUT_WOOL;
                selectCutWool();
                break;
            case R.id.btn_vaccine:
                mServiceType = Constants.TYPE_SERVICE_VACCINE;
                selectVaccine();
                break;
            case R.id.btn_sheep_dung:
                mServiceType = Constants.TYPE_SERVICE_SHEEP_DUNG;
                selectSheepDung();
                break;
            case R.id.btn_look_car:
                mServiceType = Constants.TYPE_SERVICE_LOOK_CAR;
                selectLookCar();
                break;

            default:
                break;
        }
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        mHomeContentFragment = HomeContentFragment.newInstance(mServiceType, TAB_TYPE_SERVICE);
        fragmentTransaction.replace(R.id.container, mHomeContentFragment).commit();
    }


    private void selectCutWool() {
        mCutWool.setSelected(true);
        mVaccine.setSelected(false);
        mSheepDung.setSelected(false);
        mLookCar.setSelected(false);
        tv_service_content.setText(mCutWool.getText());
    }

    private void selectVaccine() {
        mCutWool.setSelected(false);
        mVaccine.setSelected(true);
        mSheepDung.setSelected(false);
        mLookCar.setSelected(false);
        tv_service_content.setText(mVaccine.getText());
    }

    private void selectSheepDung() {
        mCutWool.setSelected(false);
        mVaccine.setSelected(false);
        mSheepDung.setSelected(true);
        mLookCar.setSelected(false);
        tv_service_content.setText(mSheepDung.getText());
    }

    private void selectLookCar() {
        mCutWool.setSelected(false);
        mVaccine.setSelected(false);
        mSheepDung.setSelected(false);
        mLookCar.setSelected(true);
        tv_service_content.setText(mLookCar.getText());
    }


}
