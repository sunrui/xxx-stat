package com.xxx.stat.core.transaction.provider.index;

import com.xxx.stat.core.transaction.provider.ISummaryAnalyzer;
import com.xxx.stat.core.transaction.provider.SummaryType;
import com.xxx.stat.core.transaction.data.TransactionDataProvider;
import com.xxx.stat.api.transaction.res.model.TransactionElement;
import com.xxx.stat.core.stat.provider.Platform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 首页漏斗统计信息
 */
@Service
public class IndexFunnelSummaryAnalyzer extends ISummaryAnalyzer {
    @Autowired
    private TransactionDataProvider transactionDataProvider;

    @Override
    public SummaryType getSummaryType() {
        return SummaryType.A_INDEX_FUNNEL;
    }

    @Override
    public TransactionElement[] getTransactionElement(Platform platform, String retailerUuid, Integer day, Integer dayStart, Integer dayEnd) {
        return new TransactionElement[]{
                new TransactionElement("活跃用户", "统计周期内，按登录状态访问平台的用户数", transactionDataProvider.getUserLogin(platform, retailerUuid, day, dayStart, dayEnd)),
                new TransactionElement("加购用户数", "统计周期内，登录状态下有加购行为的用户数", transactionDataProvider.getUserAddCartNum(platform, retailerUuid, day, dayStart, dayEnd)),
                new TransactionElement("checkout", "统计周期内，点击checkout的用户数", transactionDataProvider.getUserCheckOutNum(platform, retailerUuid, day, dayStart, dayEnd)),
                new TransactionElement("购买", "统计周期内，完成支付的用户数", transactionDataProvider.getUserBuyNum(platform, retailerUuid, day, dayStart, dayEnd))
        };
    }
}
