package com.meishe.yangquan.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

/**
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/4/20 18:37
 * @Description : 这个是引导页面专用
 */
public class SlideFragment extends Fragment {

    private static final String ARG_LAYOUT_RES_ID = "layoutResId";

    public static SlideFragment newInstance(int layoutResId) {
        SlideFragment slideFragment = new SlideFragment();

        Bundle args = new Bundle();
        args.putInt(ARG_LAYOUT_RES_ID, layoutResId);
        slideFragment.setArguments(args);

        return slideFragment;
    }

    private int layoutResId;

    public SlideFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(ARG_LAYOUT_RES_ID))
            layoutResId = getArguments().getInt(ARG_LAYOUT_RES_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(layoutResId, container, false);
    }

}
