package com.meishe.yangquan.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.meishe.yangquan.R;
import com.meishe.yangquan.view.BannerLayout;
import com.meishe.yangquan.wiget.CustomButton;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liupanfeng
 * @desc 主页-行情页面
 * @date 2020/11/26 10:41
 */
public class HomeQuotationFragment extends BaseRecyclerFragment implements View.OnClickListener {

    private BannerLayout mBanner;
    private List<String> mUrlList;
    private CustomButton mLittleSheep;
    private CustomButton mBigSheep;
    private CustomButton mDieSheep;
    private CustomButton mForageGrass;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_home_quotation, container, false);
        mBanner = view.findViewById(R.id.banner);

        mLittleSheep = view.findViewById(R.id.btn_little_sheep);
        mBigSheep = view.findViewById(R.id.btn_big_sheep);
        mDieSheep = view.findViewById(R.id.btn_die_sheep);
        mForageGrass = view.findViewById(R.id.btn_forage_grass);

        return view;
    }

    @Override
    protected void initListener() {
        mLittleSheep.setOnClickListener(this);
        mBigSheep.setOnClickListener(this);
        mDieSheep.setOnClickListener(this);
        mForageGrass.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mUrlList = new ArrayList<>();
        mUrlList.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1423774393,3301272101&fm=26&gp=0.jpg");
        mUrlList.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1764774024,3138989852&fm=26&gp=0.jpg");
        mUrlList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1606555763664&di=6a180735dfa87a6be39a67ddbb47f4a6&imgtype=0&src=http%3A%2F%2Fpic.baike.soso.com%2Fp%2F20140408%2F20140408130734-1854992764.jpg");
        mUrlList.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3811056552,313222880&fm=26&gp=0.jpg");
        List<String> titleas = new ArrayList<>();
        titleas.add("标题一");
        titleas.add("标题二");
        titleas.add("标题三");
        titleas.add("标题四");
        if (mBanner != null) {
            mBanner.setViewUrls(mContext, mUrlList, titleas);
            mBanner.setOnBannerItemClickListener(new BannerLayout.OnBannerItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Toast.makeText(mContext, "position:" + position, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_little_sheep:
                mLittleSheep.setSelected(true);
                mBigSheep.setSelected(false);
                mDieSheep.setSelected(false);
                mForageGrass.setSelected(false);
                break;
            case R.id.btn_big_sheep:
                mLittleSheep.setSelected(false);
                mBigSheep.setSelected(true);
                mDieSheep.setSelected(false);
                mForageGrass.setSelected(false);
                break;
            case R.id.btn_die_sheep:
                mLittleSheep.setSelected(false);
                mBigSheep.setSelected(false);
                mDieSheep.setSelected(true);
                mForageGrass.setSelected(false);
                break;
            case R.id.btn_forage_grass:
                mLittleSheep.setSelected(false);
                mBigSheep.setSelected(false);
                mDieSheep.setSelected(false);
                mForageGrass.setSelected(true);
                break;
            default:
                break;
        }
    }
}
