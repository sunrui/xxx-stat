package com.xxx.stat.api.transaction.res.model;

import com.xxx.stat.core.stat.provider.SizeOfTotal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 交易数据基本信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionElement {
    /**
     * 显示名称
     */
    private String name;
    /**
     * 提示
     */
    private String prompt;
    /**
     * 显示数额，数字或金钱
     */
    private SizeOfTotal sizeOfTotal;
}
