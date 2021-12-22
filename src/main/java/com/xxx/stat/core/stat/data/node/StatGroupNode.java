package com.xxx.stat.core.stat.data.node;

import com.xxx.stat.core.stat.provider.StatType;
import com.xxx.stat.core.stat.provider.StatUnit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 分析的节点
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class StatGroupNode {
    /**
     * 分析类型
     */
    private StatType statType;
    /**
     * 单位
     */
    private StatUnit statUint;
    /**
     * 备注信息
     */
    private String comment;
}
