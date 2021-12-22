package com.xxx.stat.common.time;

import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.TimeZone;

/**
 * 时区返回固定 UTC+0
 */
@Configuration
public class UTCTimeZoneConfiguration implements ServletContextListener {
    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    public void contextInitialized(ServletContextEvent event) {
        System.setProperty("user.timezone", "UTC");
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    public void contextDestroyed(ServletContextEvent event) {
    }
}
