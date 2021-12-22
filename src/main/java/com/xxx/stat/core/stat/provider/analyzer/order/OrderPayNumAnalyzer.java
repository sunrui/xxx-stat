package com.xxx.stat.core.stat.provider.analyzer.order;

import com.xxx.stat.core.stat.provider.Platform;
import com.xxx.stat.core.stat.provider.StatType;
import com.xxx.stat.core.stat.provider.analyzer.IStatAnalyzerHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 订单完成 - 分析
 */
@Service
public class OrderPayNumAnalyzer extends IStatAnalyzerHelper {
    @Override
    public StatType getStatType() {
        return StatType.ORDER_PAY_NUM;
    }

    @Override
    protected String getSizeOfTotalSql(Platform platform, String retailerUuid) {
        String sql = "select count(1) as size from o_order where state in ('WAIT_SHIP','SHIPPING','SHIPPED','WAIT_DELIVER','DELIVERED','REFUNDED','RECEIVED','COMPLETED') ";
        return contactSql(sql, platform, retailerUuid);
    }

    @Override
    protected String getSizeOfDayBetweenSql(Platform platform, String retailerUuid, String dayStart, String dayEnd) {
        String sql;
        if (StringUtils.isEmpty(retailerUuid)) {
            sql = "select count(id) as size from o_order where state in ('WAIT_SHIP','SHIPPING','SHIPPED','WAIT_DELIVER','DELIVERED','REFUNDED','RECEIVED','COMPLETED') and created_at >= '" + dayStart + "' and " + "created_at <= '" + dayEnd + "'";
            return contactSql(sql, platform, retailerUuid);
        } else {
            return "select count(id) as size  from o_order_item where state in ('WAIT_SHIP','SHIPPING','SHIPPED','WAIT_DELIVER', 'DELIVERED', 'RECEIVED' , 'WITH_WAIT_SHIP_REFUNDED' , 'WITH_RECEIVED_REFUNDED')    and created_at >= '" + dayStart + "' and " + "created_at <= '" + dayEnd + "' AND retailer_uuid='" + retailerUuid + "'";
        }
    }
}
