package com.tienda.a_shop.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DateUtil {

    public static String getNameCurrentMonth(){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM", Locale.US);
        String monthName = month_date.format(cal.getTime());
        return  monthName;
    }
}
