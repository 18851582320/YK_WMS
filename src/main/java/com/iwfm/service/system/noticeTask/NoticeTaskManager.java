package com.iwfm.service.system.noticeTask;


import java.util.List;

import com.iwfm.util.PageData;

/**
 * 
 * ClassName: NoticeTaskManager 
 * @Description: TODO
 * @author yk
 * @date: 2018年10月26日
 */
public interface NoticeTaskManager {

	/**
	 * @Title: save
	 * @Description: 保存
	 * @author yk
	 * @date: 2018年10月26日
	 * @param: @param pd
	 * @param: @throws Exception   
	 * @return: void  
	 * @throws: 
	 */
	public void save(PageData pd)throws Exception;
	
	/**
	 * @Title: edit
	 * @Description: 编辑
	 * @author yk
	 * @date: 2018年10月26日
	 * @param: @param pd
	 * @param: @throws Exception   
	 * @return: void  
	 * @throws: 
	 */
	public void edit(PageData pd)throws Exception;
	
	/**
	 * 
	 * @Title: editSms
	 * @Description: TODO
	 * @author yk
	 * @date: 2018年11月29日
	 * @param: @param pd
	 * @param: @throws Exception   
	 * @return: void  
	 * @throws:
	 */
	public void editSms(PageData pd)throws Exception;
	
	/**
	 * 
	 * @Title: editBatchSaleNoSend
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年5月9日
	 * @param: @param pd
	 * @param: @throws Exception   
	 * @return: void  
	 * @throws:
	 */
	public void editBatchSaleNoSend(PageData pd)throws Exception;
	
	/**
	 * 
	 * @Title: editBatch
	 * @Description: TODO
	 * @author yk
	 * @date: 2018年10月26日
	 * @param: @param ArrayDATA_IDS
	 * @param: @throws Exception   
	 * @return: void  
	 * @throws:
	 */
	public void editBatch(String[] ArrayDATA_IDS)throws Exception;
	
	
	/**
	 * 
	 * @Title: editBatchSms
	 * @Description: TODO
	 * @author yk
	 * @date: 2018年11月29日
	 * @param: @param ArrayDATA_IDS
	 * @param: @throws Exception   
	 * @return: void  
	 * @throws:
	 */
	public void editBatchSms(String[] ArrayDATA_IDS)throws Exception;
	
	
	
	
	/**
	 * @Title: findById
	 * @Description: 通过主键获取数据
	 * @author yk
	 * @date: 2018年10月26日
	 * @param: @param pd
	 * @param: @return
	 * @param: @throws Exception   
	 * @return: PageData  
	 * @throws: 
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**
	 * 
	 * @Title: listAll
	 * @Description: TODO
	 * @author: HB-PC-042
	 * @date: 2018年10月26日
	 * @param: @param pd
	 * @param: @return
	 * @param: @throws Exception   
	 * @return: List<PageData>  
	 * @throws:
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
	/**
	 * 
	 * @Title: listSmsNoSend
	 * @Description: TODO
	 * @author yk
	 * @date: 2018年11月29日
	 * @param: @param pd
	 * @param: @return
	 * @param: @throws Exception   
	 * @return: List<PageData>  
	 * @throws:
	 */
	public List<PageData> listSmsNoSend(PageData pd)throws Exception;
	
	/**
	 * 
	 * @Title: listBugNoSend
	 * @Description: TODO
	 * @author yk
	 * @date: 2018年11月30日
	 * @param: @param pd
	 * @param: @return
	 * @param: @throws Exception   
	 * @return: List<PageData>  
	 * @throws:
	 */
	public List<PageData> listBugNoSend(PageData pd)throws Exception;
	
	/**
	 * 
	 * @Title: listSaleLead
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年1月17日
	 * @param: @param pd
	 * @param: @return
	 * @param: @throws Exception   
	 * @return: List<PageData>  
	 * @throws:
	 */
	public List<PageData> listSaleLead(PageData pd)throws Exception;
	
	/**
	 * 
	 * @Title: listExpense
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年3月1日
	 * @param: @param pd
	 * @param: @return
	 * @param: @throws Exception   
	 * @return: List<PageData>  
	 * @throws:
	 */
	public List<PageData> listExpense(PageData pd)throws Exception;

}
