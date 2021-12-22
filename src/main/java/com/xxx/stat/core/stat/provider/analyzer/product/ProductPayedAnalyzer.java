package com.xxx.stat.core.stat.provider.analyzer.product;

import com.xxx.stat.core.stat.provider.analyzer.IStatAnalyzerHelper;
import com.xxx.stat.core.stat.provider.Platform;
import com.xxx.stat.core.stat.provider.StatType;
import org.springframework.stereotype.Service;

/**
 * 出单商品数 - 分析
 */
@Service
public class ProductPayedAnalyzer extends IStatAnalyzerHelper {
    @Override
    public StatType getStatType() {
        return StatType.PRODUCT_PAYED;
    }

    @Override
    protected String getSizeOfTotalSql(Platform platform, String retailerUuid) {
        String sql = "select count(DISTINCT(product_uuid)) as size from o_order_item where state in ('WAIT_SHIP','SHIPPED','WAIT_DELIVER','DELIVERED','RECEIVED','WITH_SHIP_REFUNDING','WITH_RECEIVED_REFUNDING','WITH_WAIT_SHIP_REFUNDED','WITH_RECEIVED_REFUNDED','COMPLETED') ";
        return contactSql(sql, platform, retailerUuid);
    }

    @Override
    protected String getSizeOfDayBetweenSql(Platform platform, String retailerUuid, String dayStart, String dayEnd) {
        String sql = "select count(DISTINCT(product_uuid)) as size from o_order_item where state in  ('WAIT_SHIP','SHIPPED','WAIT_DELIVER','DELIVERED','RECEIVED','WITH_SHIP_REFUNDING','WITH_RECEIVED_REFUNDING','WITH_WAIT_SHIP_REFUNDED','WITH_RECEIVED_REFUNDED','COMPLETED') and created_at >= '" + dayStart + "' and " + "created_at <= '" + dayEnd + "'";
        return contactSql(sql, platform, retailerUuid);
    }
}
