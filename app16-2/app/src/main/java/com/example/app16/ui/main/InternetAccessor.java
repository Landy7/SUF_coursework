package com.example.app16.ui.main;

import android.os.AsyncTask;
import android.os.Bundle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

public class InternetAccessor extends AsyncTask<String, Void, String>
{  private InternetCallback delegate = null;
//modify
   private InternetAccessor instance = null;

   public void setDelegate(InternetCallback c)
   { delegate = c; }

   //modify
   public static InternetAccessor getInstance()
   {
       InternetAccessor internetAccessor = new InternetAccessor();
       if (internetAccessor.instance == null)
     { internetAccessor.instance = new InternetAccessor(); }
     return internetAccessor.instance; }

   @Override
   protected void onPreExecute() {}

   @Override
   protected String doInBackground(String... params)
   { String url = params[0];
     String myData = "";
     try { myData = fetchUrl(url); }
     catch (Exception _e)
     { delegate.internetAccessCompleted(null);
         System.out.println("hello_randy1");
       return null;
     }
     //获取到数据
     return myData;
   }

   //应该是访问改网址获得这段时间的数据
   private String fetchUrl(String url)
   { String urlContent = "";
     StringBuilder myStrBuff = new StringBuilder();

     try{
          URL myUrl = new URL(url);
          HttpURLConnection myConn = (HttpURLConnection) myUrl.openConnection();
          myConn.setRequestProperty("User-Agent", "");
          myConn.setRequestMethod("GET");
          myConn.setDoInput(true);
          myConn.connect();

          InputStream myInStrm = myConn.getInputStream();
          BufferedReader myBuffRdr = new BufferedReader(new InputStreamReader(myInStrm));

          while ((urlContent = myBuffRdr.readLine()) != null) {
              myStrBuff.append(urlContent + '\n');
          }
      } catch (IOException e) {
          delegate.internetAccessCompleted(null);
         System.out.println("hello_randy2");
          return null;
      }

      return myStrBuff.toString();
  }

  //最后结果
  @Override
  protected void onPostExecute(String result) {
      delegate.internetAccessCompleted(result);
      System.out.println("hello_randy3");
  }

  @Override
  protected void onProgressUpdate(Void... values) {}
 }

 //modify 接口---异步处理成功后进入makeFromCSV导入数据
interface InternetCallback
{ public ArrayList<DailyQuote> internetAccessCompleted(String response); }


