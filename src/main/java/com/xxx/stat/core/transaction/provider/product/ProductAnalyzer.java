package com.xxx.stat.core.transaction.provider.product;

import com.xxx.stat.common.util.BigDecimalUtil;
import com.xxx.stat.core.stat.provider.Platform;
import com.xxx.stat.core.stat.provider.StatType;
import com.xxx.stat.api.stat.StatController;
import com.xxx.stat.core.stat.provider.SizeOfTotal;
import com.xxx.stat.core.transaction.provider.ISummaryAnalyzer;
import com.xxx.stat.core.transaction.provider.SummaryType;
import com.xxx.stat.api.transaction.res.model.TransactionElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 商品统计信息
 */
@Service
public class ProductAnalyzer extends ISummaryAnalyzer {
    @Autowired
    private StatController statController;

    @Override
    public SummaryType getSummaryType() {
        return SummaryType.A_PRODUCT;
    }

    @Override
    public TransactionElement[] getTransactionElement(Platform platform, String retailerUuid, Integer day, Integer dayStart, Integer dayEnd) {
        SizeOfTotal productSaled = statController.getSizeOfTotal(StatType.PRODUCT_SALE, platform, retailerUuid, day, dayStart, dayEnd);
        SizeOfTotal productPayed = statController.getSizeOfTotal(StatType.PRODUCT_PAYED, platform, retailerUuid, day, dayStart, dayEnd);
        SizeOfTotal saledPerPayedRate = new SizeOfTotal();

        if (productSaled.getSize() == null || productSaled.getSize() == 0) {
            saledPerPayedRate.setPrice(null);
        } else {
            saledPerPayedRate.setPrice(BigDecimalUtil.div(BigDecimal.valueOf(productPayed.getSize()) ,BigDecimal.valueOf(productSaled.getSize()),2).floatValue());
        }

        return new TransactionElement[]{
                new TransactionElement("新增商品数", "统计周期内，首次上架的商品数", statController.getSizeOfTotal(StatType.PRODUCT_PUTON, platform, retailerUuid, day, dayStart, dayEnd)),
                new TransactionElement("在售商品数", "统计周期内，截止统计周期结束时间点时，在售状态的商品数", productSaled),
                new TransactionElement("在售SKU数", "统计周期内，截止统计周期结束时间点时，在售状态的sku数，比如商品A，颜色有2个，尺码有3个，sku数就是6个", statController.getSizeOfTotal(StatType.PRODUCT_SALE_SKU, platform, retailerUuid, day, dayStart, dayEnd)),
                new TransactionElement("出单商品数", "统计周期内，有被用户购买过的商品数，不区分是否在售状态", productPayed),
                new TransactionElement("出单商品占比", "统计周期内，出单商品数/在售商品数",saledPerPayedRate ),
                new TransactionElement("加价商品数", "统计周期内，被加价的商品数，仅统计在售状态", statController.getSizeOfTotal(StatType.PRODUCT_PRICE_ADJUSTED, platform, retailerUuid, day, dayStart, dayEnd)),
                new TransactionElement("women’s fashion", "统计周期内，截止统计周期结束时间点时，对应类目在售状态的商品数", statController.getSizeOfTotal(StatType.PRODUCT_NUM_CATEGORY, platform, "05", day, dayStart, dayEnd)),
                new TransactionElement("men’s fashion", "统计周期内，截止统计周期结束时间点时，对应类目在售状态的商品数", statController.getSizeOfTotal(StatType.PRODUCT_NUM_CATEGORY, platform, "14", day, dayStart, dayEnd)),
                new TransactionElement("beauty & makeup", "统计周期内，截止统计周期结束时间点时，对应类目在售状态的商品数", statController.getSizeOfTotal(StatType.PRODUCT_NUM_CATEGORY, platform, "03", day, dayStart, dayEnd)),
                new TransactionElement("women’s shoes", "统计周期内，截止统计周期结束时间点时，对应类目在售状态的商品数", statController.getSizeOfTotal(StatType.PRODUCT_NUM_CATEGORY, platform, "10", day, dayStart, dayEnd)),
                new TransactionElement("women’s wallets", "统计周期内，截止统计周期结束时间点时，对应类目在售状态的商品数", statController.getSizeOfTotal(StatType.PRODUCT_NUM_CATEGORY, platform, "01", day, dayStart, dayEnd)),
                new TransactionElement("watches", "统计周期内，截止统计周期结束时间点时，对应类目在售状态的商品数", statController.getSizeOfTotal(StatType.PRODUCT_NUM_CATEGORY, platform, "04", day, dayStart, dayEnd)),
                new TransactionElement("accessories", "统计周期内，截止统计周期结束时间点时，对应类目在售状态的商品数", statController.getSizeOfTotal(StatType.PRODUCT_NUM_CATEGORY, platform, "07", day, dayStart, dayEnd)),
                new TransactionElement("household", "统计周期内，截止统计周期结束时间点时，对应类目在售状态的商品数", statController.getSizeOfTotal(StatType.PRODUCT_NUM_CATEGORY, platform, "20", day, dayStart, dayEnd)),
                new TransactionElement("baby&kids", "统计周期内，截止统计周期结束时间点时，对应类目在售状态的商品数", statController.getSizeOfTotal(StatType.PRODUCT_NUM_CATEGORY, platform, "15", day, dayStart, dayEnd)),
                new TransactionElement("hobbies & sport&kids", "统计周期内，截止统计周期结束时间点时，对应类目在售状态的商品数", statController.getSizeOfTotal(StatType.PRODUCT_NUM_CATEGORY, platform, "06", day, dayStart, dayEnd)),
                new TransactionElement("sports shoes", "统计周期内，截止统计周期结束时间点时，对应类目在售状态的商品数", statController.getSizeOfTotal(StatType.PRODUCT_NUM_CATEGORY, platform, "21", day, dayStart, dayEnd))
        };
    }
}
