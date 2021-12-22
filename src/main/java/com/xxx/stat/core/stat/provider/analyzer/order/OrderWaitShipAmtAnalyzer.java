package com.xxx.stat.core.stat.provider.analyzer.order;

import com.xxx.stat.core.stat.provider.Platform;
import com.xxx.stat.core.stat.provider.StatType;
import com.xxx.stat.core.stat.provider.analyzer.IStatAnalyzerHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 待发订单金额/$ - 分析
 */
@Service
public class OrderWaitShipAmtAnalyzer extends IStatAnalyzerHelper {
    @Override
    public StatType getStatType() {
        return StatType.ORDER_WAIT_SHIP_AMT;
    }

    @Override
    protected String getSizeOfTotalSql(Platform platform, String retailerUuid) {
        String sql;
        if (StringUtils.isEmpty(retailerUuid)) {
            sql = "select sum(platform_amt) as price from o_order where state = 'WAIT_SHIP' ";
        } else {
            sql = "select sum(merchant_amt_sku) as price from o_order_item where state = 'WAIT_SHIP' ";
        }

        return contactSql(sql, platform, retailerUuid);
    }

    @Override
    protected String getSizeOfDayBetweenSql(Platform platform, String retailerUuid, String dayStart, String dayEnd) {
        String sql;
        if (StringUtils.isEmpty(retailerUuid)) {
            sql = "select sum(platform_amt) as price from o_order where  state = 'WAIT_SHIP'  and created_at >= '" + dayStart + "' and " + "created_at <= '" + dayEnd + "'";
        } else {
            sql = "select sum(merchant_amt_sku) as price from o_order_item where  state = 'WAIT_SHIP'  and created_at >= '" + dayStart + "' and " + "created_at <= '" + dayEnd + "'";
        }

        return contactSql(sql, platform, retailerUuid);
    }
}
