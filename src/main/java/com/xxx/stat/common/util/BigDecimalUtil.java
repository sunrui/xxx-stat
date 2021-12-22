package com.xxx.stat.common.util;

import org.apache.commons.lang3.ArrayUtils;

import java.math.BigDecimal;

/**
 * BigDecimal 工具类
 */
public class BigDecimalUtil {

    public static BigDecimal div(BigDecimal source, BigDecimal target, int scale) {
        return source.divide(target, scale, 4);
    }

    public static BigDecimal mul(BigDecimal source, BigDecimal... targets) {
        return mul(source, 2, targets);
    }

    public static BigDecimal mul(BigDecimal source, int scale, BigDecimal... targets) {
        if (source == null) {
            return null;
        } else if (ArrayUtils.isEmpty(targets)) {
            return source;
        } else {
            for (BigDecimal target : targets) {
                if (target != null) {
                    source = source.multiply(target);
                }
            }

            return source.setScale(scale, 4);
        }
    }
}
