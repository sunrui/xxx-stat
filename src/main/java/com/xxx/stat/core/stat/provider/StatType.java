package com.xxx.stat.core.stat.provider;

import lombok.Getter;

/**
 * 统计类型
 */
@Getter
public enum StatType {
    USER_REG("用户注册"),
    USER_LOGIN("用户登录"),
    USER_ADD_CART("用户加购"),
    USER_CHECKOUT_NUM("用户checkout"),
    USER_BUY_NUM("用户购买"),
    MERCHANT_REG("商户入驻"),
    MERCHANT_ACTIVE("商户活跃"),
    MERCHANT_SALE("商户售卖"),
    PRODUCT_PUTON("商品上架"),
    PRODUCT_SALE("在售商品数"),
    PRODUCT_SALE_SKU("在售SKU数"),
    PRODUCT_PAYED("出单商品数"),
    PRODUCT_PRICE_ADJUSTED("加价商品数"),
    PRODUCT_PUTOFF("商品下架"),
    PRODUCT_NUM_CATEGORY("类目商品数"),
    ORDER_UNPAY_NUM("订单待付款"),
    ORDER_PAY_NUM("订单支付数量"),
    ORDER_PAY_AMT("订单支付金额/$"),
    ORDER_WAIT_SHIP_NUM("待发订单数"),
    ORDER_WAIT_SHIP_AMT("待发订单金额/$"),
    ORDER_IN_SHIP_NUM("在途订单数"),
    ORDER_IN_SHIP_AMT("在途订单金额/$"),
    ORDER_DELIVERED_NUM("妥投订单数"),
    ORDER_DELIVERED_AMT("妥投订单金额/$"),
    ORDER_RECEIVED_NUM("签收订单数"),
    ORDER_RECEIVED_AMT("签收订金额/$"),
    ORDER_SKU_PAY("订单SKU支付"),
    ORDER_SKU_REFUNDED_AMT("订单SKU退款金额/$"),
    ORDER_SKU_REFUNDED_NUM("订单SKU退款数"),
    ORDER_PRODUCT_REFUNDED_NUM("订单商品退款数"),
    ORDER_REFUNDED("订单退款-平台"),
    ORDER_REFUNDED_B("订单退款-B端"),
    LOGISTICS_NEW("物流新增"),
    LOGISTICS_FINISH("物流完成");

    StatType(String comment) {
        this.comment = comment;
    }

    private final String comment;
}
