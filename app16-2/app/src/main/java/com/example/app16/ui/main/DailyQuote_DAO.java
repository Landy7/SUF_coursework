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
import java.util.StringTokenizer;
import java.util.Date; 
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.*;

public class DailyQuote_DAO {
  //获取URL---modify
  public String getURL(String command, ArrayList<String> pars, ArrayList<String> values) {

    String res = "https://query1.finance.yahoo.com/v7/finance/download/";

    //add GBPUSD=X
    if (command != null)
    { res = res + command; }

    if (pars.size() == 0)
    { return res; }

    res = res + "?";

    for (int i = 0; i < pars.size(); i++) {
      //par与value一一定义, period=t1&period=t2&.....
      String par = pars.get(i);
      String val = values.get(i); 
      res = res + par + "=" + val;
      if (i < pars.size() - 1)
      { res = res + "&"; }
    }
    return res;
  }

  //id传入为date---modify
//  public boolean isCached(String id) {
//    //DailyQuote_index是静态的---获取key值为date
//    //看是否ID存在---就是date存在
//    DailyQuote _x = DailyQuote.DailyQuote_index.get(id);
//    if (_x == null) { return false; }
//    return true;
//  }



  //modify
  public DailyQuote getCachedInstance(String id)
  { DailyQuote dailyQuote = new DailyQuote();
    return dailyQuote.DailyQuote_index.get(id); }



  //modify---把从网上获取的数据写入dailyQuote的各个属性
  public static DailyQuote parseCSV(String _line) {

    if (_line == null) { return null; }
    ArrayList<String> _line1vals = Ocl.tokeniseCSV(_line);
//    DailyQuote dailyquotex;
//    dailyquotex = DailyQuote.DailyQuote_index.get((String) _line1vals.get(0));
    //modify
    DailyQuote dailyquotex = new DailyQuote();
//    { dailyquotex = DailyQuote.createByPKDailyQuote((String) _line1vals.get(0)); }

    String date = _line1vals.get(0).equals("null") ? "0" : _line1vals.get(0);
    String open = _line1vals.get(1).equals("null") ? "0" : _line1vals.get(1);
    String high = _line1vals.get(2).equals("null") ? "0" : _line1vals.get(2);
    String low = _line1vals.get(3).equals("null") ? "0" : _line1vals.get(3);
    String close = _line1vals.get(4).equals("null") ? "0" : _line1vals.get(4);
    String adjclose = _line1vals.get(5).equals("null") ? "0" : _line1vals.get(5);
    String volume = _line1vals.get(6).equals("null") ? "0" : _line1vals.get(6);

    //index 0 是date
    System.out.println(_line1vals);
    dailyquotex.date = (String) date;
    dailyquotex.open = Double.parseDouble((String) open);
    dailyquotex.high = Double.parseDouble((String) high);
    dailyquotex.low = Double.parseDouble((String) low);
    dailyquotex.close = Double.parseDouble((String) close);
    dailyquotex.adjclose = Double.parseDouble((String) adjclose);
    dailyquotex.volume = Double.parseDouble((String) volume);
    return dailyquotex;
  }


//modify
  public static DailyQuote parseJSON(JSONObject obj) {

    if (obj == null) { return null; }
    try {
      String date = obj.getString("date");
//      DailyQuote _dailyquotex = DailyQuote.DailyQuote_index.get(date);
//      if (_dailyquotex == null) { _dailyquotex = DailyQuote.createByPKDailyQuote(date); }
      DailyQuote _dailyquotex = new DailyQuote();
      _dailyquotex.date = obj.getString("date");
      _dailyquotex.open = obj.getDouble("open");
      _dailyquotex.high = obj.getDouble("high");
      _dailyquotex.low = obj.getDouble("low");
      _dailyquotex.close = obj.getDouble("close");
      _dailyquotex.adjclose = obj.getDouble("adjclose");
      _dailyquotex.volume = obj.getDouble("volume");
      return _dailyquotex;
    }
    catch (Exception _e) { return null; }
  }



  public static ArrayList<DailyQuote> makeFromCSV(String lines) {

    ArrayList<DailyQuote> result = new ArrayList<DailyQuote>();
    if (lines == null)
    { return result; }

    ArrayList<String> rows = Ocl.parseCSVtable(lines);

    for (int i = 1; i < rows.size(); i++) {

      String row = rows.get(i);
      if (row == null || row.trim().length() == 0 || Ocl.tokeniseCSV(row).get(1).equals("null"))
      {

      }
      else
      { DailyQuote _x = parseCSV(row);
        if (_x != null)
        { result.add(_x); }
      }
    }
    //每一次获取的DailyQuote,都添加到了ArrayList
    return result;
  }



  public static ArrayList<DailyQuote> parseJSON(JSONArray jarray) {

    if (jarray == null) { return null; }
    ArrayList<DailyQuote> res = new ArrayList<DailyQuote>();

    int len = jarray.length();
    for (int i = 0; i < len; i++) {

      try {
        JSONObject _x = jarray.getJSONObject(i);
        if (_x != null) {
          DailyQuote _y = parseJSON(_x);
          if (_y != null) {
            res.add(_y);
          }
        }
      }
      catch (Exception _e) { }
    }
    return res;
  }


//
  public static JSONObject writeJSON(DailyQuote _x) {

    JSONObject result = new JSONObject();
    try {
       result.put("date", _x.date);
       result.put("open", _x.open);
       result.put("high", _x.high);
       result.put("low", _x.low);
       result.put("close", _x.close);
       result.put("adjclose", _x.adjclose);
       result.put("volume", _x.volume);
      } catch (Exception _e) { return null; }
    return result;
  }



  public static JSONArray writeJSONArray(ArrayList<DailyQuote> es) {

    JSONArray result = new JSONArray();
    for (int _i = 0; _i < es.size(); _i++) {
      DailyQuote _ex = es.get(_i);
      JSONObject _jx = writeJSON(_ex);
      if (_jx == null) { } 
      else {
        try {
        result.put(_jx);
      }
        catch (Exception _ee) { }
      }
    }
    return result;
  }


}
