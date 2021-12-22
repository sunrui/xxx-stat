package com.xxx.stat.core.transaction.provider;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xxx.stat.api.transaction.res.TransactionSummaryVo;
import com.xxx.stat.api.transaction.res.model.TransactionElement;
import com.xxx.stat.api.transaction.res.model.TransactionElementGrowth;
import com.xxx.stat.api.transaction.res.model.TransactionElementOfDay;
import com.xxx.stat.common.log.StatLogProvider;
import com.xxx.stat.common.util.BigDecimalUtil;
import com.xxx.stat.common.util.DateUtil;
import com.xxx.stat.core.stat.provider.Platform;
import com.xxx.stat.core.stat.utils.StatDateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 摘要提供者
 */
@Service
public abstract class ISummaryAnalyzer {
    @Autowired
    private StatLogProvider statLogProvider;

    /**
     * 获取分析类型
     *
     * @return 分析类型
     */
    public abstract SummaryType getSummaryType();

    public abstract TransactionElement[] getTransactionElement(Platform platform, String retailerUuid, Integer day, Integer dayStart, Integer dayEnd);

    /**
     * 获取数据元素
     *
     * @param date         时间
     * @param dateType     时间类型
     * @param platform     平台
     * @param retailerUuid 商户 ID
     * @return 数据元素
     */
    private TransactionElement[] getDateElement(Date date, StatDateUtils.DateType dateType, Platform platform, String retailerUuid) {
        StatDateUtils.DayBetween nowDayBetween = StatDateUtils.dateTypeToDateBetween(date, dateType);

        Integer day = StatDateUtils.dateToInteger(nowDayBetween.getDayEnd());
        Integer dayStart = StatDateUtils.dateToInteger(nowDayBetween.getDayStart());
        Integer dayEnd = StatDateUtils.dateToInteger(nowDayBetween.getDayEnd());

        if (dayStart.equals(dayEnd)) {
            dayStart = dayEnd = null;
        } else {
            day = null;
        }

        return getTransactionElement(platform, retailerUuid, day, dayStart, dayEnd);
    }

