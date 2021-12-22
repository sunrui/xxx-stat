package com.xxx.stat.core.stat.provider.analyzer.product;

import com.xxx.stat.core.stat.provider.analyzer.IStatAnalyzerHelper;
import com.xxx.stat.core.stat.provider.Platform;
import com.xxx.stat.core.stat.provider.StatType;
import org.springframework.stereotype.Service;

/**
 * 商品售卖 - 分析
 */
@Service
public class ProductPutOffAnalyzer extends IStatAnalyzerHelper {
    @Override
    public StatType getStatType() {
        return StatType.PRODUCT_PUTOFF;
    }

    @Override
    protected String getSizeOfTotalSql(Platform platform, String retailerUuid) {
        return "select count(id) as size from prod_product where status in (10,11,12,13)";
    }

    @Override
    protected String getSizeOfDayBetweenSql(Platform platform, String retailerUuid, String dayStart, String dayEnd) {
        return "select count(id) as size from prod_product where status in (10,11,12,13) and created_at >= '" + dayStart + "' and " + "created_at <= '" + dayEnd + "'";
    }
}
