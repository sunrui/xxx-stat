package com.xxx.stat.api.transaction;

import com.xxx.stat.api.transaction.res.TransactionSummaryVo;
import com.xxx.stat.common.exception.StatException;
import com.xxx.stat.core.stat.provider.Platform;
import com.xxx.stat.core.stat.utils.StatDateUtils;
import com.xxx.stat.core.job.retailer.RetailerJobProvider;
import com.xxx.stat.core.job.summary.SummaryJobProvider;
import com.xxx.stat.core.transaction.provider.SummaryAnalyzer;
import com.xxx.stat.core.transaction.provider.SummaryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 通用统计控制器
 */
@RestController
@RequestMapping("transaction")
public class TransactionController {
    @Autowired
    private SummaryAnalyzer summaryAnalyzer;
    @Autowired
    private SummaryJobProvider summaryJobProvider;
    @Autowired
    private RetailerJobProvider retailerJobProvider;

    /**
     * 获取所有 dateType
     *
     * @return 交易数据摘要信息
     */
    @GetMapping("dateType")
    @ResponseBody
    public StatDateUtils.DateType[] getDateType() {
        return StatDateUtils.DateType.values();
    }

    /**
     * 获取所有 dateType
     *
     * @return 交易数据摘要信息
     */
    @GetMapping("summaryType")
    @ResponseBody
    public SummaryType[] getSummaryType() {
        return SummaryType.values();
    }

    /**
     * 获取交易数据摘要信息
     *
     * @param summaryType  摘要类型
     * @param platform     平台
     * @param retailerUuid 商户号（暂时尚未用到）
     * @param dateType     统计类型
     * @param forceUpdate  是否要强制刷新
     * @return 交易数据摘要信息
     */
    @GetMapping("summary/{summaryType}")
    @ResponseBody
    public TransactionSummaryVo getSummary(@PathVariable(value = "summaryType", required = false) SummaryType summaryType,
                                           @RequestParam(value = "platform", required = false) Platform platform,
                                           @RequestParam(value = "retailerUuid", required = false) String retailerUuid,
                                           @RequestParam(value = "dateType") StatDateUtils.DateType dateType,
                                           @RequestParam(value = "forceUpdate", required = false, defaultValue = "false") Boolean forceUpdate) {
        /*
         * 非强制更新采用预处理数据显示，预处理数据为显示最新的更新于时间。
         */
        if (Boolean.FALSE.equals(forceUpdate)) {
            TransactionSummaryVo transactionSummaryVo = summaryJobProvider.getSummary(summaryType, platform, dateType);
            if (transactionSummaryVo != null) {
                return transactionSummaryVo;
            }
        }

        return summaryAnalyzer.getSummaryAnalyzer(summaryType).getSummary(platform, retailerUuid, dateType);
    }

    /**
     * 获取交易数据摘要信息
     *
     * @param summaryType  摘要类型
     * @param platform     平台
     * @param retailerUuid 商户号（暂时尚未用到）
     * @param dayStart     起始时间
     * @param dayEnd       结束时间
     * @return 交易数据摘要信息
     */
    @GetMapping("summary/{summaryType}/dayBetween")
    @ResponseBody
    public TransactionSummaryVo getSummaryDayBetween(@PathVariable(value = "summaryType", required = false) SummaryType summaryType,
                                                     @RequestParam(value = "platform", required = false) Platform platform,
                                                     @RequestParam(value = "retailerUuid", required = false) String retailerUuid,
                                                     @RequestParam(value = "calSummaryOfDays", required = false, defaultValue = "true") Boolean calSummaryOfDays,
                                                     @RequestParam(value = "day", required = false) Integer day,
                                                     @RequestParam(value = "dayStart", required = false) Integer dayStart,
                                                     @RequestParam(value = "dayEnd", required = false) Integer dayEnd,
                                                     @RequestParam(value = "forceUpdate", required = false, defaultValue = "false") Boolean forceUpdate) {
        if (day == null) {
            if (dayStart == null || dayEnd == null || dayEnd < dayStart) {
                throw new StatException("Invalid parameter day, dayEnd or dayEnd.");
            }
        } else {
            dayStart = dayEnd = null;
        }

        /*
         * 非强制更新采用预处理数据显示，预处理数据为显示最新的更新于时间。
         */
        if (Boolean.FALSE.equals(forceUpdate) && !StringUtils.isEmpty(retailerUuid) && Boolean.FALSE.equals(calSummaryOfDays)) {
            TransactionSummaryVo transactionSummaryVo = retailerJobProvider.getRetailer(summaryType, retailerUuid);
            if (transactionSummaryVo != null) {
                return transactionSummaryVo;
            }
        }

        return summaryAnalyzer.getSummaryAnalyzer(summaryType).getSummaryDayBetween(platform, retailerUuid, day, dayStart, dayEnd, calSummaryOfDays);
    }
}
