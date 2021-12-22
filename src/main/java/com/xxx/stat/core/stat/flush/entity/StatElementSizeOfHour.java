package com.xxx.stat.core.stat.flush.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * 按小时统计
 */
@Entity
@Getter
@Setter
public class StatElementSizeOfHour extends StatElement {
    @JoinColumn
    @ManyToOne
    private StatElementSizeOfDay statElementSizeOfDay;
    /**
     * 某一小时，格式 1
     */
    private Integer hour;
}
