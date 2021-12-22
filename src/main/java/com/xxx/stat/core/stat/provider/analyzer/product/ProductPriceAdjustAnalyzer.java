package com.xxx.stat.core.stat.provider.analyzer.product;

import com.xxx.stat.core.stat.provider.analyzer.IStatAnalyzerHelper;
import com.xxx.stat.core.stat.provider.Platform;
import com.xxx.stat.core.stat.provider.StatType;
import org.springframework.stereotype.Service;

/**
 * 加价商品数 - 分析
 */
@Service
public class ProductPriceAdjustAnalyzer extends IStatAnalyzerHelper {
    @Override
    public StatType getStatType() {
        return StatType.PRODUCT_PRICE_ADJUSTED;
    }

    @Override
    protected String getSizeOfTotalSql(Platform platform, String retailerUuid) {
        return "select count(id) as size from prod_product where status = 0 and forbidden_adjust_price = 0 ";
    }

    @Override
    protected String getSizeOfDayBetweenSql(Platform platform, String retailerUuid, String dayStart, String dayEnd) {
        return "select count(id) as size from prod_product where status = 0 and forbidden_adjust_price = 0 and  created_at >= '" + dayStart + "' and " + "created_at <= '" + dayEnd + "'";
    }
}
