package com.xxx.stat.api.transaction.res.model;

import lombok.*;

import java.util.Collection;

/**
 * 每天详细信息
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionElementOfDay {
    /**
     * 天，如 20201111
     */
    private Integer day;
    /**
     * 每天的曲线
     */
    private Collection<TransactionElement> elements;
}
