package com.xxx.stat.core.transaction.data;

import com.xxx.stat.api.stat.StatController;
import com.xxx.stat.common.util.BigDecimalUtil;
import com.xxx.stat.core.stat.provider.Platform;
import com.xxx.stat.core.stat.provider.SizeOfTotal;
import com.xxx.stat.core.stat.provider.StatType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 交易数据服务提供者
 */
@Service
public class TransactionDataProvider {
    @Autowired
    private StatController statController;

    /**
     * GMV$
     */
    public SizeOfTotal getGmv(Platform platform, String retailerUuid, Integer day, Integer dayStart, Integer dayEnd) {
        return statController.getSizeOfTotal(StatType.ORDER_PAY_AMT, platform, retailerUuid, day, dayStart, dayEnd);
    }

    /**
     * 订单量(平台)
     */
    public SizeOfTotal getTotalOrder(Platform platform, String retailerUuid, Integer day, Integer dayStart, Integer dayEnd) {
        return statController.getSizeOfTotal(StatType.ORDER_PAY_NUM, platform, retailerUuid, day, dayStart, dayEnd);
    }

    /**
     * 订单价$
     */
    public SizeOfTotal getOrderPrice(Float gmv, Long orderNum) {
        SizeOfTotal sizeOfTotal = new SizeOfTotal();
        if (orderNum != null && orderNum != 0) {
            BigDecimal numerator = BigDecimal.valueOf(gmv);
            BigDecimal denominator = BigDecimal.valueOf(orderNum);
            sizeOfTotal.setPrice(BigDecimalUtil.div(numerator, denominator, 2).floatValue());
            //sizeOfTotal.setPrice(new BigDecimal(gmv / orderNum).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue());
        }
        return sizeOfTotal;
    }

    /**
     * 订单件数
     */
    public SizeOfTotal getOrderSkuNum(Platform platform, String retailerUuid, Integer day, Integer dayStart, Integer dayEnd) {
        return statController.getSizeOfTotal(StatType.ORDER_SKU_PAY, platform, retailerUuid, day, dayStart, dayEnd);
    }

    /**
     * 件单价$
     */
    public SizeOfTotal getPricePerItem(Float gmv, Long orderSkuNum) {
        SizeOfTotal sizeOfTotal = new SizeOfTotal();
        if (orderSkuNum != null && orderSkuNum != 0 && gmv != null) {
            BigDecimal numerator = BigDecimal.valueOf(gmv);
            BigDecimal denominator = BigDecimal.valueOf(orderSkuNum);
            sizeOfTotal.setPrice(BigDecimalUtil.div(numerator, denominator, 2).floatValue());
            //sizeOfTotal.setPrice(new BigDecimal(gmv / orderSkuNum).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue());
        }
        return sizeOfTotal;
    }

    /**
     * 单均件数
     */
    public SizeOfTotal getSkuNumPerOrder(Long orderSkuNum, Long orderNum) {
        SizeOfTotal sizeOfTotal = new SizeOfTotal();
        if (orderNum != null && orderNum != 0 && orderSkuNum != null) {
            BigDecimal numerator = BigDecimal.valueOf(orderSkuNum);
            BigDecimal denominator = BigDecimal.valueOf(orderNum);
            sizeOfTotal.setPrice(BigDecimalUtil.div(numerator, denominator, 2).floatValue());
        }
        return sizeOfTotal;
    }

    /**
     * 退款GMV$
     */
    public SizeOfTotal getRefundedAmt(Platform platform, String retailerUuid, Integer day, Integer dayStart, Integer dayEnd) {
        return statController.getSizeOfTotal(StatType.ORDER_SKU_REFUNDED_AMT, platform, retailerUuid, day, dayStart, dayEnd);
    }

    /**
     * 退款订单量(平台)
     */
    public SizeOfTotal getOrderRefunded(Platform platform, String retailerUuid, Integer day, Integer dayStart, Integer dayEnd) {
        return statController.getSizeOfTotal(StatType.ORDER_REFUNDED, platform, retailerUuid, day, dayStart, dayEnd);
    }

    /**
     * 退款订单量(B端)
     */
    public SizeOfTotal getOrderRefundedFromB(Platform platform, String retailerUuid, Integer day, Integer dayStart, Integer dayEnd) {
        return statController.getSizeOfTotal(StatType.ORDER_REFUNDED_B, platform, retailerUuid, day, dayStart, dayEnd);
    }

    /**
     * 退款订单量(sku)
     */
    public SizeOfTotal getOrderSkuRefundedNum(Platform platform, String retailerUuid, Integer day, Integer dayStart, Integer dayEnd) {
        return statController.getSizeOfTotal(StatType.ORDER_SKU_REFUNDED_NUM, platform, retailerUuid, day, dayStart, dayEnd);
    }

    /**
     * 退款件数
     */
    public SizeOfTotal getOrderProductRefundedNum(Platform platform, String retailerUuid, Integer day, Integer dayStart, Integer dayEnd) {
        return statController.getSizeOfTotal(StatType.ORDER_PRODUCT_REFUNDED_NUM, platform, retailerUuid, day, dayStart, dayEnd);
    }

