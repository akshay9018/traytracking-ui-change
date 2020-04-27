package com.pointclickcare.nutrition.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;

import org.joda.time.LocalDateTime;

import com.momentum.dms.util.TimeZoneConvertor;

public class TimeUtil
{

  public static String getTimeInMinutes(Date date)
  {
    if (date == null)
      return "";
    long diffInMillies = Math.abs(new Date().getTime() - date.getTime());
    int minutes = (int) (diffInMillies / (60 * 1000) % 60);
    int hours = (int) ((diffInMillies / (1000 * 60 * 60)) % 24);
    return String.valueOf((hours * 60) + minutes);
  }

  public static String getTimeIn12HourFormat(String timezone, Date deliveryDateTime)
  {
    Date facilityDeliveryDate = TimeZoneConvertor.convertToTimeZoneDate(deliveryDateTime, timezone);
    DateFormat f2 = new SimpleDateFormat("h:mm a");
    return f2.format(facilityDeliveryDate);
  }

  public static String getTimeIn24HourFormat(String timezone, Date deliveryDateTime)
  {
    Date facilityDeliveryDate = TimeZoneConvertor.convertToTimeZoneDate(deliveryDateTime, timezone);
    DateFormat f2 = new SimpleDateFormat("yyyy/MM/dd HH:mm");
    return f2.format(facilityDeliveryDate);
  }

  public static Short dateDiffInMin(Date date1, Date date2)
  {
    if (date1 != null && date2 != null)
    {
      Long difference = date1.getTime() - date2.getTime();
      return (short) (difference / (60 * 1000));
    }
    else
    {
      return null;
    }
  }

  public static Date getThreeHoursOldDate()
  {
    long time = System.currentTimeMillis() - Duration.ofHours(3).toMillis();
    Date sqlDate = new Date(time);
    return sqlDate;
  }
  
  public static Date getTwelveHoursOldDate()
  {
    long time = System.currentTimeMillis() - Duration.ofHours(12).toMillis();
    Date sqlDate = new Date(time);
    return sqlDate;
  }
  public static Date getTodayDateToStartOfDay()
  {
    return LocalDateTime.now().toLocalDate().toDateTimeAtStartOfDay().toDate();
  }
  public static Date addNToDate(Date dt, int n)
  {
    LocalDateTime localDt = new LocalDateTime(dt);
    return localDt.plusDays(n).toDate();  
  }
  
  public static LocalTime getLocalTime(String time)
  {
	  return LocalTime.parse(time, 
      		DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));
  }
}