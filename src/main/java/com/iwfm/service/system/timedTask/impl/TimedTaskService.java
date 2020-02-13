
package com.iwfm.service.system.timedTask.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iwfm.dao.DaoSupport;
import com.iwfm.entity.Page;
import com.iwfm.service.system.log.LogManager;
import com.iwfm.service.system.timedTask.TimedTaskManager;
import com.iwfm.task.entity.ScheduleJob;
import com.iwfm.task.util.ScheduleUtils;
import com.iwfm.util.DateUtil;
import com.iwfm.util.Jurisdiction;
import com.iwfm.util.PageData;

/**
 * ClassName: DutyService 
 * @Description: 职务
 * @author yk
 * @date 2018年3月1日
 */
@Service(value="timedTaskService")
public class TimedTaskService implements TimedTaskManager{
	
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	
	@Resource(name="logService")
	private LogManager logService;
	
	/** 调度工厂Bean */
	@Autowired
	private Scheduler scheduler;
	
	
	/**
	 * @Title:save
	 * @Description: 增加
	 * @author yk
	 * @date 2018年2月28日
	 * @param @param pd
	 * @param @throws Exception   
	 * @throws
	 */
	public void save(PageData pd)throws Exception{
		dao.save("TimedTaskMapper.save", pd);
	}
	
