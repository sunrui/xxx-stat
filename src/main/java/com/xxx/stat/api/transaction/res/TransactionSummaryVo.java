package com.xxx.stat.api.transaction.res;

import com.xxx.stat.api.transaction.res.model.TransactionElementGrowth;
import com.xxx.stat.api.transaction.res.model.TransactionElementOfDay;
import lombok.*;

import java.util.Collection;

/**
 * 交易数据统计
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionSummaryVo {
    /**
     * 摘要信息
     */
    private Collection<TransactionElementGrowth> elements;
    /**
     * 每天详细信息
     */
    private Collection<TransactionElementOfDay> summaryOfDays;
    /**
     * 处理数据更新时间于
     */
    private Long updatedAt;
    /**
     * 预处理时间耗时
     */
    private String elapsed;
}
