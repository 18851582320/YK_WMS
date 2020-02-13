package com.iwfm.task;


import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.iwfm.service.system.noticeTask.NoticeTaskManager;
import com.iwfm.task.entity.ScheduleJob;
import com.iwfm.task.util.ScheduleUtils;
import com.iwfm.util.PageData;
import com.iwfm.util.PushUtils;
import com.iwfm.util.SmsUtils;

import net.sf.json.JSONObject;

public class TaskNotice implements Job{

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		ScheduleJob scheduleJob=(ScheduleJob)context.getMergedJobDataMap().get(ScheduleUtils.JOB_PARAM_KEY);
		String timedTaskName=scheduleJob.getTimedTaskName();
		//String timedTaskType=scheduleJob.getTimedTaskType();
		//String timedTaskClass=scheduleJob.getTimedTaskClass();
    	try {
			System.out.println("任务[" + timedTaskName + "]成功运行");
			
			WebApplicationContext webctx=ContextLoader.getCurrentWebApplicationContext();
			NoticeTaskManager noticeTaskService=(NoticeTaskManager)webctx.getBean("noticeTaskService");
			
			if(noticeTaskService!=null) {
				String successIdStr="";
				PageData pd=new PageData();
				
				String noticeTaskId="";
				String taskName="";
				String tableKeyValue="";
				String sendUserName="";
				String token="";
				String clientid="";
				
				List<PageData> list=noticeTaskService.listAll(pd);
				if(list!=null && list.size()>0) {
					for(int i=0;i<list.size();i++) {
						pd=list.get(i);
						
						noticeTaskId="";
						taskName="";
						tableKeyValue="";
						sendUserName="";
						token="";
						clientid="";
						
						noticeTaskId=pd.getString("noticeTaskId");
						taskName=pd.getString("taskName");
						tableKeyValue=pd.getString("tableKeyValue");
						sendUserName=pd.getString("sendUserName");
						token=pd.getString("token");
						clientid=pd.getString("clientid");
						
						String rtn=PushUtils.PushtoSingle(token, clientid, "新任务提醒", "您有新的任务，发送人："+sendUserName+",任务名称："+taskName,tableKeyValue);
						if(StringUtils.isNotEmpty(rtn) && rtn.equals("ok")) {
							if(StringUtils.isNotEmpty(successIdStr)) {
								successIdStr=successIdStr+","+noticeTaskId;
							}else {
								successIdStr=noticeTaskId;
							}
						}
						
					}
				}
				
				if(StringUtils.isNotEmpty(successIdStr)) {
					String successIdArr[]=successIdStr.split(",");
					noticeTaskService.editBatch(successIdArr);
				}
				
				
				//任务短信通知
				pd=new PageData();
				successIdStr="";
				String planEndTime="";
				String TEL="";
				String sendTel="";
				String checkTel="";
				String receiveUserName="";
				String checkUserName="";
				String noticeType="";
				String rtn="";
				
				List<PageData> smsList=noticeTaskService.listSmsNoSend(pd);
				if(smsList!=null && smsList.size()>0) {
					for(int i=0;i<smsList.size();i++) {
						pd=smsList.get(i);
						
						taskName="";
						sendUserName="";
						planEndTime="";
						TEL="";
						noticeTaskId="";
						sendTel="";
						checkTel="";
						receiveUserName="";
						checkUserName="";
						noticeType="";
						
						noticeTaskId=pd.getString("noticeTaskId");
						taskName=pd.getString("taskName");
						sendUserName=pd.getString("sendUserName");
						TEL=pd.getString("TEL");
						sendTel=pd.getString("sendTel");
						checkTel=pd.getString("checkTel");
						noticeType=pd.get("noticeType").toString();
						receiveUserName=pd.getString("receiveUserName");
						checkUserName=pd.getString("checkUserName");
						planEndTime=pd.get("planEndTime").toString();
						
						String param=sendUserName+","+taskName+","+planEndTime;
						
						
						if(StringUtils.isNotEmpty(noticeType)) {
							if(noticeType.equals("0")) {
								rtn=SmsUtils.sendTaskSms(param, TEL);
							}
							
							if(noticeType.equals("1")) {
								param=receiveUserName+","+taskName;
								rtn=SmsUtils.sendTaskSmsNoReceive(param, sendTel);
							}
							
							if(noticeType.equals("2")) {
								param=receiveUserName+","+taskName;
								rtn=SmsUtils.sendTaskSmsComplete(param, checkTel);
							}
							
							if(noticeType.equals("3")) {
								param=checkUserName+","+taskName;
								rtn=SmsUtils.sendTaskSmsNoCheck(param, TEL);
							}
						}
						
						
						JSONObject rtnObj=JSONObject.fromObject(rtn);
						String code=rtnObj.getString("code");
						if(code.equals("000000")) {
							if(StringUtils.isNotEmpty(successIdStr)) {
								successIdStr=successIdStr+","+noticeTaskId;
							}else {
								successIdStr=noticeTaskId;
							}
						}
						
					}
				}
				
				
				if(StringUtils.isNotEmpty(successIdStr)) {
					String successIdArr[]=successIdStr.split(",");
					noticeTaskService.editBatchSms(successIdArr);
				}
				
				
				//bug短信通知
				pd=new PageData();
				successIdStr="";
				
				List<PageData> bugList=noticeTaskService.listBugNoSend(pd);
				if(bugList!=null && bugList.size()>0) {
					for(int i=0;i<bugList.size();i++) {
						pd=bugList.get(i);
						
						sendUserName="";
						TEL="";
						noticeTaskId="";
						rtn="";
						
						noticeTaskId=pd.getString("noticeTaskId");
						sendUserName=pd.getString("sendUserName");
						TEL=pd.getString("TEL");
						
						String param=null;
						
						rtn=SmsUtils.sendBugSms(param, TEL);
						
						JSONObject rtnObj=JSONObject.fromObject(rtn);
						String code=rtnObj.getString("code");
						if(code.equals("000000")) {
							if(StringUtils.isNotEmpty(successIdStr)) {
								successIdStr=successIdStr+","+noticeTaskId;
							}else {
								successIdStr=noticeTaskId;
							}
						}
						
					}
				}
				
				
				if(StringUtils.isNotEmpty(successIdStr)) {
					String successIdArr[]=successIdStr.split(",");
					noticeTaskService.editBatchSms(successIdArr);
				}
				
				
				
				
				
				//销售线索短信通知
				pd=new PageData();
				
				
				//设置不需要通知的状态
				noticeTaskService.editBatchSaleNoSend(pd);
				
				
				successIdStr="";
				
				String customerName="";
				String contactName="";
				String phone="";
				
				List<PageData> saleLeadList=noticeTaskService.listSaleLead(pd);
				if(saleLeadList!=null && saleLeadList.size()>0) {
					for(int i=0;i<saleLeadList.size();i++) {
						pd=saleLeadList.get(i);
						
						TEL="";
						rtn="";
						
						noticeTaskId=pd.getString("noticeTaskId");
						customerName=pd.getString("customerName");
						contactName=pd.getString("contactName");
						phone=pd.getString("phone");
						TEL=pd.getString("TEL");
						
						String param=customerName+","+contactName+","+phone;
						
						rtn=SmsUtils.sendSaleLead(param, TEL);
						
						JSONObject rtnObj=JSONObject.fromObject(rtn);
						String code=rtnObj.getString("code");
						if(code.equals("000000")) {
							if(StringUtils.isNotEmpty(successIdStr)) {
								successIdStr=successIdStr+","+noticeTaskId;
							}else {
								successIdStr=noticeTaskId;
							}
						}
						
					}
				}
				
				
				if(StringUtils.isNotEmpty(successIdStr)) {
					String successIdArr[]=successIdStr.split(",");
					noticeTaskService.editBatchSms(successIdArr);
				}
				
				
				
				
				
				
				
				//报销短信通知
				pd=new PageData();
				successIdStr="";
				
				List<PageData> baoXiaoList=noticeTaskService.listExpense(pd);
				if(baoXiaoList!=null && baoXiaoList.size()>0) {
					for(int i=0;i<baoXiaoList.size();i++) {
						pd=baoXiaoList.get(i);
						
						TEL="";
						noticeTaskId="";
						rtn="";
						
						noticeTaskId=pd.getString("noticeTaskId");
						TEL=pd.getString("TEL");
						
						String param=null;
						
						rtn=SmsUtils.sendBaoXiaoSms(param, TEL);
						
						JSONObject rtnObj=JSONObject.fromObject(rtn);
						String code=rtnObj.getString("code");
						if(code.equals("000000")) {
							if(StringUtils.isNotEmpty(successIdStr)) {
								successIdStr=successIdStr+","+noticeTaskId;
							}else {
								successIdStr=noticeTaskId;
							}
						}
						
					}
				}
				
				
				if(StringUtils.isNotEmpty(successIdStr)) {
					String successIdArr[]=successIdStr.split(",");
					noticeTaskService.editBatchSms(successIdArr);
				}
				
				
				
				
			}
			
			
		} catch (Exception e) {
		}
	}
	
}
