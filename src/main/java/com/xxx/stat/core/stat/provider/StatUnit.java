package com.xxx.stat.core.stat.provider;

import lombok.Getter;

/**
 * 统计类型
 */
@Getter
public enum StatUnit {
    PRICE("金额"),
    SIZE("总数");

    StatUnit(String comment) {
        this.comment = comment;
    }

    private final String comment;
}
