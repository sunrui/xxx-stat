package com.xxx.stat.core.stat.provider;

import com.xxx.stat.common.exception.StatException;
import com.xxx.stat.core.stat.provider.analyzer.IStatAnalyzer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 统计引擎基础服务类
 */
@Service
public class StatProvider {
    @Resource(name = "userRegAnalyzer")
    private IStatAnalyzer userRegAnalyzer;
    @Resource(name = "userLoginAnalyzer")
    private IStatAnalyzer userLoginAnalyzer;
    @Resource(name = "userAddCartAnalyzer")
    private IStatAnalyzer userAddCartAnalyzer;
    @Resource(name = "userCheckoutAnalyzer")
    private IStatAnalyzer userCheckoutAnalyzer;
    @Resource(name = "userBuyNumAnalyzer")
    private IStatAnalyzer userBuyNumAnalyzer;

    @Resource(name = "merchantNewAnalyzer")
    private IStatAnalyzer merchantNewAnalyzer;
    @Resource(name = "merchantActiveAnalyzer")
    private IStatAnalyzer merchantActiveAnalyzer;
    @Resource(name = "merchantSaleAnalyzer")
    private IStatAnalyzer merchantSaleAnalyzer;
    @Resource(name = "productPutOnAnalyzer")
    private IStatAnalyzer productPutOnAnalyzer;
    @Resource(name = "productPutOffAnalyzer")
    private IStatAnalyzer productPutOffAnalyzer;
    @Resource(name = "productSaleAnalyzer")
    private IStatAnalyzer productSaleAnalyzer;
    @Resource(name = "productSaleSkuAnalyzer")
    private IStatAnalyzer productSaleSkuAnalyzer;
    @Resource(name = "productPayedAnalyzer")
    private IStatAnalyzer productPayedAnalyzer;
    @Resource(name = "productPriceAdjustAnalyzer")
    private IStatAnalyzer productPriceAdjustAnalyzer;
    @Resource(name = "productNumCategoryAnalyzer")
    private IStatAnalyzer productNumCategoryAnalyzer;

    @Resource(name = "orderPayedAmtAnalyzer")
    private IStatAnalyzer orderPayedAmtAnalyzer;
    @Resource(name = "orderPayNumAnalyzer")
    private IStatAnalyzer orderPayNumAnalyzer;
    @Resource(name = "orderDeliveredAmtAnalyzer")
    private IStatAnalyzer orderDeliveredAmtAnalyzer;
    @Resource(name = "orderDeliveredNumAnalyzer")
    private IStatAnalyzer orderDeliveredNumAnalyzer;
    @Resource(name = "orderInShipAmtAnalyzer")
    private IStatAnalyzer orderInShipAmtAnalyzer;
    @Resource(name = "orderInShipNumAnalyzer")
    private IStatAnalyzer orderInShipNumAnalyzer;
    @Resource(name = "orderReceivedAmtAnalyzer")
    private IStatAnalyzer orderReceivedAmtAnalyzer;
    @Resource(name = "orderReceivedNumAnalyzer")
    private IStatAnalyzer orderReceivedNumAnalyzer;
    @Resource(name = "orderWaitShipAmtAnalyzer")
    private IStatAnalyzer orderWaitShipAmtAnalyzer;
    @Resource(name = "orderWaitShipNumAnalyzer")
    private IStatAnalyzer orderWaitShipNumAnalyzer;

    @Resource(name = "orderSkuPayedAnalyzer")
    private IStatAnalyzer skuOrderPayedAnalyzer;
    @Resource(name = "orderSkuRefundAmtAnalyzer")
    private IStatAnalyzer orderSkuRefundAmtAnalyzer;
    @Resource(name = "orderRefundedAnalyzer")
    private IStatAnalyzer orderRefundedAnalyzer;
    @Resource(name = "orderRefundedRetailerAnalyzer")
    private IStatAnalyzer orderRefundedRetailerAnalyzer;
    @Resource(name = "orderUnPayAnalyzer")
    private IStatAnalyzer orderUnPayAnalyzer;
    @Resource(name = "orderSkuRefundNumAnalyzer")
    private  IStatAnalyzer orderSkuRefundNumAnalyzer;

    @Resource(name = "productRefundNumAnalyzer")
    private  IStatAnalyzer productRefundNumAnalyzer;

    @Resource(name = "logisticsNewAnalyzer")
    private IStatAnalyzer logisticsNewAnalyzer;
    @Resource(name = "logisticsFinishAnalyzer")
    private IStatAnalyzer logisticsFinishAnalyzer;

    public IStatAnalyzer getProvider(StatType statType) {
        switch (statType) {
            case USER_REG:
                return userRegAnalyzer;
            case USER_LOGIN:
                return userLoginAnalyzer;
            case USER_ADD_CART:
                return userAddCartAnalyzer;
            case USER_CHECKOUT_NUM:
                return userCheckoutAnalyzer;
            case USER_BUY_NUM:
                return userBuyNumAnalyzer;
            case MERCHANT_REG:
                return merchantNewAnalyzer;
            case MERCHANT_ACTIVE:
                return merchantActiveAnalyzer;
            case MERCHANT_SALE:
                return merchantSaleAnalyzer;
            case PRODUCT_PUTON:
                return productPutOnAnalyzer;
            case PRODUCT_PUTOFF:
                return productPutOffAnalyzer;
            case PRODUCT_SALE:
                 return productSaleAnalyzer;
            case PRODUCT_SALE_SKU:
                 return  productSaleSkuAnalyzer;
            case PRODUCT_PAYED:
                 return  productPayedAnalyzer;
            case PRODUCT_PRICE_ADJUSTED:
                return  productPriceAdjustAnalyzer;
            case PRODUCT_NUM_CATEGORY:
                return productNumCategoryAnalyzer;
            case ORDER_WAIT_SHIP_NUM:
                return orderWaitShipNumAnalyzer;
            case ORDER_WAIT_SHIP_AMT:
                return orderWaitShipAmtAnalyzer;
            case ORDER_IN_SHIP_NUM:
                return orderInShipNumAnalyzer;
            case ORDER_IN_SHIP_AMT:
                return orderInShipAmtAnalyzer;
            case ORDER_DELIVERED_NUM:
                return orderDeliveredNumAnalyzer;
            case ORDER_DELIVERED_AMT:
                return orderDeliveredAmtAnalyzer;
            case ORDER_RECEIVED_NUM:
                return orderReceivedNumAnalyzer;
            case  ORDER_RECEIVED_AMT:
                return orderReceivedAmtAnalyzer;
            case ORDER_PAY_AMT:
                return orderPayedAmtAnalyzer;
            case ORDER_PAY_NUM:
                return orderPayNumAnalyzer;
            case ORDER_SKU_PAY:
                return skuOrderPayedAnalyzer;
            case ORDER_UNPAY_NUM:
                return orderUnPayAnalyzer;
            case ORDER_SKU_REFUNDED_AMT:
                return orderSkuRefundAmtAnalyzer;
            case ORDER_REFUNDED:
                return orderRefundedAnalyzer;
            case ORDER_REFUNDED_B:
                return orderRefundedRetailerAnalyzer;
            case ORDER_SKU_REFUNDED_NUM:
                return orderSkuRefundNumAnalyzer;
            case ORDER_PRODUCT_REFUNDED_NUM:
                return productRefundNumAnalyzer;
            case LOGISTICS_NEW:
                return logisticsNewAnalyzer;
            case LOGISTICS_FINISH:
                return logisticsFinishAnalyzer;
        }

        throw new StatException("can not fund provider for statType: " + statType);
    }
}
