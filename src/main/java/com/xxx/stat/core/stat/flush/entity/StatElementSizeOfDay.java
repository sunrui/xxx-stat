package com.xxx.stat.core.stat.flush.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * 按天统计
 */
@Entity
@Getter
@Setter
public class StatElementSizeOfDay extends StatElement {
    @ManyToOne
    private StatElementSizeOfYear statElementSizeOfYear;
    /**
     * 某一天，格式 20201101
     */
    private Integer day;
}
