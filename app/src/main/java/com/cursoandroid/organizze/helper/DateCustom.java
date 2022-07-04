package com.cursoandroid.organizze.helper;

import android.util.Log;

import java.text.SimpleDateFormat;

public class DateCustom {

    public static String currentData() {
        long date = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dataString = simpleDateFormat.format(date);
        return dataString;
    }

    public static String monthYearDayChosenDate(String date) {
       String returnDate[] = date.split("/");
        String day = returnDate[0];
        String month = returnDate[1];
        String year = returnDate[2];

        String monthYear = month + year;
        return monthYear;
    }
}
