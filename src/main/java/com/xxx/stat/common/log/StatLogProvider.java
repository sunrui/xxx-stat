package com.xxx.stat.common.log;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 统计数据 stat 信息
 */
@Service
public class StatLogProvider {
    /**
     * 判断在生产环境中不再显示
     */
    @Value("${spring.profiles.active}")
    private String profile;

    public void debug(String message) {
        if (!profile.contentEquals("prod")) {
            System.out.println(message);
        }
    }
}
