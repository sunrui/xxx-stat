package com.xxx.stat.core.stat.provider.analyzer.user;

import com.xxx.stat.core.stat.provider.analyzer.IStatAnalyzerHelper;
import com.xxx.stat.core.stat.provider.Platform;
import com.xxx.stat.core.stat.provider.StatType;
import org.springframework.stereotype.Service;

/**
 * 用户加购 - 分析
 */
@Service
public class UserAddCartAnalyzer extends IStatAnalyzerHelper {
    @Override
    public StatType getStatType() {
        return StatType.USER_ADD_CART;
    }

    @Override
    protected String getSizeOfTotalSql(Platform platform, String retailerUuid) {
        return "select count(DISTINCT(user_uuid)) as size  from user_add_cart_log ";
    }

    @Override
    protected String getSizeOfDayBetweenSql(Platform platform, String retailerUuid, String dayStart, String dayEnd) {
        return "select count(DISTINCT(user_uuid)) as size from user_add_cart_log  where  created_at >= '" + dayStart + "' and " + "created_at <= '" + dayEnd + "'";
    }
}
