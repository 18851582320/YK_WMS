package com.iwfm.service.oa.staff.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Service;

import com.iwfm.dao.DaoSupport;
import com.iwfm.entity.Page;
import com.iwfm.service.oa.staff.StaffManager;
import com.iwfm.service.system.coderule.CodeRuleManager;
import com.iwfm.service.system.log.LogManager;
import com.iwfm.service.system.user.UserManager;
import com.iwfm.service.system.userRole.UserRoleManager;
import com.iwfm.util.Jurisdiction;
import com.iwfm.util.PageData;
import com.iwfm.util.UuidUtil;

/**
 * ClassName: StaffService 
 * @Description: 员工
 * @author yk
 * @date 2018年1月25日
 */
@Service(value="staffService")
public class StaffService implements StaffManager{
	
	@Resource(name="daoSupport")
	private DaoSupport dao;
	
	@Resource(name="codeRuleService")
	private CodeRuleManager codeRuleService;
	
	@Resource(name="logService")
	private LogManager logService;
	
	@Resource(name="userService")
	private UserManager userService;
	
	@Resource(name="userRoleService")
	private UserRoleManager userRoleService;
	
	
	
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
	public void save(PageData pd)throws Exception{
		
		PageData pdUser=new PageData();
		
		pdUser.put("USERNAME",pd.getString("USERNAME"));
		pdUser.put("PASSWORD",pd.getString("PASSWORD"));
		pdUser.put("ROLE_ID",pd.getString("ROLE_ID"));
		pdUser.put("NUMBER",pd.getString("BIANMA"));
		
		pdUser.put("USER_ID", UuidUtil.get32UUID());	//ID 主键
		pdUser.put("LAST_LOGIN", "");				//最后登录时间
		pdUser.put("IP", "");						//IP
		pdUser.put("STATUS", "0");					//状态
		pdUser.put("SKIN", "metro-seablue");        //mes默认皮肤
		pdUser.put("witSkin", "metro-wit-seablue"); //wit默认皮肤
		pdUser.put("RIGHTS", "");		
		pdUser.put("PASSWORD", new SimpleHash("SHA-1", pd.getString("USERNAME"), pd.getString("PASSWORD")).toString());	//密码加密
		
		dao.save("UserMapper.save", pdUser);
		logService.save(Jurisdiction.getUsername(), "新增系统用户："+pdUser.getString("USERNAME"));
		
		pd.put("USER_ID",pdUser.getString("USER_ID"));
		dao.save("StaffMapper.save", pd);
		logService.save(Jurisdiction.getUsername(), "新增员工："+pd.getString("NAME"));
		String codeRuleType=pd.getString("codeRuleType");
		//保存成功后反写流水号
		if(StringUtils.isNotEmpty(codeRuleType)){
			codeRuleService.updateFormCodeSerialNum("OA_STAFF", "BIANMA", codeRuleType);
		}
		
		String ROLE_ID=pd.getString("ROLE_ID");
		String USER_ID=pdUser.getString("USER_ID");
		if(StringUtils.isNotEmpty(ROLE_ID)){
			String roleIdArr[]=ROLE_ID.split(",");
			if(roleIdArr!=null && roleIdArr.length>0){
				PageData pdUserRole=new PageData();
				pdUserRole.put("userId",USER_ID);
				
				for(int i=0;i<roleIdArr.length;i++){
					pdUserRole.put("roleId",roleIdArr[i]);
					pdUserRole.put("userRoleId",UuidUtil.get32UUID());
					dao.save("UserRoleMapper.save", pdUserRole);
				}
			}
		}
	}
	
	
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
	public void saveRegister(PageData pd)throws Exception{
		
		PageData pdUser=new PageData();
		
		pdUser.put("USERNAME",pd.getString("USERNAME"));
		pdUser.put("PASSWORD",pd.getString("PASSWORD"));
		pdUser.put("ROLE_ID",pd.getString("ROLE_ID"));
		pdUser.put("NUMBER",pd.getString("BIANMA"));
		
		pdUser.put("USER_ID", UuidUtil.get32UUID());	//ID 主键
		pdUser.put("LAST_LOGIN", "");				//最后登录时间
		pdUser.put("IP", "");						//IP
		pdUser.put("STATUS", "0");					//状态
		pdUser.put("SKIN", "metro-seablue");        //mes默认皮肤
		pdUser.put("witSkin", "metro-wit-seablue"); //wit默认皮肤
		pdUser.put("RIGHTS", "");		
		pdUser.put("PASSWORD", new SimpleHash("SHA-1", pd.getString("USERNAME"), pd.getString("PASSWORD")).toString());	//密码加密
		
		dao.save("UserMapper.save", pdUser);
		
		pd.put("USER_ID",pdUser.getString("USER_ID"));
		dao.save("StaffMapper.save", pd);
		
		String ROLE_ID=pd.getString("ROLE_ID");
		String USER_ID=pdUser.getString("USER_ID");
		if(StringUtils.isNotEmpty(ROLE_ID)){
			String roleIdArr[]=ROLE_ID.split(",");
			if(roleIdArr!=null && roleIdArr.length>0){
				PageData pdUserRole=new PageData();
				pdUserRole.put("userId",USER_ID);
				
				for(int i=0;i<roleIdArr.length;i++){
					pdUserRole.put("roleId",roleIdArr[i]);
					pdUserRole.put("userRoleId",UuidUtil.get32UUID());
					dao.save("UserRoleMapper.save", pdUserRole);
				}
			}
		}
	}
	
	
	
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
	public void saveBatch(List<PageData> pdLst)throws Exception{
		PageData pd=new PageData();
		PageData pdUser=new PageData();
		for(int i=0;i<pdLst.size();i++){
			pd=pdLst.get(i);
			
			pdUser.put("USERNAME",pd.getString("USERNAME"));
			pdUser.put("PASSWORD","111111");
			pdUser.put("ROLE_ID",pd.getString("ROLE_ID"));
			pdUser.put("NUMBER",pd.getString("BIANMA"));
			
			pdUser.put("USER_ID", UuidUtil.get32UUID());	//ID 主键
			pdUser.put("LAST_LOGIN", "");				//最后登录时间
			pdUser.put("IP", "");						//IP
			pdUser.put("STATUS", "0");					//状态
			pdUser.put("SKIN", "metro-seablue");        //mes默认皮肤
			pdUser.put("witSkin", "metro-wit-seablue"); //wit默认皮肤
			pdUser.put("RIGHTS", "");		
			pdUser.put("PASSWORD", new SimpleHash("SHA-1", pd.getString("USERNAME"), pdUser.getString("PASSWORD")).toString());	//密码加密
			
			dao.save("UserMapper.save", pdUser);
			logService.save(Jurisdiction.getUsername(), "新增系统用户："+pdUser.getString("USERNAME"));
			
			pd.put("USER_ID",pdUser.getString("USER_ID"));
			dao.save("StaffMapper.save", pd);
			logService.save(Jurisdiction.getUsername(), "新增员工："+pd.getString("NAME"));
			String codeRuleType=pd.getString("codeRuleType");
			//保存成功后反写流水号
			if(StringUtils.isNotEmpty(codeRuleType)){
				codeRuleService.updateFormCodeSerialNum("OA_STAFF", "BIANMA", codeRuleType);
			}
			
			String ROLE_ID=pd.getString("ROLE_ID");
			String USER_ID=pdUser.getString("USER_ID");
			if(StringUtils.isNotEmpty(ROLE_ID)){
				String roleIdArr[]=ROLE_ID.split(",");
				if(roleIdArr!=null && roleIdArr.length>0){
					PageData pdUserRole=new PageData();
					pdUserRole.put("userId",USER_ID);
					
					for(int z=0;z<roleIdArr.length;z++){
						pdUserRole.put("roleId",roleIdArr[z]);
						pdUserRole.put("userRoleId",UuidUtil.get32UUID());
						dao.save("UserRoleMapper.save", pdUserRole);
					}
				}
			}
			
			
		}
	}
	
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
	public void delete(PageData pd)throws Exception{
		dao.delete("StaffMapper.delete", pd);
	}
	
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
	public void edit(PageData pd)throws Exception{
		dao.update("StaffMapper.edit", pd);
		logService.save(Jurisdiction.getUsername(), "修改员工："+pd.getString("NAME"));
		
		PageData pdUser=new PageData();
		pdUser.put("ROLE_ID",pd.getString("ROLE_ID"));
		pdUser.put("USER_ID",pd.getString("USER_ID"));
		
		dao.update("UserMapper.editRoleId", pdUser);
		
		String ROLE_ID=pd.getString("ROLE_ID");
		String USER_ID=pdUser.getString("USER_ID");
		if(StringUtils.isNotEmpty(ROLE_ID)){
			String roleIdArr[]=ROLE_ID.split(",");
			if(roleIdArr!=null && roleIdArr.length>0){
				PageData pdUserRole=new PageData();
				pdUserRole.put("userId",USER_ID);
				
				dao.delete("UserRoleMapper.deleteByUserId", pdUserRole);  //先删除角色信息，然后重新添加
				
				for(int i=0;i<roleIdArr.length;i++){
					pdUserRole.put("roleId",roleIdArr[i]);
					pdUserRole.put("userRoleId",UuidUtil.get32UUID());
					dao.save("UserRoleMapper.save", pdUserRole);
				}
			}
		}
		
	}
	
	
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
	public void editAppInfo(PageData pd)throws Exception{
		dao.update("StaffMapper.editAppInfo", pd);
	}
	
	
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
	@SuppressWarnings("unchecked")
	public List<PageData> listPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("StaffMapper.datalistPage", page);
	}
	
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
	@SuppressWarnings("unchecked")
	public List<PageData> querylistPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("StaffMapper.querylistPage", page);
	}
	
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
	@SuppressWarnings("unchecked")
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("StaffMapper.listAll", pd);
	}
	
	
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
	@SuppressWarnings("unchecked")
	public List<PageData> listByUserAndTrackLimitCode(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("StaffMapper.listByUserAndTrackLimitCode", pd);
	}
	
	/**
	 * 
	 * @Title: listByDept
	 * @Description: TODO
	 * @author yk
	 * @date: 2019年1月24日
	 * @param: @param pd
	 * @param: @return
	 * @param: @throws Exception   
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<PageData> listByDept(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("StaffMapper.listByDept", pd);
	}
	
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
	@SuppressWarnings("unchecked")
	public List<PageData> listForTrackLimit(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("StaffMapper.listForTrackLimit", pd);
	}
	
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
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("StaffMapper.findById", pd);
	}
	
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
	public PageData findByUserId(PageData pd)throws Exception{
		return (PageData)dao.findForObject("StaffMapper.findByUserId", pd);
	}
	
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
	public PageData findAppInfo(PageData pd)throws Exception{
		return (PageData)dao.findForObject("StaffMapper.findAppInfo", pd);
	}
	
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
	public PageData findByUSERNAME(PageData pd)throws Exception{
		return (PageData)dao.findForObject("StaffMapper.findByUSERNAME", pd);
	}
	
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
	public PageData findByIdCard(PageData pd)throws Exception{
		return (PageData)dao.findForObject("StaffMapper.findByIdCard", pd);
	}
	
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
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		
		dao.delete("UserRoleMapper.deleteByStaffId", ArrayDATA_IDS);  //删除用户角色信息
		dao.delete("UserMapper.deleteUserByStaffId", ArrayDATA_IDS);  //删除用户信息
		dao.delete("StaffMapper.deleteAll", ArrayDATA_IDS);
		logService.save(Jurisdiction.getUsername(), "删除员工："+ArrayDATA_IDS.toString());
		
	}
	
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
	public void userBinding(PageData pd)throws Exception{
		dao.update("StaffMapper.userBinding", pd);
	}
	
	/**
	 * @Title:findByBianMa
	 * @Description: 通过编码查找数据
	 * @author yk
	 * @date 2018年1月25日
	 * @param @param pd
	 * @param @return
	 * @param @throws Exception   
	 * @throws
	 */
	public PageData findByBianMa(PageData pd)throws Exception{
		return (PageData)dao.findForObject("StaffMapper.findByBianMa", pd);
	}
	
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
	public PageData findByName(PageData pd)throws Exception{
		return (PageData)dao.findForObject("StaffMapper.findByName", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> listPageSelect(Page page) throws Exception {
		return (List<PageData>)dao.findForList("StaffMapper.listPageSelect", page);
	}
	
	
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
	@SuppressWarnings("unchecked")
	public List<PageData> findByIdArray(String[] ArrayDATA_IDS)throws Exception{
		return (List<PageData>)dao.findForList("StaffMapper.findByIdArray", ArrayDATA_IDS);
	}
	
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
	@SuppressWarnings("unchecked")
	public List<PageData> findByNameArray(String[] ArrayDATA_IDS)throws Exception{
		return (List<PageData>)dao.findForList("StaffMapper.findByNameArray", ArrayDATA_IDS);
	}
	
}
