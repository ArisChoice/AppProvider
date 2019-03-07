package com.app.barber.util;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.app.barber.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Created by harish on 20/12/18.
 */

public class CustomDate {
    private static final String APPOINTMENT_FORMAT = "EEEE,MMMM YY";

    /**
     * Format date as required
     */
    public static String formatThis(String informat, String dateStr, String outFormat) {
        String inputPattern = informat;
        String outputPattern = outFormat;
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(dateStr);
            String updaetedFormat = outputFormat.format(date);
            Log.d(" formatThis :::::", updaetedFormat);
            return updaetedFormat;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateStr;
    }

    /**
     * Format time as required
     */
    public static String formatTimeRemainig(Activity serviceListActivity, String timeRemaining) {

        try {
            String[] timeR = timeRemaining.split(":");
            Log.d(" formatTimeRemainig ", timeR[0] + ":" + timeR[1]);
            timeRemaining = timeR[0] + ":" + timeR[1] + " " + serviceListActivity.getString(R.string.str_hrs_left);
        } catch (Exception e) {
            return serviceListActivity.getString(R.string.str_expired);
        }
        return timeRemaining;
    }


    /**
     * get required current date format.
     */
    public static String getCurrentMonth(Activity activity, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Date date = new Date();
        Log.d("Month :: ", dateFormat.format(date));
        return dateFormat.format(date);
    }

    /**
     * get required current date format.
     */
    public static String getCurrentTimeUTC(Activity activity, String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setTimeZone(TimeZone.getTimeZone("gmt"));
        Date date = new Date();
        Log.d("getCurrentTimeUTC ::  ", dateFormat.format(date));
        return dateFormat.format(date);
    }

    /**
     * get required current date format.
     */
    public static String getCurrentFormat(Activity activity, String dateText, String inFormat, String outFormat) {
        SimpleDateFormat inputFormat = new SimpleDateFormat(inFormat);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outFormat);

        Date date = new Date();
        String str = null;

