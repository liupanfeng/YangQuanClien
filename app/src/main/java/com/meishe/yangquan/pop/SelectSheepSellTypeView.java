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
 * @Description : 选择肉 出售方式：羊腔  活羊
 */
public class SelectSheepSellTypeView extends AttachPopupView {

    private OnAttachListener mAttachListener;


    public static SelectSheepSellTypeView create(Context context, View aView, OnAttachListener attachListener) {
        return (SelectSheepSellTypeView) new XPopup.Builder(context)
                .atView(aView).asCustom(new SelectSheepSellTypeView(context).
                        setAttachListener(attachListener));
    }

    public SelectSheepSellTypeView(Context context) {
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
        return R.layout.pop_attach_select_sheep_breed_sell_type;
    }


    public interface OnAttachListener {

        void onSelectType(String content);

    }


    public SelectSheepSellTypeView setAttachListener(OnAttachListener attachListener) {
        mAttachListener = attachListener;
        return this;
    }

}
