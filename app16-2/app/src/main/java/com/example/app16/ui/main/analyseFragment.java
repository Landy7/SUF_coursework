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
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;


//analyse page
public class analyseFragment extends Fragment implements OnClickListener {
    View root;
    Context myContext;
    analyseBean analysebean;
    HorizontalScrollView scroll_view;

    ImageView analyseResult;
    Button analyseOkButton;
    Button analysecancelButton;
    Button download;
    //add a button
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
//    analyseResult = (ImageView) root.findViewById(R.id.analyseResult);
        //linechart--cancel
        hello = root.findViewById(R.id.lineChart1);
        //linechart--normal
        mLineChart = root.findViewById(R.id.lineChart);
        //get instance
        analysebean = new analyseBean(myContext);
        //analyse button
        analyseOkButton = root.findViewById(R.id.analyseOK);
        //set listener
        analyseOkButton.setOnClickListener(this);
        //analyse_cancel button
        analysecancelButton = root.findViewById(R.id.analyseCancel);
        //set listener
        analysecancelButton.setOnClickListener(this);
        //download button
        download = root.findViewById(R.id.download);
        //set listener
        download.setOnClickListener(this);
        //add the button
        changeButton = root.findViewById(R.id.indicators);
        changeButton.setOnClickListener(this);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    //choose different buttons to call different functions
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
            downloadFile(_v);
        }
        //add the control button
        else if (_v.getId() == R.id.indicators) {
            change(_v);
        }
    }

    //download data
    public void downloadFile (View _v){
        showDialog();
        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        System.out.println("enter");
        FileAccessor fileAccessor = new FileAccessor(myContext);
        fileAccessor.initData();
        System.out.println("complete download");


    }
    //download alert
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
            Toast.makeText(getActivity(), "no data", Toast.LENGTH_SHORT).show();
            showDialog2();
            return;
        }
        //set two lines---one is normal and another is calculated
        List<ILineDataSet> sets = new ArrayList<>();
        mLineChart.setVisibility(_v.VISIBLE);
        //layout invisible
        hello.setVisibility(_v.INVISIBLE);
//        LineChart mLineChart = (LineChart) root.findViewById(R.id.lineChart);

        //set border
        mLineChart.setDrawBorders(true);
        // touch
        mLineChart.setTouchEnabled(true);
        // drag
        mLineChart.setDragEnabled(true);
        // scale
        mLineChart.setScaleEnabled(true);
        Matrix m = new Matrix();
        //set the scale of X axis (1.5) and Y axis--normal
        m.postScale(1.5f, 1f);
        mLineChart.getViewPortHandler().refresh(m, mLineChart, false);
        //set data---normal
        List<Entry> entries = new ArrayList<>();
        List<String> xList = new ArrayList<>();
        // the data before the period of date
        List<Double> preQuotes = new ArrayList<>();
        // the data in choosing period of date
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

        // get the different formula
        if (selected == "SMA") {
            results = new SMA().calculate(priceArray, 25).getSMA();
        } else if (selected == "EMA") {
            results = new EMA().calculate(priceArray, 25).getEMA();
        } else if (selected == "MACD") {
//<<<<<<< Updated upstream
            results = new MACD().calculate(priceArray, 11, 25).getMACD();
//=======
//            results = new MovingAverageConvergenceDivergence().calculate(priceArray, 11, 25).getMACD();
//>>>>>>> Stashed changes
        } else if (selected == "MACDAVG") {
            results = new MACDAVG().calculate(priceArray,8,11, 25).getMACDAVG();
        }

        for (int i = 0; i < postQuotes.size(); i++) {
            entries.add(new Entry(i, (float) priceArray[25 + i]));
            entries_1.add(new Entry(i, (float) results[25 + i]));
        }
        //a LineDataSet is a line
        LineDataSet lineDataSet = new LineDataSet(entries, "share");
//<<<<<<< Updated upstream
        lineDataSet.setColor(Color.BLUE);
//=======
//
//
//        lineDataSet.setColor(Color.BLUE);
//         //the color of circle
//>>>>>>> Stashed changes
        lineDataSet.setCircleColor(Color.BLUE);
        sets.add(lineDataSet);
        if (selected.length() > 0) {
            LineDataSet lineDataSet_1 = new LineDataSet(entries_1, selected);
//<<<<<<< Updated upstream
            lineDataSet_1.setColor(Color.YELLOW);
//
            lineDataSet_1.setCircleColor(Color.YELLOW);
            sets.add(lineDataSet_1);
            lineDataSet_1.setCircleColor(Color.BLACK);
            lineDataSet_1.setColor(Color.YELLOW);
        }
//        LineData data_1 = new LineData(lineDataSet_1);
            LineData lineData = new LineData(sets);
            lineData.setValueFormatter(new MyValueFormatter());

            //show x axis
            lineData.setDrawValues(true);

        XAxis xAxis = mLineChart.getXAxis();

        String[] xArray = xList.toArray(new String[0]);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(xArray.length, false);
        xAxis.setAxisMinimum(0f);
        xAxis.setAxisMaximum((float) xArray.length);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xArray));
        System.out.println("xAis" + xAxis);
        xAxis.setLabelRotationAngle(30); //slope of label
        int xScale = xArray.length / 8;
        m.postScale(Math.max(xScale, 1), 1f);
        mLineChart.getViewPortHandler().refresh(m, mLineChart, false);



        mLineChart.setData(lineData);
        mLineChart.setExtraBottomOffset(10f);
        //the location of x axis
        mLineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        // animation，time 1000mm。
        mLineChart.animateX(1000);

        }



        public void change (View _v){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Holo_Light_Dialog);
            //builder.setIcon(R.drawable.ic_launcher);
            builder.setTitle("choose indicator");
            //SMA, EMA, MACD, MACDAVG
            final String[] indicator = {"SMA", "EMA", "MACD", "MACDAVG"};
            //  set a selecting list
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

        //if click the analyse cancel button, the Linechart would be hidden and the empty would be shown
        public void analyseCancel (View _v){
            ModelFacade.clearData();

            mLineChart.setVisibility(_v.INVISIBLE);
            //layout show
            hello.setVisibility(_v.VISIBLE);

        }
    }
