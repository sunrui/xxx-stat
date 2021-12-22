package com.xxx.stat.core.transaction.provider.index;

import com.xxx.stat.core.stat.provider.Platform;
import com.xxx.stat.core.stat.provider.SizeOfTotal;
import com.xxx.stat.core.transaction.data.TransactionDataProvider;
import com.xxx.stat.core.transaction.provider.ISummaryAnalyzer;
import com.xxx.stat.core.transaction.provider.SummaryType;
import com.xxx.stat.api.transaction.res.model.TransactionElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 首页数据统计信息
 */
@Service
public class IndexTransactionSummaryAnalyzer extends ISummaryAnalyzer {
    @Autowired
    private TransactionDataProvider transactionDataProvider;

    @Override
    public SummaryType getSummaryType() {
        return SummaryType.A_INDEX_TRANSACTION;
    }

    @Override
    public TransactionElement[] getTransactionElement(Platform platform, String retailerUuid, Integer day, Integer dayStart, Integer dayEnd) {
        SizeOfTotal gmv = transactionDataProvider.getGmv(platform, retailerUuid, day, dayStart, dayEnd);
        SizeOfTotal totalOrder = transactionDataProvider.getTotalOrder(platform, retailerUuid, day, dayStart, dayEnd);
        SizeOfTotal orderSkuNum = transactionDataProvider.getOrderSkuNum(platform, retailerUuid, day, dayStart, dayEnd);
        return new TransactionElement[]{
                new TransactionElement("GMV$", "统计周期内，平台交易金额，不含退款", gmv),
                new TransactionElement("订单量(平台)", "统计周期内，平台成交订单量", totalOrder),
                new TransactionElement("订单价$", "统计周期内，GMV/订单量(平台)", transactionDataProvider.getOrderPrice(gmv.getPrice() == null ? 0 : gmv.getPrice(), totalOrder.getSize() == null ? 0 : totalOrder.getSize())),
                new TransactionElement("商品件数", "统计周期内，订单中的商品件数", orderSkuNum),
                new TransactionElement("件单价$", "统计周期内，GMV/商品件数", transactionDataProvider.getPricePerItem(gmv.getPrice(), orderSkuNum.getSize())),
                new TransactionElement("单均件数", "统计周期内，商品件数/平台订单量，肯定是大于等于1", transactionDataProvider.getSkuNumPerOrder(orderSkuNum.getSize(), totalOrder.getSize())),
                new TransactionElement("退款GMV$", "统计周期内，退款订单中的退款总金额(sku维度，精确统计)", transactionDataProvider.getRefundedAmt(platform, retailerUuid, day, dayStart, dayEnd)),
                new TransactionElement("退款订单量(平台)", "统计周期内，退款订单数量，以平台的订单数为准（1个订单包含多个商家的，也算是1个订单）", transactionDataProvider.getOrderRefunded(platform, retailerUuid, day, dayStart, dayEnd)),
                new TransactionElement("退款件数", "统计周期内，退款订单中退款商品的sku件数，比如1个退款订单中有5件数商品，退款件数就是5件", transactionDataProvider.getOrderProductRefundedNum(platform, retailerUuid, day, dayStart, dayEnd)),
                new TransactionElement("退款订单量(sku)", "统计周期内，退款订单数量，以sku项为准（1个订单包含多种sku，比如1个订单有3种sku，就是3个订单）", transactionDataProvider.getOrderSkuRefundedNum(platform, retailerUuid, day, dayStart, dayEnd))
        };
    }
}
