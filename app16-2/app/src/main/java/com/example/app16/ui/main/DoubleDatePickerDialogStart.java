package com.example.app16.ui.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.DatePicker;

/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import java.lang.reflect.Field;

import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker.OnDateChangedListener;

import com.example.app16.R;

/**
 * A simple dialog containing an {@link android.widget.DatePicker}.
 *
 * <p>
 * See the <a href="{@docRoot}guide/topics/ui/controls/pickers.html">Pickers</a>
 * guide.
 * </p>
 */
public class DoubleDatePickerDialogStart extends AlertDialog implements OnClickListener, OnDateChangedListener {

    private static final String START_YEAR = "start_year";
//    private static final String END_YEAR = "end_year";
    private static final String START_MONTH = "start_month";
//    private static final String END_MONTH = "end_month";
    private static final String START_DAY = "start_day";
//    private static final String END_DAY = "end_day";

    private final DatePicker mDatePicker_start;
//    private final DatePicker mDatePicker_end;
    private final OnDateSetListenerStart mCallBackStart;

    /**
     * The callback used to indicate the user is done filling in the date.
     */
    public interface OnDateSetListenerStart {

        /**
         * @param view
         *            The view associated with this listener.
         * @param year
         *            The year that was set.
         * @param monthOfYear
         *            The month that was set (0-11) for compatibility with
         *            {@link java.util.Calendar}.
         * @param dayOfMonth
         *            The day of the month that was set.
         */
        void onDateSetStart(DatePicker startDatePicker, int startYear, int startMonthOfYear, int startDayOfMonth);
    }

    /**
     * @param context
     *            The context the dialog is to run in.
     * @param callBack
     *            How the parent is notified that the date is set.
     * @param year
     *            The initial year of the dialog.
     * @param monthOfYear
     *            The initial month of the dialog.
     * @param dayOfMonth
     *            The initial day of the dialog.
     */
//    public DoubleDatePickerDialog(Context context, OnDateSetListener callBack, int year, int monthOfYear, int dayOfMonth) {
//        this(context, 0, callBack, year, monthOfYear, dayOfMonth);
//    }

    public DoubleDatePickerDialogStart(Context context, int theme, OnDateSetListenerStart callBack, int year, int monthOfYear,
                                       int dayOfMonth) {
        this(context, 0, callBack, year, monthOfYear, dayOfMonth, true);
    }
    /**
     * @param context
     *            The context the dialog is to run in.
     * @param theme
     *            the theme to apply to this dialog
     * @param callBack
     *            How the parent is notified that the date is set.
     * @param year
     *            The initial year of the dialog.
     * @param monthOfYear
     *            The initial month of the dialog.
     * @param dayOfMonth
     *            The initial day of the dialog.
     */
    public DoubleDatePickerDialogStart(Context context, int theme, OnDateSetListenerStart callBack, int year, int monthOfYear,
                                       int dayOfMonth, boolean isDayVisible) {
        super(context, theme);

        mCallBackStart = callBack;

        Context themeContext = getContext();
        setButton(BUTTON_POSITIVE, "CONFIRM", this);
        setButton(BUTTON_NEGATIVE, "CANCEL", this);
        // setButton(BUTTON_POSITIVE,
        // themeContext.getText(android.R.string.date_time_done), this);
        setIcon(0);

        LayoutInflater inflater = (LayoutInflater) themeContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.date_start, null);
        setView(view);
        //get start and end date
        mDatePicker_start = (DatePicker) view.findViewById(R.id.datePickerStart);
//        mDatePicker_end = (DatePicker) view.findViewById(R.id.datePickerEnd);
        mDatePicker_start.init(year, monthOfYear, dayOfMonth, this);
//        mDatePicker_end.init(year, monthOfYear, dayOfMonth, this);
        // updateTitle(year, monthOfYear, dayOfMonth);

