package com.example.app16.ui.main;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.app16.R;
import android.content.Context;
import android.view.View.OnClickListener;

import java.util.Calendar;

import android.view.View;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.TextView;


public class findQuoteFragment extends Fragment implements OnClickListener {

    View root;
    Context myContext;
    findQuoteBean findquotebean;
    //modify
    //开始日期button
    Button btnStart;
    //开始日期的输出条
    TextView et1;
    //结束日期button
    Button btnEnd;
    //结束日期的输出条
    TextView et2;

    //date editText
//    EditText findQuotedateTextFieldStart;
//    EditText findQuotedateTextFieldEnd;
    //开始时间字符串
    String findQuotedateDataStart = "";
    //结束时间字符串
    String findQuotedateDataEnd = "";
    TextView findQuoteResult;
    Button findQuoteOkButton;
    Button findQuotecancelButton;


 public findQuoteFragment() {}

  public static findQuoteFragment newInstance(Context c) {

      findQuoteFragment fragment = new findQuoteFragment();
      Bundle args = new Bundle();
      fragment.setArguments(args);
      fragment.myContext = c;
      return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

     //确定root
     root = inflater.inflate(R.layout.findquote_layout, container, false);
     Bundle data = getArguments();
     //获取客户所输入的各个信息

      //modify
//     findQuotedateTextField = (EditText) root.findViewById(R.id.findQuotedateField);
     findQuoteResult = (TextView) root.findViewById(R.id.findQuoteResult);
     //实例化对象
     findquotebean = new findQuoteBean(myContext);
     //Button findquote
     findQuoteOkButton = root.findViewById(R.id.findQuoteOK);
     //设置监听
     findQuoteOkButton.setOnClickListener(this);
     //button cancel
     findQuotecancelButton = root.findViewById(R.id.findQuoteCancel);
     //设置监听
     findQuotecancelButton.setOnClickListener(this);

     //modify---开始时间和输出条
      btnStart = (Button) root.findViewById(R.id.dateBtnStart);
      et1 = (TextView) root.findViewById(R.id.et1);

      btnStart.setOnClickListener(new View.OnClickListener() {
          //打开日历
          Calendar c = Calendar.getInstance();

          @Override
          public void onClick(View v) {
              // 最后一个false表示不显示日期，如果要显示日期，最后参数可以是true或者不用输入
              new DoubleDatePickerDialogStart(myContext, 0, new DoubleDatePickerDialogStart.OnDateSetListenerStart() {

                  @Override
                  public void onDateSetStart(DatePicker startDatePicker, int startYear, int startMonthOfYear,
                                        int startDayOfMonth) {
                      String textString = String.format("%d-%d-%d\n", startYear,
                              startMonthOfYear + 1, startDayOfMonth);
                      et1.setText(textString);
                  }
              }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), true).show();
          }
      });

      //结束时间和输出条
      btnEnd = (Button) root.findViewById(R.id.dateBtnEnd);
      et2 = (TextView) root.findViewById(R.id.et2);

      btnEnd.setOnClickListener(new View.OnClickListener() {
          //打开日历
          Calendar c = Calendar.getInstance();

          @Override
          public void onClick(View v) {
              // 最后一个false表示不显示日期，如果要显示日期，最后参数可以是true或者不用输入
              new DoubleDatePickerDialogEnd(myContext, 0, new DoubleDatePickerDialogEnd.OnDateSetListenerEnd() {

                  @Override
                  public void onDateSetEnd(DatePicker endDatePicker, int endYear, int endMonthOfYear,
                                             int endDayOfMonth) {
                      String textString = String.format("%d-%d-%d\n", endYear,
                              endMonthOfYear + 1, endDayOfMonth);
                      et2.setText(textString);
                  }
              }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), true).show();
          }
      });


     return root;
  }




//获取客户点击的按钮----findquote还是cancel
  public void onClick(View _v)
  { InputMethodManager _imm = (InputMethodManager) myContext.getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
    try { _imm.hideSoftInputFromWindow(_v.getWindowToken(), 0); } catch (Exception _e) { }

    if (_v.getId() == R.id.findQuoteOK)
    { findQuoteOK(_v); }
    else if (_v.getId() == R.id.findQuoteCancel)
    { findQuoteCancel(_v); }


  }

  public void findQuoteOK(View _v) 
  {
      if(DailyQuote.DailyQuote_allInstances != null && DailyQuote.DailyQuote_index != null){
          DailyQuote.DailyQuote_allInstances.clear();
          DailyQuote.DailyQuote_index.clear();
      }
      //获取开始时间--modify
    findQuotedateDataStart = et1.getText() + "";

    //获取结束时间--modify
    findQuotedateDataEnd = et2.getText() + "";

    //设置时间
    findquotebean.setStartdate(findQuotedateDataStart);
      //设置时间
     findquotebean.setEnddate(findQuotedateDataEnd);

    //检查时间是否有效 + 间隔相差大于两年的date无效--没有设计
    if (findquotebean.isfindQuoteerror())
    { Log.w(getClass().getName(), findquotebean.errors());
      Toast.makeText(myContext, "Errors: " + findquotebean.errors(), Toast.LENGTH_LONG).show();
    }
    else
    {   //Result显示在app上
        findQuoteResult.setText(findquotebean.findQuote() + ""); }
  }



  //重置时间
  public void findQuoteCancel(View _v)
  {
      //重置data
      findquotebean.resetData();
      //开始时间
      et1.setText("");
      et2.setText("");
      findQuoteResult.setText("");
  }
}
