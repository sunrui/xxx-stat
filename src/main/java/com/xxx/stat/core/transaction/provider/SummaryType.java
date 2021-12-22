package com.xxx.stat.core.transaction.provider;

import lombok.Getter;

/**
 * 摘要类型
 */
@Getter
public enum SummaryType {
    A_INDEX_TRANSACTION("A端-首页交易数据"),
    B_INDEX_TRANSACTION("B端-首页交易数据"),
    A_INDEX_FUNNEL("首页漏斗"),
    A_USER("用户"),
    A_RETAILER("商户"),
    A_ORDER_PAY("订单支付"),
    A_PRODUCT("商品"),
    A_ORDER_REFUND("订单退款");

    SummaryType(String comment) {
        this.comment = comment;
    }

    private final String comment;
}
