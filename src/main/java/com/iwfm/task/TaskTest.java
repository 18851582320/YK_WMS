package com.iwfm.task;


import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.iwfm.plugin.websocketOnline.OnlineChatServer;
import com.iwfm.task.entity.ScheduleJob;
import com.iwfm.task.util.ScheduleUtils;
import com.iwfm.util.Const;
import com.iwfm.util.Tools;

public class TaskTest implements Job{

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		ScheduleJob scheduleJob=(ScheduleJob)context.getMergedJobDataMap().get(ScheduleUtils.JOB_PARAM_KEY);
		String timedTaskName=scheduleJob.getTimedTaskName();
//		String timedTaskType=scheduleJob.getTimedTaskType();
//		String timedTaskClass=scheduleJob.getTimedTaskClass();
    	try {
			System.out.println("任务[" + timedTaskName + "]成功运行");
			
			OnlineChatServer s;
			String strWEBSOCKET = Tools.readTxtFile(Const.WEBSOCKET);//读取WEBSOCKET配置,获取端口配置
			if(null != strWEBSOCKET && !"".equals(strWEBSOCKET)){
				String strIW[] = strWEBSOCKET.split(",IWFM,");
				if(strIW.length == 5){
					s = new OnlineChatServer(Integer.parseInt(strIW[3]));
					s.start();
					s.senSmsWithMsg("admin","发送消息测试");
				}
			}
			
			
		} catch (Exception e) {
		}
	}
	
}