        // hide the start
        if (!isDayVisible) {
            hidDay(mDatePicker_start);
//            hidDay(mDatePicker_end);
        }
    }

    /**
     * hide DatePicker
     *
     * @param mDatePicker
     */
    private void hidDay(DatePicker mDatePicker) {
        Field[] datePickerfFields = mDatePicker.getClass().getDeclaredFields();
        for (Field datePickerField : datePickerfFields) {
            if ("mDaySpinner".equals(datePickerField.getName())) {
                datePickerField.setAccessible(true);
                Object dayPicker = new Object();
                try {
                    dayPicker = datePickerField.get(mDatePicker);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
                // datePicker.getCalendarView().setVisibility(View.GONE);
                ((View) dayPicker).setVisibility(View.GONE);
            }
        }
    }

    public void onClick(DialogInterface dialog, int which) {
        // Log.d(this.getClass().getSimpleName(), String.format("which:%d",
        // which));
        if (which == BUTTON_POSITIVE)
            tryNotifyDateSet();
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int month, int day) {
        if (view.getId() == R.id.datePickerStart)
            mDatePicker_start.init(year, month, day, this);
//        if (view.getId() == R.id.datePickerEnd)
//            mDatePicker_end.init(year, month, day, this);
    }

    /**
     *
     *
     * @return The calendar view.
     */
    public DatePicker getDatePickerStart() {
        return mDatePicker_start;
    }

    /**
     *
     *
     * @return The calendar view.
     */

//    public DatePicker getDatePickerEnd() {
//        return mDatePicker_end;
//    }

    /**
     * Sets the start date.
     *
     * @param year
     *            The date year.
     * @param monthOfYear
     *            The date month.
     * @param dayOfMonth
     *            The date day of month.
     */
    public void updateStartDate(int year, int monthOfYear, int dayOfMonth) {
        mDatePicker_start.updateDate(year, monthOfYear, dayOfMonth);
    }

    /**
     * Sets the end date.
     *
     * @param year
     *            The date year.
     * @param monthOfYear
     *            The date month.
     * @param dayOfMonth
     *            The date day of month.
     */

//    public void updateEndDate(int year, int monthOfYear, int dayOfMonth) {
//        mDatePicker_end.updateDate(year, monthOfYear, dayOfMonth);
//    }

    private void tryNotifyDateSet() {
        if (mCallBackStart != null) {
            mDatePicker_start.clearFocus();
//            mDatePicker_end.clearFocus();
            mCallBackStart.onDateSetStart(mDatePicker_start, mDatePicker_start.getYear(), mDatePicker_start.getMonth(),
                    mDatePicker_start.getDayOfMonth());
//            mCallBack1.onDateSetEnd(mDatePicker_end, mDatePicker_end.getYear(),
//                    mDatePicker_end.getMonth(), mDatePicker_end.getDayOfMonth());

        }
    }

    @Override
    protected void onStop() {
        // tryNotifyDateSet();
        super.onStop();
    }

    @Override
    public Bundle onSaveInstanceState() {
        Bundle state = super.onSaveInstanceState();
        state.putInt(START_YEAR, mDatePicker_start.getYear());
        state.putInt(START_MONTH, mDatePicker_start.getMonth());
        state.putInt(START_DAY, mDatePicker_start.getDayOfMonth());
//        state.putInt(END_YEAR, mDatePicker_end.getYear());
//        state.putInt(END_MONTH, mDatePicker_end.getMonth());
//        state.putInt(END_DAY, mDatePicker_end.getDayOfMonth());
        return state;
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int start_year = savedInstanceState.getInt(START_YEAR);
        int start_month = savedInstanceState.getInt(START_MONTH);
        int start_day = savedInstanceState.getInt(START_DAY);
        mDatePicker_start.init(start_year, start_month, start_day, this);

//        int end_year = savedInstanceState.getInt(END_YEAR);
//        int end_month = savedInstanceState.getInt(END_MONTH);
//        int end_day = savedInstanceState.getInt(END_DAY);
//        mDatePicker_end.init(end_year, end_month, end_day, this);

    }
}
