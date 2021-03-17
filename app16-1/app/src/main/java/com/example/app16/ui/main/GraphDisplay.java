package com.example.app16.ui.main;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.HashMap; 

public class GraphDisplay extends Drawable implements Drawable.Callback {

    private String graphKind = "line"; // could also be "scatter" or "bar"
    private Canvas canvas = null;

    private final Paint bluePaint;
    private final Paint blackPaint;
    private final Paint orangePaint;
    private final Paint textPaint;
    private final int offset = 50; // space for axes and labels
    private final int divisions = 12; // scale divisions

    public ArrayList<Double> xpoints;
    public ArrayList<Double> ypoints;
    public ArrayList<Double> ypointsma;
    public ArrayList<Double> ylist;
    private ArrayList<Double> zpoints;
    private ArrayList<String> xlabels;
    private HashMap<String,ArrayList<Double>> linesx;
    private HashMap<String,ArrayList<Double>> linesy;
    private HashMap<String,Double> labelsx;
    private HashMap<String,Double> labelsy;

    private String xName = "date";
    private String yName = "price";

    private static GraphDisplay instance = null;

  public GraphDisplay() {
        // Set up color and text size
        bluePaint = new Paint();
        bluePaint.setARGB(255, 0, 0, 255);
        blackPaint = new Paint();
        blackPaint.setARGB(255, 0, 0, 0);
        orangePaint = new Paint();
        orangePaint.setARGB(255, 128, 128, 0);
        textPaint = new Paint();
        textPaint.setARGB(255, 0, 0, 0);
        //设置文字大小
        textPaint.setTextSize((float) 20.0);
        xpoints = new ArrayList<Double>();
        ypoints = new ArrayList<Double>();
      ypointsma = new ArrayList<Double>();
      ylist = new ArrayList<Double>();
        zpoints = new ArrayList<Double>();
        xlabels = new ArrayList<String>();

        linesx = new HashMap<String,ArrayList<Double>>();
        linesy = new HashMap<String,ArrayList<Double>>();
        labelsx = new HashMap<String,Double>();
        labelsy = new HashMap<String,Double>();
  }

  public static GraphDisplay defaultInstance()
  {
    if (instance == null) {
      instance = new GraphDisplay();
    }
    return instance;
  }

  public void invalidateDrawable(Drawable drawable) {

      xpoints = new ArrayList<Double>();
      ypoints = new ArrayList<Double>();
      ypointsma = new ArrayList<Double>();
      ylist = new ArrayList<Double>();
      zpoints = new ArrayList<Double>();
      xlabels = new ArrayList<String>();

      linesx = new HashMap<String,ArrayList<Double>>();
      linesy = new HashMap<String,ArrayList<Double>>();
      labelsx = new HashMap<String,Double>();
      labelsy = new HashMap<String,Double>();
  }

    public void scheduleDrawable (Drawable who,
                                           Runnable what,
                                           long when) { }

    public void unscheduleDrawable (Drawable who,
                                             Runnable what) { }


  /*  public void setPoints(ArrayList<Double> xp, ArrayList<Double> yp)
    { xpoints = xp;
      ypoints = yp;
    }

    public void addPoints(ArrayList<Double> xp, ArrayList<Double> yp)
    { xpoints.addAll(xp);
      ypoints.addAll(yp);
    }

    public void setNominalScalerPoints(ArrayList<String> xps, ArrayList<Double> yps)
    { xlabels = xps; ypoints = yps; } */

    public void setXScalar(ArrayList<Double> xvalues)
    { xpoints = xvalues; }

    public void setXNominal(ArrayList<String> xvalues)
    { xlabels = xvalues; }

    public void setYPoints(ArrayList<Double> yvalues)
    { ypoints = yvalues; }

    public void setZPoints(ArrayList<Double> zvalues)
    { zpoints = zvalues; }

    public void setxname(String xn)
    { xName = xn; }

    public void setyname(String yn)
    { yName = yn; }

    public void setGraphKind(String kind)
    { graphKind = kind; }

	public void addLine(String name, ArrayList<Double> xvalues, ArrayList<Double> yvalues)
	{ linesx.put(name, xvalues);
	  linesy.put(name, yvalues);
	}

