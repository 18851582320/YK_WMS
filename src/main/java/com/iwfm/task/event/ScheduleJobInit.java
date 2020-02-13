package com.iwfm.task.event;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.iwfm.service.system.timedTask.TimedTaskManager;


/**
 * 定时任务初始化
 */
@Component
public class ScheduleJobInit  {

    /** 日志对象 */
    private static final Logger LOG = LoggerFactory.getLogger(ScheduleJobInit.class);

    /** 定时任务service */
    @Resource(name="timedTaskService")
    private TimedTaskManager  timedTaskService;

    /**
     * 项目启动时初始化
     */
    @PostConstruct
    public void init()  {
        LOG.info("定时任务初始化init");
        try {
        	timedTaskService.initScheduleJob();
		} catch (Exception e) {
			// TODO: handle exception
		}
        LOG.info("定时任务初始化end");
    }

}
