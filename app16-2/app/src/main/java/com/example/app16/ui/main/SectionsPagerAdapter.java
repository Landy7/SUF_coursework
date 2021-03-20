package com.example.app16.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.app16.R;

public class SectionsPagerAdapter extends FragmentPagerAdapter
{
    //modify
  private static final String[] TAB_TITLES = new String[]{ "FindQuote", "Graph" };
  private final Context mContext;

  public SectionsPagerAdapter(Context context, FragmentManager fm)
  { super(fm);
  //数据内容值传给mContext
    mContext = context;
  }

  @Override
  public Fragment getItem(int position)
  { // instantiate a fragment for the page.
      //0表示findQuote
      analyseFragment af_all = new analyseFragment();
    if (position == 0)
    {   //findquote
        //modify
        findQuoteFragment fqf = new findQuoteFragment();
        return fqf.newInstance(mContext); }

    //1表示Fragment
    else if (position == 1)
    {   //analyse
        analyseFragment af = new analyseFragment();
        return af.newInstance(mContext); }
    //modify
    return af_all.newInstance(mContext);
  }

  @Nullable
 @Override
  public CharSequence getPageTitle(int position) 
  { return TAB_TITLES[position]; }

  @Override
  public int getCount()
  { return 2; }
}