	public void addLabel(String name, double x, double y)
    { labelsx.put(name, new Double(x));
      labelsy.put(name, new Double(y));
    }
    public ArrayList<Double> addList(ArrayList<Double> target, ArrayList<Double> list){
    for(int i=0;i<list.size();i++){
        if(i<list.size()) target.add(list.get(i));

    }
    return target;
    }
    public void setYPointssma(ArrayList<Double> yvalues)
    {
        double a;
        for (int i = 0; i < yvalues.size(); i++) {
            a=yvalues.get(i)+1;
            ypointsma.add(a);
        }

    }
//    //都在这个方法里面---画图
    //抽象类
    @Override
    public void draw(Canvas canvas)
    {
        this.canvas = canvas;
        Canvas canvas1 = new Canvas();

        //如果x轴为空，那么重新创建
      if (xpoints.size() == 0)
      { if (xlabels.size() == 0) {
            return;
        } else {
            this.drawNominalScaler(canvas);
        }
      }



//        // Get the drawable's bounds
//        //如果已经有了数据，就在原来数据加长
//        int width = getBounds().width();
//        int height = getBounds().height();
//        float radius = 5;
//
//        double minx = xpoints.get(0);
//        double maxx = xpoints.get(0);
//
//        for (int i = 1; i < xpoints.size(); i++)
//		{
//          double xcoord = xpoints.get(i);
//          if (xcoord < minx)
//          { minx = xcoord; }
//          if (xcoord > maxx)
//          { maxx = xcoord; }
//        }
//
//		ArrayList<String> linekeys = new ArrayList<String>();
//		linekeys.addAll(linesx.keySet());
//
//		for (int j = 0; j < linekeys.size(); j++)
//		{ String key = linekeys.get(j);
//		  ArrayList<Double> linexvals = linesx.get(key);
//		  for (int k = 0; k < linexvals.size(); k++)
//		  { double linex = linexvals.get(k).doubleValue();
//		    if (linex < minx)
//            { minx = linex; }
//            if (linex > maxx)
//            { maxx = linex; }
//          }
//		}
//
//        ArrayList<String> labelkeys = new ArrayList<String>();
//        labelkeys.addAll(labelsx.keySet());
//
//        for (int i = 0; i < labelkeys.size(); i++) {
//            String labelkey = labelkeys.get(i);
//            double labelx = labelsx.get(labelkey);
//            if (labelx < minx) {
//                minx = labelx;
//            }
//            if (labelx > maxx) {
//                maxx = labelx;
//            }
//        }
//
//        double deltax = maxx - minx;
//
//		// ArrayList<String> xmarkers = new ArrayList<String>();
//		double xstep = deltax/12.0;
//
//        for (int i = 0; i <= 12; i++)
//        { double xcoord = i*xstep*(width - 100)/deltax;
//          int currx = (int) xcoord + 45;
//          double xvalue = minx + i*xstep;
//          xvalue = Math.round(xvalue*100);
//		  canvas.drawText("" + (xvalue/100), currx,height-20,textPaint);
//		}
//
//        double miny = ypoints.get(0);
//        double maxy = ypoints.get(0);
//
//        for (int i = 1; i < ypoints.size(); i++)
//		{ double ycoord = ypoints.get(i);
//          if (ycoord < miny) {
//                miny = ycoord;
//          }
//          if (ycoord > maxy) {
//                maxy = ycoord;
//          }
//        }
//
//        //z轴
//        for (int i = 0; i < zpoints.size(); i++)
//        { double ycoord = zpoints.get(i);
//            if (ycoord < miny) {
//                miny = ycoord;
//            }
//            if (ycoord > maxy) {
//                maxy = ycoord;
//            }
//        }
//
//		for (int j = 0; j < linekeys.size(); j++)
//		{ String key = linekeys.get(j);
//		  ArrayList<Double> lineyvals = linesy.get(key);
//		  for (int k = 0; k < lineyvals.size(); k++)
//		  { double liney = lineyvals.get(k).doubleValue();
//		    if (liney < miny)
//            { miny = liney; }
//            if (liney > maxy)
//            { maxy = liney; }
//          }
//		}
//
//        for (int i = 0; i < labelkeys.size(); i++) {
//            String labelkey = labelkeys.get(i);
//            double labely = labelsy.get(labelkey);
//            if (labely < miny) {
//                miny = labely;
//            }
//            if (labely > maxy) {
//                maxy = labely;
//            }
//        }
//
//        double deltay = maxy - miny;
//        double ystep = deltay/12.0;
//
//        for (int i = 0; i <= 12; i++)
//        { double ycoord = i*ystep*(height - 100)/deltay;
//          int curry = (int) ycoord + 45;
//          double yvalue = miny + i*ystep;
//          yvalue = Math.round(yvalue*1000);
//          canvas.drawText("" + (yvalue/1000), 5,height-curry,textPaint);
//        }
//
//        // ArrayList<String> ymarkers = new ArrayList<String>();
//
//        int prevx = (int) ((xpoints.get(0) - minx)*(width - 100)/deltax + 50);
//        int prevy = height - (int) ((ypoints.get(0) - miny)*(height - 100)/deltay + 50);
//        canvas.drawCircle(prevx, prevy, radius, bluePaint);
//
//        // Draw a circle at each point:
//        for (int i = 1; i < xpoints.size() && i < ypoints.size(); i++)
//		{
//            double xcoord = (xpoints.get(i) - minx)*(width - 100)/deltax;
//            double ycoord = (ypoints.get(i) - miny)*(height - 100)/deltay;
//            int currx = (int) xcoord + 50;
//            int curry = height - ((int) ycoord + 50);
//            canvas.drawCircle(currx, curry, radius, bluePaint);
//
//            if (graphKind.equals("line"))
//            { canvas.drawLine(prevx, prevy, currx, curry, blackPaint); }
//
//            prevx = currx;
//            prevy = curry;
//        }
//
//        //zpoint没有用到
//        if (zpoints.size() > 0)
//		{
//            prevx = (int) ((xpoints.get(0) - minx) * (width - 100) / deltax + 50);
//            int prevz = height - (int) ((zpoints.get(0) - miny) * (height - 100) / deltay + 50);
//            canvas.drawCircle(prevx, prevz, radius, orangePaint);
//            // Draw a circule at each point:
//            for (int i = 1; i < xpoints.size() && i < zpoints.size(); i++)
//			{
//                double xcoord = (xpoints.get(i) - minx) * (width - 100) / deltax;
//                double zcoord = (zpoints.get(i) - miny) * (height - 100) / deltay;
//                int currx = (int) xcoord + 50;
//                int currz = height - ((int) zcoord + 50);
//                canvas.drawCircle(currx, currz, radius, orangePaint);
//                if (graphKind.equals("line"))
//                { canvas.drawLine(prevx, prevz, currx, currz, orangePaint); }
//                prevx = currx;
//                prevz = currz;
//            }
//        }
//
//
//
//        for (int p = 0; p < linekeys.size(); p++)
//		{ String key = linekeys.get(p);
//		  ArrayList<Double> linexvals = linesx.get(key);
//		  ArrayList<Double> lineyvals = linesy.get(key);
//
// 	      int previousx = (int) ((linexvals.get(0) - minx)*(width - 100)/deltax + 50);
//          int previousy = height - (int) ((lineyvals.get(0) - miny)*(height - 100)/deltay + 50);
//          canvas.drawCircle(previousx, previousy, radius, bluePaint);
//
//          for (int i = 1; i < linexvals.size() && i < lineyvals.size(); i++)
//		  {
//            double xcoord = (linexvals.get(i) - minx)*(width - 100)/deltax;
//            double ycoord = (lineyvals.get(i) - miny)*(height - 100)/deltay;
//            int currx = (int) xcoord + 50;
//            int curry = height - ((int) ycoord + 50);
//            canvas.drawCircle(currx, curry, radius, bluePaint);
//            if (graphKind.equals("line"))
//            { canvas.drawLine(previousx, previousy, currx, curry, blackPaint); }
//            previousx = currx;
//            previousy = curry;
//          }
//		}
//
//
//
//        for (int i = 0; i < labelkeys.size(); i++)
//        { String labkey = labelkeys.get(i);
//          int labx = (int) ((labelsx.get(labkey) - minx)*(width - 100)/deltax) + 50;
//          int laby = height - ((int) ((labelsy.get(labkey) - miny)*(height - 100)/deltay) + 50);
//          canvas.drawText(labkey, labx,laby,textPaint);
//        }
//
//        canvas.drawText(xName, width - 50, height, textPaint);
//        canvas.drawText(yName, 15, 25, textPaint);
//
    }


