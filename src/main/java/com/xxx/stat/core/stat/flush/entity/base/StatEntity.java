package com.xxx.stat.core.stat.flush.entity.base;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 统计基础元素父类
 */
@Getter
@Setter
@MappedSuperclass
public class StatEntity implements Serializable {
    @Id
    @NotBlank
    private String id;
    @NotNull
    private Date createdAt;
    @NotNull
    private Date updatedAt;
}
