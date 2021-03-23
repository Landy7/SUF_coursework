package com.example.app16.ui.main;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.app16.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;


//分析页面
public class analyseFragment extends Fragment implements OnClickListener {
    View root;
    Context myContext;
    analyseBean analysebean;
    HorizontalScrollView scroll_view;

    ImageView analyseResult;
    Button analyseOkButton;
    Button analysecancelButton;
    Button download;
    //加一个按钮
    Button changeButton;
    LineChart hello;
    LineChart mLineChart;

    public analyseFragment() {
    }

    //modify
    public analyseFragment newInstance(Context c) {
        analyseFragment fragment = new analyseFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        fragment.myContext = c;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("WrongViewCast")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.analyse_layout, container, false);
        Bundle data = getArguments();
//        scroll_view = root.findViewById(R.id.sc);
        //analyse之后的图像id
//    analyseResult = (ImageView) root.findViewById(R.id.analyseResult);
        //linechart--cancel
        hello = root.findViewById(R.id.lineChart1);
        //linechart--normal
        mLineChart = root.findViewById(R.id.lineChart);
        //实例化获取instance
        analysebean = new analyseBean(myContext);
        //analyse按钮
        analyseOkButton = root.findViewById(R.id.analyseOK);
        //设置监听
        analyseOkButton.setOnClickListener(this);
        //analyse_cancel按钮
        analysecancelButton = root.findViewById(R.id.analyseCancel);
        //设置监听
        analysecancelButton.setOnClickListener(this);
        //download按钮
        download = root.findViewById(R.id.download);
        //设置监听
        download.setOnClickListener(this);
        //加一个按钮监听器
        changeButton = root.findViewById(R.id.indicators);
        changeButton.setOnClickListener(this);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    //根据点击按钮的不同来调用不同函数
    public void onClick(View _v) {
        InputMethodManager _imm = (InputMethodManager) myContext.getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
        try {
            _imm.hideSoftInputFromWindow(_v.getWindowToken(), 0);
        } catch (Exception _e) {
        }
        if (_v.getId() == R.id.analyseOK) {
            try {
                analyseOK(_v, "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (_v.getId() == R.id.analyseCancel) {
            analyseCancel(_v);
        } else if (_v.getId() == R.id.download) {
            download(_v);
        }
        //加一个控制响应
        else if (_v.getId() == R.id.indicators) {
            change(_v);
        }
    }

    //下载数据
    public void download (View _v){
        showDialog();
        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        System.out.println("enter");
        FileAccessor fileAccessor = new FileAccessor(myContext);
        fileAccessor.initData();
        System.out.println("complete download");


    }
    //download提示框
    private void showDialog () {
        AlertDialog.Builder builder = new AlertDialog.Builder(myContext);
        builder.setTitle("Message");
        if (ModelFacade.dailyQuotes.isEmpty() || ModelFacade.dailyQuotes == null) {
            builder.setMessage("no data, cannot be download");
        } else {
            builder.setMessage("success");
        }
        builder.setPositiveButton("ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();

    }
    //data null
    private void showDialog2 () {
        AlertDialog.Builder builder = new AlertDialog.Builder(myContext);
        builder.setTitle("ERROR!");
        builder.setMessage("the data is null, please try again!");
        builder.setPositiveButton("ok",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();

    }


    public void analyseOK(View _v, String selected) throws Exception {
        System.out.println("enter the analyse");
        if (ModelFacade.dailyQuotes == null || ModelFacade.dailyQuotes.isEmpty()) {
            Toast.makeText(getActivity(), "暂无数据", Toast.LENGTH_SHORT).show();
            showDialog2();
            return;
        }
        //多条线
        List<ILineDataSet> sets = new ArrayList<>();
        mLineChart.setVisibility(_v.VISIBLE);
        //layout不显示
        hello.setVisibility(_v.INVISIBLE);
//        LineChart mLineChart = (LineChart) root.findViewById(R.id.lineChart);

        //显示边界
        mLineChart.setDrawBorders(true);
        // 触摸
        mLineChart.setTouchEnabled(true);
        // 拖拽
        mLineChart.setDragEnabled(true);
        // 缩放
        mLineChart.setScaleEnabled(true);
        Matrix m = new Matrix();
        m.postScale(1.5f, 1f);//两个参数分别是x,y轴的缩放比例。例如：将x轴的数据放大为之前的1.5倍
        mLineChart.getViewPortHandler().refresh(m, mLineChart, false);//将图表动画显示之前进行缩放
        //设置数据---normal
        List<Entry> entries = new ArrayList<>();
        List<String> xList = new ArrayList<>();
        // 所选日期之前多获取的数据
        List<Double> preQuotes = new ArrayList<>();
        // 所选日期区间内的数据
        List<Double> postQuotes = new ArrayList<>();
        for (int i = 0; i < ModelFacade.dailyQuotes.size(); i++) {
            DailyQuote dailyQuote = ModelFacade.dailyQuotes.get(i);
            DateComponent dateComponent = new DateComponent();
            if (dateComponent.getEpochSeconds(dailyQuote.date) < dateComponent.getEpochSeconds(ModelFacade.originalStartDate)) {
                preQuotes.add(dailyQuote.close);
            } else {
                postQuotes.add(dailyQuote.close);
                xList.add(dailyQuote.getDate());
            }
        }
//        LineData data = new LineData(lineDataSet);

        List<Entry> entries_1 = new ArrayList<>();
        List<Double> prices = preQuotes.subList(preQuotes.size() - 25, preQuotes.size());
        prices.addAll(postQuotes);
        double[] priceArray = new double[prices.size()];
        for(int i = 0; i < prices.size(); i++) {
            priceArray[i] = prices.get(i);
        }
        double[] results = new double[prices.size()];

        // 根据选中项调用不同的公式
        if (selected == "SMA") {
            results = new SimpleMovingAverage().calculate(priceArray, 25).getSMA();
        } else if (selected == "EMA") {
            results = new ExponentialMovingAverage().calculate(priceArray, 25).getEMA();
        } else if (selected == "MACD") {
            results = new MovingAverageConvergenceDivergence().calculate(priceArray, 11, 25).getMACD();
        }

        for (int i = 0; i < postQuotes.size(); i++) {
            entries.add(new Entry(i, (float) priceArray[25 + i]));
            entries_1.add(new Entry(i, (float) results[25 + i]));
        }
        //一个LineDataSet就是一条线
        LineDataSet lineDataSet = new LineDataSet(entries, "share");
        sets.add(lineDataSet);
        //一个LineDataSet就是一条线
        if (selected.length() > 0) {
            LineDataSet lineDataSet_1 = new LineDataSet(entries_1, selected);
            sets.add(lineDataSet_1);
            lineDataSet_1.setCircleColor(Color.BLACK);
            lineDataSet_1.setColor(Color.YELLOW);
        }
//        LineData data_1 = new LineData(lineDataSet_1);
            LineData lineData = new LineData(sets);
            //显示纵坐标
            lineData.setDrawValues(true);

        XAxis xAxis = mLineChart.getXAxis();

        String[] xArray = xList.toArray(new String[0]);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(xArray.length, false);
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum((float) xArray.length);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xArray));
        System.out.println("xAis" + xAxis);
        xAxis.setLabelRotationAngle(30); //标签倾斜
        int xScale = xArray.length / 8;
        m.postScale(Math.max(xScale, 1), 1f);
        mLineChart.getViewPortHandler().refresh(m, mLineChart, false);

        mLineChart.setData(lineData);
        mLineChart.setExtraBottomOffset(10f);
        mLineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);//设置X轴的位置 默认在上方
        // 沿x轴动画，时间2000毫秒。
        mLineChart.animateX(1000);
//=======
//            //多条线
//            List<ILineDataSet> sets = new ArrayList<>();
//            mLineChart.setVisibility(_v.VISIBLE);
////        LineChart mLineChart = (LineChart) root.findViewById(R.id.lineChart);
//
//            //显示边界
//            mLineChart.setDrawBorders(true);
//            // 触摸
//            mLineChart.setTouchEnabled(true);
//            // 拖拽
//            mLineChart.setDragEnabled(true);
//            // 缩放
//            mLineChart.setScaleEnabled(true);
//            Matrix m = new Matrix();
//            m.postScale(1.5f, 1f);//两个参数分别是x,y轴的缩放比例。例如：将x轴的数据放大为之前的1.5倍
//            mLineChart.getViewPortHandler().refresh(m, mLineChart, false);//将图表动画显示之前进行缩放
//            //设置数据---normal
//            List<Entry> entries = new ArrayList<>();
//            List<String> xList = new ArrayList<>();
//            for (int i = 0; i < ModelFacade.dailyQuotes.size(); i++) {
//                DailyQuote dailyQuote = ModelFacade.dailyQuotes.get(i);
//                entries.add(new Entry(i, (float) dailyQuote.getClose()));
//                xList.add(dailyQuote.getDate());
//            }
//            //一个LineDataSet就是一条线
//            LineDataSet lineDataSet = new LineDataSet(entries, "share");
//            sets.add(lineDataSet);
//            LineData data = new LineData(lineDataSet);
//
//            //test_entry
//            List<Entry> entries_1 = new ArrayList<>();
//            for (int i = 0; i < ModelFacade.dailyQuotes.size(); i++) {
//                DailyQuote dailyQuote = ModelFacade.dailyQuotes.get(i);
//                entries_1.add(new Entry(i, (float) 1.35));
//            }
//            //一个LineDataSet就是一条线
//            LineDataSet lineDataSet_1 = new LineDataSet(entries_1, "share");
//            sets.add(lineDataSet_1);
//            //设置折线的颜色
//            lineDataSet_1.setColor(Color.YELLOW);
//            //圆点颜色
//            lineDataSet_1.setCircleColor(Color.BLACK);
//
//            //test_entry
//            List<Entry> entries_2 = new ArrayList<>();
//            for (int i = 0; i < ModelFacade.dailyQuotes.size(); i++) {
//                DailyQuote dailyQuote = ModelFacade.dailyQuotes.get(i);
//                entries_2.add(new Entry(i, (float) 1.40));
//            }
//            //一个LineDataSet就是一条线
//            LineDataSet lineDataSet_2 = new LineDataSet(entries_2, "share");
//            sets.add(lineDataSet_2);
//            //不显示折线
//            lineDataSet_2.setVisible(true);
//            //设置折线的颜色
//            lineDataSet_2.setColor(Color.YELLOW);
//            //圆点颜色
//            lineDataSet_2.setCircleColor(Color.BLACK);
//
//            LineData lineData = new LineData(sets);
//            //显示纵坐标
//            lineData.setDrawValues(true);

//            XAxis xAxis = mLineChart.getXAxis();
//
//            String[] xArray = xList.toArray(new String[0]);
//            xAxis.setGranularity(1f);
//            xAxis.setLabelCount(xArray.length, false);
//            xAxis.setAxisMinimum(0f);
//            xAxis.setAxisMaximum((float) xArray.length);
//            xAxis.setValueFormatter(new IndexAxisValueFormatter(xArray));
//            System.out.println("xAis" + xAxis);
//            xAxis.setLabelRotationAngle(30); //标签倾斜
//            int xScale = xArray.length / 8;
//            m.postScale(Math.max(xScale, 1), 1f);
//            mLineChart.getViewPortHandler().refresh(m, mLineChart, false);
//
//            mLineChart.setData(lineData);
//            mLineChart.setExtraBottomOffset(10f);
//            mLineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);//设置X轴的位置 默认在上方
//            // 沿x轴动画，时间2000毫秒。
//            mLineChart.animateX(1000);
//>>>>>>>Stashed changes
        }


//  @SuppressLint("WrongViewCast")
//  public void analyseOK(View _v)
//  {
//    if (analysebean.isanalyseerror())
//    { Log.w(getClass().getName(), analysebean.errors());
//      Toast.makeText(myContext, "Errors: " + analysebean.errors(), Toast.LENGTH_LONG).show();
//    }
//    else
//    {
////        //显示图像折线图
////        GraphDisplay _result = analysebean.analyse();
////
////        analyseResult.invalidate();
////        analyseResult.refreshDrawableState();
////        analyseResult.setImageDrawable(_result);
////        analyseResult.setScaleType(ImageView.ScaleType.FIT_XY);
//        LineChart mLineChart =root.findViewById(R.id.lineChart);
//        //显示边界
//        mLineChart.setDrawBorders(true);
//        //设置数据
//        List<Entry> entries = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            entries.add(new Entry(i, (float) (Math.random()) * 80));
//        }
//        //一个LineDataSet就是一条线
//        LineDataSet lineDataSet = new LineDataSet(entries, "温度");
//        LineData data = new LineData(lineDataSet);
//        mLineChart.setData(data);
//    }
//  }


        public void change (View _v){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Holo_Light_Dialog);
            //builder.setIcon(R.drawable.ic_launcher);
            builder.setTitle("choose indicator");
            //    指定下拉列表的显示数据SMA,
            //EMA, MACD, MACDAVG
            final String[] indicator = {"SMA", "EMA", "MACD", "MACDAVG"};
            //    设置一个下拉的列表选择项
            builder.setItems(indicator, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Toast.makeText(getActivity(), "Choice is：" + indicator[which], Toast.LENGTH_SHORT).show();
                    try {
                        analyseOK(_v, indicator[which]);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            builder.show();


        }

        //这个功能有问题---modify
        public void analyseCancel (View _v){
            ModelFacade.clearData();

            mLineChart.setVisibility(_v.INVISIBLE);
            //layout显示
            hello.setVisibility(_v.VISIBLE);

        }
    }
