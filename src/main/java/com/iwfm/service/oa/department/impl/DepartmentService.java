package com.iwfm.service.oa.department.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.iwfm.entity.oa.Department;
import com.iwfm.util.Jurisdiction;
import com.iwfm.util.Tools;
import com.iwfm.dao.DaoSupport;
import com.iwfm.entity.Page;
import com.iwfm.service.oa.department.DepartmentManager;
import com.iwfm.service.system.coderule.CodeRuleManager;
import com.iwfm.service.system.log.LogManager;
import com.iwfm.util.PageData;

/**
 * ClassName: DepartmentService 
 * @Description: 组织架构
 * @author yk
 * @date 2018年1月17日
 */
@Service("departmentService")
public class DepartmentService implements DepartmentManager{

	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	@Resource(name="codeRuleService")
	private CodeRuleManager codeRuleService;//
	
	@Resource(name="logService")
	private LogManager logService;
	
	/**
	 * @Title:save
	 * @Description: 组织架构保存
	 * @author yk
	 * @date 2018年1月17日
	 * @param @param pd
	 * @param @throws Exception   
	 * @throws
	 */
	public void save(PageData pd) throws Exception {
		dao.save("DepartmentMapper.save", pd);
		logService.save(Jurisdiction.getUsername(), "新增部门："+pd.getString("NAME"));
		String codeRuleType=pd.getString("codeRuleType");
		//保存成功后反写流水号
		if(StringUtils.isNotEmpty(codeRuleType)){
			codeRuleService.updateFormCodeSerialNum("oa_department", "BIANMA", codeRuleType);
		}
	}
	
	/**
	 * @Title:saveBatch
	 * @Description: 批量保存
	 * @author yk
	 * @date 2018年2月9日
	 * @param @param pdLst
	 * @param @throws Exception   
	 * @throws
	 */
	public void saveBatch(List<PageData> pdLst) throws Exception{
		for(int i=0;i<pdLst.size();i++){
			dao.save("DepartmentMapper.save", pdLst.get(i));
		}
	}
	

	/**
	 * @Title:edit
	 * @Description: 组织架构修改
	 * @author yk
	 * @date 2018年1月17日
	 * @param @param pd
	 * @param @throws Exception   
	 * @throws
	 */
	public void edit(PageData pd) throws Exception {
		dao.update("DepartmentMapper.edit", pd);
		logService.save(Jurisdiction.getUsername(), "修改部门："+pd.getString("NAME"));
		
	}

	/**
	 * @Title:delete
	 * @Description: 组织架构删除
	 * @author yk
	 * @date 2018年1月17日
	 * @param @param pd
	 * @param @throws Exception   
	 * @throws
	 */
	public void delete(PageData pd) throws Exception {
		dao.delete("DepartmentMapper.delete", pd);
	}

	/**
	 * @Title:list
	 * @Description: 列表
	 * @author yk
	 * @date 2018年1月17日
	 * @param @param page
	 * @param @return
	 * @param @throws Exception   
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listPage(Page page) throws Exception {
		return (List<PageData>)dao.findForList("DepartmentMapper.datalistPage", page);
	}
	
	/**
	 * @Title:querylistPage
	 * @Description: 部门列表（datagrid分页查询）
	 * @author yk
	 * @date 2018年2月9日
	 * @param @param page
	 * @param @return
	 * @param @throws Exception   
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> querylistPage(Page page) throws Exception{
		return (List<PageData>)dao.findForList("DepartmentMapper.querylistPage", page);
	}

	/**
	 * @Title:findById
	 * @Description: 根据id查询数据
	 * @author yk
	 * @date 2018年1月17日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @throws
	 */
	public PageData findById(PageData pd) throws Exception {
		return (PageData)dao.findForObject("DepartmentMapper.findById", pd);
	}

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
	public PageData findByName(PageData pd)throws Exception{
		return (PageData)dao.findForObject("DepartmentMapper.findByName", pd);
	}
	
