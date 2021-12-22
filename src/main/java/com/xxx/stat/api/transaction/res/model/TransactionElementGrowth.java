package com.xxx.stat.api.transaction.res.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 交易数据环比信息
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionElementGrowth extends TransactionElement {
    /**
     * 环比
     */
    private Float growth;
}
