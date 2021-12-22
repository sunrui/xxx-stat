package com.xxx.stat.core.stat.data.node;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * A 端管理后台各被分析的组
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class StatGroup {
    /**
     * 组名
     */
    private String name;
    /**
     * 分析的节点
     */
    private StatGroupNode[] nodes;
}
