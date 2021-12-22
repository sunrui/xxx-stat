package com.xxx.stat.common.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 日期 util
 */
public class DateUtil {
    public static final String BUNDLE_KEY = "ApplicationResources";

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    public static final String SLASH_DATE_FORMAT = "yyyy/MM/dd";

    public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String DEFAULT_DATETIME_MI_FORMAT = "yyyy-MM-dd HH:mm";

    public static final int WeekSpan = 7;

    public static final long SCOND_ONE_DAY = 86400; // 24*60*60*1000

    public static final long MILLISECOND_ONE_DAY = 86400000; // 24*60*60*1000

    public static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            .withZone(ZoneId.systemDefault());

    public static Date addHour(Date date, Integer hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hour);
        return calendar.getTime();
    }

    public static Integer getSubtractDay(Date endDate, Date beginDate) {
        endDate = getDayFirst(endDate);
        beginDate = getDayFirst(beginDate);
        long day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
        return (int) day;
    }

    /**
     * 获取当天最开始 00:00:00.000
     *
     * @param date 时间
     * @return 开始时间
     */
    public static Date getDayFirst(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取当天最后时间 23:59:59.999
     *
     * @param date 时间
     * @return 最后时间
     */
    public static Date getDayLast(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * 判断format时间是否相等
     *
     * @param date1  时间 1
     * @param date2  时间 2
     * @param format 格式
     * @return 是否相等
     */
    public static boolean formatDateEqual(Date date1, Date date2, String format) {
        String str1 = formatDate(date1, format);
        String str2 = formatDate(date2, format);
        return str1.equals(str2);
    }

    /**
     * 获取截去时，分，秒的当前日期
     *
     * @return
     */
    public static Date truncateToDay() {
        return truncateToDay(new Date());
    }

    /**
     * 获取截去时，分，秒的指定日期
     *
     * @param date 时间
     * @return 日期
     */
    public static Date truncateToDay(Date date) {
        return truncateToDay(date, 0);
    }

    public static Date truncateToDay(int days) {
        return truncateToDay(new Date(), days);
    }

    /**
     * 获取截去时，分，秒的指定增量日期
     */
    public static Date truncateToDay(Date date, int days) {
        Date theDate = DateUtils.addDays(date, days);
        return DateUtils.truncate(theDate, Calendar.DAY_OF_MONTH);
    }

    public static Date truncateToMonth(int months) {
        return truncateToMonth(new Date(), months);
    }

    public static Date truncateToMonth(Date date, int months) {
        Date theDate = DateUtils.addMonths(date, months);
        return DateUtils.truncate(theDate, Calendar.MONTH);
    }

    /**
     * 判断一个年份是否是闰年
     *
     * @param year 年
     * @return 是否为闰年
     */
    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    /**
     * 判断一个日期是否是闰年
     *
     * @param date 日期
     * @return 是否为闰年
     */
    public static boolean isLeapYear(Date date) {
        return isLeapYear(getYear(date));
    }

    public static boolean isToday(Date date) {
        return dateTimeFormatter.format(date.toInstant())
                .equals(dateTimeFormatter.format(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
    }

    /**
     * 获取指定年月日的Date
     *
     * @param year  年
     * @param month - 0~11
     * @param date  - 1~31
     * @return 日期
     */
    public static Date getDate(int year, int month, int date) {
        return getDate(year, month, date, 0, 0, 0);
    }

    /**
     * 获取指定年月日时分秒的Date
     *
     * @param year      年
     * @param month     - 0~11
     * @param date      - 1~31
     * @param hourOfDay - 0~23
     * @param minute    - 0~59
     * @param second    - 0~59
     * @return 日期
     */
    public static Date getDate(int year, int month, int date, int hourOfDay, int minute, int second) {
        return getDate(year, month, date, hourOfDay, minute, second, 0);
    }

    /**
     * 获取指定年月日时分秒毫秒的Date
     *
     * @param year        年
     * @param month       - 0~11
     * @param date        - 1~31
     * @param hourOfDay   - 0~23
     * @param minute      - 0~59
     * @param second      - 0~59
     * @param millisecond - 0~999
     * @return 日期
     */
    public static Date getDate(int year, int month, int date, int hourOfDay, int minute, int second, int millisecond) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, date, hourOfDay, minute, second);
        cal.set(Calendar.MILLISECOND, millisecond);
        return cal.getTime();
    }

    public static int getYear(Date date) {
        return DateUtils.toCalendar(date).get(Calendar.YEAR);
    }

    public static int getMonth(Date date) {
        return DateUtils.toCalendar(date).get(Calendar.MONTH);
    }

    /**
     * 返回指定时间段的星期序数,周1为1，周日为7
     *
     * @param date 日期
     * @return 周开始时间
     */
    public static int getDayOfWeek(Date date) {
        if (DateUtils.toCalendar(date).get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            return 7;
        } else {
            return DateUtils.toCalendar(date).get(Calendar.DAY_OF_WEEK) - 1;
        }
    }

    public static int getDayOfMonth(Date date) {
        return DateUtils.toCalendar(date).get(Calendar.DAY_OF_MONTH);
    }

    public static Date getFirstDayOfWeek() {
        return getFirstDayOfWeek(new Date());
    }

    public static Date getFirstDayOfWeek(Date date) {
        int dayOfWeek = DateUtils.toCalendar(date).get(Calendar.DAY_OF_WEEK);
        return DateUtils.addDays(date, -dayOfWeek);
    }

    public static Date getLastDayOfWeek() {
        return getLastDayOfWeek(new Date());
    }

    public static Date getLastDayOfWeek(Date date) {
        int dayOfWeek = DateUtils.toCalendar(date).get(Calendar.DAY_OF_WEEK);
        return DateUtils.addDays(date, WeekSpan - dayOfWeek);
    }

    public static Date getFirstDayOfMonth() {
        return getFirstDayOfMonth(new Date());
    }

    public static Date getFirstDayOfMonth(Date date) {
        return getDayOfMonth(date, 0);
    }

    public static Date getDayOfMonth(int offset) {
        return getDayOfMonth(new Date(), offset);
    }

    public static Date getDayOfMonth(Date date, int offset) {
        Calendar cal = DateUtils.toCalendar(DateUtils.truncate(DateUtils.addMonths(date, offset), Calendar.DAY_OF_MONTH));
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    public static Date getLastDayOfMonth() {
        return getLastDayOfMonth(new Date());
    }

    public static Date getLastDayOfMonth(Date date) {
        Date tempDate = DateUtils.addMonths(truncateToDay(date), 1);
        tempDate = getFirstDayOfMonth(tempDate);
        return DateUtils.addMilliseconds(tempDate, -1);
    }

    public static Date getLastTimeOfDay(Date date) {
        Date tempDate = truncateToDay(date, 1);
        return DateUtils.addMilliseconds(tempDate, -1);
    }

    public static String getYear(Calendar cal) {
        return String.valueOf(cal.get(Calendar.YEAR));
    }

    public static String getMonth(Calendar cal) {
        return StringUtils.leftPad(String.valueOf(cal.get(Calendar.MONTH) + 1), 2, '0');
    }

    public static String getDay(Calendar cal) {
        return StringUtils.leftPad(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)), 2, '0');
    }

    public static String getHour(Calendar cal) {
        return StringUtils.leftPad(String.valueOf(cal.get(Calendar.HOUR_OF_DAY)), 2, '0');
    }

    public static String getMinute(Calendar cal) {
        return StringUtils.leftPad(String.valueOf(cal.get(Calendar.MINUTE)), 2, '0');
    }

    public static String getSecond(Calendar cal) {
        return StringUtils.leftPad(String.valueOf(cal.get(Calendar.SECOND)), 2, '0');
    }

    public static Date parseDate(String strDate) {
        return parseDate(strDate, "yyyy-MM-dd", "MM/dd/yyyy");
    }

    public static Date parseTime(String strDate) {
        return parseDate(strDate, "yyyy-MM-dd HH:mm:ss", "MM/dd/yyyy HH:mm:ss");
    }

    public static Date parseDate(String strDate, String... format) {
        try {
            if (StringUtils.isBlank(strDate)) {
                return null;
            }
            return DateUtils.parseDate(strDate, format);
        } catch (Exception e) {
            return null;
        }
    }

    public static String formatDate(Date date, String format) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat(format).format(date);
    }

    public static String formatDate(Date date) {
        return formatDate(date, "yyyy-MM-dd");
    }

    public static String formatTime(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }


    public static int getDaysBetween(Date dateFrom, Date dateTo) {
        return getDaysBetween(dateFrom, dateTo, true);
    }

    public static int getDaysBetween(Date dateFrom, Date dateTo, boolean isTruncated) {
        if (dateFrom == null) {
            dateFrom = new Date();
        }
        if (dateTo == null) {
            dateTo = new Date();
        }
        if (isTruncated) {
            dateFrom = truncateToDay(dateFrom);
            dateTo = truncateToDay(dateTo);
        }

        return (int) ((dateTo.getTime() - dateFrom.getTime()) / MILLISECOND_ONE_DAY);
    }

    public static int getMonthsBetween(Date dateFrom, Date dateTo) {
        if (dateFrom == null) {
            dateFrom = new Date();
        }
        if (dateTo == null) {
            dateTo = new Date();
        }
        Calendar bef = Calendar.getInstance();
        Calendar aft = Calendar.getInstance();
        bef.setTime(dateFrom);
        aft.setTime(dateTo);
        int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
        int month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;
        return Math.abs(month + result);
    }

    /**
     * 本月有几天
     *
     * @return
     */
    public static int getDaysOfMonth(Date d) {
        Calendar calendar = DateUtils.toCalendar(d);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 今年有几天
     *
     * @return
     */
    public static long getDaysOfYear(Date d) {
        Calendar calendar = DateUtils.toCalendar(d);
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        return calendar.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * 是否过期
     *
     * @param date 时间
     * @return 是否过期
     */
    public static boolean isOverdue(Date date) {
        return date != null && date.after(new Date());
    }

    /**
     * 返回指定时间段内的整点天的日期
     *
     * @param date1 日期 1
     * @param date2 日期 2
     * @return 整点天的日期
     */
    public static List<Date> getDays(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("[DateUtil.getDays()]date1 or date2 can not be null!");
        }
        if (date1.after(date2)) {
            throw new IllegalArgumentException("[DateUtil.getDays()]date1 " + date1 + " can not be after date2 " + date2);
        }
        List<Date> days = new ArrayList<>();
        date2 = truncateToDay(date2);
        while (!date1.after(date2)) {
            date1 = truncateToDay(date1);
            days.add(date1);
            date1 = DateUtils.addDays(date1, 1);
        }
        return days;
    }

    public static Date getNextDay(Date date) {
        return DateUtils.addDays(date, 1);
    }

    public static Date getPreDay(Date date) {
        return DateUtils.addDays(date, -1);
    }

    public static boolean isSameYear(Date d1, Date d2) {
        return d1 != null && d2 != null && getYear(d1) == getYear(d2);
    }

    public static boolean isSameMonth(Date d1, Date d2) {
        return isSameYear(d1, d2) && getMonth(d1) == getMonth(d2);
    }

    public static boolean isSameDay(Date d1, Date d2) {
        return isSameMonth(d1, d2) && getDayOfMonth(d1) == getDayOfMonth(d2);
    }

    public static Date addYears(Date date, int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, year);
        return calendar.getTime();
    }

    public static Date addMonths(Date date, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, month);
        return calendar.getTime();
    }

    public static Date addDays(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, day);
        return calendar.getTime();
    }

    public static Date addMinutes(Date date, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    public static Date addSeconds(Date date, long millisecond) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, (int) millisecond);
        return calendar.getTime();
    }

    public static Date addMilliseconds(Date date, long millisecond) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MILLISECOND, (int) millisecond);
        return calendar.getTime();
    }

    /**
     * 验证时间字符串格式输入是否正确 (yyyy-MM-dd hh:mm:ss)
     *
     * @param timeStr 日期字符串
     * @return 是否正确
     */
    public static boolean validateTimeWithLongFormat(String timeStr) {
        String timeFormat =
                "((19|20)[0-9]{2})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01]) " + "([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]";
        String dateformat = "((19|20)[0-9]{2})-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])";
        Pattern pattern = Pattern.compile(timeFormat);
        Matcher matcher = pattern.matcher(timeStr);
        Pattern datePattern = Pattern.compile(dateformat);
        Matcher dateMatcher = datePattern.matcher(timeStr);
        if (matcher.matches()) {
            pattern = Pattern.compile("(\\d{4})-(\\d+)-(\\d+).*");
            return validateDate(pattern, timeStr);
        } else if (dateMatcher.matches()) {
            pattern = Pattern.compile("(\\d{4})-(\\d+)-(\\d+).*");
            return validateDate(pattern, timeStr);
        }
        return false;
    }

    /**
     * 判断年月日是否符合
     */
    private static boolean validateDate(Pattern pattern, String timeStr) {
        Matcher matcher = pattern.matcher(timeStr);
        if (matcher.matches()) {
            int y = Integer.parseInt(matcher.group(1));
            int m = Integer.parseInt(matcher.group(2));
            int d = Integer.parseInt(matcher.group(3));
            if (d > 28) {
                Calendar c = Calendar.getInstance();
                c.set(y, m - 1, 1);
                int lastDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
                return (lastDay >= d);
            }
        }
        return true;
    }

    /**
     * 查看当天日期是否在特定的时间段里
     *
     * @param date      日期
     * @param beginHour 开始小时
     * @param beginMin  开始分钟
     * @param endHour   结束小时
     * @param endMin    结束分钟
     * @return 是否在时间段
     */
    public static Boolean isInZone(Date date, Integer beginHour, Integer beginMin, Integer endHour, Integer endMin) {
        int year = DateUtil.getYear(date);
        int month = DateUtil.getMonth(date);
        int day = DateUtil.getDayOfMonth(date);
        Date beginDate = DateUtil.getDate(year, month, day, beginHour, beginMin, 0);
        Date endDate = DateUtil.getDate(year, month, day, endHour, endMin, 0);
        long nowDateL = date.getTime();
        long beginDateL = beginDate.getTime();
        long endDateL = endDate.getTime();
        return nowDateL > beginDateL && nowDateL < endDateL;
    }

    public static Date now() {
        return new Date();
    }

    public static long nowTime() {
        return now().getTime();
    }

    public static long todayRemainsMills() {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.MIN);
        Duration duration = Duration.between(start, end);
        return duration.toMillis();
    }

    public static Calendar dataToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static String getElapsedTime(Date currentTime, Date firstTime) {
        long diff = currentTime.getTime() - firstTime.getTime();//这样得到的差值是微秒级别
        long days = diff / (1000 * 60 * 60 * 24);
        long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);//获取时
        long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);//获取分钟
        long s = (diff / 1000 - days * 24 * 60 * 60 - hours * 60 * 60 - minutes * 60);//获取秒
        return (days > 0 ? (days + "天") : "") +
                (hours > 0 ? (hours + "小时") : "") +
                (minutes > 0 ? (minutes + "分") : "") +
                (s > 0 ? (s + "秒") : "");
    }
}
