package com.iwfm.service.oa.staff;

import java.util.List;

import com.iwfm.entity.Page;
import com.iwfm.util.PageData;

/**
 * ClassName: StaffManager 
 * @Description: 员工接口
 * @author yk
 * @date 2018年1月25日
 */
public interface StaffManager {
	
	/**
	 * @Title:save
	 * @Description: 新增
	 * @author yk
	 * @date 2018年1月25日
	 * @param @param pd
	 * @param @throws Exception   
	 * @return void  
	 * @throws
	 */
	public void save(PageData pd)throws Exception;
	
	/**
	 * 
	 * @Title: saveRegister
	 * @Description: TODO
	 * @author yk
	 * @date: 2018年12月25日
	 * @param: @param pd
	 * @param: @throws Exception   
	 * @return: void  
	 * @throws:
	 */
	public void saveRegister(PageData pd)throws Exception;
	
	/**
	 * @Title:saveBatch
	 * @Description: 批量保存
	 * @author yk
	 * @date 2018年2月11日
	 * @param @param pd
	 * @param @throws Exception   
	 * @return void  
	 * @throws
	 */
	public void saveBatch(List<PageData> pdLst)throws Exception;
	
	/**
	 * @Title:delete
	 * @Description: 删除
	 * @author yk
	 * @date 2018年1月25日
	 * @param @param pd
	 * @param @throws Exception   
	 * @return void  
	 * @throws
	 */
	public void delete(PageData pd)throws Exception;
	
	/**
	 * @Title:edit
	 * @Description: 修改
	 * @author yk
	 * @date 2018年1月25日
	 * @param @param pd
	 * @param @throws Exception   
	 * @return void  
	 * @throws
	 */
	public void edit(PageData pd)throws Exception;
	
	/**
	 * 
	 * @Title: editAppInfo
	 * @Description: TODO
	 * @author: HB-PC-042
	 * @date: 2018年10月26日
	 * @param: @param pd
	 * @param: @throws Exception   
	 * @return: void  
	 * @throws:
	 */
	public void editAppInfo(PageData pd)throws Exception;
	
	/**
	 * @Title:listPage
	 * @Description: 列表
	 * @author yk
	 * @date 2018年1月25日
	 * @param @param page
	 * @param @return
	 * @param @throws Exception   
	 * @return List<PageData>  
	 * @throws
	 */
	public List<PageData> listPage(Page page)throws Exception;
	
	/**
	 * @Title:querylistPage
	 * @Description: 员工列表（datagrid分页查询）
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
	 * @Title:listAll
	 * @Description: 
	 * @author yk
	 * @date 2018年1月25日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @return List<PageData>  
	 * @throws
	 */
	public List<PageData> listAll(PageData pd)throws Exception;
	
	/**
	 * 
	 * @Title: listByUserAndTrackLimitCode
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年1月25日
	 * @param: @param pd
	 * @param: @return
	 * @param: @throws Exception   
	 * @return: List<PageData>  
	 * @throws:
	 */
	public List<PageData> listByUserAndTrackLimitCode(PageData pd)throws Exception;
	
	/**
	 * 
	 * @Title: listByDept
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年1月24日
	 * @param: @param pd
	 * @param: @return
	 * @param: @throws Exception   
	 * @return: List<PageData>  
	 * @throws:
	 */
	public List<PageData> listByDept(PageData pd)throws Exception;
	
	/**
	 * 
	 * @Title: listForTrackLimit
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年1月25日
	 * @param: @param pd
	 * @param: @return
	 * @param: @throws Exception   
	 * @return: List<PageData>  
	 * @throws:
	 */
	public List<PageData> listForTrackLimit(PageData pd)throws Exception;
	
	/**
	 * @Title:findById
	 * @Description: 通过id查找数据
	 * @author yk
	 * @date 2018年1月25日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @return PageData  
	 * @throws
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**
	 * 
	 * @Title: findByUserId
	 * @Description: TODO
	 * @author: HB-PC-042
	 * @date: 2018年10月26日
	 * @param: @param pd
	 * @param: @return
	 * @param: @throws Exception   
	 * @return: PageData  
	 * @throws:
	 */
	public PageData findByUserId(PageData pd)throws Exception;
	
	
	/**
	 * 
	 * @Title: findAppInfo
	 * @Description: TODO
	 * @author: HB-PC-042
	 * @date: 2018年10月26日
	 * @param: @param pd
	 * @param: @return
	 * @param: @throws Exception   
	 * @return: PageData  
	 * @throws:
	 */
	public PageData findAppInfo(PageData pd)throws Exception;
	
	
	/**
	 * @Title:findByUSERNAME
	 * @Description: 通过用户名查找数据
	 * @author yk
	 * @date 2018年2月24日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @return PageData  
	 * @throws
	 */
	public PageData findByUSERNAME(PageData pd)throws Exception;
	
	/**
	 * @Title:findByIdCard
	 * @Description: 通过卡号查找员工信息
	 * @author yk
	 * @date 2018年2月27日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @return PageData  
	 * @throws
	 */
	public PageData findByIdCard(PageData pd)throws Exception;
	
	/**
	 * @Title:deleteAll
	 * @Description: 批量删除
	 * @author yk
	 * @date 2018年1月25日
	 * @param @param ArrayDATA_IDS
	 * @param @throws Exception   
	 * @return void  
	 * @throws
	 */
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception;
	
	/**
	 * @Title:userBinding
	 * @Description: 绑定用户
	 * @author yk
	 * @date 2018年1月25日
	 * @param @param pd
	 * @param @throws Exception   
	 * @return void  
	 * @throws
	 */
	public void userBinding(PageData pd)throws Exception;
	
	/**
	 * @Title:findByBianMa
	 * @Description: TODO
	 * @author yk
	 * @date 2018年1月25日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @return PageData  
	 * @throws
	 */
	public PageData findByBianMa(PageData pd)throws Exception;
	
	/**
	 * 
	 * @Title: findByName
	 * @Description: TODO
	 * @author yk
	 * @date: 2018年10月19日
	 * @param: @param pd
	 * @param: @return
	 * @param: @throws Exception   
	 * @return: PageData  
	 * @throws:
	 */
	public PageData findByName(PageData pd)throws Exception;
	/**
	 * 
	 * @Title:listPage
	 * @Description: 人员选择查询页面
	 * @author dj
	 * @date 2018年2月8日
	 * @param @param page
	 * @param @return
	 * @param @throws Exception   
	 * @return List<PageData>  
	 * @throws
	 */
	public List<PageData> listPageSelect(Page page)throws Exception;
	
	/**
	 * 
	 * @Title: findByIdArray
	 * @Description: TODO
	 * @author yk
	 * @date: 2018年10月16日
	 * @param: @param ArrayDATA_IDS
	 * @param: @return
	 * @param: @throws Exception   
	 * @return: List<PageData>  
	 * @throws:
	 */
	public List<PageData> findByIdArray(String[] ArrayDATA_IDS)throws Exception;
	
	/**
	 * 
	 * @Title: findByNameArray
	 * @Description: TODO
	 * @author yk
	 * @date: 2018年10月19日
	 * @param: @param ArrayDATA_IDS
	 * @param: @return
	 * @param: @throws Exception   
	 * @return: List<PageData>  
	 * @throws:
	 */
	public List<PageData> findByNameArray(String[] ArrayDATA_IDS)throws Exception;
	
}
