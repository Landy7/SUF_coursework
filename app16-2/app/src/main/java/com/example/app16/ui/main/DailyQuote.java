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


  //not static ---modify
  public Map<String,DailyQuote> DailyQuote_index = new HashMap<String,DailyQuote>();
//  public ArrayList<DailyQuote> DailyQuote_allInstances = new ArrayList<DailyQuote>();

//  Map<String,DailyQuote> DailyQuote_index = new HashMap<String,DailyQuote>();
//  ArrayList<DailyQuote> DailyQuote_allInstances = new ArrayList<DailyQuote>();


    DailyQuote(){};

  static DailyQuote createDailyQuote() {

    DailyQuote result = new DailyQuote();
    return result;
  }


  String date = ""; /* primary */




//attributes in DailyQuote
  double open = 0;
  double high = 0;
  double low = 0;
  double close = 0;
  double adjclose = 0;
  double volume = 0;

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public double getOpen() {
    return open;
  }

  public void setOpen(double open) {
    this.open = open;
  }

  public double getHigh() {
    return high;
  }

  public void setHigh(double high) {
    this.high = high;
  }

  public double getLow() {
    return low;
  }

  public void setLow(double low) {
    this.low = low;
  }

  public double getClose() {
    return close;
  }

  public void setClose(double close) {
    this.close = close;
  }

  public double getAdjclose() {
    return adjclose;
  }

  public void setAdjclose(double adjclose) {
    this.adjclose = adjclose;
  }

  public double getVolume() {
    return volume;
  }

  public void setVolume(double volume) {
    this.volume = volume;
  }
}

