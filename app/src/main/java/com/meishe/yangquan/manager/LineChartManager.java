package com.meishe.yangquan.manager;

import android.content.Context;
import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : lpf
 * @CreateDate : 2021/1/25
 * @Description : 折现图管着者
 */
public class LineChartManager {

    private Context mContext;

    //折线图对象LineChart
    private LineChart mLineChart;

    //x轴实例对象
    private XAxis mXAxis;

    //左边y轴实例对象
    private YAxis mLeftAxis;

    //右边y轴实例对象
    private YAxis mRightAxis;

    //图例对象
    private Legend mLegend;


    public LineChartManager(Context context, LineChart lineChart) {
        this.mLineChart = lineChart;
        mContext = context;
        mXAxis = lineChart.getXAxis();
        mLeftAxis = lineChart.getAxisLeft();
        mRightAxis = lineChart.getAxisRight();
        mLegend = lineChart.getLegend();
        initLinechart();
    }

    private void initLinechart() {
        //设置折线图属性
        setBarChartProperties();

        //设置X轴
        setXAxis();

        //设置左边Y轴
        setLeftAxis();

        //设置右边Y轴
        setRightAxis();

        //设置图例
        setLegend(false);

        //设置限制线
        //setLimitLine();
    }


    /**
     * 设置折线图属性
     */
    private void setBarChartProperties() {
        //设置背景颜色
        mLineChart.setBackgroundColor(Color.WHITE);

        //是否显示网格背景
        mLineChart.setDrawGridBackground(true);

        //无数据时显示
        mLineChart.setNoDataText("统计数据为空");

        //隐藏描述
        mLineChart.getDescription().setEnabled(false);

        //是否显示边界
        mLineChart.setDrawBorders(false);

        //如果图表中显示超过60个条目，则不会绘制任何折线图的值
        //mLineChart.setMaxVisibleValueCount(60);

        //缩放现在只能在x轴和y轴上分别进行
        mLineChart.setPinchZoom(false);

        mLineChart.setViewPortOffsets(120, 20, 60, 100);

        //设置动画效果
        mLineChart.animateXY(2000, 2000);

        //设置可缩放,默认可缩放
//        mLineChart.setScaleEnabled(true);
         mLineChart.setScaleXEnabled(true);
//        mLineChart.setScaleEnabled(true);
        // mLineChart.setScaleYEnabled(true);

        //支持触控手势
        mLineChart.setTouchEnabled(true);

        // 支持拖动
        mLineChart.setDragEnabled(true);

        mLineChart.setDoubleTapToZoomEnabled(false);

    }

    /**
     * 设置X轴
     */
    private void setXAxis() {
        //X轴对象实例
        mXAxis = mLineChart.getXAxis();
        //设置X轴的位置
        mXAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //隐藏水平网格线
        //mXAxis.setGridColor(Color.TRANSPARENT);
        mXAxis.setDrawGridLines(true);

        mXAxis.setDrawLabels(true);
        //设置x抽的值每隔多少个显示
        mXAxis.setGranularity(1f);

        //设置x抽的值倾斜显示
        mXAxis.setLabelRotationAngle(-60);

//        mXAxis.setLabelCount(dataX.size(),true);

        //设置文字颜色
        // mXAxis.setTextColor(Color.RED);


        //格式化x抽显示的值
         /* mXAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xAxisValues.get((int) value);
            }
        });*/
    }

    /**
     * 设置右边Y轴
     */
    private void setRightAxis() {
        //右边Y轴对象实例
        mRightAxis = mLineChart.getAxisRight();
        //是否启用该轴
        mRightAxis.setEnabled(false);
        // 保证Y轴从0开始，不然会上移一点
        mRightAxis.setAxisMinimum(0f);
        // 设置y轴的刻度数
        //mRightAxis.setLabelCount(8, false);
    }

