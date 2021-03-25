package com.example.app16.ui.main;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class ModelFacade implements InternetCallback
{ 
  FileAccessor fileSystem;
  Context myContext;
  //initial instance---modify----not static
//  static ModelFacade instance = null;
  public static ModelFacade instance = null;

  public static ArrayList<DailyQuote> dailyQuotes = new ArrayList<DailyQuote>();

  public static String originalStartDate;

  //modify(not static)
  public static ModelFacade getInstance(Context context)
  {
    //if instanceis null，那么initial modelFacade
    if (instance == null)
    { instance = new ModelFacade(context); }
    return instance;
  }


  private ModelFacade(Context context)
  { myContext = context; 
    fileSystem = new FileAccessor(context); 
  }





  //obtain dataset
  public ArrayList<DailyQuote> internetAccessCompleted(String response)
  {
    System.out.println("from aysTask to DailyQuote_DAO make from csv");
    dailyQuotes = DailyQuote_DAO.makeFromCSV(response);
    System.out.println("complete obtain the all dailyQuote");
    return dailyQuotes;
  }

  //find the quote price in period of time
  public String findQuote(String Startdate, String EndDate)
  {
    DailyQuote_DAO dailyQuote_dao = new DailyQuote_DAO();
    //modify
    DateComponent dateComponent = new DateComponent();
    //result input
    String result = "";

      long t1 = 0;
      //get time
      originalStartDate = Startdate;
      t1 = dateComponent.getEpochSeconds(Startdate) - 40 * 24 * 60 * 60;
      long t2 = dateComponent.getEpochSeconds(EndDate);
      //two years
      if(t2 - t1 > 63072000){
        result = "over 2 years";
      }
      else if(t2 < dateComponent.getEpochSeconds(Startdate)){
        result = "wrong date,please try again";
      }
      else if(t1 == -1 || t2 == -1){
        result = "the date is null, try it again";
      }
      else{
//      long t3 = t2 - t1;
//      long t4 = t1 + t3;
//      long t5 = t1 + 20 * 86400;
//     t1 add 7 date  86400 = 24 * 60 * 60
      String url = "";

      ArrayList<String> sq1 = null;
      sq1 = Ocl.copySequence(Ocl.initialiseSequence("period1","period2","interval","events"));
      ArrayList<String> sq2 = null;
      sq2 = Ocl.copySequence(Ocl.initialiseSequence((t1 + ""),(t2 + ""),"1d","history"));
      //get url
      url = dailyQuote_dao.getURL("GBPUSD=X", sq1, sq2);
      InternetAccessor x = null;
      x = new InternetAccessor();
      x.setDelegate(this);
      x.execute(url);
      result = ("Called url: " + url);

      //get url
      System.out.println(result);}

      return result;

//         }
  }
  public static void clearData()
  {
    dailyQuotes.clear();
  }



}
