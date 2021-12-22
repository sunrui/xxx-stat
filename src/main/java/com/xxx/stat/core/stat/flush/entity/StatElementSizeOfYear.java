package com.xxx.stat.core.stat.flush.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * 按年统计
 */
@Entity
@Getter
@Setter
public class StatElementSizeOfYear extends StatElement {
    @JoinColumn
    @ManyToOne
    private StatElement statElement;
    /**
     * 某一天
     */
    private Integer year;
}
