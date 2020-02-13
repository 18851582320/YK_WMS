
package com.iwfm.service.oa.duty.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.iwfm.dao.DaoSupport;
import com.iwfm.entity.Page;
import com.iwfm.service.oa.duty.DutyManager;
import com.iwfm.service.system.coderule.CodeRuleManager;
import com.iwfm.service.system.log.LogManager;
import com.iwfm.util.Jurisdiction;
import com.iwfm.util.PageData;

/**
 * ClassName: DutyService 
 * @Description: 职务
 * @author yk
 * @date 2018年3月1日
 */
@Service(value="dutyService")
public class DutyService implements DutyManager{
	
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	@Resource(name="codeRuleService")
	private CodeRuleManager codeRuleService;//编码规则
	
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
		dao.save("DutyMapper.save", pd);
		logService.save(Jurisdiction.getUsername(), "新增职务："+pd.getString("dutyCode"));
		String codeRuleType=pd.getString("codeRuleType");
		//保存成功后反写流水号
		if(StringUtils.isNotEmpty(codeRuleType)){
			codeRuleService.updateFormCodeSerialNum("OA_duty", "dutyCode", codeRuleType);
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
			dao.save("DutyMapper.save", pdLst.get(i));
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
		dao.delete("DutyMapper.delete", pd);
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
		dao.update("DutyMapper.edit", pd);
		logService.save(Jurisdiction.getUsername(), "修改职务："+pd.getString("dutyCode"));			
	}
	
	/**
	 * @Title:listPage
	 * @Description: 列表
	 * @author yk
	 * @date 2018年2月28日
	 * @param @param page
	 * @param @return
	 * @param @throws Exception   
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("DutyMapper.datalistPage", page);
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
		return (List<PageData>)dao.findForList("DutyMapper.querylistPage", page);
	}
	
	/**
	 * @Title:listAll
	 * @Description: 导出
	 * @author yk
	 * @date 2018年2月28日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("DutyMapper.listAll", pd);
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
		return (PageData)dao.findForObject("DutyMapper.findById", pd);
	}
	
	/**
	 * @Title:findByCode
	 * @Description: 通过编码获取数据
	 * @author yk
	 * @date 2018年2月28日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @throws
	 */
	public PageData findByCode(PageData pd) throws Exception {
		return (PageData)dao.findForObject("DutyMapper.findByCode", pd);
	}
	
	/**
	 * @Title:findByName
	 * @Description: TODO
	 * @author yk
	 * @date 2018年5月31日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @return PageData  
	 * @throws
	 */
	public PageData findByName(PageData pd)throws Exception{
		return (PageData)dao.findForObject("DutyMapper.findByName", pd);
	}
	
	
	/**
	 * @Title:listForSelect
	 * @Description: TODO
	 * @author yk
	 * @date 2018年3月1日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @return PageData  
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listForSelect(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("DutyMapper.listForSelect", pd);
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
		dao.delete("DutyMapper.deleteAll", ArrayDATA_IDS);
		logService.save(Jurisdiction.getUsername(), "删除职务："+ArrayDATA_IDS.toString());				
	}
	
}
