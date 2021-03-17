package com.example.app16.ui.main; 

import java.util.ArrayList;
import java.util.HashMap; 
import java.util.StringTokenizer;
import java.util.Date; 
import java.text.DateFormat; 
import java.text.SimpleDateFormat; 


public class DateComponent
{
    //获取时间
    public static long getEpochSeconds(String date)
  {
      DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

      try
      { Date d = df.parse(date);
      //应该是换算成秒数
      long time = d.getTime()/1000;
	  return time;
	  }
      catch (Exception _e) { return -1; }
  }

  public static long getEpochMilliseconds(String format, String date)
  {
      DateFormat df = new SimpleDateFormat(format);

      try
      { Date d = df.parse(date);
      long time = d.getTime(); 
	  return time;
      } catch (Exception _e) { return -1; }
  }

  public static long getTime()
  {
      Date d = new Date();
      return d.getTime();
  }

}