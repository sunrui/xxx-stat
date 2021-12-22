package com.xxx.stat.core.stat.provider.analyzer.logistics;

import com.xxx.stat.core.stat.provider.Platform;
import com.xxx.stat.core.stat.provider.StatType;
import com.xxx.stat.core.stat.provider.analyzer.IStatAnalyzerHelper;
import org.springframework.stereotype.Service;

/**
 * 物流新增 - 分析
 */
@Service
public class LogisticsNewAnalyzer extends IStatAnalyzerHelper {
    @Override
    public StatType getStatType() {
        return StatType.LOGISTICS_NEW;
    }

    @Override
    protected String getSizeOfTotalSql(Platform platform, String retailerUuid) {
        String sql = "select count(*) as size from o_order_item where  state =  'SHIPPED' ";
        return contactSql(sql, platform, retailerUuid);
    }

    @Override
    protected String getSizeOfDayBetweenSql(Platform platform, String retailerUuid, String dayStart, String dayEnd) {
        String sql = "select count(*) as size from o_order_item where state = 'SHIPPED' and  created_at >= '" + dayStart + "' and " + "created_at <= '" + dayEnd + "'";
        return contactSql(sql, platform, retailerUuid);
    }
}
