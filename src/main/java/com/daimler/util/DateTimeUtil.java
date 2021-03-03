package com.daimler.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {

    public static String formatDate(Date date, String formatString) {
        if (date == null) {
            return "";
        }
        java.text.DateFormat format1 = new SimpleDateFormat(formatString);
        return format1.format(date);
    }
}