    /**
     * 获取交易数据摘要信息
     *
     * @param platform     平台
     * @param retailerUuid 商户号（暂时尚未用到）
     * @param dateType     统计类型
     * @return 交易数据摘要信息
     */
    public TransactionSummaryVo getSummary(Platform platform, String retailerUuid, StatDateUtils.DateType dateType) {
        TransactionSummaryVo transactionSummaryVo = new TransactionSummaryVo();

        statLogProvider.debug("<GetSummary> for platform: " + platform + ", dateType: " + dateType);

        Date nowDate = new Date();

        // 本周摘要
        TransactionElement[] summaryElements = getDateElement(nowDate, dateType, platform, retailerUuid);

        // 上一个周期摘要
        StatDateUtils.DateType growDateType = null;
        switch (dateType) {
            case TODAY:
                growDateType = StatDateUtils.DateType.TODAY;
                break;
            case YESTERDAY:
                growDateType = StatDateUtils.DateType.YESTERDAY;
                break;
            case WEEK:
            case LAST_WEEK:
                growDateType = StatDateUtils.DateType.WEEK;
                break;
            case MONTH:
            case LAST_MONTH:
                growDateType = StatDateUtils.DateType.MONTH;
                break;
        }

        TransactionElement[] growthSummaryElements = getDateElement(StatDateUtils.getGrowthDate(nowDate, growDateType), dateType, platform, retailerUuid);

        // 环比增长摘要
        Collection<TransactionElementGrowth> transactionElementGrowthCollection = new ArrayList<>();
        for (int index = 0; index < summaryElements.length; index++) {
            TransactionElementGrowth transactionSummaryElementGrowth = new TransactionElementGrowth();
            transactionSummaryElementGrowth.setName(summaryElements[index].getName());
            transactionSummaryElementGrowth.setSizeOfTotal(summaryElements[index].getSizeOfTotal());
            transactionSummaryElementGrowth.setPrompt(summaryElements[index].getPrompt());
            transactionSummaryElementGrowth.setGrowth(summaryElements[index].getSizeOfTotal().getPrice());

            if (growthSummaryElements[index].getSizeOfTotal().getPrice() != null) {
                if (growthSummaryElements[index].getSizeOfTotal().getPrice() == null || growthSummaryElements[index].getSizeOfTotal().getPrice() == 0) {
                    transactionSummaryElementGrowth.setGrowth(null);
                } else {
                    BigDecimal numerator = BigDecimal.valueOf(summaryElements[index].getSizeOfTotal().getPrice() == null ? 0 : summaryElements[index].getSizeOfTotal().getPrice() - growthSummaryElements[index].getSizeOfTotal().getPrice());
                    BigDecimal denominator = BigDecimal.valueOf(growthSummaryElements[index].getSizeOfTotal().getPrice());
                    transactionSummaryElementGrowth.setGrowth(BigDecimalUtil.mul(BigDecimalUtil.div(numerator, denominator, 3), 1, BigDecimal.valueOf(100)).floatValue());
                }

            } else {
                if (growthSummaryElements[index].getSizeOfTotal().getSize() == null || growthSummaryElements[index].getSizeOfTotal().getSize() == 0) {
                    transactionSummaryElementGrowth.setGrowth(null);
                } else {
                    BigDecimal numerator = BigDecimal.valueOf(summaryElements[index].getSizeOfTotal().getSize() == null ? 0 : summaryElements[index].getSizeOfTotal().getSize() - growthSummaryElements[index].getSizeOfTotal().getSize());
                    BigDecimal denominator = BigDecimal.valueOf(growthSummaryElements[index].getSizeOfTotal().getSize());
                    transactionSummaryElementGrowth.setGrowth(BigDecimalUtil.mul(BigDecimalUtil.div(numerator, denominator, 3), 1, BigDecimal.valueOf(100)).floatValue());
                }
            }

            transactionElementGrowthCollection.add(transactionSummaryElementGrowth);
        }

        // 获取当前类型时间周期
        StatDateUtils.DayBetween dayBetween = StatDateUtils.dateTypeToDateBetween(nowDate, dateType);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        statLogProvider.debug("\tnowDate: " + simpleDateFormat.format(nowDate) +
                ", dateType: " + dateType.name() +
                ", dayStart: " + simpleDateFormat.format(dayBetween.getDayStart()) +
                ", dayEnd: " + simpleDateFormat.format(dayBetween.getDayEnd()));

        try {
            statLogProvider.debug("\t\tsummary: " + new ObjectMapper().writeValueAsString(transactionElementGrowthCollection));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        List<TransactionElementOfDay> transactionElementOfDayList = new ArrayList<>();

        // 拿到每一天的值
        Collection<Date> dateCollection = StatDateUtils.dateToDayCollection(dayBetween.getDayStart(), dayBetween.getDayEnd());
        for (Date date : dateCollection) {
            Integer day = StatDateUtils.dateToInteger(date);
            List<TransactionElement> transactionElementList = Arrays.asList(getDateElement(date, StatDateUtils.DateType.TODAY, platform, retailerUuid));

            TransactionElementOfDay transactionElementOfDay = TransactionElementOfDay.builder()
                    .day(day)
                    .elements(transactionElementList)
                    .build();

            statLogProvider.debug("\tday: " + day);
            try {
                statLogProvider.debug("\t\tjson: " + new ObjectMapper().writeValueAsString(transactionElementList));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            transactionElementOfDayList.add(transactionElementOfDay);
        }

        transactionSummaryVo.setElements(transactionElementGrowthCollection);
        transactionSummaryVo.setSummaryOfDays(transactionElementOfDayList);
        transactionSummaryVo.setUpdatedAt(new Date().getTime());
        transactionSummaryVo.setElapsed(DateUtil.getElapsedTime(new Date(), nowDate));

        try {
            statLogProvider.debug("\tvo: " + new ObjectMapper().writeValueAsString(transactionSummaryVo) + "\n");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return transactionSummaryVo;
    }

    /**
     * 获取交易数据摘要信息（不具备性能考虑，这个函数需要性能需要 ETL 加流计算架构改）
     *
     * @param platform     平台
     * @param retailerUuid 商户号（暂时尚未用到）
     * @param dayStart     开始开
     * @param dayEnd       结束天
     * @return 交易数据摘要信息
     */
    public TransactionSummaryVo getSummaryDayBetween(Platform platform, String retailerUuid, Integer day, Integer dayStart, Integer dayEnd, Boolean calSummaryOfDays) {
        TransactionSummaryVo transactionSummaryVo = new TransactionSummaryVo();

        statLogProvider.debug("<getSummaryDayBetween> for platform: " + platform + ", retailerUuid: " + retailerUuid + ", day: " + day + ", dayStart: " + dayStart + ", dayEnd: " + dayEnd);

        Date nowDate = new Date();

        // 本周期摘要
        TransactionElement[] summaryElements = getTransactionElement(platform, retailerUuid, day, dayStart, dayEnd);

        Integer growthDay = null;
        Integer growthDayStart = null;
        Integer growthDayEnd = null;

        if (day != null) {
            StatDateUtils.DayBetween dayBetween = StatDateUtils.integerToDayBetween(day);
            growthDay = StatDateUtils.dateToInteger(new Date(dayBetween.getDayStart().getTime() - 24 * 60 * 60 * 1000L));
        } else {
            Collection<Date> dateCollection = StatDateUtils.integerToDateCollection(dayStart, dayEnd);
            StatDateUtils.DayBetween dayBetween = StatDateUtils.integerToDayBetween(dayStart);
            growthDayEnd = StatDateUtils.dateToInteger(new Date(dayBetween.getDayStart().getTime() - 24 * 60 * 60 * 1000L));
            dayBetween = StatDateUtils.integerToDayBetween(growthDayEnd);
            growthDayStart = StatDateUtils.dateToInteger(new Date(dayBetween.getDayStart().getTime() - dateCollection.size() * 24 * 60 * 60 * 1000L));
        }

        // 上一个周期摘要
        TransactionElement[] growthSummaryElements = getTransactionElement(platform, retailerUuid, growthDay, growthDayStart, growthDayEnd);

        // 环比增长摘要
        Collection<TransactionElementGrowth> transactionElementGrowthCollection = new ArrayList<>();
        for (int index = 0; index < summaryElements.length; index++) {
            TransactionElementGrowth transactionSummaryElementGrowth = new TransactionElementGrowth();
            transactionSummaryElementGrowth.setName(summaryElements[index].getName());
            transactionSummaryElementGrowth.setSizeOfTotal(summaryElements[index].getSizeOfTotal());
            transactionSummaryElementGrowth.setPrompt(summaryElements[index].getPrompt());
            transactionSummaryElementGrowth.setGrowth(summaryElements[index].getSizeOfTotal().getPrice());

            if (growthSummaryElements[index].getSizeOfTotal().getPrice() != null) {
                if (growthSummaryElements[index].getSizeOfTotal().getPrice() == null || growthSummaryElements[index].getSizeOfTotal().getPrice() == 0) {
                    transactionSummaryElementGrowth.setGrowth(null);
                } else {
                    BigDecimal numerator = BigDecimal.valueOf(summaryElements[index].getSizeOfTotal().getPrice() == null ? 0 : summaryElements[index].getSizeOfTotal().getPrice() - growthSummaryElements[index].getSizeOfTotal().getPrice());
                    BigDecimal denominator = BigDecimal.valueOf(growthSummaryElements[index].getSizeOfTotal().getPrice());
                    transactionSummaryElementGrowth.setGrowth(BigDecimalUtil.mul(BigDecimalUtil.div(numerator, denominator, 3), 1, BigDecimal.valueOf(100)).floatValue());
                }

            } else {
                if (growthSummaryElements[index].getSizeOfTotal().getSize() == null || growthSummaryElements[index].getSizeOfTotal().getSize() == 0) {
                    transactionSummaryElementGrowth.setGrowth(null);
                } else {
                    BigDecimal numerator = BigDecimal.valueOf(summaryElements[index].getSizeOfTotal().getSize() == null ? 0 : summaryElements[index].getSizeOfTotal().getSize() - growthSummaryElements[index].getSizeOfTotal().getSize());
                    BigDecimal denominator = BigDecimal.valueOf(growthSummaryElements[index].getSizeOfTotal().getSize());
                    transactionSummaryElementGrowth.setGrowth(BigDecimalUtil.mul(BigDecimalUtil.div(numerator, denominator, 3), 1, BigDecimal.valueOf(100)).floatValue());
                }
            }

            transactionElementGrowthCollection.add(transactionSummaryElementGrowth);
        }

        if (Boolean.TRUE.equals(calSummaryOfDays)) {
            List<TransactionElementOfDay> transactionElementOfDayList = new ArrayList<>();

            // 拿到每一天的值
            Collection<Date> dateCollection = new ArrayList<>();

            if (dayStart != null) {
                dateCollection = StatDateUtils.integerToDateCollection(dayStart, dayEnd);
            } else {
                dateCollection.add(StatDateUtils.integerToDayBetween(day).getDayStart());
            }

            for (Date date : dateCollection) {
                Integer today = StatDateUtils.dateToInteger(date);
                List<TransactionElement> transactionElementList = Arrays.asList(getDateElement(date, StatDateUtils.DateType.TODAY, platform, retailerUuid));

                TransactionElementOfDay transactionElementOfDay = TransactionElementOfDay.builder()
                        .day(today)
                        .elements(transactionElementList)
                        .build();

                statLogProvider.debug("\tday: " + today);
                try {
                    statLogProvider.debug("\t\tjson: " + new ObjectMapper().writeValueAsString(transactionElementList));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }

                transactionElementOfDayList.add(transactionElementOfDay);
            }

            transactionSummaryVo.setSummaryOfDays(transactionElementOfDayList);
        }

        transactionSummaryVo.setElements(transactionElementGrowthCollection);
        transactionSummaryVo.setUpdatedAt(new Date().getTime());
        transactionSummaryVo.setElapsed(DateUtil.getElapsedTime(new Date(), nowDate));

        try {
            statLogProvider.debug("\tvo: " + new ObjectMapper().writeValueAsString(transactionSummaryVo) + "\n");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return transactionSummaryVo;
    }
}
