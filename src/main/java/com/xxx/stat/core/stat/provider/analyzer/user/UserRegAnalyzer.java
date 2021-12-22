package com.xxx.stat.core.stat.provider.analyzer.user;

import com.xxx.stat.core.stat.provider.analyzer.IStatAnalyzerHelper;
import com.xxx.stat.core.stat.provider.Platform;
import com.xxx.stat.core.stat.provider.StatType;
import org.springframework.stereotype.Service;

/**
 * 用户注册 - 分析
 */
@Service
public class UserRegAnalyzer extends IStatAnalyzerHelper {
    @Override
    public StatType getStatType() {
        return StatType.USER_REG;
    }

    @Override
    public String getSizeOfTotalSql(Platform platform, String retailerUuid) {
        String sql = "select count(*) as size from usr_user where status = 0 and is_deleted = 0   ";
        if (platform != null) {
            sql = sql + " AND source_type ='" + platform.name() + "'";
        }

        return sql;
    }

    @Override
    public String getSizeOfDayBetweenSql(Platform platform, String retailerUuid, String dayStart, String dayEnd) {
        String sql = "select count(*) as size from usr_user where status = 0 and is_deleted = 0 and created_at >= '" + dayStart + "' and " + "created_at <= '" + dayEnd + "'";
        if (platform != null) {
            sql = sql + " AND source_type ='" + platform.name() + "'";
        }

        return sql;
    }
}
