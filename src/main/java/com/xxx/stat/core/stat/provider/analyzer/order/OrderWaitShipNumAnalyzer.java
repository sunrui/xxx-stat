package com.xxx.stat.core.stat.provider.analyzer.order;

import com.xxx.stat.core.stat.provider.Platform;
import com.xxx.stat.core.stat.provider.StatType;
import com.xxx.stat.core.stat.provider.analyzer.IStatAnalyzerHelper;
import org.springframework.stereotype.Service;

/**
 * 待发订单数 - 分析
 */
@Service
public class OrderWaitShipNumAnalyzer extends IStatAnalyzerHelper {
    @Override
    public StatType getStatType() {
        return StatType.ORDER_WAIT_SHIP_NUM;
    }

    @Override
    protected String getSizeOfTotalSql(Platform platform, String retailerUuid) {
        String sql = "select count(id) as size from o_order_item where state =  'WAIT_SHIP' ";
        return contactSql(sql, platform, retailerUuid);
    }

    @Override
    protected String getSizeOfDayBetweenSql(Platform platform, String retailerUuid, String dayStart, String dayEnd) {
        String sql = "select count(id) as size from o_order_item where state = 'WAIT_SHIP' and created_at >= '" + dayStart + "' and " + "created_at <= '" + dayEnd + "'";
        return contactSql(sql, platform, retailerUuid);
    }
}
