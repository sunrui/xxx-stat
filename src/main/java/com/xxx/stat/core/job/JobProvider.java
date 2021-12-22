package com.xxx.stat.core.job;

import com.xxx.stat.common.util.DateUtil;
import com.xxx.stat.core.stat.provider.cache.StatCacheHelper;
import com.xxx.stat.core.job.retailer.RetailerJobProvider;
import com.xxx.stat.core.job.summary.SummaryJobProvider;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 预处理任务启动服务
 */
@Getter
@Service
public class JobProvider {
    @Autowired
    private RetailerJobProvider retailerJobProvider;
    @Autowired
    private SummaryJobProvider summaryJobProvider;
    @Autowired
    private StatCacheHelper statCacheHelper;

    @Value("${job.pre-process}")
    private Boolean preProcess;

    private JobStat jobStat = null;

    @Scheduled(initialDelay = 5000, fixedDelay = 5 * 60 * 1000)
    void doJob() {
        if (!preProcess) {
            return;
        }

        Date nowDate = new Date();

        statCacheHelper.clear();
        retailerJobProvider.doJob();
        summaryJobProvider.doJob();

        jobStat = JobStat.builder()
                .sqlHitsTimes(statCacheHelper.getSqlHitsTimes())
                .sqlUnHitsTimes(statCacheHelper.getSqlUnHitsTimes())
                .processedAt(nowDate)
                .finishedAt(new Date())
                .elapsed(DateUtil.getElapsedTime(new Date(), nowDate))
                .build();
    }

    /**
     * 预处理状态
     */
    @Getter
    @Setter
    @Builder
    public static class JobStat {
        /**
         * SQL 触发命中次数
         */
        private Integer sqlHitsTimes;
        /**
         * SQL 未触发命中次数
         */
        private Integer sqlUnHitsTimes;
        /**
         * 预处理时间
         */
        private Date processedAt;
        /**
         * 结束时间
         */
        private Date finishedAt;
        /**
         * 耗时
         */
        private String elapsed;
    }

    public JobStat getJobStat() {
        return jobStat;
    }
}
