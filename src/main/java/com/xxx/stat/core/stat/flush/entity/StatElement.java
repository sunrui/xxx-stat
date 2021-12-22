package com.xxx.stat.core.stat.flush.entity;

import com.xxx.stat.core.stat.flush.entity.base.StatEntity;
import com.xxx.stat.core.stat.provider.Platform;
import com.xxx.stat.core.stat.provider.StatType;
import com.xxx.stat.core.stat.provider.StatUnit;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

/**
 * 统计基础元素
 */
@Entity
@Getter
@Setter
public class StatElement extends StatEntity {
    /**
     * 平台
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    private Platform platform;
    /**
     * 统计类型
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    private StatType statType;
    /**
     * 计量单位
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    private StatUnit statUnit;
    /**
     * 总数
     */
    private Integer size;
    /**
     * 金额
     */
    private Float price;
}
