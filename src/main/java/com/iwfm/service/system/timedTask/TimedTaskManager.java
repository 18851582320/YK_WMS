package com.iwfm.service.system.timedTask;

import java.util.List;

import com.iwfm.entity.Page;
import com.iwfm.task.entity.ScheduleJob;
import com.iwfm.util.PageData;

/**
 * ClassName: TimedTaskManager 
 * @Description: 定时任务
 * @author yk
 * @date 2018年3月29日
 */
public interface TimedTaskManager {

	/**
	 * @Title:save
	 * @Description: 新增
	 * @author yk
	 * @date 2018年2月28日
	 * @param @param pd
	 * @param @throws Exception   
	 * @return void  
	 * @throws
	 */
	public void save(PageData pd)throws Exception;
	
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
	public void saveBatch(List<PageData> pdLst)throws Exception;
	
	
	/**
	 * @Title:delete
	 * @Description: 删除
	 * @author yk
	 * @date 2018年2月28日
	 * @param @param pd
	 * @param @throws Exception   
	 * @return void  
	 * @throws
	 */
	public void delete(PageData pd)throws Exception;
	
	/**
	 * @Title:edit
	 * @Description: 修改
	 * @author yk
	 * @date 2018年2月28日
	 * @param @param pd
	 * @param @throws Exception   
	 * @return void  
	 * @throws
	 */
	public void edit(PageData pd)throws Exception;
	
	
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
	public List<PageData> querylistPage(Page page)throws Exception;
	
	/**
	 * @Title:findById
	 * @Description: 通过id查找数据
	 * @author yk
	 * @date 2018年2月28日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @return PageData  
	 * @throws
	 */
	public PageData findById(PageData pd)throws Exception;
	
	
	/**
	 * @Title:deleteAll
	 * @Description: 批量删除
	 * @author yk
	 * @date 2018年2月28日
	 * @param @param ArrayDATA_IDS
	 * @param @throws Exception   
	 * @return void  
	 * @throws
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
	/**
	 * @Title:initScheduleJob
	 * @Description: 初始化
	 * @author yk
	 * @date 2018年3月29日
	 * @param    
	 * @return void  
	 * @throws
	 */
	public void initScheduleJob() throws Exception;
	
	/**
	 * @Title:runOnce
	 * @Description: 运行一次任务
	 * @author yk
	 * @date 2018年3月29日
	 * @param @param o
	 * @param @return   
	 * @return int  
	 * @throws
	 */
	public int runOnce(ScheduleJob o);
	
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
	public int pauseJob(ScheduleJob o);
	
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
	public int resumeJob(ScheduleJob o);
}
