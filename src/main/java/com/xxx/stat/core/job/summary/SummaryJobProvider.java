package com.xxx.stat.core.job.summary;

import com.xxx.stat.api.transaction.TransactionController;
import com.xxx.stat.api.transaction.res.TransactionSummaryVo;
import com.xxx.stat.common.log.StatLogProvider;
import com.xxx.stat.core.stat.provider.Platform;
import com.xxx.stat.core.stat.utils.StatDateUtils;
import com.xxx.stat.core.job.IJobProvider;
import com.xxx.stat.core.transaction.provider.SummaryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据预更新作业，前端将返回多少秒之前处理完的内容
 */
@Component
public class SummaryJobProvider implements IJobProvider {
    @Autowired
    private TransactionController transactionController;
    @Autowired
    private StatLogProvider statLogProvider;

    private final Map<String, TransactionSummaryVo> transactionSummaryVoMap = new ConcurrentHashMap<>();

    private String getSummaryKey(SummaryType summaryType, Platform platform, StatDateUtils.DateType dateType) {
        return summaryType.name() + "-" + (platform != null ? platform.name() : "ALL") + "-" + dateType.name();
    }

    public TransactionSummaryVo getSummary(SummaryType summaryType, Platform platform, StatDateUtils.DateType dateType) {
        return transactionSummaryVoMap.get(getSummaryKey(summaryType, platform, dateType));
    }

    private void doSummaryJob(SummaryType[] summaryTypes, Platform[] platforms, StatDateUtils.DateType[] dateTypes) {
        for (SummaryType summaryType : summaryTypes) {
            // B 端数据不在 summary 中分析
            if (summaryType == SummaryType.B_INDEX_TRANSACTION) {
                continue;
            }

            for (StatDateUtils.DateType dateType : dateTypes) {
                if (platforms != null) {
                    for (Platform platform : platforms) {
                        statLogProvider.debug("<DoSummaryJob>" + summaryType + "-" + platform + "-" + dateType);
                        String key = getSummaryKey(summaryType, platform, dateType);
                        TransactionSummaryVo transactionSummaryVo = transactionController.getSummary(summaryType, platform, null, dateType, true);
                        transactionSummaryVoMap.put(key, transactionSummaryVo);
                    }
                } else {
                    statLogProvider.debug("<DoSummaryJob>" + summaryType + "-" + "ALL" + "-" + dateType);
                    String key = getSummaryKey(summaryType, null, dateType);
                    TransactionSummaryVo transactionSummaryVo = transactionController.getSummary(summaryType, null, null, dateType, true);
                    transactionSummaryVoMap.put(key, transactionSummaryVo);
                }
            }
        }
    }

    @Override
    public void doJob() {
        doSummaryJob(SummaryType.values(), null, StatDateUtils.DateType.values());
        doSummaryJob(SummaryType.values(), Platform.values(), StatDateUtils.DateType.values());
    }
}
