package com.meishe.yangquan.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.DynamicDrawableSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import com.meishe.yangquan.R;
import com.meishe.yangquan.apannable.CircleMovementMethod;
import com.meishe.yangquan.apannable.MyImageSpan;
import com.meishe.yangquan.bean.ServerZan;

import java.util.List;

public class LikesView extends android.support.v7.widget.AppCompatTextView {

    private Context mContext;
    private List<ServerZan> list;

    public LikesView(Context context) {
        this(context, null);
    }

    public LikesView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LikesView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    /**
     * 设置点赞数据
     *
     * @param list
     */
    public void setList(List<ServerZan> list) {
        this.list = list;
    }

    /**
     * 刷新点赞列表
     */
    public void notifyDataSetChanged() {
        if (list == null || list.size() <= 0) {
            return;
        }
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(setImageSpan());
        for (int i = 0; i < list.size(); i++) {
            ServerZan item = list.get(i);
            builder.append(setClickableSpan(item.getNickName(), item));
            if (i != list.size() - 1) {
                builder.append(" , ");
            } else {
                builder.append(" ");
            }
        }

        setText(builder);
        setMovementMethod(new CircleMovementMethod(0xffffffff, 0xffffffff));
// setMovementMethod(LinkMovementMethod.getInstance());
    }

    /**
     * 设置评论用户名字点击事件
     *
     * @param item
     * @param bean
     * @return
     */
    public SpannableString setClickableSpan(final String item, final ServerZan bean) {
        final SpannableString string = new SpannableString(item);
        ClickableSpan span = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                // TODO: 2017/9/3 评论用户名字点击事件
                Toast.makeText(mContext, bean.getNickName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                // 设置显示的文字颜色
                ds.setColor(mContext.getResources().getColor(R.color.black66));
                ds.setUnderlineText(false);
            }
        };

        string.setSpan(span, 0, string.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return string;
    }

    /**
     * 设置点赞图标
     *
     * @return
     */
    private SpannableString setImageSpan() {
        String text = " ";
        SpannableString imgSpanText = new SpannableString(text);
        MyImageSpan imageSpan=new MyImageSpan(getContext(), R.mipmap.ic_zan_two_level, DynamicDrawableSpan.ALIGN_BASELINE);
        Drawable drawable=imageSpan.getDrawable();
        imageSpan.zoomDrawable(drawable,2,2);
        imgSpanText.setSpan(imageSpan,
                0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return imgSpanText;
    }

}