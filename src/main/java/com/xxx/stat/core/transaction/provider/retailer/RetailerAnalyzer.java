package com.xxx.stat.core.transaction.provider.retailer;


import com.xxx.stat.core.stat.provider.Platform;
import com.xxx.stat.core.stat.provider.StatType;
import com.xxx.stat.api.stat.StatController;
import com.xxx.stat.core.transaction.provider.ISummaryAnalyzer;
import com.xxx.stat.core.transaction.provider.SummaryType;
import com.xxx.stat.api.transaction.res.model.TransactionElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 商户统计信息
 */
@Service
public class RetailerAnalyzer extends ISummaryAnalyzer {
    @Autowired
    private StatController statController;

    @Override
    public SummaryType getSummaryType() {
        return SummaryType.A_RETAILER;
    }

    @Override
    public TransactionElement[] getTransactionElement(Platform platform, String retailerUuid, Integer day, Integer dayStart, Integer dayEnd) {
        return new TransactionElement[]{
                new TransactionElement("入驻商户", "统计周期内，完成入驻的商户数", statController.getSizeOfTotal(StatType.MERCHANT_REG, platform, retailerUuid, day, dayStart, dayEnd)),
                new TransactionElement("活跃商户", "统计周期内，有在售商品的商户数", statController.getSizeOfTotal(StatType.MERCHANT_ACTIVE, platform, retailerUuid, day, dayStart, dayEnd))
        };
    }
}
