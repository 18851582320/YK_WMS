package com.iwfm.service.system.log.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.iwfm.dao.DaoSupport;
import com.iwfm.entity.Page;
import com.iwfm.service.system.log.LogManager;
import com.iwfm.util.PageData;
import com.iwfm.util.Tools;
import com.iwfm.util.UuidUtil;

/**
 * ClassName: logService 
 * @Description: 系统日志记录
 * @author yk
 * @date 2017年8月11日
 */
@Service("logService")
public class LogService implements LogManager{
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	/**
	 * @Title: save
	 * @Description: 新增
	 * @author yk
	 * @date 2017年8月11日
	 * @param @param USERNAME
	 * @param @param CONTENT
	 * @param @throws Exception
	 * @return 
	 * @throws
	 */
	public void save(String USERNAME, String CONTENT)throws Exception{
		PageData pd = new PageData();
		pd.put("USERNAME", USERNAME);					//用户名
		pd.put("CONTENT", CONTENT);						//事件
		pd.put("LOG_ID", UuidUtil.get32UUID());		//主键
		pd.put("CZTIME", Tools.date2Str(new Date()));	//操作时间
		dao.save("LogMapper.save", pd);
	}
	
	/**
	 * @Title: delete
	 * @Description: 删除
	 * @author yk
	 * @date 2017年8月11日
	 * @param @param pd
	 * @param @throws Exception
	 * @return 
	 * @throws
	 */
	public void delete(PageData pd)throws Exception{
		dao.delete("LogMapper.delete", pd);
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	
	/**
	 * @Title: list
	 * @Description: 列表
	 * @author yk
	 * @date 2017年8月11日
	 * @param @param page
	 * @param @return
	 * @param @throws Exception
	 * @return 
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> querylistPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("LogMapper.querylistPage", page);
	}
	
	/**列表(全部)
	 * @param pd
	 * @throws Exception
	 */
	
	/**
	 * @Title: listAll
	 * @Description: 列表(全部)
	 * @author yk
	 * @date 2017年8月11日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception
	 * @return 
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("LogMapper.listAll", pd);
	}
	
	/**
	 * @Title: findById
	 * @Description: 通过id获取数据
	 * @author yk
	 * @date 2017年8月11日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception
	 * @return 
	 * @throws
	 */
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("LogMapper.findById", pd);
	}
	
	/**批量删除
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	
	/**
	 * @Title: deleteAll
	 * @Description: 批量删除
	 * @author yk
	 * @date 2017年8月11日
	 * @param @param ArrayDATA_IDS
	 * @param @throws Exception
	 * @return 
	 * @throws
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("LogMapper.deleteAll", ArrayDATA_IDS);
	}
}
