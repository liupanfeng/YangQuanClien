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
 * @Description : 选择车的服务类别:拉成品羊 拉饲料  拉玉米 冷藏车
 */
public class SelectCarServiceTypeView extends AttachPopupView {

    private OnAttachListener mAttachListener;


    public static SelectCarServiceTypeView create(Context context, View aView, OnAttachListener attachListener) {
        return (SelectCarServiceTypeView) new XPopup.Builder(context)
                .atView(aView).asCustom(new SelectCarServiceTypeView(context).
                        setAttachListener(attachListener));
    }

    public SelectCarServiceTypeView(Context context) {
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
        return R.layout.pop_attach_select_car_serevice;
    }


    public interface OnAttachListener {

        void onSelectType(String content);

    }


    public SelectCarServiceTypeView setAttachListener(OnAttachListener attachListener) {
        mAttachListener = attachListener;
        return this;
    }

}
