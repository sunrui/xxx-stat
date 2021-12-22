package com.xxx.stat.core.stat.provider.analyzer.order;

import com.xxx.stat.core.stat.provider.Platform;
import com.xxx.stat.core.stat.provider.StatType;
import com.xxx.stat.core.stat.provider.analyzer.IStatAnalyzerHelper;
import org.springframework.stereotype.Service;

/**
 * 订单SKU支付 - 分析
 */
@Service
public class OrderSkuPayedAnalyzer extends IStatAnalyzerHelper {
    @Override
    public StatType getStatType() {
        return StatType.ORDER_SKU_PAY;
    }

    @Override
    protected String getSizeOfTotalSql(Platform platform, String retailerUuid) {
        String sql = "select sum(sum) as size from o_order where state in ('WAIT_SHIP','SHIPPING','SHIPPED','WAIT_DELIVER','DELIVERED','REFUNDED','RECEIVED','COMPLETED') ";
        return contactSql(sql, platform, retailerUuid);
    }

    @Override
    protected String getSizeOfDayBetweenSql(Platform platform, String retailerUuid, String dayStart, String dayEnd) {
        String sql = "select sum(sum) as size from o_order where state in ('WAIT_SHIP','SHIPPING','SHIPPED','WAIT_DELIVER','DELIVERED','REFUNDED','RECEIVED','COMPLETED') and created_at >= '" + dayStart + "' and " + "created_at <= '" + dayEnd + "'";
        return contactSql(sql, platform, retailerUuid);
    }
}
