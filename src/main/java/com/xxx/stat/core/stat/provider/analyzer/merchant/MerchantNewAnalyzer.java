package com.xxx.stat.core.stat.provider.analyzer.merchant;

import com.xxx.stat.core.stat.provider.Platform;
import com.xxx.stat.core.stat.provider.StatType;
import com.xxx.stat.core.stat.provider.analyzer.IStatAnalyzerHelper;
import org.springframework.stereotype.Service;

/**
 * 商户新增 - 分析
 */
@Service
public class MerchantNewAnalyzer extends IStatAnalyzerHelper {
    @Override
    public StatType getStatType() {
        return StatType.MERCHANT_REG;
    }

    @Override
    protected String getSizeOfTotalSql(Platform platform, String retailerUuid) {
        return "select count(*) as size from mms_retailer where  status = 0";
    }

    @Override
    protected String getSizeOfDayBetweenSql(Platform platform, String retailerUuid, String dayStart, String dayEnd) {
        return "select count(*) as size from mms_retailer where  status = 0 and created_at >= '" + dayStart + "' and " + "created_at <= '" + dayEnd + "'";
    }
}
