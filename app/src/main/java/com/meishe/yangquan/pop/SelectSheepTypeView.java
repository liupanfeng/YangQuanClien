package com.meishe.yangquan.pop;

import android.content.Context;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.meishe.libbase.pop.XPopup;
import com.meishe.libbase.pop.core.AttachPopupView;
import com.meishe.yangquan.R;

/**
 * @Author : lpf
 * @CreateDate : 2021/1/12
 * @Description : 选择肉羊品种类别:
 */
public class SelectSheepTypeView extends AttachPopupView {

    private OnAttachListener mAttachListener;


    public static SelectSheepTypeView create(Context context, View aView, OnAttachListener attachListener) {
        return (SelectSheepTypeView) new XPopup.Builder(context)
                .atView(aView).asCustom(new SelectSheepTypeView(context).
                        setAttachListener(attachListener));
    }

    public SelectSheepTypeView(Context context) {
        super(context);
        RadioGroup radioGroup = findViewById(R.id.rg_select_service);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = radioGroup.findViewById(checkedRadioButtonId);
                String content = radioButton.getText().toString();
                if (mAttachListener != null) {
                    mAttachListener.onSelectType(content);
                }
            }
        });
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.pop_attach_select_sheep_breed_helper;
    }


    public interface OnAttachListener {

        void onSelectType(String content);

    }


    public SelectSheepTypeView setAttachListener(OnAttachListener attachListener) {
        mAttachListener = attachListener;
        return this;
    }

}
