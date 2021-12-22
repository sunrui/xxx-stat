package com.xxx.stat.core.stat.provider.cache;

import com.xxx.stat.core.stat.provider.SizeOfTotal;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据查询结果级缓存
 */
@Getter
@Service
public class StatCacheHelper {
    private final Map<String, CalCache> calCacheMap = new ConcurrentHashMap<>();
    private Integer sqlHitsTimes = 0;
    private Integer sqlUnHitsTimes = 0;

    @Getter
    @Setter
    static class CalCache {
        private SizeOfTotal sizeOfTotal;
        private Date cachedAt;

        /**
         * 最长缓存时间
         */
        private final Integer FORCE_UPDATE_TIME = 5 * 60 * 1000;

        public Boolean isExpired() {
            return new Date(cachedAt.getTime() + FORCE_UPDATE_TIME).before(new Date());
        }
    }

    public void set(String key, SizeOfTotal sizeOfTotal) {
        CalCache calCache = new CalCache();
        calCache.setSizeOfTotal(sizeOfTotal);
        calCache.setCachedAt(new Date());
        calCacheMap.put(key, calCache);
    }

    public SizeOfTotal get(String key) {
        CalCache calCache = calCacheMap.get(key);
        if (calCache == null || calCache.isExpired()) {
            sqlUnHitsTimes++;
            return null;
        } else {
            sqlHitsTimes++;
        }

        return calCache.getSizeOfTotal();
    }

    public void clear() {
        sqlHitsTimes = 0;
        sqlUnHitsTimes = 0;
        calCacheMap.clear();
    }
}
