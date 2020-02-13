package com.iwfm.service.system.sysSet;

import java.util.List;

import com.iwfm.util.PageData;

/**
 * ClassName: SysSetManager 
 * @Description: 系统设置
 * @author yk
 * @date 2018年6月5日
 */
public interface SysSetManager {
	/**
	 * @Title: save
	 * @Description: 新增
	 * @author yk
	 * @date 2018年6月5日
	 * @param @param pd
	 * @param @throws Exception
	 * @return void
	 * @throws
	 */
	public void save(PageData pd)throws Exception;
	
	/**
	 * @Title: delete
	 * @Description: 删除
	 * @author yk
	 * @date 2018年6月5日
	 * @param @param pd
	 * @param @throws Exception
	 * @return void
	 * @throws
	 */
	public void delete(PageData pd)throws Exception;
	
	/**
	 * @Title: edit
	 * @Description: 修改
	 * @author yk
	 * @date 2018年6月5日
	 * @param @param pd
	 * @param @throws Exception
	 * @return void
	 * @throws
	 */
	public void edit(PageData pd)throws Exception;
	
	/**
	 * @Title: listAll
	 * @Description: 列表(全部)
	 * @author yk
	 * @date 2018年6月5日
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
	 * @date 2018年6月5日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception
	 * @return PageData
	 * @throws
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**
	 * @Title:findByFieldCode
	 * @Description: TODO
	 * @author yk
	 * @date 2018年7月20日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @return PageData  
	 * @throws
	 */
	public PageData findByFieldCode(PageData pd)throws Exception;
	
	/**
	 * @Title: deleteAll
	 * @Description: 批量删除
	 * @author yk
	 * @date 2018年6月5日
	 * @param @param ArrayDATA_IDS
	 * @param @throws Exception
	 * @return void
	 * @throws
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
}
