package com.xxx.stat.api.stat;

import com.xxx.stat.core.stat.data.StatDataProvider;
import com.xxx.stat.common.exception.StatException;
import com.xxx.stat.core.stat.data.node.StatGroup;
import com.xxx.stat.core.stat.provider.*;
import com.xxx.stat.core.stat.utils.StatDateUtils;
import com.xxx.stat.core.stat.provider.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 通用统计控制器
 */
@RestController
@RequestMapping("stat")
public class StatController {
    @Autowired
    private StatProvider statProvider;
    @Autowired
    private StatDataProvider statDataProvider;

    /**
     * 获取所有统计类型
     *
     * @return 返回所有统计类型
     */
    @GetMapping("")
    @ResponseBody
    public StatGroup[] getIndex() {
        return statDataProvider.getAllStatGroup();
    }

    /**
     * 获取所有统计类型
     *
     * @return 返回所有统计类型
     */
    @GetMapping("/platform")
    @ResponseBody
    public Platform[] getAllPlatform() {
        return Platform.values();
    }

    /**
     * 获取所有商户
     *
     * @return 所有商户
     */
    @GetMapping("/retailer")
    @ResponseBody
    public Collection<RetailerPair> getAllRetailer() {
        return statDataProvider.getAllRetailerPair();
    }

