package com.xxx.stat.core.stat.utils;

import com.xxx.stat.common.exception.StatException;
import com.xxx.stat.common.util.DateUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 统计用到的时间工具，此类并不完全通用。
 */
public class StatDateUtils {
    /**
     * 指定天的开始时间和结束时间
     */
    @Getter
    @Setter
    @Builder
    public static class DayBetween {
        /**
         * 开始时间
         */
        private Date dayStart;
        /**
         * 结束时间
         */
        private Date dayEnd;
    }

    /**
     * 获取指定天的开始时间和结束时间
     *
     * @param day 时间 20201010
     * @return 返回指定天的开始时间和结束时间
     */
    public static DayBetween integerToDayBetween(Integer day) {
        Date date;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            date = simpleDateFormat.parse(day + "");
        } catch (ParseException e) {
            e.printStackTrace();
            throw new StatException(e.getMessage());
        }

        return dateToDayBetween(date);
    }

    /**
     * 获取指定天的开始时间和结束时间
     *
     * @param dayStart 时间 20201010
     * @param dayEnd 时间 202011011
     * @return 返回指定天的开始时间和结束时间
     */
    public static DayBetween integerToDayBetween(Integer dayStart, Integer dayEnd) {
        if (dayEnd == null || dayEnd < dayStart) {
            throw new StatException("dayStart or dayEnd not valid");
        }

        Date dateStart;
        Date dateEnd;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            dateStart = simpleDateFormat.parse(dayStart + "000000");
            dateEnd = simpleDateFormat.parse(dayEnd + "235959");
        } catch (ParseException e) {
            e.printStackTrace();

            throw new StatException(e.getMessage());
        }

        return DayBetween.builder().dayStart(dateStart).dayEnd(dateEnd).build();
    }

    /**
     * 获取指定天的开始时间和结束时间
     *
     * @param date 时间
     * @return 返回 20201010
     */
    public static Integer dateToInteger(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        return Integer.valueOf(simpleDateFormat.format(date));
    }

    /**
     * 获取指定天的开始时间和结束时间
     *
     * @param date 时间
     * @return 返回指定天的开始时间和结束时间
     */
    public static DayBetween dateToDayBetween(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Date start = calendar.getTime();

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.SECOND, -1);

        Date end = calendar.getTime();

        return DayBetween.builder().dayStart(start).dayEnd(end).build();
    }

    /**
     * 获取指定天的开始时间和结束时间
     *
     * @param dayStart 起始时间 20201010
     * @param dayEnd   结束时间 1010
     * @return 返回区间内的所有时间
     */
    public static Collection<Date> integerToDateCollection(Integer dayStart, Integer dayEnd) {
        Date dateStart;
        Date dateEnd;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            dateStart = simpleDateFormat.parse(dayStart + "");
            dateEnd = simpleDateFormat.parse(dayEnd + "");
        } catch (ParseException e) {
            e.printStackTrace();

            throw new StatException(e.getMessage());
        }

        return dateToDayCollection(dateStart, dateEnd);
    }

    /**
     * 获取指定天的开始时间和结束时间
     *
     * @param dateStart 起始时间
     * @param dateEnd   结束时间
     * @return 返回区间内的所有时间
     */
    public static Collection<Date> dateToDayCollection(Date dateStart, Date dateEnd) {
        dateStart = new Date( dateStart.getTime()+1000);
        List<Date> days = DateUtil.getDays(dateStart,dateEnd);
        List<Date> dateList = new ArrayList<>();
        if (days.size() == 0) {
            dateList.add(dateStart);
            return dateList;
        }
        return days;
    }

    public enum DateType {
        /**
         * 今天
         */
        TODAY,
        /**
         * 昨天
         */
        YESTERDAY,
        /**
         * 本周
         */
        WEEK,
        /**
         * 上周
         */
        LAST_WEEK,
        /**
         * 本月
         */
        MONTH,
        /**
         * 上月
         */
        LAST_MONTH
    }


    /**
     * 获取所有的时间区间
     *
     * @param dateType 时间类型
     * @return 时间区间
     */
    public static DayBetween dateTypeToDateBetween(Date date, DateType dateType) {
        switch (dateType) {
            case TODAY: {
                DayBetween dayBetween = dateToDayBetween(date);
                return DayBetween.builder()
                        .dayStart(dayBetween.getDayStart())
                        .dayEnd(dayBetween.getDayEnd()).build();
            }
            case YESTERDAY: {
                DayBetween yesterdayBetween = dateToDayBetween(DateUtil.getPreDay(date));
                return DayBetween.builder()
                        .dayStart(yesterdayBetween.getDayStart())
                        .dayEnd(yesterdayBetween.getDayEnd()).build();
            }
            case WEEK: {
                Date firstDayOfWeek = DateUtil.getDayLast(DateUtil.getFirstDayOfWeek(date));
                firstDayOfWeek = new Date(firstDayOfWeek.getTime() + 1000);
                Date lastDayOfWeek = DateUtil.getDayLast(DateUtil.getLastDayOfWeek(date));
                return DayBetween.builder()
                        .dayStart(firstDayOfWeek)
                        .dayEnd(lastDayOfWeek).build();
            }
            case LAST_WEEK: {
                Date firstDayOfLastWeek = DateUtil.getDayLast(DateUtil.getFirstDayOfWeek(DateUtil.truncateToDay(date,-7)));
                firstDayOfLastWeek = new Date(firstDayOfLastWeek.getTime() + 1000);
                Date lastDayOfLastWeek = DateUtil.getDayLast(DateUtil.getLastDayOfWeek(DateUtil.truncateToDay(date,-7)));
                return DayBetween.builder()
                        .dayStart(firstDayOfLastWeek)
                        .dayEnd(lastDayOfLastWeek).build();
            }
            case MONTH: {
                Date firstDayOfMonth = DateUtil.getFirstDayOfMonth(date);
                Date lastDayOfMonth = DateUtil.getLastDayOfMonth(date);
                return DayBetween.builder()
                        .dayStart(firstDayOfMonth)
                        .dayEnd(lastDayOfMonth).build();
            }
            case LAST_MONTH: {
                Date firstDayOfLastMonth = DateUtil.getDayOfMonth(date, -1);
                Date lastDayOfLastMonth = DateUtil.getLastDayOfMonth(DateUtil.getDayOfMonth(date, -1));
                return DayBetween.builder()
                        .dayStart(firstDayOfLastMonth)
                        .dayEnd(lastDayOfLastMonth).build();
            }
            default: {
                throw new StatException("can not fund dayBetween for dateType: " + dateType);
            }
        }
    }

    /**
     * 根据当前时间获取上一个周期时间
     *
     * @param nowDate  当前时间
     * @param dateType 时间类型
     * @return 上一个周期时间
     */
    public static Date getGrowthDate(Date nowDate, StatDateUtils.DateType dateType) {
        Date growthDate;
        switch (dateType) {
            case TODAY:

            case YESTERDAY: {

                growthDate = new Date(nowDate.getTime() - 24 * 60 * 60 * 1000L);
                break;
            }
            case WEEK: {
                growthDate = new Date(nowDate.getTime() - 7 * 24 * 60 * 60 * 1000L);
                break;
            }
            case LAST_WEEK: {
                growthDate = new Date(nowDate.getTime() - 2 * 7 * 24 * 60 * 60 * 1000L);
                break;
            }
            case MONTH: {
                Calendar cal = Calendar.getInstance();
                cal.setTime(nowDate);
                cal.add(Calendar.MONTH, -1);
                growthDate = cal.getTime();
                break;
            }
            case LAST_MONTH: {
                Calendar cal = Calendar.getInstance();
                cal.setTime(nowDate);
                cal.add(Calendar.MONTH, -2);
                growthDate = cal.getTime();
                break;
            }
            default:
                throw new StatException("Unexpected value: " + dateType);
        }

        return growthDate;
    }
}
