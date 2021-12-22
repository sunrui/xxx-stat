package com.xxx.stat.core.transaction.provider;

import com.xxx.stat.common.exception.StatException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SummaryAnalyzer {
    @Resource(name = "indexFunnelSummaryAnalyzer")
    private ISummaryAnalyzer indexFunnelSummaryAnalyzer;
    @Resource(name = "indexTransactionSummaryAnalyzer")
    private ISummaryAnalyzer indexTransactionSummaryAnalyzer;
    @Resource(name = "indexRetailerTransactionSummaryAnalyzer")
    private ISummaryAnalyzer indexRetailerTransactionSummaryAnalyzer;
    @Resource(name = "userAnalyzer")
    private ISummaryAnalyzer userAnalyzer;
    @Resource(name = "retailerAnalyzer")
    private ISummaryAnalyzer retailerAnalyzer;
    @Resource(name = "orderPayAnalyzer")
    private ISummaryAnalyzer orderPayAnalyzer;
    @Resource(name = "orderRefundAnalyzer")
    private ISummaryAnalyzer orderRefundAnalyzer;
    @Resource(name = "productAnalyzer")
    private ISummaryAnalyzer productAnalyzer;

    public ISummaryAnalyzer getSummaryAnalyzer(SummaryType summaryType) {
        switch (summaryType) {
            case A_INDEX_TRANSACTION:
                return indexTransactionSummaryAnalyzer;
            case A_INDEX_FUNNEL:
                return indexFunnelSummaryAnalyzer;
            case A_USER:
                return userAnalyzer;
            case A_RETAILER:
                return retailerAnalyzer;
            case A_ORDER_PAY:
                return orderPayAnalyzer;
            case A_ORDER_REFUND:
                return orderRefundAnalyzer;
            case A_PRODUCT:
                return productAnalyzer;
            case B_INDEX_TRANSACTION:
                return indexRetailerTransactionSummaryAnalyzer;
            default:
                throw new StatException("can not fund Analyzer for SummaryType: " + summaryType);
        }
    }
}
