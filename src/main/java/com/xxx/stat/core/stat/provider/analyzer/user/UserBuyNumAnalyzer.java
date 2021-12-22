package com.xxx.stat.core.stat.provider.analyzer.user;

import com.xxx.stat.core.stat.provider.analyzer.IStatAnalyzerHelper;
import com.xxx.stat.core.stat.provider.Platform;
import com.xxx.stat.core.stat.provider.StatType;
import org.springframework.stereotype.Service;

/**
 * 用户购买数 - 分析
 */
@Service
public class UserBuyNumAnalyzer extends IStatAnalyzerHelper {
    @Override
    public StatType getStatType() {
        return StatType.USER_BUY_NUM;
    }

    @Override
    protected String getSizeOfTotalSql(Platform platform, String retailerUuid) {
        String sql = "select count(DISTINCT(user_uuid)) as size from o_order where state in ('WAIT_SHIP','SHIPPING','SHIPPED','WAIT_DELIVER','DELIVERED','REFUNDED','RECEIVED','COMPLETED') ";
        return contactSql(sql, platform, retailerUuid);
    }

    @Override
    public String getSizeOfDayBetweenSql(Platform platform, String retailerUuid, String dayStart, String dayEnd) {
        String sql = "select count(DISTINCT(user_uuid)) as size from o_order where state in ('WAIT_SHIP','SHIPPING','SHIPPED','WAIT_DELIVER','DELIVERED','REFUNDED','RECEIVED','COMPLETED') and created_at >= '" + dayStart + "' and " + "created_at <= '" + dayEnd + "'";
        return contactSql(sql, platform, retailerUuid);
    }
}
