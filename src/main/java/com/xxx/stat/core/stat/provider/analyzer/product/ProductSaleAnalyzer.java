package com.xxx.stat.core.stat.provider.analyzer.product;

import com.xxx.stat.core.stat.provider.Platform;
import com.xxx.stat.core.stat.provider.StatType;
import com.xxx.stat.core.stat.provider.analyzer.IStatAnalyzerHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 在售商品数 - 分析
 */
@Service
public class ProductSaleAnalyzer extends IStatAnalyzerHelper {
    @Override
    public StatType getStatType() {
        return StatType.PRODUCT_SALE;
    }

    @Override
    protected String getSizeOfTotalSql(Platform platform, String retailerUuid) {
        String sql = "select count(id) as size from prod_product where status = 0 ";
        if (!StringUtils.isEmpty(retailerUuid)) {
            sql = sql + " AND retailer_uuid='" + retailerUuid + "'";
        }

        return sql;
    }

    @Override
    protected String getSizeOfDayBetweenSql(Platform platform, String retailerUuid, String dayStart, String dayEnd) {
        String sql = "select count(id)  as size  from prod_product where status = 0 and  publish_at <= '" + dayEnd + "'";
        if (!StringUtils.isEmpty(retailerUuid)) {
            sql = sql + " AND retailer_uuid='" + retailerUuid + "'";
        }

        return sql;
    }
}
