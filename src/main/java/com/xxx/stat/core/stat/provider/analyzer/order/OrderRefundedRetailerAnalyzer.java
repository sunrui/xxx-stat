package com.xxx.stat.core.stat.provider.analyzer.order;

import com.xxx.stat.core.stat.provider.Platform;
import com.xxx.stat.core.stat.provider.StatType;
import com.xxx.stat.core.stat.provider.analyzer.IStatAnalyzerHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 订单退款-B端 - 分析
 */
@Service
public class OrderRefundedRetailerAnalyzer extends IStatAnalyzerHelper {
    @Override
    public StatType getStatType() {
        return StatType.ORDER_REFUNDED_B;
    }

    @Override
    protected String getSizeOfTotalSql(Platform platform, String retailerUuid) {
        String sql = "select count(id) as size  from o_order_item where state in ( 'WITH_WAIT_SHIP_REFUNDED' , 'WITH_RECEIVED_REFUNDED') ";
        if (!StringUtils.isEmpty(platform)) {
            sql = sql + " AND platform='" + platform + "'";
        }
        if (!StringUtils.isEmpty(retailerUuid)) {
            sql = sql + " AND retailer_uuid='" + retailerUuid + "'";
        }

        return sql;
    }

    @Override
    protected String getSizeOfDayBetweenSql(Platform platform, String retailerUuid, String dayStart, String dayEnd) {
        String sql = "select count(id) as size from o_order_item where state in ( 'WITH_WAIT_SHIP_REFUNDED' , 'WITH_RECEIVED_REFUNDED') and refund_at >= '" + dayStart + "' and " + "refund_at <= '" + dayEnd + "'";
        if (!StringUtils.isEmpty(platform)) {
            sql = sql + " AND platform='" + platform + "'";
        }
        if (!StringUtils.isEmpty(retailerUuid)) {
            sql = sql + " AND retailer_uuid='" + retailerUuid + "'";
        }

        return sql;
    }
}