    //开始创建的图像
    private void drawNominalScaler(Canvas canvas)
    {
//       Rect r = new Rect(0,1000,1000,2000);
      int width = getBounds().width();
      int height = getBounds().height();
      float radius = 10;

      //传过来的日期
      int nsize = xlabels.size();
      //.....
      double xstep = (width - 100)/nsize;

      for (int i = 0; i < nsize ; i++)
      { double xcoord = i*xstep;
      //每一个横坐标的跨度是45
        int currx = (int) xcoord + 45;
        //输出日期
        String xvalue = xlabels.get(i);
        //画横坐标xvalue = date, currx, height - 20 应该都是每一个横坐标的坐标
        canvas.drawText(xvalue, currx,height-20,textPaint);
      }


        setYPointssma(ypoints);

        ylist=addList(ylist,ypoints);
        ylist=addList(ylist,ypointsma);
      //画纵坐标
        //获得price
        double miny = ylist.get(0);
        double maxy = ylist.get(0);

        //交换price，纵坐标从小到大排序
        for (int i = 1; i < ylist.size(); i++)
        { double ycoord =ylist.get(i);
            if (ycoord < miny) {
                miny = ycoord;
            }
            if (ycoord > maxy) {
                maxy = ycoord;
            }
        }
        //z轴不用管
        for (int i = 0; i < zpoints.size(); i++)
        { double ycoord = zpoints.get(i);
            if (ycoord < miny) {
                miny = ycoord;
            }
            if (ycoord > maxy) {
                maxy = ycoord;
            }
        }

        double deltay = maxy - miny;
        //输出y轴有13个数字
        double ystep = deltay/12.0;

        for (int i = 0; i <= 12; i++)
        { double ycoord = i*ystep*(height - 100)/deltay;
            int curry = (int) ycoord + 45;
            double yvalue = miny + i*ystep;
            //？？？
            yvalue = Math.round(yvalue*1000);
            //画纵坐标，x=5,y=height-curry 是每一个纵坐标的坐标
            canvas.drawText("" + (yvalue/1000), 5,height-curry,textPaint);
        }
      drawtheline(height, miny,deltay,xstep,radius,nsize,ypoints);
        drawtheline(height, miny,deltay,xstep,radius,nsize,ypointsma);
        //drawtheline(height, miny,deltay,xstep,radius,nsize,ypointsma);
        // ArrayList<String> ymarkers = new ArrayList<String>();



//        //z不用看
//        if (zpoints.size() > 0) {
//            prevx = 50;
//            int prevz = height - (int) ((zpoints.get(0) - miny) * (height - 100) / deltay + 50);
//            canvas.drawCircle(prevx, prevz, radius, orangePaint);
//            // Draw a circle at each point:
//            for (int i = 1; i < nsize && i < zpoints.size(); i++) {
//                double xcoord = i*xstep;
//                double zcoord = (zpoints.get(i) - miny) * (height - 100) / deltay;
//                int currx = (int) xcoord + 50;
//                int currz = height - ((int) zcoord + 50);
//                canvas.drawCircle(currx, currz, radius, orangePaint);
//                if (graphKind.equals("line"))
//                { canvas.drawLine(prevx, prevz, currx, currz, orangePaint); }
//                prevx = currx;
//                prevz = currz;
//            }
//        }

        //添加横纵坐标的标识
        canvas.drawText(xName, width - 50, height, textPaint);
        canvas.drawText(yName, 15, 25, textPaint);
    }

public void drawtheline(int height,double miny, double deltay,double xstep,float radius,int nsize,ArrayList<Double> list) {

    int prevx = 50;
    int prevy = height - (int) ((list.get(0) - miny) * (height - 100) / deltay + 50);

   // int prevx1 = 50;
   // int prevy1 = height - (int) ((ypoints.get(0) - miny) * (height - 100) / deltay + 50);
    //设置蓝色圆点
    canvas.drawCircle(prevx, prevy, radius, bluePaint);
    // Draw a circle at each point:
    for (int i = 1; i < nsize && i < list.size(); i++) {
        double xcoord = i * xstep;
        int currx = (int) xcoord + 50;
        //int currx1 = (int) xcoord + 70;
        double ycoord = (list.get(i) - miny) * (height - 100) / deltay;

        int curry = height - ((int) ycoord + 50);
        //int curry1 = height - ((int) ycoord + 80);
        //循环画圆点
        canvas.drawCircle(currx, curry, radius, bluePaint);
       // canvas.drawCircle(currx1, curry1, radius, orangePaint);
        if (graphKind.equals("line")) {   //循环画线
            canvas.drawLine(prevx, prevy, currx, curry, blackPaint);
          //  canvas.drawLine(prevx1, prevy1, currx1, curry1, blackPaint);
        }
        prevx = currx;
        prevy = curry;

       // prevx1 = currx1;
       // prevy1 = curry1;
    }
}







    @Override
    public void setAlpha(int alpha) {
        // This method is required
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        // This method is required
    }

    @Override
    public int getOpacity() {
        // Must be PixelFormat.UNKNOWN, TRANSLUCENT, TRANSPARENT, or OPAQUE
        return PixelFormat.OPAQUE;
    }

//    public void redraw()
//    { draw(canvas); }
}