        try {
            Log.d(" in date :::::", dateText);
            date = inputFormat.parse(dateText);
            dateText = outputFormat.format(date);

            Log.d(" out date :::::", dateText);
            return dateText;
        } catch (ParseException e) {
            e.printStackTrace();

        }
        return dateText;
    }

    /**
     * convert timestamp into required date format.
     */
    public static String getDateFromUTCTimestamp(Activity activity, long mTimestamp, String mDateFormate) {
        String date;
        try {
            String currentDate = getCurrentMonth(activity, GlobalValues.DateFormats.DEFAULT_FORMAT_DATE);
            long longTimestamp = Long.parseLong(String.valueOf(mTimestamp));
            Calendar cal = Calendar.getInstance(Locale.ENGLISH);
            cal.setTimeInMillis(longTimestamp * 1000L);
            date = android.text.format.DateFormat.format(GlobalValues.DateFormats.DEFAULT_FORMAT_DATE, cal).toString();

            String messageDate = date;
            if (currentDate.equals(messageDate)) {
                date = android.text.format.DateFormat.format("hh:mm a", cal).toString();
            } else {
                date = android.text.format.DateFormat.format("MMM dd,hh:ss a", cal).toString();
            }
            return date;
        } catch (NumberFormatException e) {
            return "Not available";
        }

    }

    public static Long getTimeStampFromTime(String strTme, String format) throws ParseException {
        String str_date = strTme;
        DateFormat formatter = new SimpleDateFormat(format);
        Date date = (Date) formatter.parse(str_date);
        System.out.println("Today is " + date.getTime());
        return date.getTime();
    }

    /**
     * convert UTC string time  into local time format.
     *
     * @param inFormat
     * @param outFormat
     * @param time
     */
    public static String convertUTCtimetoLocal(String time, String inFormat, String outFormat) {
        SimpleDateFormat oldFormatter = new SimpleDateFormat(inFormat);
        oldFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date value = null;
        String dueDateAsNormal = "";
        try {
            value = oldFormatter.parse(time);
            SimpleDateFormat newFormatter = new SimpleDateFormat(outFormat);

            newFormatter.setTimeZone(TimeZone.getDefault());
            dueDateAsNormal = newFormatter.format(value);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dueDateAsNormal;
    }

    public static long getValidTimestamp(long dateSent) {
        return dateSent * 1000;
    }

    public static boolean isValidTime(FragmentActivity act, String timeSlots) {
        Log.e("isValidTime", " 1 " + timeSlots);
        String endTime = getCurrentMonth(act, "MM/dd/yyyy") + " " + timeSlots.split("-")[1];
        String startTime = getCurrentMonth(act, "MM/dd/yyyy hh:mm a");
        Log.e("isValidTime", " 2 " + startTime + "  " + endTime);
        if (CompairTimeDifference(startTime, endTime, "MM/dd/yyyy hh:mm a") > 5) {//if greater then 5 min
            return false;
        } else
            return true;
    }

    /**
     * calculate time time difference in hours.
     */
    public static long CompairTimeDifference(String firstTime, String secondTime, String format) {//return type long
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date startDate = null;
        try {
            startDate = simpleDateFormat.parse(firstTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date endDate = null;
        try {
            endDate = simpleDateFormat.parse(secondTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long difference = endDate.getTime() - startDate.getTime();

        long hours = difference / (1000 * 60 * 60);
        long mins = (difference / (1000 * 60)) % 60;

        String diff = hours + ":" + mins;
        Log.i("  Difference  ", "  ::   " + diff);
        if (hours > 0) {//if  difference is in hours.
            return ((hours * 60) + mins);
        } else if (hours == 0 && mins >= 0) {//if  difference is in min.
            return mins;
        } else return -1;//if  difference is in -ve.
    }

    /**
     * calculate time tiffereence in hours.
     *
     * @param firstTime
     * @param secondTime
     * @param tYpe
     */
    public static String compairTimeDifference(String firstTime, String secondTime, String format, String tYpe) {

        Log.i("first ", "  compairTimeDifference  " + firstTime);
        Log.i("second ", "  compairTimeDifference  " + secondTime);
//        firstTime = "02/04/2019 04:58:40";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date startDate = null;
        try {
            startDate = simpleDateFormat.parse(firstTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date endDate = null;
        try {
            endDate = simpleDateFormat.parse(secondTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        //in milliseconds
        long diff1 = startDate.getTime() - endDate.getTime();

        long diffSeconds = diff1 / 1000 % 60;
        long diffMinutes = diff1 / (60 * 1000) % 60;
        long diffHours = diff1 / (60 * 60 * 1000) % 24;
        long diffDays = diff1 / (24 * 60 * 60 * 1000);

        System.out.print(diffDays + " days, ");
        System.out.print(diffHours + " hours, ");
        System.out.print(diffMinutes + " minutes, ");
        System.out.print(diffSeconds + " seconds.");


        Log.i("difference ", "  =====>   " + diffDays + " " + diffHours + "   " + diffMinutes);
//        int remainingMinutes = (int) ((diffHours * 60) + diffMinutes);
        if (diffDays == 1) {
            return "1 day ago";
        } else if (diffDays > 1) {
            return diffDays + " days ago";
        } else if (diffDays == 0) {
            return CustomDate.formatThis(GlobalValues.DateFormats.FULL_DATE_TIME, secondTime, "hh:mm a");
        } else {
            return CustomDate.formatThis(GlobalValues.DateFormats.FULL_DATE_TIME, secondTime, "hh:mm a");
        }
    }


    /**
     * calculate time time difference in hours.
     *
     * @param firstTime
     * @param secondTime
     * @param format
     */
    public static int getTimeDifferenceinHours(String firstTime, String secondTime, String format) {//return type int
        Log.i("first ", "  getTimeDifferenceinHours  " + firstTime);
        Log.i("second ", "  getTimeDifferenceinHours  " + secondTime);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date startDate = null;
        try {
            startDate = simpleDateFormat.parse(firstTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date endDate = null;
        try {
            endDate = simpleDateFormat.parse(secondTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long difference = endDate.getTime() - startDate.getTime();

        long hours = difference / (1000 * 60 * 60);
        long mins = (difference / (1000 * 60)) % 60;

        String diff = hours + ":" + mins;
        Log.i("Difference in hours ", "  ::   " + (int) hours);
        if ((int) hours > 0) {//if  difference is in hours.
            return (int) hours;
        } else if (hours == 0 && mins >= 0) {//if  difference is in min.
            return 0;
        } else return -1;//if  difference is in -ve.
    }

    /**
     * calculate time time difference in hours.
     */
    public static long getTimeDifferenceinMinutes(String firstTime, String secondTime, String format) {//return type int
//        Log.i("first ", "  getTimeDifferenceinMinutes  " + firstTime);
//        Log.i("second ", "  getTimeDifferenceinMinutes  " + secondTime);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date startDate = null;
        try {
            startDate = simpleDateFormat.parse(firstTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date endDate = null;
        try {
            endDate = simpleDateFormat.parse(secondTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //in milliseconds
        long diff1 = endDate.getTime() - startDate.getTime();

        long diffSeconds = diff1 / 1000 % 60;
        long diffMinutes = diff1 / (60 * 1000) % 60;
        long diffHours = diff1 / (60 * 60 * 1000) % 24;
        long diffDays = diff1 / (24 * 60 * 60 * 1000);

        System.out.print(diffDays + " days, ");
        System.out.print(diffHours + " hours, ");
        System.out.print(diffMinutes + " minutes, ");
        System.out.print(diffSeconds + " seconds.");
        long hours = TimeUnit.MILLISECONDS.toHours(diff1);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(diff1);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(diff1);
        Log.i("Difference in Minutes ", "  -------1   :   " + hours + " " + minutes + "   " + seconds);

//        Log.i("New difference in Minutes ", " -----1   :   "+diffDays+" " + diffHours + "   " + diffMinutes);
        long remainingMinutes = minutes;


        if ( remainingMinutes > 0) {//if  difference is in hours.
            return  remainingMinutes;
        } else if (remainingMinutes == 0 && remainingMinutes >= 0) {//if  difference is in min.
            return 0;
        } else return -1;//if  difference is in -ve.
    }
}
