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

class FindQuote {

  static ArrayList<FindQuote> FindQuote_allInstances = new ArrayList<FindQuote>();

  FindQuote() { FindQuote_allInstances.add(this); }

  static FindQuote createFindQuote() {
    FindQuote result = new FindQuote();
    return result;
  }


  public String findQuote(String date) {

    String result = "";
    return result;
  }

}