    /**
     * 设置左边Y轴
     */
    private void setLeftAxis() {
        //启用该轴
        mLeftAxis.setEnabled(true);
//        // 保证Y轴从0开始，不然会上移一点
//        mLeftAxis.setAxisMinimum(0f);
//        // 设置y轴的刻度数,可以不设置，自动适应
//        mLeftAxis.setLabelCount(10, false);
        //设置Y轴的值显示在外面
        mLeftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        //设置坐标轴宽度
        //mLeftAxis.setAxisLineWidth(5f);
        //设置轴上最高位置在表中最高位置的顶部间距，占总轴的百分比。
        // mLeftAxis.setSpaceTop(20f);

        //设置Y轴网格线为虚线
        mLeftAxis.enableGridDashedLine(10f, 10f, 0f);

        //格式化y抽显示的值
        mLeftAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                if (value < 10000) {
                    return ((int) value) + "";
                } else {
                    return ((int) (value / 10000)) + "  万";
                }
            }
        });
    }

    /**
     * @param xAxisValues x轴的值
     * @param yAxisValues y轴的值
     */
    public void showLineChart(final List<String> xAxisValues, List<Float> yAxisValues) {

        //点击弹窗显示值
        showMarkerView(xAxisValues);

        //通过下面两句代码实现左右滚动
        // float ratio = (float) yAxisValues.size() / (float) 10;//我默认手机屏幕上显示10剩下的滑动直方图然后显示。
        // 假如要显示25个 那么除以10 就是放大2.5f
        //mLineChart.zoom(ratio, 1f, 0, 0);//显示的时候是按照多大的比率缩放显示  1f表示不放大缩小

        //设置x轴的值
        mXAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisValues));

        // 设置X轴的刻度数，第二个参数表示是否平均分配
        mXAxis.setLabelCount(xAxisValues.size(), false);

        //y轴的值
        ArrayList<Entry> entries = new ArrayList<>();
        for (int i = 0; i < yAxisValues.size(); i++) {
            entries.add(new BarEntry(i, yAxisValues.get(i)));
        }
        //LineDataSet lineDataSet = new LineDataSet(entries, "The year 2018");
        LineDataSet lineDataSet = new LineDataSet(entries, null);

        //显示填充颜色
        lineDataSet.setDrawFilled(true);
        //设置填充颜色
        // lineDataSet.setFillColor(Color.RED);
        //lineDataSet.setFillAlpha(100);

        //设置填充背景图
        //Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.fade_linechart)
        //lineDataSet.setFillDrawable(drawable)

        //是否显示折线图上的值
        lineDataSet.setDrawValues(false);

        //lineDataSet.setDrawIcons(true);

        //折线图上的值保留几位小数
        //lineDataSet.setValueFormatter(new DefaultValueFormatter(2));

        //折线图颜色
        //lineDataSet.setColor(Color.RED);

        //折线图上值的颜色
        //lineDataSet.setValueTextColor(Color.RED);

        //lineDataSet.setValueTextSize(20f);

        //lineDataSet.setLineWidth(1f);

        lineDataSet.setDrawCircles(true);
        //lineDataSet.setCircleColor(Color.RED);
        //lineDataSet.setCircleRadius(3f);
        //设置曲线值的圆点是实心还是空心
        lineDataSet.setDrawCircleHole(true);

        // lineDataSet.setFormLineWidth(1f);
        //lineDataSet.setFormSize(15.f);

        //线模式为圆滑曲线（默认折线）
        lineDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
//        lineDataSet.setMode(LineDataSet.Mode.LINEAR);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);

        //是否显示折线图上的值,默认显示
        //lineDataSet.setDrawValues(true);

        LineData data = new LineData(dataSets);

        //设置折线图不能点击,默认可点击
        data.setHighlightEnabled(true);

        //设置折线图大小
        // data.setValueTextSize(20f);
        // 折线图上值的颜色
        //data.setValueTextColor(Color.RED);
        mLineChart.setData(data);
    }

    /**
     * 设置图例
     */
    private void setLegend(boolean isShowLegend) {
        //图例相关设置
        mLegend = mLineChart.getLegend();
        //是否显示图例
        mLegend.setEnabled(isShowLegend);
        //显示位置, 左下方
        mLegend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        mLegend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        mLegend.setOrientation(Legend.LegendOrientation.HORIZONTAL);

        //是否绘制在图表里面
        mLegend.setDrawInside(false);

        //设置图例形状
        mLegend.setForm(Legend.LegendForm.SQUARE);
        mLegend.setFormSize(9f);
        mLegend.setTextSize(11f);
        mLegend.setXEntrySpace(4f);

        //设置标签是否换行（当多条标签时 需要换行显示）
        mLegend.setWordWrapEnabled(true);
    }



    /**
     * 点击弹窗显示值
     *
     * @param xAxisValues
     */
    private void showMarkerView(List<String> xAxisValues) {
        //创建一个定制的MarkerView(扩展MarkerView) 并指定布局。
        //MyMarkerView mv = new MyMarkerView(mContext, R.layout.custom_marker_view);
        LineChartMarkView mv = new LineChartMarkView(mContext, mXAxis.getValueFormatter());

        String[] array = new String[xAxisValues.size()];
        for (int i = 0; i < xAxisValues.size(); i++) {
            array[i] = xAxisValues.get(i);
        }
        mv.setxNames(array);
        mv.setChartView(mLineChart);
        mLineChart.setMarker(mv);
    }

    /**
     * 设置限制线
     */
    private void setLimitLine() {
        // x轴限制线
        LimitLine llXAxis = new LimitLine(10f, "Index 10");
        llXAxis.setLineWidth(4f);
        llXAxis.enableDashedLine(10f, 10f, 0f);
        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        llXAxis.setTextSize(10f);
        mXAxis.enableGridDashedLine(10f, 10f, 0f);

        LimitLine ll1 = new LimitLine(150f, "Upper Limit");
        ll1.setLineWidth(4f);
        ll1.enableDashedLine(10f, 10f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll1.setTextSize(10f);

        LimitLine ll2 = new LimitLine(-30f, "Lower Limit");
        ll2.setLineWidth(4f);
        ll2.enableDashedLine(10f, 10f, 0f);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll2.setTextSize(10f);

        mLeftAxis.removeAllLimitLines(); // 重置所有限制线以避免重叠线。
        mLeftAxis.addLimitLine(ll1);
        mLeftAxis.addLimitLine(ll2);
        mLeftAxis.setAxisMaximum(200f);
        mLeftAxis.setAxisMinimum(-50f);
        //mLeftAxis.setYOffset(20f);
        mLeftAxis.enableGridDashedLine(10f, 10f, 0f);
        mLeftAxis.setDrawZeroLine(false);

        // 限制线绘制在数据后面(而不是顶部)
        mLeftAxis.setDrawLimitLinesBehindData(true);
    }
}