    /**
     * 活跃用户数
     */
    public SizeOfTotal getUserLogin(Platform platform, String retailerUuid, Integer day, Integer dayStart, Integer dayEnd) {
        return statController.getSizeOfTotal(StatType.USER_LOGIN, platform, retailerUuid, day, dayStart, dayEnd);
    }

    /**
     * 加购用户数
     */
    public SizeOfTotal getUserAddCartNum(Platform platform, String retailerUuid, Integer day, Integer dayStart, Integer dayEnd) {
        return statController.getSizeOfTotal(StatType.USER_ADD_CART, platform, retailerUuid, day, dayStart, dayEnd);
    }

    /**
     * checkout数
     */
    public SizeOfTotal getUserCheckOutNum(Platform platform, String retailerUuid, Integer day, Integer dayStart, Integer dayEnd) {
        return statController.getSizeOfTotal(StatType.USER_CHECKOUT_NUM, platform, retailerUuid, day, dayStart, dayEnd);
    }

    /**
     * 用户购买数
     */
    public SizeOfTotal getUserBuyNum(Platform platform, String retailerUuid, Integer day, Integer dayStart, Integer dayEnd) {
        return statController.getSizeOfTotal(StatType.USER_BUY_NUM, platform, retailerUuid, day, dayStart, dayEnd);
    }

    /**
     * 待发订单数
     */
    public SizeOfTotal getOrderWaitShipNum(Platform platform, String retailerUuid, Integer day, Integer dayStart, Integer dayEnd) {
        return statController.getSizeOfTotal(StatType.ORDER_WAIT_SHIP_NUM, platform, retailerUuid, day, dayStart, dayEnd);
    }

    /**
     * 待发订单金额/$
     */
    public SizeOfTotal getOrderWaitShipAmt(Platform platform, String retailerUuid, Integer day, Integer dayStart, Integer dayEnd) {
        return statController.getSizeOfTotal(StatType.ORDER_WAIT_SHIP_AMT, platform, retailerUuid, day, dayStart, dayEnd);
    }

    /**
     * 在途订单数
     */
    public SizeOfTotal getOrderInShipNum(Platform platform, String retailerUuid, Integer day, Integer dayStart, Integer dayEnd) {
        return statController.getSizeOfTotal(StatType.ORDER_IN_SHIP_NUM, platform, retailerUuid, day, dayStart, dayEnd);
    }

    /**
     * 在途订单金额/$
     */
    public SizeOfTotal getOrderInShipAmt(Platform platform, String retailerUuid, Integer day, Integer dayStart, Integer dayEnd) {
        return statController.getSizeOfTotal(StatType.ORDER_IN_SHIP_AMT, platform, retailerUuid, day, dayStart, dayEnd);
    }

    /**
     * 妥投订单数
     */
    public SizeOfTotal getOrderDeliveredNum(Platform platform, String retailerUuid, Integer day, Integer dayStart, Integer dayEnd) {
        return statController.getSizeOfTotal(StatType.ORDER_DELIVERED_NUM, platform, retailerUuid, day, dayStart, dayEnd);
    }

    /**
     * 妥投订单金额/$
     */
    public SizeOfTotal getOrderDeliveredAmt(Platform platform, String retailerUuid, Integer day, Integer dayStart, Integer dayEnd) {
        return statController.getSizeOfTotal(StatType.ORDER_DELIVERED_AMT, platform, retailerUuid, day, dayStart, dayEnd);
    }

    /**
     * 签收订单数
     */
    public SizeOfTotal getOrderReceivedNum(Platform platform, String retailerUuid, Integer day, Integer dayStart, Integer dayEnd) {
        return statController.getSizeOfTotal(StatType.ORDER_RECEIVED_NUM, platform, retailerUuid, day, dayStart, dayEnd);
    }

    /**
     * 签收订金额/$
     */
    public SizeOfTotal getOrderReceivedAmt(Platform platform, String retailerUuid, Integer day, Integer dayStart, Integer dayEnd) {
        return statController.getSizeOfTotal(StatType.ORDER_RECEIVED_AMT, platform, retailerUuid, day, dayStart, dayEnd);
    }

    /**
     * 在售商品数
     */
    public SizeOfTotal getProductSaleNum(Platform platform, String retailerUuid, Integer day, Integer dayStart, Integer dayEnd) {
        return statController.getSizeOfTotal(StatType.PRODUCT_SALE, platform, retailerUuid, day, dayStart, dayEnd);
    }

    /**
     * 在售SKU数
     */
    public SizeOfTotal getProductSaleSkuNum(Platform platform, String retailerUuid, Integer day, Integer dayStart, Integer dayEnd) {
        return statController.getSizeOfTotal(StatType.PRODUCT_SALE_SKU, platform, retailerUuid, day, dayStart, dayEnd);
    }
}
