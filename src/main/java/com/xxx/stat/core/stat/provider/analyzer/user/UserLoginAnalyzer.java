package com.xxx.stat.core.stat.provider.analyzer.user;

import com.xxx.stat.core.stat.provider.Platform;
import com.xxx.stat.core.stat.provider.StatType;
import com.xxx.stat.core.stat.provider.analyzer.IStatAnalyzerHelper;
import org.springframework.stereotype.Service;

/**
 * 用户登录 - 分析
 */
@Service
public class UserLoginAnalyzer extends IStatAnalyzerHelper {
    @Override
    public StatType getStatType() {
        return StatType.USER_LOGIN;
    }

    @Override
    public String getSizeOfTotalSql(Platform platform, String retailerUuid) {
        String sql = "  select count(user_uuid) as size  from usr_active_log ";
        if (platform != null) {
            sql += " AND platform='" + platform.name() + "'";
        }

        return sql;
    }

    @Override
    public String getSizeOfDayBetweenSql(Platform platform, String retailerUuid, String dayStart, String dayEnd) {
        String sql = "select count(user_uuid) as size from usr_active_log where created_at >= '" + dayStart + "' and " + "created_at <= '" + dayEnd + "'";
        if (platform != null) {
            sql += " AND platform='" + platform.name() + "'";
        }

        return sql;
    }
}
