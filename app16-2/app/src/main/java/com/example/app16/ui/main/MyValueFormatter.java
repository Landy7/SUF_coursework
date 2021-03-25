package com.example.app16.ui.main;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

class MyValueFormatter implements IValueFormatter {

    private DecimalFormat format = new DecimalFormat("0.000000");
    // ValueFormatter
    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex,
                                    ViewPortHandler viewPortHandler) {
        return format.format(value) ;
    }

}