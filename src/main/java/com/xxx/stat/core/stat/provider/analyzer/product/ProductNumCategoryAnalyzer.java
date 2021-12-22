package com.xxx.stat.core.stat.provider.analyzer.product;

import com.xxx.stat.core.stat.provider.analyzer.IStatAnalyzerHelper;
import com.xxx.stat.core.stat.provider.Platform;
import com.xxx.stat.core.stat.provider.StatType;
import org.springframework.stereotype.Service;

/**
 * 类目商品数
 */
@Service
public class ProductNumCategoryAnalyzer extends IStatAnalyzerHelper {
    @Override
    public StatType getStatType() {
        return StatType.PRODUCT_NUM_CATEGORY;
    }

    @Override
    protected String getSizeOfTotalSql(Platform platform, String retailerUuid) {
        return "SELECT count(p.id) as size from prod_product p, prod_category c where p.status = 0 and  p.category_no like '" + retailerUuid + "%' and  c.name = '" + retailerUuid + "' ";
    }

    @Override
    protected String getSizeOfDayBetweenSql(Platform platform, String retailerUuid, String dayStart, String dayEnd) {
        return " SELECT count(p.id) as size from prod_product p  where p.status = 0 and  p.category_no like '" + retailerUuid + "%'  and  p.publish_at <= '" + dayEnd + "'";
    }
}
