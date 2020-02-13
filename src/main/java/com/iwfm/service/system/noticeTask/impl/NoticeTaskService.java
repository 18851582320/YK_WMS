package com.iwfm.service.system.noticeTask.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.iwfm.dao.DaoSupport;
import com.iwfm.service.system.noticeTask.NoticeTaskManager;
import com.iwfm.util.PageData;

/**
 * ClassName: NoticeTaskService
 * @Description: 
 * @author yk
 * @date: 2018年10月26日
 */
@Service("noticeTaskService")
public class NoticeTaskService implements NoticeTaskManager {
	
	@Resource(name="daoSupport")
	private DaoSupport dao;

	/**
	 * @Title: save
	 * @Description: 保存
	 * @author yk
	 * @date: 2018年10月26日
	 * @param: @param pd
	 * @param: @throws Exception   
	 * @throws
	 */
	@Override
	public void save(PageData pd) throws Exception {
		dao.save("NoticeTaskMapper.save", pd);
	}

	/**
	 * @Title: edit
	 * @Description: 编辑
	 * @author yk
	 * @date: 2018年10月26日
	 * @param: @param pd
	 * @param: @throws Exception   
	 * @throws
	 */
	@Override
	public void edit(PageData pd) throws Exception {
		dao.update("NoticeTaskMapper.edit", pd);
	}
	
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
	public void editSms(PageData pd)throws Exception{
		dao.update("NoticeTaskMapper.editSms", pd);
	}
	
	
	
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
	public void editBatchSaleNoSend(PageData pd)throws Exception{
		dao.update("NoticeTaskMapper.editBatchSaleNoSend", pd);
	}
	
	
	/**
	 * 
	 * @Title: editBatch
	 * @Description: TODO
	 * @author yk
	 * @date: 2018年10月26日
	 * @param: @param ArrayDATA_IDS
	 * @param: @throws Exception   
	 * @throws
	 */
	@Override
	public void editBatch(String[] ArrayDATA_IDS) throws Exception {
		dao.update("NoticeTaskMapper.editBatch", ArrayDATA_IDS);
	}
	
	
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
	public void editBatchSms(String[] ArrayDATA_IDS)throws Exception{
		dao.update("NoticeTaskMapper.editBatchSms", ArrayDATA_IDS);
	}


	/**
	 * @Title: findById
	 * @Description: 通过主键获取数据
	 * @author yk
	 * @date: 2018年10月26日
	 * @param: @param pd
	 * @param: @return
	 * @param: @throws Exception   
	 * @throws
	 */
	@Override
	public PageData findById(PageData pd) throws Exception {
		return (PageData)dao.findForObject("NoticeTaskMapper.findById", pd);
	}
	
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
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("NoticeTaskMapper.listAll", pd);
	}
	
	
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
	@SuppressWarnings("unchecked")
	public List<PageData> listSmsNoSend(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("NoticeTaskMapper.listSmsNoSend", pd);
	}
	
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
	@SuppressWarnings("unchecked")
	public List<PageData> listBugNoSend(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("NoticeTaskMapper.listBugNoSend", pd);
	}
	
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
	@SuppressWarnings("unchecked")
	public List<PageData> listSaleLead(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("NoticeTaskMapper.listSaleLead", pd);
	}
	
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
	@SuppressWarnings("unchecked")
	public List<PageData> listExpense(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("NoticeTaskMapper.listExpense", pd);
	}
	
}
