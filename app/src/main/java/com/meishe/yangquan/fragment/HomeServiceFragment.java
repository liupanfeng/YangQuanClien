package com.meishe.yangquan.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.meishe.yangquan.R;
import com.meishe.yangquan.activity.PublishServiceActivity;
import com.meishe.yangquan.utils.AppManager;
import com.meishe.yangquan.wiget.CustomButton;

import static com.meishe.yangquan.utils.Constants.TAB_TYPE_SERVICE;

/**
 * @author liupanfeng
 * @desc 主页-服务页面
 * @date 2020/11/26 10:43
 */
public class HomeServiceFragment extends BaseRecyclerFragment implements View.OnClickListener {
    /*剪羊毛*/
    public static final int TYPE_SERVICE_CUT_WOOL = 13;
    /*打疫苗*/
    public static final int TYPE_SERVICE_VACCINE = 14;
    /*拉羊粪*/
    public static final int TYPE_SERVICE_SHEEP_DUNG = 15;
    /*找车辆*/
    public static final int TYPE_SERVICE_LOOK_CAR = 16;

    /*剪羊毛*/
    private CustomButton mCutWool;
    /*打疫苗*/
    private CustomButton mVaccine;
    /*拉羊粪*/
    private CustomButton mSheepDung;
    /*找车辆*/
    private CustomButton mLookCar;


    private int mServiceType = TYPE_SERVICE_CUT_WOOL;

    private ImageView mIvPublishService;
    private HomeContentFragment mHomeContentFragment;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view=inflater.inflate(R.layout.fragment_home_service,container,false);
        mCutWool = view.findViewById(R.id.btn_cut_wool);
        mVaccine = view.findViewById(R.id.btn_vaccine);
        mSheepDung = view.findViewById(R.id.btn_sheep_dung);
        mLookCar = view.findViewById(R.id.btn_look_car);

        mRecyclerView = view.findViewById(R.id.recycler);
        mIvPublishService = view.findViewById(R.id.iv_publish_service);
        return view;
    }

    @Override
    protected void initListener() {
        mCutWool.setOnClickListener(this);
        mVaccine.setOnClickListener(this);
        mSheepDung.setOnClickListener(this);
        mLookCar.setOnClickListener(this);
        mIvPublishService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putInt("service_type",mServiceType);
                //发布服务
                AppManager.getInstance().jumpActivity(getActivity(), PublishServiceActivity.class,bundle);
            }
        });
    }

    @Override
    protected void initData() {
        HomeContentFragment contentFragment = HomeContentFragment.newInstance(mServiceType,TAB_TYPE_SERVICE);
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, contentFragment).commit();

        selectCutWool();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cut_wool:
                mServiceType = TYPE_SERVICE_CUT_WOOL;
                selectCutWool();
                break;
            case R.id.btn_vaccine:
                mServiceType = TYPE_SERVICE_VACCINE;
                selectVaccine();
                break;
            case R.id.btn_sheep_dung:
                mServiceType = TYPE_SERVICE_SHEEP_DUNG;
                selectSheepDung();
                break;
            case R.id.btn_look_car:
                mServiceType = TYPE_SERVICE_LOOK_CAR;
                selectLookCar();
                break;

            default:
                break;
        }
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        mHomeContentFragment = HomeContentFragment.newInstance(mServiceType,TAB_TYPE_SERVICE);
        fragmentTransaction.replace(R.id.container, mHomeContentFragment).commit();
    }


    private void selectCutWool() {
        mCutWool.setSelected(true);
        mVaccine.setSelected(false);
        mSheepDung.setSelected(false);
        mLookCar.setSelected(false);
    }

    private void selectVaccine() {
        mCutWool.setSelected(false);
        mVaccine.setSelected(true);
        mSheepDung.setSelected(false);
        mLookCar.setSelected(false);
    }

    private void selectSheepDung() {
        mCutWool.setSelected(false);
        mVaccine.setSelected(false);
        mSheepDung.setSelected(true);
        mLookCar.setSelected(false);
    }

    private void selectLookCar() {
        mCutWool.setSelected(false);
        mVaccine.setSelected(false);
        mSheepDung.setSelected(false);
        mLookCar.setSelected(true);
    }


}
