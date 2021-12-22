package com.xxx.stat.core.transaction.provider.order;

import com.xxx.stat.api.stat.StatController;
import com.xxx.stat.api.transaction.res.model.TransactionElement;
import com.xxx.stat.core.stat.provider.Platform;
import com.xxx.stat.core.stat.provider.StatType;
import com.xxx.stat.core.transaction.provider.ISummaryAnalyzer;
import com.xxx.stat.core.transaction.provider.SummaryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 订单退款
 */
@Service
public class OrderRefundAnalyzer extends ISummaryAnalyzer {
    @Autowired
    private StatController statController;

    @Override
    public SummaryType getSummaryType() {
        return SummaryType.A_ORDER_REFUND;
    }

    @Override
    public TransactionElement[] getTransactionElement(Platform platform, String retailerUuid, Integer day, Integer dayStart, Integer dayEnd) {
        return new TransactionElement[]{
                new TransactionElement("退款订单量(sku)", "统计周期内，退款订单数量，以sku项为准（1个订单包含多种sku，比如1个订单有3种sku，就是3个订单）", statController.getSizeOfTotal(StatType.ORDER_SKU_REFUNDED_NUM, platform, retailerUuid, day, dayStart, dayEnd)),
                new TransactionElement("退款件数", "统计周期内，退款订单中退款商品的sku件数，比如1个退款订单中有5件数商品，退款件数就是5件", statController.getSizeOfTotal(StatType.ORDER_PRODUCT_REFUNDED_NUM, platform, retailerUuid, day, dayStart, dayEnd)),
                new TransactionElement("退款GMV", "统计周期内，退款订单中的退款总金额", statController.getSizeOfTotal(StatType.ORDER_SKU_REFUNDED_AMT, platform, retailerUuid, day, dayStart, dayEnd))
        };
    }
}
