package com.example.app16.ui.main;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import androidx.core.content.res.ResourcesCompat;
import android.content.res.AssetManager;
import android.graphics.drawable.BitmapDrawable;
import java.io.InputStream;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.inputmethod.InputMethodManager;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.app16.R;
import android.content.Context;
import androidx.annotation.LayoutRes;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.fragment.app.FragmentManager;
import android.view.View.OnClickListener;
import java.util.List;
import java.util.ArrayList;
import android.view.View;
import android.util.Log;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;
import android.widget.RadioGroup;
import android.widget.EditText;
import android.webkit.WebView;
import android.widget.TextView;


//分析页面
public class analyseFragment extends Fragment implements OnClickListener
{ View root;
  Context myContext;
  analyseBean analysebean;
    HorizontalScrollView scroll_view;

  ImageView analyseResult;
  Button analyseOkButton;
  Button analysecancelButton;
    //加一个按钮
    Button changeButton;

 public analyseFragment() {}

  public static analyseFragment newInstance(Context c)
  { analyseFragment fragment = new analyseFragment();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    fragment.myContext = c;
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState)
  { super.onCreate(savedInstanceState); }

  @SuppressLint("WrongViewCast")
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
  { root = inflater.inflate(R.layout.analyse_layout, container, false);
    Bundle data = getArguments();
    //analyse之后的图像id
    analyseResult = (ImageView) root.findViewById(R.id.analyseResult);
    //实例化获取instance
    analysebean = new analyseBean(myContext);
    //analyse按钮
    analyseOkButton = root.findViewById(R.id.analyseOK);
    //设置监听
    analyseOkButton.setOnClickListener(this);
    //analyse_cancel按钮
    analysecancelButton = root.findViewById(R.id.analyseCancel);
    //设置监听
    analysecancelButton.setOnClickListener(this);
      //加一个按钮监听器
      changeButton = root.findViewById(R.id.indicators);
      changeButton.setOnClickListener(this);
      scroll_view = (HorizontalScrollView) root.findViewById(R.id.sc);
      scroll_view.setOnTouchListener(new View.OnTouchListener() {
          @Override
          public boolean onTouch(View arg0, MotionEvent arg1) {
              Log.e("chen", "ScrollView-onTouch");
              //可以滑动
              return false;
          }
      });
    return root;
  }


    //根据点击按钮的不同来调用不同函数
  public void onClick(View _v)
  { InputMethodManager _imm = (InputMethodManager) myContext.getSystemService(android.content.Context.INPUT_METHOD_SERVICE);
    try { _imm.hideSoftInputFromWindow(_v.getWindowToken(), 0); } catch (Exception _e) { }
    if (_v.getId() == R.id.analyseOK)
    { analyseOK(_v); }
    else if (_v.getId() == R.id.analyseCancel)
    { analyseCancel(_v); }
    //加一个控制响应
    else if (_v.getId() == R.id.indicators)
    { change(_v); }
  }

  @SuppressLint("WrongViewCast")
  public void analyseOK(View _v)
  {

    if (analysebean.isanalyseerror())
    { Log.w(getClass().getName(), analysebean.errors());
      Toast.makeText(myContext, "Errors: " + analysebean.errors(), Toast.LENGTH_LONG).show();
    }
    else
    {
        //显示图像折线图
        GraphDisplay _result = analysebean.analyse();

        analyseResult.invalidate();
        analyseResult.refreshDrawableState();
        analyseResult.setImageDrawable(_result);
        analyseResult.setScaleType(ImageView.ScaleType.FIT_XY);

    }
  }



    public void change(View _v)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),android.R.style.Theme_Holo_Light_Dialog);
        //builder.setIcon(R.drawable.ic_launcher);
        builder.setTitle("choose indicator");
        //    指定下拉列表的显示数据SMA,
        //EMA, MACD, MACDAVG
        final String[] indicator = {"SMA", "EMA", "MACD", "MACDAVG"};
        //    设置一个下拉的列表选择项
        builder.setItems(indicator, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Toast.makeText(getActivity(), "Choice is：" + indicator[which], Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();


    }

  //这个功能有问题---修改了
  public void analyseCancel(View _v)
  { analysebean.resetData();
  }
}
