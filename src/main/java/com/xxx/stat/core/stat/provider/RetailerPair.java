package com.xxx.stat.core.stat.provider;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 商户对象
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class RetailerPair {
    /**
     * 主键 uuid
     */
    private String uuid;
    /**
     * 名称
     */
    private String name;
}
