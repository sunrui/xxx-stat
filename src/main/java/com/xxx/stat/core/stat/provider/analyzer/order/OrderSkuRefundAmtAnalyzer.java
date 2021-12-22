package com.xxx.stat.core.stat.provider.analyzer.order;


import com.xxx.stat.core.stat.provider.Platform;
import com.xxx.stat.core.stat.provider.StatType;
import com.xxx.stat.core.stat.provider.analyzer.IStatAnalyzerHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 订单SKU退款金额/$ - 分析
 */
@Service
public class OrderSkuRefundAmtAnalyzer extends IStatAnalyzerHelper {
    @Override
    public StatType getStatType() {
        return StatType.ORDER_SKU_REFUNDED_AMT;
    }

    @Override
    protected String getSizeOfTotalSql(Platform platform, String retailerUuid) {
        String sql;
        if (StringUtils.isEmpty(retailerUuid)) {
            sql = "select sum(platform_amt_sku) as price from o_order_item where state in ('WITH_WAIT_SHIP_REFUNDED','WITH_RECEIVED_REFUNDED') ";
        } else {
            sql = "select sum(merchant_amt_sku) as price from o_order_item where state in ('WITH_WAIT_SHIP_REFUNDED','WITH_RECEIVED_REFUNDED') ";
        }

        return contactSql(sql, platform, retailerUuid);
    }

    @Override
    protected String getSizeOfDayBetweenSql(Platform platform, String retailerUuid, String dayStart, String dayEnd) {
        String sql;
        if (StringUtils.isEmpty(retailerUuid)) {
            sql = "select sum(platform_amt_sku) as price from o_order_item where state in ('WITH_WAIT_SHIP_REFUNDED','WITH_RECEIVED_REFUNDED') and refund_at >= '" + dayStart + "' and " + "refund_at <= '" + dayEnd + "'";
        } else {
            sql = "select sum(merchant_amt_sku) as price from o_order_item where state in ('WITH_WAIT_SHIP_REFUNDED','WITH_RECEIVED_REFUNDED') and refund_at >= '" + dayStart + "' and " + "refund_at <= '" + dayEnd + "'";
        }

        return contactSql(sql, platform, retailerUuid);
    }
}
