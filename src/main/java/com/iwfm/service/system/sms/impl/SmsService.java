
package com.iwfm.service.system.sms.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.iwfm.dao.DaoSupport;
import com.iwfm.entity.Page;
import com.iwfm.service.system.log.LogManager;
import com.iwfm.service.system.sms.SmsManager;
import com.iwfm.util.PageData;
import com.iwfm.util.UuidUtil;

/**
 * ClassName: SmsService 
 * @Description: TODO
 * @author yk
 * @date 2018年3月28日
 */
@Service(value="smsService")
public class SmsService implements SmsManager{
	
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	@Resource(name="logService")
	private LogManager logService;
	
	
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
		String toUserAll=pd.getString("toUserAll");
		if(StringUtils.isNotEmpty(toUserAll)){
		  String toUserAllArray[]=toUserAll.split(",");
		  for(int i=0;i<toUserAllArray.length;i++){
			  pd.put("smsId", UuidUtil.get32UUID());	//主键
			  pd.put("toUser", toUserAllArray[i]);	//主键
			  dao.save("SmsMapper.save", pd);
		  }
		}
		
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
			dao.save("SmsMapper.save", pdLst.get(i));
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
		dao.delete("SmsMapper.delete", pd);
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
		dao.update("SmsMapper.edit", pd);
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
		return (List<PageData>)dao.findForList("SmsMapper.querylistPage", page);
	}
	
	
	/**
	 * 
	 * @Title:queryReceivelistPage
	 * @Description: TODO
	 * @author yk
	 * @date 2018年9月6日
	 * @param @param page
	 * @param @return
	 * @param @throws Exception   
	 * @return List<PageData>  
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> queryReceivelistPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("SmsMapper.queryReceivelistPage", page);
	}
	
	
	/**
	 * 
	 * @Title:queryNotRead
	 * @Description: TODO
	 * @author yk
	 * @date 2018年9月6日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @return List<PageData>  
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> queryNotRead(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("SmsMapper.queryNotRead", pd);
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
		return (PageData)dao.findForObject("SmsMapper.findById", pd);
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
		dao.delete("SmsMapper.deleteAll", ArrayDATA_IDS);
	}
	
}
