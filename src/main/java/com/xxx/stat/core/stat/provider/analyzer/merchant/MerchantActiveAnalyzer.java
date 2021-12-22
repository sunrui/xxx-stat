package com.xxx.stat.core.stat.provider.analyzer.merchant;

import com.xxx.stat.core.stat.provider.Platform;
import com.xxx.stat.core.stat.provider.StatType;
import com.xxx.stat.core.stat.provider.analyzer.IStatAnalyzerHelper;
import org.springframework.stereotype.Service;

/**
 * 商户活跃 - 分析
 */
@Service
public class MerchantActiveAnalyzer extends IStatAnalyzerHelper {
    @Override
    public StatType getStatType() {
        return StatType.MERCHANT_ACTIVE;
    }

    @Override
    protected String getSizeOfTotalSql(Platform platform, String retailerUuid) {
        return "select count(m.id) as size from mms_retailer m,  prod_product p where p.retailer_uuid = m.uuid and m.status = 0 and p.status = 0";
    }

    @Override
    protected String getSizeOfDayBetweenSql(Platform platform, String retailerUuid, String dayStart, String dayEnd) {
        return "select count(m.id) as size from mms_retailer m,  prod_product p where p.retailer_uuid = m.uuid and m.status = 0 and  p.status = 0   and  m.created_at >= '" + dayStart + "' and " + " m.created_at <= '" + dayEnd + "'";
    }
}