	/**
	 * @Title:findByBianma
	 * @Description: 根据编码查询数据
	 * @author yk
	 * @date 2018年1月17日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @throws
	 */
	public PageData findByBianma(PageData pd) throws Exception {
		return (PageData)dao.findForObject("DepartmentMapper.findByBianma", pd);
	}
	
	
	/**
	 * @Title:listSubDepartmentByParentId
	 * @Description: 通过ID获取其子级列表
	 * @author yk
	 * @date 2018年1月18日
	 * @param @param parentId
	 * @param @return
	 * @param @throws Exception   
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<Department> listSubDepartmentByParentId(String parentId) throws Exception {
		return (List<Department>) dao.findForList("DepartmentMapper.listSubDepartmentByParentId", parentId);
	}
	
	/**
	 * @Title:listAllDepartment
	 * @Description: 获取所有数据并填充每条数据的子级列表(递归处理)
	 * @author yk
	 * @date 2018年1月18日
	 * @param @param parentId
	 * @param @return
	 * @param @throws Exception   
	 * @throws
	 */
	public List<Department> listAllDepartment(String parentId) throws Exception {
		List<Department> departmentList = this.listSubDepartmentByParentId(parentId);
		for(Department depar : departmentList){
			depar.setSubDepartment(this.listAllDepartment(depar.getDEPARTMENT_ID()));
		}
		return departmentList;
	}
	
	/**
	 * @Title:listAllDepartmentToSelect
	 * @Description: 获取所有数据并填充每条数据的子级列表(递归处理)下拉ztree用
	 * @author yk
	 * @date 2018年1月18日
	 * @param @param parentId
	 * @param @param zdepartmentPdList
	 * @param @return
	 * @param @throws Exception   
	 * @throws
	 */
	public List<PageData> listAllDepartmentToSelect(String parentId,List<PageData> zdepartmentPdList) throws Exception {
		List<PageData>[] arrayDep = this.listAllbyPd(parentId,zdepartmentPdList);
		List<PageData> departmentPdList = arrayDep[1];
		for(PageData pd : departmentPdList){
			this.listAllDepartmentToSelect(pd.getString("id"),arrayDep[0]);
		}
		return arrayDep[0];
	}
	
	/**
	 * @Title:listAllbyPd
	 * @Description: 获取所有数据并填充每条数据的子级列表(递归处理)下拉ztree用
	 * @author yk
	 * @date 2018年1月18日
	 * @param @param parentId
	 * @param @param zdepartmentPdList
	 * @param @return
	 * @param @throws Exception   
	 * @return List<PageData>[]  
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<PageData>[] listAllbyPd(String parentId,List<PageData> zdepartmentPdList) throws Exception {
		List<Department> departmentList = this.listSubDepartmentByParentId(parentId);
		List<PageData> departmentPdList = new ArrayList<PageData>();
		for(Department depar : departmentList){
			PageData pd = new PageData();
			pd.put("id", depar.getDEPARTMENT_ID());
			pd.put("parentId", depar.getPARENT_ID());
			pd.put("name", depar.getNAME());
			pd.put("icon", "static/images/user.gif");
			departmentPdList.add(pd);
			zdepartmentPdList.add(pd);
		}
		List<PageData>[] arrayDep = new List[2];
		arrayDep[0] = zdepartmentPdList;
		arrayDep[1] = departmentPdList;
		return arrayDep;
	}
	
	/**
	 * @Title:getDEPARTMENT_IDS
	 * @Description: 获取某个部门所有下级部门ID(返回拼接字符串 in的形式)
	 * @author yk
	 * @date 2018年1月18日
	 * @param @param DEPARTMENT_ID
	 * @param @return
	 * @param @throws Exception   
	 * @throws
	 */
	public String getDEPARTMENT_IDS(String DEPARTMENT_ID) throws Exception {
		DEPARTMENT_ID = Tools.notEmpty(DEPARTMENT_ID)?DEPARTMENT_ID:"0";
		List<PageData> zdepartmentPdList = new ArrayList<PageData>();
		zdepartmentPdList = this.listAllDepartmentToSelect(DEPARTMENT_ID,zdepartmentPdList);
		StringBuffer sb = new StringBuffer();
		sb.append("(");
		for(PageData dpd : zdepartmentPdList){
			sb.append("'");
			sb.append(dpd.getString("id"));
			sb.append("'");
			sb.append(",");
		}
		sb.append("'iwfm')");
		return sb.toString();
	}

	/**
	 * @Title:deleteAll
	 * @Description: 批量删除部门
	 * @author yk
	 * @date 2018年1月24日
	 * @param @param ArrayDATA_IDS
	 * @param @throws Exception   
	 * @throws
	 */
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		dao.delete("DepartmentMapper.deleteAll", ArrayDATA_IDS);
		logService.save(Jurisdiction.getUsername(), "删除部门："+ArrayDATA_IDS.toString());
	}

	/**
	 * @Title:listAll
	 * @Description: 获取所有部门
	 * @author yk
	 * @date 2018年2月2日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("DepartmentMapper.listAll", pd);
	}
}
