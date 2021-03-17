package com.example.app16.ui.main;

import java.util.ArrayList;

import java.util.List;

import android.content.Context;

public class findQuoteBean
{ ModelFacade model = null;

  private String Startdate = "";
  private String Enddate = "";
  private List errors = new ArrayList();

  public findQuoteBean(Context _c) {
    model = ModelFacade.getInstance(_c);
  }

  public void setStartdate(String datex)
  { Startdate = datex; }

  public void setEnddate(String datex)
  { Enddate = datex; }

  public void resetData()
  { Startdate = "";
    Enddate = "";
    }

  public boolean isfindQuoteerror() {
    errors.clear();
    return errors.size() > 0;
  }

  public String errors() { return errors.toString(); }

  public String findQuote() {
    return model.findQuote(Startdate,Enddate);
  }

}

