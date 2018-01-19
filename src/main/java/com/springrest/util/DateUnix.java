package com.springrest.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by tanerali on 26/07/2017.
 */
public class DateUnix {

    public static long dateToSeconds (String time) {
        DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long dateInSecs = date.getTime()/1000;

        return dateInSecs;
    }

    public static String secondsToDate (long timeInSecs) {

        Date dateOb = new Date(timeInSecs*1000);

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String date = df.format(dateOb);

        return date;
    }

    public static String secondsToSpecificTime (long timeInSecs) {

//        Date dateOb = new Date(timeInSecs*1000);

        Calendar calendarOb = new GregorianCalendar();
        calendarOb.setTimeInMillis(timeInSecs*1000);

        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");

        df.setCalendar(calendarOb);

        String time = df.format(calendarOb.getTime());

        return time;
    }

    public static int diffBetweenDatesInDays (Calendar date1, Calendar date2) {

        long diffBetweenDatesInMillisecs = date1.getTimeInMillis() - date2.getTimeInMillis();

        return (int) (diffBetweenDatesInMillisecs/1000/60/60/24);
    }
}
