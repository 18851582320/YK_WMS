package com.iwfm.service.oa.department;

import java.util.List;

import com.iwfm.entity.oa.Department;
import com.iwfm.entity.Page;
import com.iwfm.util.PageData;

/**
 * ClassName: DepartmentManager 
 * @Description: 组织结构接口类
 * @author yk
 * @date 2018年1月17日
 */
public interface DepartmentManager {
	
	/**
	 * @Title:save
	 * @Description: 组织结构新增
	 * @author yk
	 * @date 2018年1月17日
	 * @param @param pd
	 * @param @throws Exception   
	 * @return void  
	 * @throws
	 */
	public void save(PageData pd) throws Exception;
	
	/**
	 * @Title:saveBatch
	 * @Description: 批量保存
	 * @author yk
	 * @date 2018年2月9日
	 * @param @param pdLst
	 * @param @throws Exception   
	 * @return void  
	 * @throws
	 */
	public void saveBatch(List<PageData> pdLst) throws Exception;
	
	/**
	 * @Title:edit
	 * @Description: 组织架构修改
	 * @author yk
	 * @date 2018年1月17日
	 * @param @param pd
	 * @param @throws Exception   
	 * @return void  
	 * @throws
	 */
	public void edit(PageData pd) throws Exception;
	
	/**
	 * @Title:
	 * @Description: TODO
	 * @author yk
	 * @date 2018年1月17日
	 * @param @param pd
	 * @param @throws Exception   
	 * @return void  
	 * @throws
	 */
	public void delete(PageData pd)throws Exception;
	
	/**
	 * @Title:list
	 * @Description: TODO
	 * @author yk
	 * @date 2018年1月17日
	 * @param @param page
	 * @param @return
	 * @param @throws Exception   
	 * @return List<PageData>  
	 * @throws
	 */
	public List<PageData> listPage(Page page) throws Exception;
	
	/**
	 * @Title:querylistPage
	 * @Description: 部门列表（datagrid分页查询）
	 * @author yk
	 * @date 2018年2月9日
	 * @param @param page
	 * @param @return
	 * @param @throws Exception   
	 * @return List<PageData>  
	 * @throws
	 */
	public List<PageData> querylistPage(Page page) throws Exception;
	
	/**
	 * @Title:findById
	 * @Description: 通过id获取数据
	 * @author yk
	 * @date 2018年1月17日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @return PageData  
	 * @throws
	 */
	public PageData findById(PageData pd) throws Exception;
	
	/**
	 * @Title:findByBianma
	 * @Description: 通过编码获取数据
	 * @author yk
	 * @date 2018年1月17日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @return PageData  
	 * @throws
	 */
	public PageData findByBianma(PageData pd)throws Exception;
	
	/**
	 * 
	 * @Title:findByName
	 * @Description: 通过名称获取数据
	 * @author yk
	 * @date 2018年2月12日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @return PageData  
	 * @throws
	 */
	public PageData findByName(PageData pd)throws Exception;
	
	/**
	 * @Title:listSubDepartmentByParentId
	 * @Description: 通过ID获取其子级列表
	 * @author yk
	 * @date 2018年1月18日
	 * @param @param parentId
	 * @param @return
	 * @param @throws Exception   
	 * @return List<Department>  
	 * @throws
	 */
	public List<Department> listSubDepartmentByParentId(String parentId) throws Exception;
	
	
	/**
	 * @Title:listAllDepartment
	 * @Description: 获取所有数据并填充每条数据的子级列表(递归处理)
	 * @author yk
	 * @date 2018年1月18日
	 * @param @param parentId
	 * @param @return
	 * @param @throws Exception   
	 * @return List<Department>  
	 * @throws
	 */
	public List<Department> listAllDepartment(String parentId) throws Exception;
	
	/**
	 * @Title:listAllDepartmentToSelect
	 * @Description: 获取所有数据并填充每条数据的子级列表(递归处理)下拉ztree用
	 * @author yk
	 * @date 2018年1月18日
	 * @param @param parentId
	 * @param @param zdepartmentPdList
	 * @param @return
	 * @param @throws Exception   
	 * @return List<PageData>  
	 * @throws
	 */
	public List<PageData> listAllDepartmentToSelect(String parentId, List<PageData> zdepartmentPdList) throws Exception;
	
	/**
	 * @Title:getDEPARTMENT_IDS
	 * @Description: 获取某个部门所有下级部门ID(返回拼接字符串 in的形式)
	 * @author yk
	 * @date 2018年1月18日
	 * @param @param DEPARTMENT_ID
	 * @param @return
	 * @param @throws Exception   
	 * @return String  
	 * @throws
	 */
	public String getDEPARTMENT_IDS(String DEPARTMENT_ID) throws Exception;
	
	/**
	 * @Title:deleteAll
	 * @Description: 批量删除部门
	 * @author yk
	 * @date 2018年1月24日
	 * @param @param ArrayDATA_IDS
	 * @param @throws Exception   
	 * @return void  
	 * @throws
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
	/**
	 * @Title:listAll
	 * @Description: 获取列表全部数据
	 * @author yk
	 * @date 2018年2月2日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @return List<PageData>  
	 * @throws
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
}
