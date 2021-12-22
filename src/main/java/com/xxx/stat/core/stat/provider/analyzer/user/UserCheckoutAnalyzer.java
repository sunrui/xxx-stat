package com.xxx.stat.core.stat.provider.analyzer.user;

import com.xxx.stat.core.stat.provider.analyzer.IStatAnalyzerHelper;
import com.xxx.stat.core.stat.provider.Platform;
import com.xxx.stat.core.stat.provider.StatType;
import org.springframework.stereotype.Service;

/**
 * 用户checkout数 - 分析
 */
@Service
public class UserCheckoutAnalyzer extends IStatAnalyzerHelper {
    @Override
    public StatType getStatType() {
        return StatType.USER_CHECKOUT_NUM;
    }

    @Override
    public String getSizeOfTotalSql(Platform platform, String retailerUuid) {
        String sql = "select count(DISTINCT(user_uuid)) as size from o_order ";
        return contactSql(sql, platform, retailerUuid);
    }

    @Override
    public String getSizeOfDayBetweenSql(Platform platform, String retailerUuid, String dayStart, String dayEnd) {
        String sql = " select count(DISTINCT(user_uuid)) as size from  o_order where created_at >= '" + dayStart + "' and " + "created_at <= '" + dayEnd + "'";
        return contactSql(sql, platform, retailerUuid);
    }
}
