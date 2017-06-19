package com.idohoo.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;

/**
 *
 *
 * 类描述：
 *
 * @version casking Server 0.1
 */
public class DateUtil
{
    public final static String NORMAL_DATE_FORMAT_STR = "yyyy-MM-dd HH:mm:ss";

    public final static String MILLI_DATE_FORMAT_STR = "yyyy-MM-dd HH:mm:ss.SSS";

    public final static String NUM_MILLI_DATE_FORMAT_STR = "yyyyMMddHHmmssSSS";

    public final static String SMS_DATE_FORMAT_STR = "yyyyMMddHHmmss";

    public final static String INTEGRAL_DATE_FORMAT_STR = "yyyy-MM-dd";

    public final static String YYMMDD = "yyMMdd";

    public final static String ZH_DATE_FORMAT_STR = "yyyy年MM月dd日";

    public final static SimpleDateFormat NUM_INTEGRAL_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");

    public final static SimpleDateFormat NUM_NORMAL_DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");

    public static Date getDateZeroTime(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        getDateZeroTime(calendar);
        return calendar.getTime();
    }

    public static Date getCriticalTime(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    public static Calendar getDateZeroTime(Calendar calendar)
    {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    public static String getTimeStampStr(Timestamp ts)
    {
        String tsStr = "";
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try
        {
            if (null != ts && !"".equals(ts))
                tsStr = sdf.format(ts);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return tsStr;
    }

    public static String date2Str(Date date, SimpleDateFormat formatter)
    {
        return formatter.format(date);
    }

    public static String date2Str(Date date, String pattern)
    {
        if (null == date)
            return "";
        return new SimpleDateFormat(pattern).format(date);
    }

    public static String date2Str(Date date)
    {
        if (date == null)
            return null;
        return new SimpleDateFormat(NORMAL_DATE_FORMAT_STR).format(date);
    }
    
    public static Date getDateFromLong(long time)
    {
        return new Date(time);
    }

    /**
     * @Description 获取yyyy-MM-dd
     * @author xhz
     * @date 2016-8-13 下午3:24:12
     * @param date
     * @return
     * @lastModifier
     */
    public static String dateOnly2Str(Date date)
    {
        if (date == null)
            return null;
        return new SimpleDateFormat(INTEGRAL_DATE_FORMAT_STR).format(date);
    }

    public static String getDateStart()
    {
        Date date = new Date();

        String str = dateOnly2Str(date);

        return str + " " + "00:00:00";
    }

    public static String getDateEnd()
    {
        Date date = new Date();

        String str = dateOnly2Str(date);

        return str + " " + "23:59:59";
    }

    public static Date str2Date(String dateStr, SimpleDateFormat formatter) throws ParseException
    {
        return formatter.parse(dateStr);
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     * */
    public static Date str2Date(String dateStr)
    {
        if (StringUtils.isEmpty(dateStr))
            return null;
        Date date = null;
        SimpleDateFormat sf = new SimpleDateFormat(NORMAL_DATE_FORMAT_STR);
        try
        {
            date = sf.parse(dateStr);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * yyyy-MM-dd
     * */
    public static Date str2DateYMD(String dateStr)
    {
        if (StringUtils.isEmpty(dateStr))
            return null;
        Date date = null;
        SimpleDateFormat sf = new SimpleDateFormat(INTEGRAL_DATE_FORMAT_STR);
        try
        {
            date = sf.parse(dateStr);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return date;
    }

    public static Date smsDatestr2Date(String dateStr)
    {
        if (StringUtils.isEmpty(dateStr))
            return null;
        Date date = null;
        SimpleDateFormat sf = new SimpleDateFormat(SMS_DATE_FORMAT_STR);
        try
        {
            date = sf.parse(dateStr);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return date;
    }

    public static Date Datestr2Date(String dateStr)
    {
        if (StringUtils.isEmpty(dateStr))
            return null;
        Date date = null;
        SimpleDateFormat sf = new SimpleDateFormat(INTEGRAL_DATE_FORMAT_STR);
        try
        {
            date = sf.parse(dateStr);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return date;
    }

    public static String getCurrentTimeStr()
    {
        return new SimpleDateFormat(NORMAL_DATE_FORMAT_STR).format(new Date());
    }

    public static String getCurrentZhTimeStr()
    {
        return new SimpleDateFormat(ZH_DATE_FORMAT_STR).format(new Date());
    }

    public static String getYYMMDD()
    {
        return new SimpleDateFormat(YYMMDD).format(new Date());
    }

    /**
     * 得到当前时间，精确到毫秒 时间格式：yyyy-MM-dd HH:mm:ss.SSS
     * @return
     */
    public static String getCurrentMilliTimeStr()
    {
        return new SimpleDateFormat(MILLI_DATE_FORMAT_STR).format(new Date());
    }

    /**
     * 得到当前时间，精确到毫秒 时间格式：yyyy-MM-dd HH:mm:ss.SSS
     * @return
     */
    public static String getDateMilliTimeStr(Date date)
    {
        return new SimpleDateFormat(MILLI_DATE_FORMAT_STR).format(date);
    }

    /**
     * 得到当前时间，精确到毫秒 时间格式：yyyyMMddHHmmssSSS
     * @return
     */
    public static String getCurrentNumMilliTimeStr()
    {
        return new SimpleDateFormat(NUM_MILLI_DATE_FORMAT_STR).format(new Date());
    }

    public static String getCurrentTimeStr(Date date)
    {
        return new SimpleDateFormat(NORMAL_DATE_FORMAT_STR).format(date);
    }

    public static String convertsqlDate2UtilDate(java.sql.Date date)
    {
        return new SimpleDateFormat(NORMAL_DATE_FORMAT_STR).format(new Date());
    }

    public static String convertGmtDateStr(String _timeZone, Date date)
    {
        TimeZone timeZone = null;
        if (StringUtils.isEmpty(_timeZone))
        {
            timeZone = TimeZone.getDefault();
        }
        else
        {
            timeZone = TimeZone.getTimeZone(_timeZone);
        }

        SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.NORMAL_DATE_FORMAT_STR);
        sdf.setTimeZone(timeZone);
        return sdf.format(date);
    }

    public static String getTimeAfter(Date date, int minute)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(NORMAL_DATE_FORMAT_STR);
        String time = sdf.format(date);
        Calendar cd = Calendar.getInstance();

        try
        {
            cd.setTime(sdf.parse(time));
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        cd.add(Calendar.MINUTE, minute);
        Date res = cd.getTime();

        return date2Str(res);
    }

    public static String getTimeStr()
    {
        return new SimpleDateFormat(NORMAL_DATE_FORMAT_STR).format(new Date());
    }

    public static String getSMSTimeStr()
    {
        return new SimpleDateFormat(SMS_DATE_FORMAT_STR).format(new Date());
    }

    public static String getOracleDateStr(String dateStr)
    {
        StringBuffer sb = new StringBuffer();
        sb.append("to_date('").append(dateStr).append("','yyyy-mm-dd hh24:mi:ss')");

        return sb.toString();
    }

    /**
     *
     * 方法表述
     * @param source
     * @param target
     * @return boolean
     */
    public static int compareTime(Date source, Date target)
    {
        java.util.Calendar c1 = java.util.Calendar.getInstance();
        java.util.Calendar c2 = java.util.Calendar.getInstance();

        c1.setTime(source);
        c2.setTime(target);

        return c1.compareTo(c2);
    }

    public static int compareDates(Date source, Date target)
    {
        java.util.Calendar c1 = java.util.Calendar.getInstance();
        java.util.Calendar c2 = java.util.Calendar.getInstance();

        c1.setTime(source);
        c2.setTime(target);

        return c1.compareTo(c2);
    }

    /**
     *
     * 方法表述
     * @param source
     * @param target
     * @return boolean
     */
    public static boolean compareDate(Date source, Date target)
    {
        java.util.Calendar c1 = java.util.Calendar.getInstance();
        java.util.Calendar c2 = java.util.Calendar.getInstance();

        c1.setTime(source);
        c2.setTime(target);

        int result = c1.compareTo(c2);
        if (result > 0)
            return false;
        else
            return true;
    }

    public static boolean compareTimes(Date source, Date target)
    {
        java.util.Calendar c1 = java.util.Calendar.getInstance();
        java.util.Calendar c2 = java.util.Calendar.getInstance();

        c1.setTime(source);
        c2.setTime(target);

        int result = c1.compareTo(c2);
        if (result > 0)
            return false;
        else
            return true;
    }

    public static String getTimeAfterDate(Date date, int day)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(NORMAL_DATE_FORMAT_STR);
        String time = sdf.format(date);
        Calendar cd = Calendar.getInstance();

        try
        {
            cd.setTime(sdf.parse(time));
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        cd.add(Calendar.DATE, day);
        Date res = cd.getTime();

        return date2Str(res);
    }

    public static Date getTimeAfterMinute(Date date, int minute)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(NORMAL_DATE_FORMAT_STR);
        String time = sdf.format(date);
        Calendar cd = Calendar.getInstance();

        try
        {
            cd.setTime(sdf.parse(time));
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        cd.add(Calendar.MINUTE, minute);
        return cd.getTime();
    }

    public static Date getTimeAfterSecond(Date date, int second)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(NORMAL_DATE_FORMAT_STR);
        String time = sdf.format(date);
        Calendar cd = Calendar.getInstance();

        try
        {
            cd.setTime(sdf.parse(time));
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        cd.add(Calendar.SECOND, second);
        return cd.getTime();
    }

    public static String getTimeBeforeDate(Date date, int day)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(NORMAL_DATE_FORMAT_STR);
        String time = sdf.format(date);
        Calendar cd = Calendar.getInstance();

        try
        {
            cd.setTime(sdf.parse(time));
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        cd.add(Calendar.DATE, -day);
        Date res = cd.getTime();

        return date2Str(res);
    }

    public static String getTimeBeforeDate2(Date date, int day)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(INTEGRAL_DATE_FORMAT_STR);
        String time = sdf.format(date);
        Calendar cd = Calendar.getInstance();

        try
        {
            cd.setTime(sdf.parse(time));
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        cd.add(Calendar.DATE, -day);
        Date res = cd.getTime();
        SimpleDateFormat sdf2 = new SimpleDateFormat(INTEGRAL_DATE_FORMAT_STR);

        return sdf2.format(res);
    }

    /**
     * 返回当前的 yyyy-MM-dd
     * @return
     */
    public static String getCurDate()
    {
        return dateOnly2Str(new Date());
    }

    /**
     * 返回多少天以前的 yyyy-MM-dd
     * @return
     */
    public static String getBeforeDay(int days)
    {
        Calendar cd = Calendar.getInstance();
        cd.setTime(new Date());
        cd.add(Calendar.DAY_OF_MONTH, -days);
        return dateOnly2Str(cd.getTime());
    }

    /**
     * 返回多少月以前的 yyyy-MM-dd
     * @return
     */
    public static String getBeforeMonth(int month)
    {
        Calendar cd = Calendar.getInstance();
        cd.setTime(new Date());
        cd.add(Calendar.MONTH, -month);
        return dateOnly2Str(cd.getTime());
    }

    public static int getCurYear()
    {
        Calendar cd = Calendar.getInstance();
        return cd.get(Calendar.YEAR);
    }

    public static int getCurMonth()
    {
        Calendar cd = Calendar.getInstance();
        return cd.get(Calendar.MONTH) + 1;
    }

    public static int getCurDay()
    {
        Calendar cd = Calendar.getInstance();
        return cd.get(Calendar.DATE);
    }

    /** 通过时间戳返回日期 yyyy:MM:dd hh:mm:ss*/
    public static Date fromTimestamp(long timestamp)
    {
        return new Date(timestamp);
    }

    /** 获取今天0点日期 yyyy:MM:dd*/
    public static Date getTodayZeroDate()
    {
        return getDateZeroTime(new Date());
    }

    /** 获取今天0点的时间戳*/
    public static long getTodayZeroTimestamp()
    {
        return getTodayZeroDate().getTime();
    }

    /** 获取0点的时间戳*/
    public static long getDateZeroTimestamp(Date date)
    {
        return getDateZeroTime(date).getTime();
    }

    /** 获取0点的时间戳*/
    public static long getDateZeroTimestamp(long timestamp)
    {
        return getDateZeroTime(new Date(timestamp)).getTime();
    }

    /** 判断是否同天*/
    public static boolean isSameDate(Date date,Date date2)
    {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(date);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);

        if(calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
           calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH) &&
           calendar1.get(Calendar.DAY_OF_MONTH) == calendar2.get(Calendar.DAY_OF_MONTH)
        ){
            return true;
        }
        return false;
    }

    /** 判断是否同天*/
    public static boolean isSameDate(long date,long date2)
    {
        return isSameDate(new Date(date),new Date(date2));
    }

    /**
     * @Description 是否今天
     * @author xhz
     * @date 2016-8-14 下午6:17:15
     * @param date
     * @return
     * @lastModifier
     */
    public static boolean isToday(Date date)
    {
        return isSameDate(date,new Date());
    }

    /**
     * yyyy-MM-dd
     * */
    public static Date DateTime2DateYMD(Date dateTime)
    {
    	String dateStr = dateOnly2Str(dateTime);

    	if (StringUtils.isEmpty(dateStr))
            return null;

    	Date date = null;

    	SimpleDateFormat sf = new SimpleDateFormat(INTEGRAL_DATE_FORMAT_STR);
        try
        {
            date = sf.parse(dateStr);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return date;
    }

    public static void main(String[] arg)
    {
        System.out.println(getCriticalTime(new Date()));
        //System.out.println(Datestr2Date("2016-07-12"));
        //System.out.println(smsDatestr2Date("20160712121212"));
        Date expireTime = str2DateYMD("2016-09-25");
//        str2DateYMD
        Date currentDate = str2DateYMD(getCurrentTimeStr());
        
        System.out.println(!compareTimes(currentDate, expireTime));
        
        System.out.println(getDateZeroTime(new Date()));
        
        System.out.println(DateUtil.getCriticalTime(new Date()));
    }

}
