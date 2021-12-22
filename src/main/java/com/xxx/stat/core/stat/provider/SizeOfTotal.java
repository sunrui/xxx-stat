package com.xxx.stat.core.stat.provider;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

/**
 * 某一时段的数量
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SizeOfTotal {
    /**
     * 总的数量
     */
    private Long size;
    /**
     * 总的金额
     */
    private Float price;
}
