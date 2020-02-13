package com.iwfm.service.system.log;

import java.util.List;

import com.iwfm.entity.Page;
import com.iwfm.util.PageData;

/**
 * ClassName: logServiceManager 
 * @Description: 操作日志记录接口
 * @author yk
 * @date 2017年8月11日
 */
public interface LogManager {
	/**
	 * @Title: save
	 * @Description: 新增
	 * @author yk
	 * @date 2017年8月11日
	 * @param @param USERNAME
	 * @param @param CONTENT
	 * @param @throws Exception
	 * @return void
	 * @throws
	 */
	public void save(String USERNAME, String CONTENT)throws Exception;
	
	/**
	 * @Title: delete
	 * @Description: 删除
	 * @author yk
	 * @date 2017年8月11日
	 * @param @param pd
	 * @param @throws Exception
	 * @return void
	 * @throws
	 */
	public void delete(PageData pd)throws Exception;
	
	/**
	 * @Title: list
	 * @Description: 列表
	 * @author yk
	 * @date 2017年8月11日
	 * @param @param page
	 * @param @return
	 * @param @throws Exception
	 * @return List<PageData>
	 * @throws
	 */
	public List<PageData> querylistPage(Page page)throws Exception;
	
	/**
	 * @Title: listAll
	 * @Description: 列表(全部)
	 * @author yk
	 * @date 2017年8月11日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception
	 * @return List<PageData>
	 * @throws
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
	/**
	 * @Title: findById
	 * @Description: 通过id获取数据
	 * @author yk
	 * @date 2017年8月11日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception
	 * @return PageData
	 * @throws
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**
	 * @Title: deleteAll
	 * @Description: 批量删除
	 * @author yk
	 * @date 2017年8月11日
	 * @param @param ArrayDATA_IDS
	 * @param @throws Exception
	 * @return void
	 * @throws
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
}
