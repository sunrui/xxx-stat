package com.xxx.stat.core.stat.provider.analyzer.merchant;

import com.xxx.stat.core.stat.provider.Platform;
import com.xxx.stat.core.stat.provider.StatType;
import com.xxx.stat.core.stat.provider.analyzer.IStatAnalyzerHelper;
import org.springframework.stereotype.Service;

/**
 * 商户售卖 - 分析
 */
@Service
public class MerchantSaleAnalyzer extends IStatAnalyzerHelper {
    @Override
    public StatType getStatType() {
        return StatType.MERCHANT_SALE;
    }

    @Override
    protected String getSizeOfTotalSql(Platform platform, String retailerUuid) {
        String sql = "select sum(merchant_amt) as price from o_order where state in ('WAIT_SHIP','SHIPPING','SHIPPED','WAIT_DELIVER','DELIVERED','REFUNDED','RECEIVED','COMPLETED')  ";
        return contactSql(sql, platform, retailerUuid);
    }

    @Override
    protected String getSizeOfDayBetweenSql(Platform platform, String retailerUuid, String dayStart, String dayEnd) {
        String sql = "select sum(merchant_amt) as price from o_order where state in ('WAIT_SHIP','SHIPPING','SHIPPED','WAIT_DELIVER','DELIVERED','REFUNDED','RECEIVED','COMPLETED')  and  created_at >= '" + dayStart + "' and " + "created_at <= '" + dayEnd + "'";
        return contactSql(sql, platform, retailerUuid);
    }
}