	/**
	 * @Title:saveBatch
	 * @Description: 批量保存
	 * @author yk
	 * @date 2018年2月28日
	 * @param @param pdLst
	 * @param @throws Exception   
	 * @return void  
	 * @throws
	 */
	public void saveBatch(List<PageData> pdLst)throws Exception{
		for(int i=0;i<pdLst.size();i++){
			dao.save("TimedTaskMapper.save", pdLst.get(i));
		}
	}
	
	
	/**
	 * @Title:delete
	 * @Description: 删除
	 * @author yk
	 * @date 2018年2月28日
	 * @param @param pd
	 * @param @throws Exception   
	 * @throws
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("TimedTaskMapper.delete", pd);
	}
	
	
	/**
	 * @Title:edit
	 * @Description: 修改
	 * @author yk
	 * @date 2018年2月28日
	 * @param @param pd
	 * @param @throws Exception   
	 * @throws
	 */
	public void edit(PageData pd)throws Exception{
		dao.update("TimedTaskMapper.edit", pd);
		logService.save(Jurisdiction.getUsername(), "修改职务："+pd.getString("dutyCode"));			
	}
	
	
	/**
	 * @Title:querylistPage
	 * @Description: 列表（datagrid分页查询）
	 * @author yk
	 * @date 2018年2月28日
	 * @param @param page
	 * @param @return
	 * @param @throws Exception   
	 * @return List<PageData>  
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> querylistPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("TimedTaskMapper.querylistPage", page);
	}
	
	
	/**
	 * @Title:findById
	 * @Description: 通过id获取数据
	 * @author yk
	 * @date 2018年2月28日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @throws
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("TimedTaskMapper.findById", pd);
	}

	/**
	 * @Title:deleteAll
	 * @Description: 批量删除
	 * @author yk
	 * @date 2018年2月28日
	 * @param @param ArrayDATA_IDS
	 * @param @throws Exception   
	 * @throws
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("TimedTaskMapper.deleteAll", ArrayDATA_IDS);
	}
	
	/**
	 * @Title:initScheduleJob
	 * @Description: 初始化定时任务
	 * @author yk
	 * @date 2018年3月29日
	 * @param    
	 * @return void  
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public void initScheduleJob()throws Exception {
		//查找启用的任务
		PageData pd=new PageData();
		pd.put("timedTaskState","1");
		
		List<PageData> pdLst=(List<PageData>)dao.findForList("TimedTaskMapper.listAll", pd);
		
		ScheduleJob scheduleJob=new ScheduleJob();
		if (CollectionUtils.isNotEmpty(pdLst)) {
			for (PageData pd2 : pdLst) {
				
				scheduleJob=null;
				
				scheduleJob=new ScheduleJob();
				
				scheduleJob.setTimedTaskId(pd2.getString("timedTaskId"));
				scheduleJob.setTimedTaskName(pd2.getString("timedTaskName"));
				scheduleJob.setTimedTaskAliasName(pd2.getString("timedTaskAliasName"));
				scheduleJob.setTimedTaskType(pd2.getString("timedTaskType"));
				scheduleJob.setTimedTaskClass(pd2.getString("timedTaskClass"));
				scheduleJob.setTimedTaskState((Integer)pd2.get("timedTaskState"));
				scheduleJob.setCronExpression(pd2.getString("cronExpression"));
				scheduleJob.setTimedTaskMemo(pd2.getString("timedTaskMemo"));
				
				CronTrigger cronTrigger = ScheduleUtils.getCronTrigger(scheduler, scheduleJob.getTimedTaskName(),scheduleJob.getTimedTaskType());
				try {
					if (cronTrigger == null) {
						// 不存在，创建一个
						ScheduleUtils.createScheduleJob(scheduler, scheduleJob);	
					} else {
						// 已存在，那么更新相应的定时设置
						ScheduleUtils.updateScheduleJob(scheduler, scheduleJob);
					}
				} catch (Exception e) {
				}
			}
		}
	}
	
	/**
	 * @Title:runOnce
	 * @Description: 运行一次任务
	 * @author yk
	 * @date 2018年3月29日
	 * @param @param o
	 * @param @return   
	 * @throws
	 */
	@Transactional
	public int runOnce(ScheduleJob o) {
		int res=0;
		try {
			//从数据库查找原信息
			PageData pd=new PageData();
			pd.put("timedTaskId",o.getTimedTaskId());
			pd=(PageData)dao.findForObject("TimedTaskMapper.findById", pd);
			ScheduleJob scheduleJob=new ScheduleJob();
			
			scheduleJob.setTimedTaskId(pd.getString("timedTaskId"));
			scheduleJob.setTimedTaskName(pd.getString("timedTaskName"));
			scheduleJob.setTimedTaskAliasName(pd.getString("timedTaskAliasName"));
			scheduleJob.setTimedTaskType(pd.getString("timedTaskType"));
			scheduleJob.setTimedTaskClass(pd.getString("timedTaskClass"));
			scheduleJob.setTimedTaskState((Integer)pd.get("timedTaskState"));
			scheduleJob.setCronExpression(pd.getString("cronExpression"));
			scheduleJob.setTimedTaskMemo(pd.getString("timedTaskMemo"));
			
			if(scheduleJob.getTimedTaskState()!=null && scheduleJob.getTimedTaskState()==1){
				//运行一次任务
				res=2;
			}else{
				//当任务没启动，必须先创建
				ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
				//时间短可能促发多次
				//ScheduleUtils.pauseJob(scheduler,scheduleJob.getJobName(), scheduleJob.getJobGroup());
				//然后立刻运行一次任务
				ScheduleUtils.runOnce(scheduler, scheduleJob.getTimedTaskName(), scheduleJob.getTimedTaskType());
				try {
					//休眠3秒，等任务完成，完成不了就加长休眠时间吧...
			        Thread.sleep(3000);
			    } catch (InterruptedException e) {
			        e.printStackTrace();
			    }
				//再删除任务
				ScheduleUtils.deleteScheduleJob(scheduler,scheduleJob.getTimedTaskName(), scheduleJob.getTimedTaskType());
				res=1;
			}			
		} catch (Exception e) {
			System.out.println("运行一次任务失败");
		}
		return res;
	}
	
	/**
	 * @Title:pauseJob
	 * @Description: 暂停任务
	 * @author yk
	 * @date 2018年3月29日
	 * @param @param o
	 * @param @return   
	 * @return int  
	 * @throws
	 */
	
