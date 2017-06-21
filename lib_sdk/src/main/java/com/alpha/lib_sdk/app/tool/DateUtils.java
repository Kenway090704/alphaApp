package com.alpha.lib_sdk.app.tool;

import com.alpha.lib_sdk.app.log.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by kenway on 17/5/22 16:15
 * Email : xiaokai090704@126.com
 * 日期工具类
 */

public class DateUtils {

    private static final String TAG = "tool.Util";

    /**
     * it will return a format time string as "mm:ss"
     *
     * @param time
     * @return
     */
    public static String getFormatTime(int time) {
        return getFormatTime("mm:ss", time);
    }

    /**
     * 获取mm:ss格式的时间
     *
     * @param format
     * @param time
     * @return
     */
    public static String getFormatTime(String format, int time) {
        if (format == null || format.equals("")) {
            throw new IllegalArgumentException("format is null");
        }
        if (format.equalsIgnoreCase("mm:ss")) {
            int minute = ((int) time) / 60;
            int second = ((int) time) % 60;
            String timeMsg;
            if (minute < 10)
                timeMsg = "0" + minute + ":";
            else
                timeMsg = minute + ":";
            if (second < 10)
                timeMsg += "0" + second;
            else
                timeMsg += second;
            return timeMsg;
        }
        return "00:00";
    }

    /**
     * <pre>
     *                     yyyy-MM-dd 1969-12-31
     *                     yyyy-MM-dd 1970-01-01
     *               yyyy-MM-dd HH:mm 1969-12-31 16:00
     *               yyyy-MM-dd HH:mm 1970-01-01 00:00
     *              yyyy-MM-dd HH:mmZ 1969-12-31 16:00-0800
     *              yyyy-MM-dd HH:mmZ 1970-01-01 00:00+0000
     *       yyyy-MM-dd HH:mm:ss.SSSZ 1969-12-31 16:00:00.000-0800
     *       yyyy-MM-dd HH:mm:ss.SSSZ 1970-01-01 00:00:00.000+0000
     *     yyyy-MM-dd'T'HH:mm:ss.SSSZ 1969-12-31T16:00:00.000-0800
     *     yyyy-MM-dd'T'HH:mm:ss.SSSZ 1970-01-01T00:00:00.000+0000
     * </pre>
     *
     * @param format
     * @param msec
     * @return
     */
    public static String getDateFormat(String format, long msec) {
        return (new SimpleDateFormat(format)).format(new Date(msec));
    }

    /**
     *  formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
     *  data Date类型的时间
     * @param data
     * @param formatType
     * @return
     */
    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }
    /**
     * 从日期获取时间
     *
     * @param formatDate
     * @return
     */
    public static long getDateMsec(String formatDate) {
        long msec = 0;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatDate);
        try {
            Date date = simpleDateFormat.parse(formatDate);
            msec = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return msec;
    }

    /**
     * 通过format,和日期的字符串获取Date
     *
     * @param format
     * @param formatDate
     * @return
     */
    public static Date getDate(String format, String formatDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = simpleDateFormat.parse(formatDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 获取日期从某月某日--某月某日
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static String getMonthAndDayDuration(String beginDate, String endDate) {
        if ((beginDate == null || beginDate.length() == 0) || (endDate == null || endDate.length() == 0)) {
            Log.e(TAG, "beginDate and endDate should not be null");
            return null;
        }

        StringBuilder stringBuilder = new StringBuilder();
        Calendar beginCalendar = Calendar.getInstance();
        beginCalendar.setTime(getDate("yyyy-MM-dd hh:mm:ss", beginDate));
        stringBuilder.append(beginCalendar.get(Calendar.MONTH) + 1);
        stringBuilder.append("月");
        stringBuilder.append(beginCalendar.get(Calendar.DAY_OF_MONTH));
        stringBuilder.append("日");

        stringBuilder.append("-");

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(getDate("yyyy-MM-dd hh:mm:ss", endDate));
        stringBuilder.append(endCalendar.get(Calendar.MONTH) + 1);
        stringBuilder.append("月");
        stringBuilder.append(endCalendar.get(Calendar.DAY_OF_MONTH));
        stringBuilder.append("日");
        return stringBuilder.toString();
    }

}
