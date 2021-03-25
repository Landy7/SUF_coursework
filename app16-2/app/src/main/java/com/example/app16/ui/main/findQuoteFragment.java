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
    //startdate  button
    Button btnStart;
    //the ouput of startDate
    TextView et1;
    //Enddate button
    Button btnEnd;
    //the ouput of enddate
    TextView et2;

    //startdate
    String findQuotedateDataStart = "";
    //enddate
    String findQuotedateDataEnd = "";
    TextView findQuoteResult;
    Button findQuoteOkButton;
    Button findQuotecancelButton;


 public findQuoteFragment() {}


  public findQuoteFragment newInstance(Context c) {

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

     root = inflater.inflate(R.layout.findquote_layout, container, false);
     Bundle data = getArguments();

      //modify
//     findQuotedateTextField = (EditText) root.findViewById(R.id.findQuotedateField);
     findQuoteResult = (TextView) root.findViewById(R.id.findQuoteResult);
     findquotebean = new findQuoteBean(myContext);
     //Button findquote
     findQuoteOkButton = root.findViewById(R.id.findQuoteOK);
     //set listener
     findQuoteOkButton.setOnClickListener(this);
     //button cancel
     findQuotecancelButton = root.findViewById(R.id.findQuoteCancel);
      //set listener
     findQuotecancelButton.setOnClickListener(this);

     //modify---Start Date and data input
      btnStart = (Button) root.findViewById(R.id.dateBtnStart);
      et1 = (TextView) root.findViewById(R.id.et1);

      btnStart.setOnClickListener(new View.OnClickListener() {
          //open Calendar
          Calendar c = Calendar.getInstance();

          @Override
          public void onClick(View v) {
              // show the date, choose the true
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

      //End date
      btnEnd = (Button) root.findViewById(R.id.dateBtnEnd);
      et2 = (TextView) root.findViewById(R.id.et2);

      btnEnd.setOnClickListener(new View.OnClickListener() {
          //open Calendar
          Calendar c = Calendar.getInstance();

          @Override
          public void onClick(View v) {
              // show the date, choose the true
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




//get the button from clients ---cancel or findquote
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
//      DailyQuote findQuote = new DailyQuote();
//      if(DailyQuote.DailyQuote_allInstances != null && DailyQuote.DailyQuote_index != null){
//          DailyQuote.DailyQuote_allInstances.clear();
//          DailyQuote.DailyQuote_index.clear();
//      }
      //get the start date
    findQuotedateDataStart = et1.getText() + "";

    //get the end date
    findQuotedateDataEnd = et2.getText() + "";

    //set startdate
    findquotebean.setStartdate(findQuotedateDataStart);
      //set enddate
     findquotebean.setEnddate(findQuotedateDataEnd);

    if (findquotebean.isfindQuoteerror())
    { Log.w(getClass().getName(), findquotebean.errors());
      Toast.makeText(myContext, "Errors: " + findquotebean.errors(), Toast.LENGTH_LONG).show();
    }
    else
    {   //get the result
        findQuoteResult.setText(findquotebean.findQuote() + ""); }
  }



  //reset data
  public void findQuoteCancel(View _v)
  {
      //reset data
      findquotebean.resetDate();
      //start date
      et1.setText("");
      et2.setText("");
      findQuoteResult.setText("");
  }
}
