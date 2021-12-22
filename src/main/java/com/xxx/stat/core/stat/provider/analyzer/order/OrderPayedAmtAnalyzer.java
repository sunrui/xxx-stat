package com.xxx.stat.core.stat.provider.analyzer.order;

import com.xxx.stat.core.stat.provider.Platform;
import com.xxx.stat.core.stat.provider.StatType;
import com.xxx.stat.core.stat.provider.analyzer.IStatAnalyzerHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 订单支付金额/$ - 分析
 */
@Service
public class OrderPayedAmtAnalyzer extends IStatAnalyzerHelper {

    @Override
    public StatType getStatType() {
        return StatType.ORDER_PAY_AMT;
    }

    @Override
    public String getSizeOfTotalSql(Platform platform, String retailerUuid) {
        String sql;
        if (StringUtils.isEmpty(retailerUuid)) {
            sql = "select sum(platform_amt) as price from o_order where state in ('WAIT_SHIP','SHIPPING','SHIPPED','WAIT_DELIVER','DELIVERED','REFUNDED','RECEIVED','COMPLETED') ";
        } else {
            sql = "select sum(merchant_amt) as price from o_order where state in ('WAIT_SHIP','SHIPPING','SHIPPED','WAIT_DELIVER','DELIVERED','REFUNDED','RECEIVED','COMPLETED') ";
        }

        return contactSql(sql, platform, retailerUuid);
    }

    @Override
    protected String getSizeOfDayBetweenSql(Platform platform, String retailerUuid, String dayStart, String dayEnd) {
        String sql;
        if (StringUtils.isEmpty(retailerUuid)) {
            sql = "select sum(platform_amt) as price from o_order where state in('WAIT_SHIP','SHIPPING','SHIPPED','WAIT_DELIVER','DELIVERED','REFUNDED','RECEIVED','COMPLETED')  and created_at >= '" + dayStart + "' and " + "created_at <= '" + dayEnd + "'";
        } else {
            sql = "select sum(merchant_amt_sku) as price from o_order_item where state in ('WAIT_SHIP','SHIPPING','SHIPPED','WAIT_DELIVER','DELIVERED', 'WITH_WAIT_SHIP_REFUNDED' , 'WITH_RECEIVED_REFUNDED','RECEIVED')  and created_at >= '" + dayStart + "' and " + "created_at <= '" + dayEnd + "'";
        }

        return contactSql(sql, platform, retailerUuid);
    }
}