	@Transactional
	public int pauseJob(ScheduleJob o){
		int res=0;
		try {
			PageData pd=new PageData();
			pd.put("timedTaskId",o.getTimedTaskId());
			pd=(PageData)dao.findForObject("TimedTaskMapper.findById", pd);
			ScheduleJob scheduleJob=new ScheduleJob();
			
			scheduleJob.setTimedTaskId(pd.getString("timedTaskId"));
			scheduleJob.setTimedTaskName(pd.getString("timedTaskName"));
			scheduleJob.setTimedTaskAliasName(pd.getString("timedTaskAliasName"));
			scheduleJob.setTimedTaskType(pd.getString("timedTaskType"));
			scheduleJob.setTimedTaskClass(pd.getString("timedTaskClass"));
			scheduleJob.setTimedTaskState((Integer)pd.get("timedTaskState"));
			scheduleJob.setCronExpression(pd.getString("cronExpression"));
			scheduleJob.setTimedTaskMemo(pd.getString("timedTaskMemo"));
			
			
			if(scheduleJob.getTimedTaskState()!=null && scheduleJob.getTimedTaskState()==1){
				//判断jobKey为不为空，如为空，任务已停止
				//先暂停任务
				//ScheduleUtils.pauseJob(scheduler, scheduleJob.getJobName(), scheduleJob.getJobGroup());		
				ScheduleUtils.deleteScheduleJob(scheduler, scheduleJob.getTimedTaskName(), scheduleJob.getTimedTaskType());
				//更新数据库
				
				PageData pd2=new PageData();
				pd2.put("timedTaskId", o.getTimedTaskId());
				pd2.put("timedTaskState", "0");
				pd2.put("updateTime", DateUtil.getTime());
				dao.update("TimedTaskMapper.editState", pd2);
				res=1;
			}else{	
				//任务没启动，谈何暂停...
				res=2;			
			}
		} catch (Exception e) {
		}
		return res;
	}
	
	/**
	 * @Title:resumeJob
	 * @Description: 启动任务
	 * @author yk
	 * @date 2018年3月29日
	 * @param @param o
	 * @param @return   
	 * @return int  
	 * @throws
	 */
	@Transactional
	public int resumeJob(ScheduleJob o){
		int res=0;
		try {
			//从数据库查找原信息
			PageData pd=new PageData();
			pd.put("timedTaskId",o.getTimedTaskId());
			pd=(PageData)dao.findForObject("TimedTaskMapper.findById", pd);
			ScheduleJob scheduleJob=new ScheduleJob();
			
			scheduleJob.setTimedTaskId(pd.getString("timedTaskId"));
			scheduleJob.setTimedTaskName(pd.getString("timedTaskName"));
			scheduleJob.setTimedTaskAliasName(pd.getString("timedTaskAliasName"));
			scheduleJob.setTimedTaskType(pd.getString("timedTaskType"));
			scheduleJob.setTimedTaskClass(pd.getString("timedTaskClass"));
			scheduleJob.setTimedTaskState((Integer)pd.get("timedTaskState"));
			scheduleJob.setCronExpression(pd.getString("cronExpression"));
			scheduleJob.setTimedTaskMemo(pd.getString("timedTaskMemo"));
			
			
			if(scheduleJob.getTimedTaskState()!=null && scheduleJob.getTimedTaskState()==0){
				ScheduleUtils.createScheduleJob(scheduler, scheduleJob);
				PageData pd2=new PageData();
				pd2.put("timedTaskId", o.getTimedTaskId());
				pd2.put("timedTaskState", "1");
				pd2.put("updateTime", DateUtil.getTime());
				dao.update("TimedTaskMapper.editState", pd2);
				
				pd2=null;
				res=1;
			}else{
				res=2;
			}
		} catch (Exception e) {
		}
		return res;
	}
	
}
