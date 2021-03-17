package com.example.app16.ui.main;

import android.content.Context;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelFacade implements InternetCallback
{ 
  FileAccessor fileSystem;
  Context myContext;
  //初始化instance
  static ModelFacade instance = null; 

  public static ModelFacade getInstance(Context context)
  {
    //如果instance为空，那么就实例化modelFacade
    if (instance == null)
    { instance = new ModelFacade(context); }
    return instance;
  }


  private ModelFacade(Context context)
  { myContext = context; 
    fileSystem = new FileAccessor(context); 
  }



  //应该是获得delegate调用的interface的方法
  //可能是mydata
  public void internetAccessCompleted(String response)
  { 
    DailyQuote_DAO.makeFromCSV(response);

  }

  //查找一段时间的quote price
  public String findQuote(String Startdate, String EndDate)
  {
    //result输出
    String result = "";
    //看是否日期已经存在
    if (DailyQuote_DAO.isCached(Startdate) || DailyQuote_DAO.isCached(EndDate))
    {
      result = "Data already exists";
    return result;
    }
    else {
      long t1 = 0;
      //获取时间
      t1 = DateComponent.getEpochSeconds(Startdate);
      long t2 = DateComponent.getEpochSeconds(EndDate);
      long t3 = t2 - t1;
      long t4 = t1 + t3;
      long t5 = t1 + 7 * 86400;
//     t1加7天 86400 = 24 * 60 * 60
      String url = "";

      ArrayList<String> sq1 = null;
      sq1 = Ocl.copySequence(Ocl.initialiseSequence("period1","period2","interval","events"));
      ArrayList<String> sq2 = null;
      sq2 = Ocl.copySequence(Ocl.initialiseSequence((t1 + ""),(t5 + ""),"1d","history"));
      //获取url
      url = DailyQuote_DAO.getURL("GBPUSD=X", sq1, sq2);
      InternetAccessor x = null;
      x = new InternetAccessor();
      x.setDelegate(this);
      x.execute(url);
      result = ("Called url: " + url);

      //获得一串url
      return result;

         }
  }

  //这一步应该就是设置图---modify
  public GraphDisplay analyse()
  {

    GraphDisplay result = null;
    result = new GraphDisplay();

    ArrayList<GraphDisplay> result_list = new ArrayList<>();


    ArrayList<DailyQuote> quotes = null;

//    //实例化了一个对象dailyQuote---modify 原本DailyQuote_allInstances是静态的---这里可能会有问题
//    DailyQuote dailyQuote = new DailyQuote();

    //获取了DailyQuote的全部实例
    quotes = Ocl.copySequence(DailyQuote.DailyQuote_allInstances);


    ArrayList<String> xnames = null;
    //x显示的日期---返回DailyQuote中的date attribute
    xnames = Ocl.copySequence(Ocl.collectSequence(quotes,(q)->{return q.date;}));
    ArrayList<Double> yvalues = null;
    yvalues = Ocl.copySequence(Ocl.collectSequence(quotes,(q)->{return q.close;}));

//    ArrayList<String> xnames2 = null;
//    //x显示的日期---返回DailyQuote中的date attribute
//    xnames2 = Ocl.copySequence(Ocl.collectSequence(quotes,(q)->{return q.date;}));
//    ArrayList<Double> yvalues2 = null;
//    yvalues2 = Ocl.copySequence(Ocl.collectSequence(quotes,(q)->{return 1.0;}));

    result.setXNominal(xnames);
    result.setYPoints(yvalues);
    //这里写if如果点了按钮就调用gd的方法来生成对应的线，返回一个result但这里还没有画图
    return result;

  }


}
