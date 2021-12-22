package com.xxx.stat.core.stat.provider.analyzer.order;

import com.xxx.stat.core.stat.provider.Platform;
import com.xxx.stat.core.stat.provider.StatType;
import com.xxx.stat.core.stat.provider.analyzer.IStatAnalyzerHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 在途订单数 - 分析
 */
@Service
public class OrderInShipNumAnalyzer extends IStatAnalyzerHelper {
    @Override
    public StatType getStatType() {
        return StatType.ORDER_IN_SHIP_NUM;
    }

    @Override
    protected String getSizeOfTotalSql(Platform platform, String retailerUuid) {
        String sql = "select count(id) as size from o_order where  state in ( 'SHIPPING','SHIPPED','WAIT_DELIVER') ";
        return contactSql(sql, platform, retailerUuid);
    }

    @Override
    protected String getSizeOfDayBetweenSql(Platform platform, String retailerUuid, String dayStart, String dayEnd) {
        String sql;
        if (StringUtils.isEmpty(retailerUuid)) {
            sql = "select count(id) as size from o_order where state in ('SHIPPING','SHIPPED','WAIT_DELIVER') and created_at >= '" + dayStart + "' and " + "created_at <= '" + dayEnd + "'";
        } else {
            sql = "select count(id) as size from o_order_item where state in ('SHIPPING','SHIPPED','WAIT_DELIVER') and created_at >= '" + dayStart + "' and " + "created_at <= '" + dayEnd + "'";
        }

        return contactSql(sql, platform, retailerUuid);
    }
}
