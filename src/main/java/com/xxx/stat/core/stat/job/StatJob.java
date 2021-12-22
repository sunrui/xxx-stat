package com.xxx.stat.core.stat.job;

import com.xxx.stat.api.stat.StatController;
import com.xxx.stat.common.log.StatLogProvider;
import com.xxx.stat.core.stat.data.node.StatGroup;
import com.xxx.stat.core.stat.data.node.StatGroupNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 数据预更新作业，前端将返回多少秒之前处理完的内容
 */
@Component
public class StatJob {
    @Autowired
    private StatController statController;
    @Autowired
    private StatLogProvider statLogProvider;

    /**
     * 每隔数秒钟以后预处理一次任务
     */
    @Scheduled(fixedDelay = 5 * 1000)
    public void doStatJob() {
        StatGroup[] statGroups = statController.getIndex();
        for (StatGroup statGroup : statGroups) {
            for (StatGroupNode statGroupNode : statGroup.getNodes()) {
                // TODO 预处理任务
            }
        }
    }
}
