package com.meishe.yangquan.manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.meishe.yangquan.R;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * @Author : lpf
 * @CreateDate : 2021/1/25
 * @Description : 折线图view
 */
public class LineChartMarkView extends MarkerView {

    private TextView tvDate;
    private TextView tvValue;
    private ValueFormatter xAxisValueFormatter;
    private String[] xNames;
    DecimalFormat df = new DecimalFormat("");

    public String[] getxNames() {
        return xNames;
    }

    public void setxNames(String[] xNames) {
        this.xNames = xNames;
    }

    public LineChartMarkView(Context context, ValueFormatter xAxisValueFormatter) {
        super(context, R.layout.layout_markview);
        this.xAxisValueFormatter = xAxisValueFormatter;

        tvDate = findViewById(R.id.tv_date);
        tvValue = findViewById(R.id.tv_value);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        //展示自定义X轴值 后的X轴内容
        if (xNames != null && xNames.length > 0) {
            int tag = (int) e.getX() % xNames.length;
            if (tag < 0)
                tag = 0;
            if (tag >= xNames.length)
                tag = xNames.length - 1;

            try {
                String intNumber = xNames[tag].substring(0, xNames[tag].indexOf("."));
                String intNumber2 = xNames[tag].substring(xNames[tag].indexOf(".") + 1, xNames[tag].length());
                tvDate.setText(intNumber + "月" + intNumber2 + "日");
            } catch (Exception ee) {
                ee.printStackTrace();
            }


        }

        tvValue.setText("当前价:" + e.getY());
        super.refreshContent(e, highlight);
    }

    @Override
    public MPPointF getOffset() {
        return new MPPointF(-(getWidth() / 2), -getHeight());
    }

    public String getTenThousandOfANumber(float num) {
        if (num < 10000) {
            return String.valueOf((int) num);
        }
        DecimalFormat df = new DecimalFormat("#.0");
        df.setRoundingMode(RoundingMode.FLOOR);
        String numStr = df.format(num / 10000d);
        String[] ss = numStr.split("\\.");
        if ("0".equals(ss[1])) {
            return ss[0] + "万";
        } else {
            return numStr + "万";
        }
    }

}

