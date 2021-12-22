package com.xxx.stat.core.stat.provider.analyzer;

import com.xxx.stat.core.stat.provider.Platform;
import com.xxx.stat.core.stat.provider.SizeOfTotal;
import com.xxx.stat.core.stat.provider.cache.StatCacheHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;

/**
 * 统计服务基础帮助类
 */
public abstract class IStatAnalyzerHelper implements IStatAnalyzer {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private StatCacheHelper statCacheHelper;

    /**
     * 工具函数，拼接字符串
     *
     * @param sql          sql 语法
     * @param platform     平台
     * @param retailerUuid 商户 id
     * @return sql 语法
     */
    protected String contactSql(String sql, Platform platform, String retailerUuid) {
        if (platform != null) {
            sql = sql + " AND platform='" + platform.name() + "'";
        }
        if (!StringUtils.isEmpty(retailerUuid)) {
            sql = sql + " AND retailer_uuid='" + retailerUuid + "'";
        }

        return sql;
    }

    /**
     * 获取总的数量 sql
     *
     * @param platform     平台
     * @param retailerUuid 商户号
     * @return 总的数量
     */
    protected abstract String getSizeOfTotalSql(Platform platform, String retailerUuid);

    /**
     * 获取总的数量
     *
     * @param platform     平台
     * @param retailerUuid 商户号
     * @return 总的数量
     */
    public SizeOfTotal getSizeOfTotal(Platform platform, String retailerUuid) {
        String key = "";
        key += getStatType().name();
        key += platform != null ? platform.name() : "<NULL>";
        key += !StringUtils.isEmpty(retailerUuid) ? retailerUuid : "<NULL>";

        SizeOfTotal sizeOfTotal = statCacheHelper.get(key);

        if (sizeOfTotal == null) {
            sizeOfTotal = jdbcTemplate.queryForObject(getSizeOfTotalSql(platform, retailerUuid),
                    new BeanPropertyRowMapper<>(SizeOfTotal.class));
            statCacheHelper.set(key, sizeOfTotal);
        }

        return sizeOfTotal;
    }

    /**
     * 获取某一时段的总数统计
     *
     * @param platform     平台
     * @param retailerUuid 商户号
     * @param dayStart     开始时段, 如 2020-11-01 00:00:00
     * @param dayEnd       结果时段, 如 2020-11-01 00:00:00
     * @return 返回天统计数的集合
     */
    protected abstract String getSizeOfDayBetweenSql(Platform platform, String retailerUuid, String dayStart, String dayEnd);

    /**
     * 获取某一时段的总数统计
     *
     * @param platform     平台
     * @param retailerUuid 商户号
     * @param dayStart     开始时段, 如 2020-11-01 00:00:00
     * @param dayEnd       结果时段, 如 2020-11-01 00:00:00
     * @return 返回天统计数的集合
     */
    public SizeOfTotal getSizeOfDayBetween(Platform platform, String retailerUuid, String dayStart, String dayEnd) {
        String key = "";
        key += getStatType().name();
        key += platform != null ? platform.name() : "<NULL>";
        key += !StringUtils.isEmpty(retailerUuid) ? retailerUuid : "<NULL>";
        key += !StringUtils.isEmpty(dayStart) ? dayStart : "<NULL>";
        key += !StringUtils.isEmpty(dayEnd) ? dayEnd : "<NULL>";

        SizeOfTotal sizeOfTotal = statCacheHelper.get(key);

        if (sizeOfTotal == null) {
            sizeOfTotal = jdbcTemplate.queryForObject(getSizeOfDayBetweenSql(platform, retailerUuid, dayStart, dayEnd),
                    new BeanPropertyRowMapper<>(SizeOfTotal.class));
            statCacheHelper.set(key, sizeOfTotal);
        }

        return sizeOfTotal;
    }
}
