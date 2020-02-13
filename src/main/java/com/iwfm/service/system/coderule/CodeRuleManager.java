package com.iwfm.service.system.coderule;

import java.util.List;

import com.iwfm.entity.Page;
import com.iwfm.util.PageData;


/**
 * ClassName: CodeRuleManager 
 * @Description: 编码规则接口
 * @author yk
 * @date 2018年1月19日
 */
public interface CodeRuleManager {
	/**
	 * @Title: save
	 * @Description: 新增
	 * @author yk
	 * @date 2018年1月19日
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
	 * @date 2018年1月19日
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
	 * @date 2018年1月19日
	 * @param @param pd
	 * @param @throws Exception
	 * @return void
	 * @throws
	 */
	public void edit(PageData pd)throws Exception;
	
	/**
	 * @Title:listPage
	 * @Description: 列表
	 * @author yk
	 * @date 2018年1月19日
	 * @param @param page
	 * @param @return
	 * @param @throws Exception   
	 * @return List<PageData>  
	 * @throws
	 */
	public List<PageData> listPage(Page page)throws Exception;
	
	/**
	 * @Title:querylistPage
	 * @Description: 编码规则列表（datagrid分页查询）
	 * @author yk
	 * @date 2018年2月9日
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
	 * @date 2018年1月19日
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
	 * @date 2018年1月19日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception
	 * @return PageData
	 * @throws
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**
	 * @Title:findByTabAndFie
	 * @Description: 通过表名和字段名获取数据
	 * @author yk
	 * @date 2018年1月22日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @return PageData  
	 * @throws
	 */
	public PageData findByTabAndFie(PageData pd)throws Exception;
	
	/**
	 * @Title:getFormCodeRule
	 * @Description: 获取表单编码
	 * @author yk
	 * @date 2018年1月22日
	 * @param @param tableName
	 * @param @param fieldName
	 * @param @return
	 * @param @throws Exception   
	 * @return PageData  
	 * @throws
	 */
	public String[] getFormCodeByRule(String tableName,String fieldName)throws Exception;
	
	/**
	 * @Title:updateFormCodeSerialNum
	 * @Description: 更新表单字段流水号
	 * @author yk
	 * @date 2018年1月24日
	 * @param @param tableName
	 * @param @param fieldName
	 * @param @param codeRuleType
	 * @param @throws Exception   
	 * @return void  
	 * @throws
	 */
	public void updateFormCodeSerialNum(String tableName,String fieldName,String codeRuleType)throws Exception;
	
	/**
	 * @Title: deleteAll
	 * @Description: 批量删除
	 * @author yk
	 * @date 2018年1月19日
	 * @param @param ArrayDATA_IDS
	 * @param @throws Exception
	 * @return void
	 * @throws
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
}
