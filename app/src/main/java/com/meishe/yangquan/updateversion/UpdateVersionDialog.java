package com.meishe.yangquan.updateversion;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.meishe.yangquan.R;
import com.meishe.yangquan.adapter.BaseRecyclerViewAdapter;
import com.meishe.yangquan.adapter.BaseRecyclerViewHolder;
import com.meishe.yangquan.divider.SpacesItemDecoration;
import com.meishe.yangquan.utils.Util;

import java.util.ArrayList;
import java.util.List;

public class UpdateVersionDialog extends Dialog {

    private TextView mTitle;
    private RecyclerView mTipList;
    private TextView mCancelBtn;
    private TextView mUpdateBtn;
    private TipsAdapter mAdpater;
    private ArrayList<String> mDataList = new ArrayList<>();
    private ButtonClickListener mButtonClickListener;
    private String mAppVersion;

    public UpdateVersionDialog(Context context) {
        super(context);
    }

    public void setButtonClickListener(ButtonClickListener listener){
        mButtonClickListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_version_dialog);

        mTitle = findViewById(R.id.title);
        mCancelBtn = findViewById(R.id.cancel_btn);
        mUpdateBtn = findViewById(R.id.update_btn);
        mTipList = findViewById(R.id.tip_list);
        mTitle.setText(mTitle.getText().toString() + "(" + mAppVersion + ")");

        setCanceledOnTouchOutside(false);

        mUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mButtonClickListener != null){
                    mButtonClickListener.onUpdateBtnClicked();
                }
                dismiss();
            }
        });

        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mButtonClickListener != null){
                    mButtonClickListener.onCancelBtnClicked();
                }
                dismiss();
            }
        });

        initRecyclerView();

    }

    public void setTitle(String appVersion){
        mAppVersion = appVersion;
    }

    public void setDesc(String desc){
        String[] descArrary = desc.split(";");
        for(int i = 0; i < descArrary.length; i++){
            mDataList.add(descArrary[i]);
        }
    }

    private void initRecyclerView(){
        mAdpater = new TipsAdapter(mDataList);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mTipList.setLayoutManager(manager);
        mTipList.setAdapter(mAdpater);
        mTipList.addItemDecoration(new SpacesItemDecoration(Util.dip2px(getContext(), 10), true));
    }


    public interface ButtonClickListener{
        void onCancelBtnClicked();
        void onUpdateBtnClicked();
    }


    private static class TipsViewHolder extends BaseRecyclerViewHolder {

        public TextView desc;

        public TipsViewHolder(View itemView) {
            super(itemView);
            desc = itemView.findViewById(R.id.desc_txt);
        }
    }

    private static class TipsAdapter extends BaseRecyclerViewAdapter<String, TipsViewHolder> {

        public TipsAdapter(List<String> list) {
            super(list);
        }

        @Override
        public void onHolder(@NonNull TipsViewHolder holder, String bean, int position, @NonNull List<Object> payloads) {

        }

        @Override
        public void onHolder(TipsViewHolder holder, String bean, int position) {
            holder.desc.setText(bean);
        }

        @Override
        public TipsViewHolder onCreateHolder(ViewGroup parent, int viewType) {
            return new TipsViewHolder(getViewByRes(R.layout.update_version_tip_item, parent));
        }
    }

}
