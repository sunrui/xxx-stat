package com.xxx.stat.core.stat.provider.analyzer;

import com.xxx.stat.core.stat.provider.Platform;
import com.xxx.stat.core.stat.provider.SizeOfTotal;
import com.xxx.stat.core.stat.provider.StatType;

/**
 * 统计服务基础类
 */
public interface IStatAnalyzer {
    /**
     * 获取类型
     *
     * @return 统计类型
     */
    StatType getStatType();

    /**
     * 获取总的数量
     *
     * @param platform     平台
     * @param retailerUuid 商户号
     * @return 总的数量
     */
    SizeOfTotal getSizeOfTotal(Platform platform, String retailerUuid);

    /**
     * 获取某一时段的总数统计
     *
     * @param platform     平台
     * @param retailerUuid 商户号
     * @param dayStart     开始时段, 如 2020-11-01 00:00:00
     * @param dayEnd       结果时段, 如 2020-11-01 00:00:00
     * @return 返回天统计数的集合
     */
    SizeOfTotal getSizeOfDayBetween(Platform platform, String retailerUuid, String dayStart, String dayEnd);
}
