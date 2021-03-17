package com.example.app16.ui.main;

import java.util.*;
import java.util.HashMap;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.Collections;

class DailyQuote {

  //不应该为静态---modify
  static Map<String,DailyQuote> DailyQuote_index = new HashMap<String,DailyQuote>();
  static ArrayList<DailyQuote> DailyQuote_allInstances = new ArrayList<DailyQuote>();

//  Map<String,DailyQuote> DailyQuote_index = new HashMap<String,DailyQuote>();
//  ArrayList<DailyQuote> DailyQuote_allInstances = new ArrayList<DailyQuote>();

  //构造函数,添加DailyQuote给数组列表
  DailyQuote() { DailyQuote_allInstances.add(this); }

  static DailyQuote createDailyQuote() {

    DailyQuote result = new DailyQuote();
    return result;
  }

  //把时间和对应的dailyQuote用key-value存储
  String date = ""; /* primary */

  //获取日期和结果
  static DailyQuote createByPKDailyQuote(String datex) {

    //实例化一个dailyQuote
    DailyQuote result = new DailyQuote();
    //静态变量 类名.变量名
    //DailyQuote_index存储对应的date和result
//    DailyQuote.DailyQuote_index.put(datex,result);

    result.DailyQuote_index.put(datex,result);
    result.date = datex;
    return result;

  }
//attributes in DailyQuote
  double open = 0;
  double high = 0;
  double low = 0;
  double close = 0;
  double adjclose = 0;
  double volume = 0;
}

