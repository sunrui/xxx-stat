package com.xxx.stat.core.job.retailer;

import com.xxx.stat.api.stat.StatController;
import com.xxx.stat.api.transaction.TransactionController;
import com.xxx.stat.api.transaction.res.TransactionSummaryVo;
import com.xxx.stat.common.log.StatLogProvider;
import com.xxx.stat.core.stat.provider.RetailerPair;
import com.xxx.stat.core.stat.utils.StatDateUtils;
import com.xxx.stat.core.job.IJobProvider;
import com.xxx.stat.core.transaction.provider.SummaryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * B 端摘要数据预更新作业，前端将返回多少秒之前处理完的内容
 */
@Component
public class RetailerJobProvider implements IJobProvider {
    @Autowired
    private StatController statController;
    @Autowired
    private TransactionController transactionController;
    @Autowired
    private StatLogProvider statLogProvider;

    private final Map<String, TransactionSummaryVo> transactionRetailerVoMap = new ConcurrentHashMap<>();

    private String getRetailerKey(SummaryType summaryType, String retailerUuid) {
        return summaryType.name() + "-" + retailerUuid;
    }

    public TransactionSummaryVo getRetailer(SummaryType summaryType, String retailerUuid) {
        return transactionRetailerVoMap.get(getRetailerKey(summaryType, retailerUuid));
    }

    private void doRetailerJob(SummaryType[] summaryTypes, String retailerUuid, Integer day) {
        for (SummaryType summaryType : summaryTypes) {
            statLogProvider.debug("<DoRetailerJob>" + summaryType + "-" + "RETAILER" + "-" + retailerUuid);
            String key = getRetailerKey(summaryType, retailerUuid);
            TransactionSummaryVo transactionSummaryVo = transactionController.getSummaryDayBetween(summaryType, null, retailerUuid, false, day, null, null, true);
            transactionRetailerVoMap.put(key, transactionSummaryVo);
        }
    }

    @Override
    public void doJob() {
        Integer day = StatDateUtils.dateToInteger(new Date());
        Collection<RetailerPair> retailerPairs = statController.getAllRetailer();

        for (RetailerPair retailerPair : retailerPairs) {
            SummaryType[] summaryTypes = new SummaryType[]{
                    SummaryType.B_INDEX_TRANSACTION
            };

            doRetailerJob(summaryTypes, retailerPair.getUuid(), day);
        }
    }
}
