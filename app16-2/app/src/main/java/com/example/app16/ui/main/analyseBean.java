package com.example.app16.ui.main;

import java.util.ArrayList;

import java.util.List;

import android.content.Context;

public class analyseBean
{ ModelFacade model = null;

  private List errors = new ArrayList();

  //get context
  public analyseBean(Context _c) { ModelFacade.getInstance(_c); }


  public boolean isanalyseerror()
  { errors.clear(); 
    return errors.size() > 0;
  }

  public String errors() { return errors.toString(); }



}

