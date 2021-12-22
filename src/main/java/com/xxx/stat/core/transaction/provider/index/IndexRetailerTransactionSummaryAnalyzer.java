package com.xxx.stat.core.transaction.provider.index;

import com.xxx.stat.api.transaction.res.model.TransactionElement;
import com.xxx.stat.common.util.DateUtil;
import com.xxx.stat.core.stat.provider.Platform;
import com.xxx.stat.core.stat.utils.StatDateUtils;
import com.xxx.stat.core.transaction.data.TransactionDataProvider;
import com.xxx.stat.core.transaction.provider.ISummaryAnalyzer;
import com.xxx.stat.core.transaction.provider.SummaryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndexRetailerTransactionSummaryAnalyzer  extends ISummaryAnalyzer {

    @Autowired
    private TransactionDataProvider transactionDataProvider;

    @Override
    public SummaryType getSummaryType() {
        return SummaryType.B_INDEX_TRANSACTION;
    }

    @Override
    public TransactionElement[] getTransactionElement(Platform platform, String retailerUuid, Integer day, Integer dayStart, Integer dayEnd) {
        StatDateUtils.DayBetween nowBetween = StatDateUtils.integerToDayBetween(day);
        StatDateUtils.DayBetween yesterdayBetween = StatDateUtils.dateToDayBetween(DateUtil.getPreDay(nowBetween.getDayEnd()));

        return new TransactionElement[]{
                new TransactionElement("orderYesterday", "统计周期内（昨日），新增的订单数总和", transactionDataProvider.getTotalOrder(platform,retailerUuid,null,StatDateUtils.dateToInteger(yesterdayBetween.getDayStart()),StatDateUtils.dateToInteger(yesterdayBetween.getDayEnd()))),
                new TransactionElement("transactionYesterday", "统计周期内（昨日），新增订单的金额总和", transactionDataProvider.getGmv(platform,retailerUuid,null,StatDateUtils.dateToInteger(yesterdayBetween.getDayStart()),StatDateUtils.dateToInteger(yesterdayBetween.getDayEnd()))),
                new TransactionElement("refundOrderYesterday", "统计周期内（昨日），退款成功的订单数", transactionDataProvider.getOrderSkuRefundedNum(platform,retailerUuid,null,StatDateUtils.dateToInteger(yesterdayBetween.getDayStart()),StatDateUtils.dateToInteger(yesterdayBetween.getDayEnd()))),
                new TransactionElement("refundGMVYesterday", "统计周期内（昨日），退款成功的订单的金额总和", transactionDataProvider.getRefundedAmt(platform,retailerUuid,null,StatDateUtils.dateToInteger(yesterdayBetween.getDayStart()),StatDateUtils.dateToInteger(yesterdayBetween.getDayEnd()))),
                new TransactionElement("waitShip", "统计周期内（2020.1.1起算），待发货状态的订单数", transactionDataProvider.getOrderWaitShipNum(platform,retailerUuid,null,StatDateUtils.dateToInteger(DateUtil.parseTime("2020-01-01 00:00:00")),StatDateUtils.dateToInteger(nowBetween.getDayEnd()))),
                new TransactionElement("GMVpending", "统计周期内（2020.1.1起算），待发货订单金额总和", transactionDataProvider.getOrderWaitShipAmt(platform,retailerUuid,null,StatDateUtils.dateToInteger(DateUtil.parseTime("2020-01-01 00:00:00")),StatDateUtils.dateToInteger(nowBetween.getDayEnd()))),
                new TransactionElement("shipping", "统计周期内（2020.1.1起算），在途状态（已发货、未妥投）的订单数", transactionDataProvider.getOrderInShipNum(platform,retailerUuid,null,StatDateUtils.dateToInteger(DateUtil.parseTime("2020-01-01 00:00:00")),StatDateUtils.dateToInteger(nowBetween.getDayEnd()))),
                new TransactionElement("GMVshipping", "统计周期内（2020.1.1起算），在途状态（已发货、未妥投）的订单金额总和", transactionDataProvider.getOrderInShipAmt(platform,retailerUuid,null,StatDateUtils.dateToInteger(DateUtil.parseTime("2020-01-01 00:00:00")),StatDateUtils.dateToInteger(nowBetween.getDayEnd()))),
                new TransactionElement("delivered", "统计周期内（2020.1.1起算），已妥投状态的订单数", transactionDataProvider.getOrderDeliveredNum(platform,retailerUuid,null,StatDateUtils.dateToInteger(DateUtil.parseTime("2020-01-01 00:00:00")),StatDateUtils.dateToInteger(nowBetween.getDayEnd()))),
                new TransactionElement("GMVdelivered", "统计周期内（2020.1.1起算），已妥投状态的订单金额总和", transactionDataProvider.getOrderDeliveredAmt(platform,retailerUuid,null,StatDateUtils.dateToInteger(DateUtil.parseTime("2020-01-01 00:00:00")),StatDateUtils.dateToInteger(nowBetween.getDayEnd()))),
                new TransactionElement("received", "统计周期内（2020.1.1起算），已签收状态的订单数", transactionDataProvider.getOrderReceivedNum(platform,retailerUuid,null,StatDateUtils.dateToInteger(DateUtil.parseTime("2020-01-01 00:00:00")),StatDateUtils.dateToInteger(nowBetween.getDayEnd()))),
                new TransactionElement("GMVreceived", "统计周期内（2020.1.1起算），已签收状态的订单金额总和", transactionDataProvider.getOrderReceivedAmt(platform,retailerUuid,null,StatDateUtils.dateToInteger(DateUtil.parseTime("2020-01-01 00:00:00")),StatDateUtils.dateToInteger(nowBetween.getDayEnd()))),
                new TransactionElement("refund", "统计周期内（2020.1.1起算），已退款状态的订单数", transactionDataProvider.getOrderRefundedFromB(platform,retailerUuid,null,StatDateUtils.dateToInteger(DateUtil.parseTime("2020-01-01 00:00:00")),StatDateUtils.dateToInteger(nowBetween.getDayEnd()))),
                new TransactionElement("GMVrefund", "统计周期内（2020.1.1起算），已退款状态的订单金额总和", transactionDataProvider.getRefundedAmt(platform,retailerUuid,null,StatDateUtils.dateToInteger(DateUtil.parseTime("2020-01-01 00:00:00")),StatDateUtils.dateToInteger(nowBetween.getDayEnd()))),
                new TransactionElement("orderCumulative", "统计周期内（2020.1.1起算），所有状态的订单数", transactionDataProvider.getTotalOrder(platform,retailerUuid,null,StatDateUtils.dateToInteger(DateUtil.parseTime("2020-01-01 00:00:00")),StatDateUtils.dateToInteger(nowBetween.getDayEnd()))),
                new TransactionElement("GMVorderCumulative", "统计周期内（2020.1.1起算），所有状态的订单金额总和", transactionDataProvider.getGmv(platform,retailerUuid,null,StatDateUtils.dateToInteger(DateUtil.parseTime("2020-01-01 00:00:00")),StatDateUtils.dateToInteger(nowBetween.getDayEnd()))),
                new TransactionElement("onsale", "统计周期内（商家账号创建时间起算），上架状态的商品数", transactionDataProvider.getProductSaleNum(platform,retailerUuid,null,StatDateUtils.dateToInteger(DateUtil.parseTime("2020-01-01 00:00:00")),StatDateUtils.dateToInteger(nowBetween.getDayEnd()))),
                new TransactionElement("onsaleSku", "统计周期内（商家账号创建时间起算），上架状态的sku数", transactionDataProvider.getProductSaleSkuNum(platform,retailerUuid,null,StatDateUtils.dateToInteger(DateUtil.parseTime("2020-01-01 00:00:00")),StatDateUtils.dateToInteger(nowBetween.getDayEnd())))
        };
    }
}
