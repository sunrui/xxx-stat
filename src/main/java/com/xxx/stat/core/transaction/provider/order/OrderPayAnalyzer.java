package com.xxx.stat.core.transaction.provider.order;

import com.xxx.stat.api.stat.StatController;
import com.xxx.stat.api.transaction.res.model.TransactionElement;
import com.xxx.stat.common.util.DateUtil;
import com.xxx.stat.core.stat.provider.Platform;
import com.xxx.stat.core.stat.provider.StatType;
import com.xxx.stat.core.stat.utils.StatDateUtils;
import com.xxx.stat.core.transaction.provider.ISummaryAnalyzer;
import com.xxx.stat.core.transaction.provider.SummaryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 订单支付
 */
@Service
public class OrderPayAnalyzer extends ISummaryAnalyzer {
    @Autowired
    private StatController statController;

    @Override
    public SummaryType getSummaryType() {
        return SummaryType.A_ORDER_PAY;
    }

    @Override
    public TransactionElement[] getTransactionElement(Platform platform, String retailerUuid, Integer day, Integer dayStart, Integer dayEnd) {
        return new TransactionElement[]{
                new TransactionElement("订单量(平台)", "平台成交订单量", statController.getSizeOfTotal(StatType.ORDER_PAY_NUM, platform, retailerUuid, day, dayStart, dayEnd)),
                new TransactionElement("销售件数", "统计周期内，被购买订单中的商品件数", statController.getSizeOfTotal(StatType.ORDER_SKU_PAY, platform, retailerUuid, day, dayStart, dayEnd)),
                new TransactionElement("GMV", "统计周期内，平台交易金额， 不含退款", statController.getSizeOfTotal(StatType.ORDER_PAY_AMT, platform, retailerUuid, day, dayStart, dayEnd)),
                new TransactionElement("订单总数", "订单总数， 不含退款", statController.getSizeOfTotal(StatType.ORDER_PAY_NUM, platform, retailerUuid, null, StatDateUtils.dateToInteger(StatDateUtils.integerToDayBetween(20190101).getDayStart()), StatDateUtils.dateToInteger(DateUtil.now())))
        };
    }
}
