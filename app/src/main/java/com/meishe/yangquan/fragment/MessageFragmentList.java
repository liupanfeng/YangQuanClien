package com.meishe.yangquan.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.meishe.yangquan.R;

public class MessageFragmentList extends BaseRecyclerFragment{

    private static final String TYPE = "type";

    private String type;

    public MessageFragmentList() {
    }

    public static MessageFragmentList newInstance(String type) {
        MessageFragmentList fragment = new MessageFragmentList();
        Bundle args = new Bundle();
        args.putString(TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getString(TYPE);
        }
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        View view=inflater.inflate(R.layout.fragment_message_fragment_list_layout,container,false);
        TextView textView=view.findViewById(R.id.tv_content);
        textView.setText(type);

        return view;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }
}
