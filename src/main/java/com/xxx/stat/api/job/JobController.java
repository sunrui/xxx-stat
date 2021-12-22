package com.xxx.stat.api.job;

import com.xxx.stat.core.job.JobProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 作业控制器
 */
@RestController
@RequestMapping("job")
public class JobController {
    @Autowired
    private JobProvider jobProvider;

    /**
     * 获取作业状态
     */
    @GetMapping("stat")
    @ResponseBody
    public JobProvider.JobStat getJobStat() {
        return jobProvider.getJobStat();
    }
}