    /**
     * 获取总的数量
     *
     * @param platform     平台
     * @param retailerUuid 商户号
     * @param day          某一天
     * @param dayStart     某一天开始
     * @param dayEnd       某一天结束
     * @return 总的数量
     */
    @GetMapping("{statType}/sizeOfTotal")
    @ResponseBody
    public SizeOfTotal getSizeOfTotal(@PathVariable("statType") StatType statType,
                                      @RequestParam(value = "platform", required = false) Platform platform,
                                      @RequestParam(value = "retailerUuid", required = false) String retailerUuid,
                                      @RequestParam(value = "day", required = false) Integer day,
                                      @RequestParam(value = "dayStart", required = false) Integer dayStart,
                                      @RequestParam(value = "dayEnd", required = false) Integer dayEnd) {
        if (day != null) {
            StatDateUtils.DayBetween dayBetween = StatDateUtils.integerToDayBetween(day);

            return statProvider.getProvider(statType).getSizeOfDayBetween(platform, retailerUuid,
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dayBetween.getDayStart()),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dayBetween.getDayEnd()));
        }

        if (dayStart != null) {
            StatDateUtils.DayBetween dayBetween = StatDateUtils.integerToDayBetween(dayStart, dayEnd);

            return statProvider.getProvider(statType).getSizeOfDayBetween(platform, retailerUuid,
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dayBetween.getDayStart()),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dayBetween.getDayEnd()));
        }

        return statProvider.getProvider(statType).getSizeOfTotal(platform, retailerUuid);
    }

    /**
     * 某一年的数量
     */
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class SizeOfYear {
        /**
         * 月
         */
        private Integer month;
        /**
         * 总的数量
         */
        private SizeOfTotal sizeOfTotal;
    }

    /**
     * 获取某一年的总数统计
     *
     * @param platform     平台
     * @param retailerUuid 商户号
     * @param year         开始时间，如 2020
     * @return 返回年统计数的集合
     */
    @GetMapping("{statType}/sizeOfYear")
    @ResponseBody
    public Collection<SizeOfYear> getSizeOfYear(@PathVariable("statType") StatType statType,
                                                @RequestParam(value = "platform", required = false) Platform platform,
                                                @RequestParam(value = "retailerUuid", required = false) String retailerUuid,
                                                @RequestParam("year") Integer year) {
        Date yearDate;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        try {
            yearDate = simpleDateFormat.parse(year + "");
        } catch (ParseException e) {
            e.printStackTrace();

            throw new StatException(e.getMessage());
        }

        List<SizeOfYear> sizeOfYearList = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        cal.setTime(yearDate);

        simpleDateFormat = new SimpleDateFormat("yyyyMM");

        for (int month = 1; month <= 12; month++) {
            Date dayStart;
            Date dayEnd;

            try {
                dayStart = simpleDateFormat.parse(String.format("%4d%2d", cal.get(Calendar.YEAR), month));
                dayEnd = simpleDateFormat.parse(String.format("%4d%2d", cal.get(Calendar.YEAR), month + 1));
            } catch (ParseException e) {
                e.printStackTrace();

                throw new StatException(e.getMessage());
            }

            SizeOfYear sizeOfYear = new SizeOfYear();
            sizeOfYear.setMonth(month);
            sizeOfYear.setSizeOfTotal(statProvider.getProvider(statType).getSizeOfDayBetween(platform, retailerUuid,
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dayStart),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dayEnd)));

            sizeOfYearList.add(sizeOfYear);
        }

        return sizeOfYearList;
    }

    /**
     * 某一月的数量
     */
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class SizeOfMonth {
        /**
         * 天
         */
        private Integer day;
        /**
         * 总的数量
         */
        private SizeOfTotal sizeOfTotal;
    }

    /**
     * 获取某一月的总数统计
     *
     * @param platform     平台
     * @param retailerUuid 商户号
     * @param year         开始时间，如 2020
     * @param month        月，如 1
     * @return 返回年统计数的集合
     */
    @GetMapping("{statType}/sizeOfMonth")
    @ResponseBody
    public Collection<SizeOfMonth> getSizeOfMonth(@PathVariable("statType") StatType statType,
                                                  @RequestParam(value = "platform", required = false) Platform platform,
                                                  @RequestParam(value = "retailerUuid", required = false) String retailerUuid,
                                                  @RequestParam("year") Integer year,
                                                  @RequestParam("month") Integer month) {
        List<SizeOfMonth> sizeOfMonthList = new ArrayList<>();

        Date yearMonthDate;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
        try {
            yearMonthDate = simpleDateFormat.parse(year + "" + month);
        } catch (ParseException e) {
            e.printStackTrace();

            throw new StatException(e.getMessage());
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(yearMonthDate);

        for (int day = 1; day <= cal.getActualMaximum(Calendar.DAY_OF_MONTH); day++) {
            StatDateUtils.DayBetween dayBetween = StatDateUtils.integerToDayBetween(Integer.valueOf(String.format("%4d%02d%02d", year, month, day)));

            SizeOfMonth sizeOfMonth = new SizeOfMonth();
            sizeOfMonth.setDay(day);
            sizeOfMonth.setSizeOfTotal(statProvider.getProvider(statType).getSizeOfDayBetween(platform, retailerUuid,
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dayBetween.getDayStart()),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dayBetween.getDayEnd())));

            sizeOfMonthList.add(sizeOfMonth);
        }

        return sizeOfMonthList;
    }


    /**
     * 某一天区间的数量
     */
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class SizeOfDay {
        /**
         * 天
         */
        private Integer day;
        /**
         * 总的数量
         */
        private SizeOfTotal sizeOfTotal;
    }

    /**
     * 获取某一天区间的总数统计
     *
     * @param platform     平台
     * @param retailerUuid 商户号
     * @param dayStart     开始时间，如 20201101
     * @param dayEnd       结果时间，如 20201101
     * @return 返回年统计数的集合
     */
    @GetMapping("{statType}/sizeOfDayBetween")
    @ResponseBody
    public Collection<SizeOfDay> getSizeOfDayBetween(@PathVariable("statType") StatType statType,
                                                     @RequestParam(value = "platform", required = false) Platform platform,
                                                     @RequestParam(value = "retailerUuid", required = false) String retailerUuid,
                                                     @RequestParam("dayStart") Integer dayStart,
                                                     @RequestParam("dayEnd") Integer dayEnd) {
        Collection<Date> dateCollection = StatDateUtils.integerToDateCollection(dayStart, dayEnd);
        List<SizeOfDay> sizeOfDayList = new ArrayList<>();

        for (Date date : dateCollection) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            Integer day = Integer.valueOf(String.format("%4d%02d%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH)));
            StatDateUtils.DayBetween dayBetween = StatDateUtils.integerToDayBetween(day);

            SizeOfDay sizeOfDay = new SizeOfDay();
            sizeOfDay.setDay(day);
            sizeOfDay.setSizeOfTotal(statProvider.getProvider(statType).getSizeOfDayBetween(platform, retailerUuid,
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dayBetween.getDayStart()),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dayBetween.getDayEnd())));

            sizeOfDayList.add(sizeOfDay);
        }

        return sizeOfDayList;
    }

    /**
     * 某一小时的数量
     */
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class SizeOfHour {
        /**
         * 小时
         */
        private Integer hour;
        /**
         * 总的数量
         */
        private SizeOfTotal sizeOfTotal;
    }

    /**
     * 获取某一时段小时的总数统计
     *
     * @param platform     平台
     * @param retailerUuid 商户号
     * @param day          20200101
     * @return 返回天统计数的集合
     */
    @GetMapping("{statType}/sizeOfDay")
    @ResponseBody
    public Collection<SizeOfHour> getSizeOfDay(@PathVariable("statType") StatType statType,
                                               @RequestParam(value = "platform", required = false) Platform platform,
                                               @RequestParam(value = "retailerUuid", required = false) String retailerUuid,
                                               @RequestParam("day") Integer day) {
        List<SizeOfHour> sizeOfHourList = new ArrayList<>();

        Date dayDate;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            dayDate = simpleDateFormat.parse(day + "");
        } catch (ParseException e) {
            e.printStackTrace();

            throw new StatException(e.getMessage());
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(dayDate);

        simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        for (int hour = 0; hour < 24; hour++) {
            Date dayStart;
            Date dayEnd;

            try {
                dayStart = simpleDateFormat.parse(String.format("%4d%2d%2d%2d0000", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH), hour));
                dayEnd = simpleDateFormat.parse(String.format("%4d%2d%2d%2d5959", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH), hour));
            } catch (ParseException e) {
                e.printStackTrace();

                throw new StatException(e.getMessage());
            }

            SizeOfHour sizeOfHour = new SizeOfHour();
            sizeOfHour.setHour(hour);
            sizeOfHour.setSizeOfTotal(statProvider.getProvider(statType).getSizeOfDayBetween(platform, retailerUuid,
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dayStart),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dayEnd)));

            sizeOfHourList.add(sizeOfHour);
        }

        return sizeOfHourList;
    }

    /**
     * 单一分钟的数量
     */
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class SizeOfMinute {
        /**
         * 分钟
         */
        private Integer minute;
        /**
         * 总的数量
         */
        private SizeOfTotal sizeOfTotal;
    }

    /**
     * 获取某一时段小时的总数统计
     *
     * @param platform     平台
     * @param retailerUuid 商户号
     * @param day          20200101
     * @param hour         1
     * @return 返回天统计数的集合
     */
    @GetMapping("{statType}/sizeOfHour")
    @ResponseBody
    public Collection<SizeOfMinute> getSizeOfHour(@PathVariable("statType") StatType statType,
                                                  @RequestParam(value = "platform", required = false) Platform platform,
                                                  @RequestParam(value = "retailerUuid", required = false) String retailerUuid,
                                                  @RequestParam("day") Integer day,
                                                  @RequestParam("hour") Integer hour) {
        List<SizeOfMinute> sizeOfMinuteList = new ArrayList<>();

        Date dayDate;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            dayDate = simpleDateFormat.parse(day + "");
        } catch (ParseException e) {
            e.printStackTrace();

            throw new StatException(e.getMessage());
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(dayDate);

        simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        for (int minute = 0; minute < 60; minute++) {
            Date dayStart;
            Date dayEnd;

            try {
                dayStart = simpleDateFormat.parse(String.format("%4d%2d%2d%2d%2d00", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH), hour, minute));
                dayEnd = simpleDateFormat.parse(String.format("%4d%2d%2d%2d%2d59", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH), hour, minute));
            } catch (ParseException e) {
                e.printStackTrace();

                throw new StatException(e.getMessage());
            }

            SizeOfMinute sizeOfMinute = new SizeOfMinute();
            sizeOfMinute.setMinute(minute);
            sizeOfMinute.setSizeOfTotal(statProvider.getProvider(statType).getSizeOfDayBetween(platform, retailerUuid,
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dayStart),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dayEnd)));

            sizeOfMinuteList.add(sizeOfMinute);
        }

        return sizeOfMinuteList;
    }
}
