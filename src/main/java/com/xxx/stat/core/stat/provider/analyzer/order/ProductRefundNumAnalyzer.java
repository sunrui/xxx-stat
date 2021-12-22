package com.xxx.stat.core.stat.provider.analyzer.order;

import com.xxx.stat.core.stat.provider.analyzer.IStatAnalyzerHelper;
import com.xxx.stat.core.stat.provider.Platform;
import com.xxx.stat.core.stat.provider.StatType;
import org.springframework.stereotype.Service;

/**
 * 订单商品退款数 - 分析
 */
@Service
public class ProductRefundNumAnalyzer extends IStatAnalyzerHelper {
    @Override
    public StatType getStatType() {
        return StatType.ORDER_PRODUCT_REFUNDED_NUM;
    }

    @Override
    protected String getSizeOfTotalSql(Platform platform, String retailerUuid) {
        String sql = "select sum(quantity) as size from o_order_item where state in ('WITH_WAIT_SHIP_REFUNDED','WITH_RECEIVED_REFUNDED') ";
        return contactSql(sql, platform, retailerUuid);
    }

    @Override
    protected String getSizeOfDayBetweenSql(Platform platform, String retailerUuid, String dayStart, String dayEnd) {
        String sql = "select sum(quantity) as size from o_order_item where state in ( 'WITH_WAIT_SHIP_REFUNDED','WITH_RECEIVED_REFUNDED') and refund_at >= '" + dayStart + "' and " + "refund_at <= '" + dayEnd + "'";
        return contactSql(sql, platform, retailerUuid);
    }
}
